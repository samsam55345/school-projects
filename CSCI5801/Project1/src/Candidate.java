import java.util.*;

/**
 * <h1>Candidate Class</h1>
 * The Candidate class represents each candidate in the election. Each candidate
 * has a name, a list of ballots, a count of the number of ballots, a value
 * to hold whether they are a winner or not.
 * 
 * @author  Jake Waro
 * 
 * */
public class Candidate {
	private String name;	// name of candidate
	private LinkedHashSet<Ballot> ballots;	// list of candidate's ballots
	private int voteCount;	// count of number of ballots
	private boolean isWinner;	// if the candidate is a winner

	/**
	 * Instantiates a candidate object.
	 * Initializes values for voteCount, ballots, and isWinner.
	 * @param name - This candidate's name. Should include whatever the
	 * 	respective name is from the header of the input files.
	 */
	Candidate(String name) {
		voteCount = 0;
		isWinner = false;
		ballots = new LinkedHashSet<Ballot>();

		try {
			this.name = name;
		} catch (IllegalArgumentException e) {
			throw e;
		}
	}

	/**
	 * Getter method to get the name.
	 * @return name - this candidate's name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Getter method to get the number of votes associated with this candidate.
	 * @return number of ballots assigned to this candidate.
	 */
	public int getVoteCount() {
		return voteCount;
	}

	/**
	 * Getter method to get the ballots assigned to this candidate.
	 * @return list of ballots assigned to this candidate.
	 */
	public LinkedHashSet<Ballot> getBallots() {
		return ballots;
	}

	/**
	 * Setter method to set the isWinner field to true;
	 */
	public void setWinner() {
		isWinner = true;
	}

	/**
	 * Getter method to get the value of if a candidate is a winner or not.
	 * @return isWinner - if the candidate is a winner or not.
	 */
	public boolean isWinner() {
		return isWinner;
	}

	/**
	 * This method adds a ballot object to the list of assigned ballots.
	 * @param ballot - a ballot object with vote rankings.
	 */
	public void addBallot(Ballot ballot) {
		ballots.add(ballot);
		voteCount++;
	}

	/**
	 * This method removes the ballots from a Candidate, and sets their number
	 * of votes to 0.
	 * @return the candidate's list of ballots before it is cleared.
	 */
	public LinkedHashSet<Ballot> removeBallots() {
		LinkedHashSet<Ballot> redistribute = new LinkedHashSet<Ballot>(ballots);
		ballots.clear();
		voteCount = 0;

		return redistribute;
	}

}
