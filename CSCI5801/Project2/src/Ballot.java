/**
 * <h1>Ballot Class</h1>
 * Ballot.java
 * This file will create a ballot object.
 * there will be x-n ballot objects, where x is the number
 * of lines in the csv files provided, and n is the number
 * of total csv files provided.
 * The ballot class will keep track of each ballot instantiated
 * with an ID number (labeled as ballotNum in the class)
 *
 * @author Sami Frank
 *
 */
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;

public class Ballot {
    private Map<Integer, Candidate> voteMap; // structure to hold votes and assign correct rank to candidates
    private static int ballotCount = 1; // keeps track of number of ballots created.
    private int ballotNum;


    /**
     * The ballot constructor takes in the candidates on the ballot
     * and a vote line from a csv file.
     * The ballotNum(or ID) is set.
     * The vote string is parsed into a list and then places
     * into a voteMap where the key is the 'rank' or 1 for plurality
     * and the value is the candidate object that refers to that rank
     * After the voteMap is set, ballotCount is incremented by one to be
     * able to account for the next incoming ballot.
     * @param candidates Takes a LinkedHashSet of candidate objects defined in
     *                   election.
     * @param votes Takes a string which is a line from a read CSV that indicated a ballot.
     * @return Ballot
     */
    public Ballot(LinkedHashSet<Candidate> candidates, String votes) {
        this.ballotNum = ballotCount;
        this.voteMap = new HashMap<>();
        String voteListSTR[] = votes.split(",", -1);
        int voteList[] = new int[voteListSTR.length];
        for(int i = 0; i < voteListSTR.length; i++){
            if(voteListSTR[i].equals("") || voteListSTR[i] == null ){
                voteList[i] = 0;
            }
            else{
                voteList[i] = Integer.parseInt(voteListSTR[i]);
            }
        }
        Iterator<Candidate> candidateIterator = candidates.iterator();
        int voteListCount = 0;
        while(candidateIterator.hasNext()) {
            Candidate next = candidateIterator.next();
            if (voteList[voteListCount] != 0) {
                this.voteMap.put(voteList[voteListCount], next);
            }
            voteListCount++;
        }
        // increase BallotCount
        ballotCount++;
    }

    /**
     * A method to get a map representation of a ballot that has been created.
     * @return Map This returns a map representation of the vote
     * string passed into the ballot constructor.
     */
    public Map<Integer, Candidate> getVotes(){
        return voteMap;
    }
    /**
     * A method to get a ballot instances ID.
     * @return int This returns a ballot instances ID number. This
     * number is used to keep track of when the ballot was created, and
     * for the audit file to give a description of which ballot the file
     * is referring to.
     */
    public int getBallotNum(){
        return this.ballotNum;
    }
}
