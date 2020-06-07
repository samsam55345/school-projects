import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * <h1>STV Class</h1>
 * The STV class calculates the result of an election when
 * STV is the chosen algorithm. It calculates the Droop quota
 * and distributes votes according to the STV algorithm,
 * choosing winners and losers to add to the lists in Election
 * along the way.
 *
 * @author  Allison Miller
 *
 * */
public class STV implements VotingAlgorithm{

    private Election election;  // Election object, used to access data fields
                                // such as winners and losers
    private LinkedHashSet<Ballot> ballots;  // list of ballots currently being distributed
    private int droop;                 // calculated Droop quota
    private Stack<Candidate> voteOrder; // keep track of order in which candidates
                                        // received their first ballot for tie-breaking purposes
    private int numCandidates;  // total number of candidates in the election, used
                                // for distributing votes

    /**
     * STV's constructor sets the class variables,
     * calculating the Droop quota and getting the
     * overall list of ballots from the Election
     * parameter for initial distribution.
     * @param e - Election object that contains
     *              information necessary for
     *              STV to process the election
     */
    public STV(Election e) {
        this.election = e;
        ballots = election.getBallotList();
        droop = calcDroopQuota();
        voteOrder = new Stack<>();
        numCandidates = election.candidates.size();
    }

    /**
     * Calculates the Droop quota using the formula:
     * floor(numBallots/(numSeats+1)) + 1
     * If the number of seats is negative, defaults to 1.
     * @return calculated Droop quota
     */
    public int calcDroopQuota() {
        // default to prevent divisions by 0
        if (election.numSeats < 0) return 1;

        // Java performs integer division with ints, so no separate floor operation is required here
        int droopQuota = (ballots.size()/(election.numSeats + 1)) + 1;
        election.droopQuota = droopQuota;
        return droopQuota;
    }

    // make public for testing
    /**
     * Shuffles the list of ballots to avoid bias towards
     * early voters. Utilizes Collections.shuffle.
     */
    private void shuffle() {
        if (election.shuffle) {

            // log original ballot order to audit file
            String order = "Order before shuffle: ";
            for (Ballot b : ballots) order += b.getBallotNum() + " ";
            election.writeToAudit(order);

            // transfer ballots to a list since Collections.shuffle doesn't work with LinkedHashSets
            LinkedList<Ballot> ballotList = new LinkedList();
            for (Ballot b : ballots) ballotList.add(b);
            ballots.clear();

            // shuffle ballots multiple times to avoid a predictable order
            for (int i = 0; i < 1000; i++) {
                Collections.shuffle(ballotList);
            }

            // transfer ballots back to the original ballots variable
            for (Ballot b : ballotList) ballots.add(b);

            // write new order to audit file
            order = "Order after shuffle: ";
            for (Ballot b : ballots) order += b.getBallotNum() + " ";
            election.writeToAudit(order);
        }
    }

    // make public for testing
    /**
     * Distributes the current set of ballots among
     * the remaining candidates. Finds the highest
     * ranked candidate for each ballot that has not
     * yet been declared a winner or a loser and
     * assigns that ballot to them.
     * <p>
     *     If the ballot is the first that the
     *     candidate has received, adds them to the
     *     tie-breaking stack.
     * </p>
     * <p>
     *     If the additional ballot causes the candidate
     *     to reach the Droop quota, they are removed
     *     from the list of candidates and added to the
     *     list of winners.
     * </p>
     * <p>
     *     If all of the ranked candidates on the
     *     ballot have already been declared winners or
     *     losers, nothing is done with the ballot.
     * </p>
     */
    public void distributeVotes() {
        for (Ballot b : ballots) {
            // start with rank 1
            int i = 1;
            Candidate person = null;

            // find highest ranked candidate that still hasn't won or lost (still in candidates)
            while (i <= numCandidates) {
            	if (b.getVotes().containsKey(i)) {
            		person = b.getVotes().get(i);
            		if (election.candidates.contains(person)) {
            			i = numCandidates+1;
            			break;
            		}
            		person = null;
            		i++;
            	} else {
            		break;
            	}
            }

            // if the candidate exists, give them the ballot
            if (person != null) {
	            person.addBallot(b);

                String description = "Ballot number " + b.getBallotNum() + " was distributed to: " + person.getName();
                election.writeToAudit(description);
	
	            // record order of first votes
	            if (!voteOrder.contains(person)) voteOrder.push(person);
	
	            // if the candidate has reached droop, remove them and add them to the winners list
	            if (person.getVoteCount() >= droop) {
                    String desc = person.getName() + " is declared a winner with " + person.getVoteCount() + " votes";
                    election.writeToAudit(desc);

	                election.winners.add(person);
	                election.candidates.remove(person);
	            }
            }
        }
    }

    // make public for testing
    /**
     * Chooses the loser of the current round of vote
     * distribution. If there are no candidates,
     * returns null.
     * <p>
     *     If multiple candidates are tied for the
     *     lowest vote count, breakTie chooses the
     *     loser based on whoever received their
     *     first vote last.
     * </p>
     * @return The candidate newly declared as a loser
     *      for the current round of vote distribution
     */
    private Candidate getLoser() {
        // find lowest vote count among all candidates
        Candidate loser = null;
        for (Candidate c : election.candidates) {
            if (loser == null || c.getVoteCount() < loser.getVoteCount()) loser = c;
        }

        // find all candidates with that vote count
        List<Candidate> losers = new LinkedList<Candidate>();
        for (Candidate c : election.candidates) {
            if (c.getVoteCount() == loser.getVoteCount()) losers.add(c);
        }

        // choose a loser from the candidates with the lowest votes
        Candidate ret = breakTie(losers);
	    if (ret != null) {
	        String desc = ret.getName() + " was declared to be a loser with " + ret.getVoteCount() + " ballots to redistribute";
            this.election.writeToAudit(desc);
	    }
        return ret;
    }

    // make public for testing
    /**
     * Chooses the overall loser of this round of
     * vote distribution from a list of tied losers.
     * Returns null if the list of losers is empty.
     * <p>
     *     If the losers all have zero votes, the
     *     loser will be chosen at random.
     * </p>
     * <p>
     *     If the potential losers have votes, the loser
     *     will be the candidate who received their first
     *     vote last among the other potential losers.
     * </p>
     * @param losers - List of tied potential losers.
     *               One of these candidates will be
     *               returned as the loser.
     * @return Candidate who lost the tie break and
     *          will be declared as a loser this round.
     */
    private Candidate breakTie(List<Candidate> losers) {
        if (losers.size() == 0) return null;

        String desc = "A tie is being broken between ";
        for (Candidate c : losers) {
            desc += c.getName() + " ";
        }
        election.writeToAudit(desc);

        if (losers.size() == 1) return losers.get(0);
        else { // find the first loser in the stack and return them
            while(!voteOrder.empty()) {
                Candidate loser = voteOrder.pop();
                if (losers.contains(loser)) return loser;
            }
        }

        // if none of the losers received a vote (aka weren't in voteOrder), choose loser randomly
        if (losers.size() > 0) {
            for (int i = 0; i < 100; i++) {
                Collections.shuffle(losers);
            }
            return losers.get(0);
        }

        return null;
    }

    /**
     * Evaluates the election. Shuffles the ballots
     * if desired, then begins the distribution loop.
     * For each round, evaluate distributes the current
     * list of ballots (and selects winners if any were
     * found, as described in distributeVotes), removes
     * the loser, and stores their ballots for redistribution
     * in the next round.
     * <p>
     *     If any seats are left open after all candidates
     *     have been declared winners or losers, evaluate
     *     fills the winners list with the most recent losers.
     * </p>
     */
    public void evaluate() {
        shuffle();
        while (election.candidates.size() > 0) {
            distributeVotes();

            Candidate loser = getLoser();
            if (loser == null) break;
            election.losers.push(loser);
            ballots = loser.removeBallots();
            election.candidates.remove(loser);
        }

        while (election.winners.size() < election.numSeats && election.losers.size() > 0) {
            String desc = election.losers.peek().getName() + " was chosen as a winner due to additional empty seats";
            election.writeToAudit(desc);
            election.winners.add(election.losers.pop());
        }
    }

    // testing purposes only - uncomment to allow testing
//    public Election getElection() {
//        return election;
//    }
//    public Stack<Candidate> getVoteOrder() {
//        return voteOrder;
//    }
//    public int getDroop() {
//        return droop;
//    }
//    public LinkedHashSet<Ballot> getBallots() {
//        return ballots;
//    }
}
