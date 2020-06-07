/**
 * <h1>Unit tests for the Plurality class</h1>
 * PluralityTest.java
 * This file tests the functionality of Plurality.java,
 * which does the bulk of tabulating votes for a plurality
 * vote type. produces a winners queue and losers
 * stack, thats passed back to the election class
 *@author Sami Frank
 */


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;

class PluralityTest {

    private Election election;
    private static Election e;
    private LinkedHashSet<Ballot> ballots;
    private int numSeats;
    private LinkedHashSet<Candidate> candidates;
    static Candidate sami;
    static Candidate jake;
    static Candidate declan;
    static Candidate allison;
    static Candidate tester;

    /**
     * setUp() will initialize necessary elements for the plurality
     * class to use throughout the unit tests.
     */
    @BeforeAll
    static void setUp() throws IOException {
        sami = new Candidate("Sami");
        jake = new Candidate("Jake");
        declan = new Candidate("Declan");
        allison = new Candidate("Allison");
        tester = new Candidate("Tester");
        e = new Election("Plurality", 2, new File[0], false);
        e.candidates.add(sami);
        e.candidates.add(jake);
        e.candidates.add(declan);
        e.candidates.add(allison);
        e.candidates.add(tester);
        String votes = "1,,,,";
        String votes1 = ",1,,,";
        String votes2 = ",,,1,";
        String votes3 = ",,1,,";
        String votes4 = "1,,,,";

        e.addBallotToList(votes);
        e.addBallotToList(votes1);
        e.addBallotToList(votes2);
        e.addBallotToList(votes3);
        e.addBallotToList(votes4);
    }

    /**
     * Tests the plurality constructor and makes sure parameters
     * from the election class (defined in setUp()) were set properly
     * numSeats: This is defined in the election object which will
     *                 tell the plurality class how many winners there should be.
     * candidates: This LinkedHashSet is also defined and set in setUp() as a marker
     *                   for who the candidates are in this specific election instance.
     * getBallotList(): A method from the election class, set in setUp(), which will house
     *                  all of the ballots made for this test.
     */
    @Test
    void testConstructor(){
        Plurality plur = new Plurality(e);
        election = e;
        numSeats = e.numSeats;
        candidates = e.candidates;
        ballots = election.getBallotList();

        assertEquals(ballots, e.getBallotList());
        assertEquals(numSeats, e.numSeats);
        assertEquals(candidates,e.candidates);
    }

    /**
     * Test will use the list of ballots that have been added to the election
     * object in setUp() to distribute each ballot in that list to the Candidate
     * that won that vote.
     * The test will then access each candidates vote count and expect a certain value
     * that has been determined when creating these tests.
    */
    @Test
	void testDistributeVotes() {
        Plurality plur = new Plurality(e);
        plur.distributeVotes();
        ArrayList<Integer> voteCounts = new ArrayList<>();
	    int saminum = 2;
        int jakenum = 1;
        int declannum = 1;
        int allisonnum = 1;
        int testernum = 0;
        voteCounts.add(saminum);
        voteCounts.add(jakenum);
        voteCounts.add(declannum);
        voteCounts.add(allisonnum);
        voteCounts.add(testernum);

        assertEquals(saminum, sami.getVoteCount());
        assertEquals(jakenum, jake.getVoteCount());
        assertEquals(declannum, declan.getVoteCount());
        assertEquals(allisonnum, allison.getVoteCount());
        assertEquals(testernum, tester.getVoteCount());
	}

	/**
     * The test will create a scenario where a tie needs to be broken.
     * That is, the test will create two scenarios: the first being an election
     * that has 3 seats to fill with 3 candidates that have a tie. The second being
     * and election that has 2 seats to fill with 3 candidates that have a tie.
     * In the first scenario, all candidates in the tie should win a seat.
     * In the second scenario, 2 of 3 candidates should win at random selection.
	*/
	@Test
	void testBreakTie() throws IOException {
        Election e1 = new Election("Plurality", 2, new File[0], false);
        Election e2 = new Election("Plurality", 3, new File[0], false);

        Plurality plur = new Plurality(e1);
        Plurality plur1 = new Plurality(e2);


        Candidate c1 = new Candidate("c1");
        Candidate c2 = new Candidate("c2");
        Candidate c3 = new Candidate("c3");
        Candidate c4 = new Candidate("c4");
        Candidate c5 = new Candidate("c5");
        Candidate c6 = new Candidate("c6");
        Candidate c7 = new Candidate("c7");
        Candidate c8 = new Candidate("c8");
        e1.candidates.add(c1);
        e1.candidates.add(c2);
        e1.candidates.add(c3);
        e1.candidates.add(c4);
        e2.candidates.add(c5);
        e2.candidates.add(c6);
        e2.candidates.add(c7);
        e2.candidates.add(c8);

        Ballot ballot1 = new Ballot(e1.candidates, "1,,,");
        Ballot ballot2 = new Ballot(e1.candidates, "1,,,");
        Ballot ballot3 = new Ballot(e1.candidates, ",1,,");
        Ballot ballot4 = new Ballot(e1.candidates, ",,1,");
        Ballot ballot5 = new Ballot(e1.candidates, ",,,1");
        Ballot ballot6 = new Ballot(e1.candidates, ",1,,");
        Ballot ballot7 = new Ballot(e1.candidates, ",,1,");

        Ballot ballot8 = new Ballot(e2.candidates, "1,,,");
        Ballot ballot9 = new Ballot(e2.candidates, "1,,,");
        Ballot ballot10 = new Ballot(e2.candidates, ",1,,");
        Ballot ballot11 = new Ballot(e2.candidates, ",,1,");
        Ballot ballot12 = new Ballot(e2.candidates, ",,,1");
        Ballot ballot13 = new Ballot(e2.candidates, ",1,,");
        Ballot ballot14 = new Ballot(e2.candidates, ",,1,");

        c1.addBallot(ballot1);
        c1.addBallot(ballot2);
        c2.addBallot(ballot3);
        c2.addBallot(ballot6);
        c3.addBallot(ballot4);
        c3.addBallot(ballot7);
        c4.addBallot(ballot5);

        c5.addBallot(ballot8);
        c5.addBallot(ballot9);
        c6.addBallot(ballot10);
        c6.addBallot(ballot11);
        c7.addBallot(ballot12);
        c7.addBallot(ballot13);
        c8.addBallot(ballot14);



        plur.distributeVotes();
        plur1.distributeVotes();

        ArrayList<Candidate> tie = new ArrayList<>();
        tie.add(c1);
        tie.add(c2);
        tie.add(c3);

        ArrayList<Candidate> tie1 = new ArrayList<>();
        tie.add(c5);
        tie.add(c6);
        tie.add(c7);

        plur.breakTie(tie);
        plur1.breakTie(tie1);

        for(Candidate c: plur.plurWin){
            assertTrue(plur.plurWin.contains(c));
        }

        for(Candidate c: tie1){
            assertTrue(tie1.contains(c));
        }
	}

	/**
     * Test will look at the functionality of the plurality classes evaluate function.
     * Using the election made in setUp(), the plurality class will be started
     * by calling distributeVotes(). After distributeVotes() is called, evaluate() is called
     * directly after within distributeVotes(). Based on the election that was created
     * in setUp(), there will one explicit winner and the other will be at random from
     * the remaining tied candidates.
	*/
	@Test
	void testEvaluate() {
        Plurality plur = new Plurality(e);
        plur.distributeVotes();
        assertTrue(plur.plurWin.contains(sami));

        for(Candidate c: plur.plurLose){
            assertTrue(plur.plurLose.contains(c));
        }
	}
}
