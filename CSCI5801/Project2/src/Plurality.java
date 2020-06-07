import java.util.*;

/**
 * <h1>Plurality Class</h1>
 * This file does the bulk of tabulating votes from the
 * election class when the plurality vote type is selected
 * and produces a winners queue and losers
 * stack, which is updated for the election class
 * 
 * @author Sami Frank
 * 
 */

public class Plurality implements VotingAlgorithm{
    private Election election; // Election instance that will be set by the constructor.
    private LinkedHashSet<Ballot> ballots; // will store the votes list from the election instance
    private static HashMap<Candidate, Integer> finalVoteCounts = new HashMap<>(); // formats Map after votes have been distributed
    private static ArrayList<Candidate> sortedCandidates = new ArrayList<>(); // sorts finalVoteCounts into a list in descending order of votes.
    private int curNumSeats;
    private ArrayList<Candidate> tie = new ArrayList<>(); // List to keep track of candidate ties until a decision has been made
    protected ArrayList<Candidate> plurWin = new ArrayList<>(); // A list to keep track of winners before its sent off to Election to produce results.
    protected ArrayList<Candidate> plurLose = new ArrayList<>(); // A list to keep track of losers before its sent off to Election to produce results.


    /**
     * Plurality constructor. It takes in a new election (type plurality),
     * and retrieves the election's ballots, and number of seats to fill.
     *
     * @param e - the election object which provides information on the
     *          election needed by the Plurality instance to compute the election.
     */
    public Plurality(Election e){
        this.election = e;
        this.curNumSeats = e.numSeats;
        ballots = election.getBallotList();
    }

    /**
     * This method will take a the list of ballots that was set in the constructor.
     * The method will then assign ballots to their respective candidate objects.
     * This method also will create a HashMap called finalVoteCounts that will store
     * a Candidate object as the key and their total vote count as the value.
     * finalVoteCounts will be used in Pluralities evaluate() method.
     * @return Nothing.
     */
    public void distributeVotes(){
        for(Ballot b: ballots){

            Candidate cand = b.getVotes().get(1);
            cand.addBallot(b);
            String description = "Ballot number " + b.getBallotNum() + " was distributed to: " + cand.getName();
            this.election.writeToAudit(description);
        }
        for(Candidate c : election.candidates){
            finalVoteCounts.put(c, c.getVoteCount());
            String desc = "Final vote count for " + c.getName() + " is: " + c.getVoteCount();
            this.election.writeToAudit(desc);
        }
        evaluate();
    }

    /**
     * breakTie will take in a list of Candidate objects who have been determined
     * to have a tie in their total vote counts. This method will evaluate how many
     * available seats there currently are and if there are enough seats left,
     * all candidates in the list would be added to the winners list, if there aren't
     * enough seats available for all candidates in the list, the list will shuffle
     * and candidates will get randomly selected for the seat until the list is empty
     * or there are no more seats to fill, whichever comes first.
     * @return Nothing.
     */
    protected void breakTie(ArrayList<Candidate> tie){
        if(tie.size() < curNumSeats){
            for(Candidate cand: tie){
                plurWin.add(cand);
                String name = cand.getName();
                String desc = name + " is declared a winner";
                this.election.writeToAudit(desc);
                curNumSeats--;
            }
            // remove all candidates in tie, since they have been
            // declared winners
            tie.clear();
        }
        else{
            Collections.shuffle(tie);
            // Add tied candidates to winners list until there are no
            // more tied candidates or no more seats to fill, whichever
            // comes first.
            while (tie.size() > 0 && curNumSeats > 0){
                plurWin.add(tie.get(0));
                String name = tie.get(0).getName();
                String desc = name + " is declared a winner";
                this.election.writeToAudit(desc);
                tie.remove(0);
                curNumSeats--;
            }
        }
    }

    /**
     * evaluate() will sort the finalVoteCounts HashMap in descending order into an ArrayList sortedCandidates.
     * The method then iterates over the sortedCandidates list. It will check two candidates vote count.
     * If the first candidate to be looked at has a higher vote count than the second
     * candidate to be looked at, the first candidate would win, if there are seats available.
     * If both candidates have the same vote count, they're both added to a tie list. breakTie will
     * be called once it has been determined that there isn't another candidate tied with candidates
     * currently in the tie list.
     * After the candidates have been fully evaluated, the winners and losers are set in the
     * election class.
     * @return Nothing.
     * */
    public void evaluate(){
        // sorted array of candidates in descending order of total votes.
        finalVoteCounts.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> sortedCandidates.add(x.getKey()));
        Candidate cand1;
        Candidate cand2;
        int cand1Ballots;
        int cand2Ballots;
        // looks at candidate vote counts to determine a win or tie.
        for(int i = 0; i < sortedCandidates.size()-1; i++){
            if (curNumSeats > 0){
                cand1 = sortedCandidates.get(i);
                cand2 = sortedCandidates.get(i+1);
                cand1Ballots = cand1.getVoteCount();
                cand2Ballots = cand2.getVoteCount();
                // a tie, add to tie list
                if(cand1Ballots == cand2Ballots){
                    if(!tie.contains(cand1)){
                        tie.add(cand1);
                    }
                    if(!tie.contains(cand2)) {
                        tie.add(cand2);
                    }
                }
                //not a tie
                else{
                    // check if there has been a tie, and that there are still seats
                    // available, if that's the case, breakTie
                    if(tie.size() > 0 && curNumSeats > 0){
                        String candidateTie = "";
                        for (Candidate c: tie) {
                            candidateTie = candidateTie + c.getName() + " ";
                        }
                        String desc = "A tie is being broken between " + candidateTie;
                        this.election.writeToAudit(desc);
                        breakTie(tie);
                    }
                    // if there is not a tie to be dealt with and there are
                    // seats available and not already a winner, make them a winner
                    if(curNumSeats > 0 && !plurWin.contains(cand1)){
                        plurWin.add(cand1);
                        String cand1Win = cand1.getName();
                        String desc = cand1Win + " won a seat in the election";
                        this.election.writeToAudit(desc);
                        curNumSeats--;
                    }
                }

            }
        }
        // looked through all candidates, but there are still
        // seats available and a tie needs to be broken
        if(tie.size() > 0 && curNumSeats > 0){
            String candidateTie = "";
            for (Candidate c: tie) {
                candidateTie = candidateTie + c.getName() + " ";
            }
            String desc = "A tie is being broken between " + candidateTie;
            this.election.writeToAudit(desc);
            breakTie(tie);
        }
        // no tie, still seats to be filled, add last candidate to winner
        if(tie.size() < 1 && curNumSeats > 0){
            if(!plurWin.contains(sortedCandidates.get(sortedCandidates.size()-1))){
                plurWin.add(sortedCandidates.get(sortedCandidates.size()-1));
                curNumSeats--;
                String cand1Win = sortedCandidates.get(sortedCandidates.size()-1).getName();
                String desc = cand1Win + " won a seat in the election";
                this.election.writeToAudit(desc);
            }
        }
        // add any candidate not in plurWin to plurLose
        for(Candidate cand: sortedCandidates){
            if(!plurWin.contains(cand)){
                plurLose.add(cand);
                String name = cand.getName();
                String desc = name + " lost the election";
                this.election.writeToAudit(desc);
            }
        }
        // set the elections winners queue
        for(Candidate cand: plurWin){
            election.winners.add(cand);
        }
        // set the elections losers stack
        for(Candidate cand: plurLose){
            election.losers.push(cand);
        }
    }
}
