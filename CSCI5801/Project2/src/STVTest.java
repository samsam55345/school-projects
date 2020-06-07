import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.PrintWriter;
import java.util.LinkedHashSet;
import java.util.Stack;
import java.util.LinkedList;

/**
 * <h1>STVTest Class</h1>
 * Contains unit tests for testing STV.
 *
 * @author Allison Miller
 *
 */
class STVTest {

	/**
	 * Checks that the STV constructor properly initializes
	 * all of STV's instance variables.
	 */
	@Test
	void test_constructor() {
		Election elect = null;
		try {
			elect = new Election("stv", 4, new File[0], false);
		} catch(Exception e) {
			System.out.println(e);
		}

		// add 50 ballots
		for (int i = 0; i < 50; i++) {
			elect.addBallotToList("1,2,3,4");
		}

		STV stv = new STV(elect);
		LinkedHashSet<Ballot> test_ballots = elect.getBallotList();
		assertEquals(test_ballots, stv.getElection().getBallotList());

		assertEquals(4,elect.getNumSeats());
		assertEquals(11, stv.getDroop());
		assertTrue(stv.getVoteOrder()!=null);

	}

	/**
	 * Checks if the Droop quota is correctly calculated for
	 * a low number of ballots and a variety of seat numbers.
	 */
	@Test
	void test_calcDroopQuota_50() {
		Election elect = null;
		try {
			elect = new Election("stv", 1, new File[0], false);
		} catch(Exception e) {}

		// add 50 ballots to start
		for (int i = 0; i < 50; i++) {
			elect.addBallotToList("1,2,,");
		}

		STV stv = new STV(elect);

		// test with 1 seat
		assertEquals(26, stv.calcDroopQuota());

		// test with 5 seats
		stv.getElection().numSeats = 5;
		assertEquals(9, stv.calcDroopQuota());

		// test with 10 seats (max num. candidates)
		stv.getElection().numSeats = 10;
		assertEquals(5, stv.calcDroopQuota());

		// test with 0 seats
		stv.getElection().numSeats = 0;
		assertEquals(51, stv.calcDroopQuota());

        // test with -1 seats
        stv.getElection().numSeats = -1;
        assertEquals(1, stv.calcDroopQuota());
	}

	/**
	 * Checks if the Droop quota was correctly calculated for
	 * the maximum number of ballots (100.000) and a variety
	 * of seat numbers.
	 */
	@Test
	void test_calcDroopQuota_max() {
		Election elect = null;
		try {
			elect = new Election("stv", 1, new File[0], false);
		} catch(Exception e) {}

		// add 100.000 ballots
		for (int i = 0; i < 100000; i++) {
			elect.addBallotToList("4,3,2,1");
		}

		STV stv = new STV(elect);

		// test with 1 seat
		stv.getElection().numSeats = 1;
		assertEquals(50001, stv.calcDroopQuota());

		// test with 5 seats
		stv.getElection().numSeats = 5;
		assertEquals(16667, stv.calcDroopQuota());

		// test with 10 seats (max num. candidates)
		stv.getElection().numSeats = 10;
		assertEquals(9091, stv.calcDroopQuota());
	}

	/**
	 * Checks if the droop quota was correctly calculated for
	 * zero ballots and a variety of seat numbers.
	 */
	@Test
	void test_calcDroopQuota_zero() {
		Election elect = null;
		try {
			elect = new Election("stv", 1, new File[0], false);
		} catch(Exception e) {}

		STV stv = new STV(elect);

		// test with 1 seat
		stv.getElection().numSeats = 1;
		assertEquals(1, stv.calcDroopQuota());

		// test with 5 seats
		stv.getElection().numSeats = 5;
		assertEquals(1, stv.calcDroopQuota());

		// test with 10 seats (max num. candidates)
		stv.getElection().numSeats = 10;
		assertEquals(1, stv.calcDroopQuota());

	}

	/**
	 * Manual test to ensure shuffle does not create patterns.
	 * Output is stored in ./testing/test_shuffle_true.txt for review
	 */
	@Test
	void test_shuffle_true() { // manual test
		// run shuffle 10 times, check to make sure ballots are changing
		Election elect = null;
		File testFile = new File("../testing/test_shuffle_true.txt");
		PrintWriter testWriter = null;
		try {
			elect = new Election("stv", 1, new File[0], true);
			elect.setCandidates("jake,allison,sami,declan,test5");
			testWriter = new PrintWriter(testFile);
		} catch (Exception e) {
			System.out.println(e);
		}

		elect.addBallotToList("1,2,3,4,5");
		elect.addBallotToList("2,3,4,5,1");
		elect.addBallotToList("3,4,5,1,2");
		elect.addBallotToList("4,5,1,2,3");
		elect.addBallotToList("5,1,2,3,4");
		elect.addBallotToList("1,3,2,5,4");
		elect.addBallotToList("1,4,2,5,3");
		elect.addBallotToList("2,4,3,5,1");
		elect.addBallotToList("3,5,4,2,1");
		elect.addBallotToList("3,4,2,1,5");

		STV stv = new STV(elect);
		testWriter.println("Original order: ");
		for (Ballot b : stv.getBallots()) {
			testWriter.print(b.getBallotNum() + " ");
		}
		testWriter.println();

		for (int i = 0; i < 10; i++) {
			stv.shuffle();
			testWriter.println("Shuffle " + (i+1) + ": ");
			for (Ballot b : stv.getBallots()) {
				testWriter.print(b.getBallotNum() + " ");
			}
			testWriter.println();
		}

		testWriter.close();
	}

	/**
	 * Manual test to ensure shuffle only shuffles when
	 * Election.shuffle is true.
	 * Output is stored in ./testing/test_shuffle_false.txt for review
	 */
	@Test
	void test_shuffle_false() { // also manual
		Election elect = null;
		File testFile = new File("../testing/test_shuffle_false.txt");
		PrintWriter testWriter = null;
		try {
			elect = new Election("stv", 1, new File[0], false);
			elect.setCandidates("jake,allison,sami,declan,test5");
			testWriter = new PrintWriter(testFile);
		} catch (Exception e) {
			System.out.println(e);
		}

		elect.addBallotToList("1,2,3,4,5");
		elect.addBallotToList("2,3,4,5,1");
		elect.addBallotToList("3,4,5,1,2");
		elect.addBallotToList("4,5,1,2,3");
		elect.addBallotToList("5,1,2,3,4");
		elect.addBallotToList("1,3,2,5,4");
		elect.addBallotToList("1,4,2,5,3");
		elect.addBallotToList("2,4,3,5,1");
		elect.addBallotToList("3,5,4,2,1");
		elect.addBallotToList("3,4,2,1,5");

		STV stv = new STV(elect);
		testWriter.println("Original order: ");
		for (Ballot b : stv.getBallots()) {
			testWriter.print(b.getBallotNum() + " ");
		}
		testWriter.println();

		for (int i = 0; i < 10; i++) {
			stv.shuffle();
			testWriter.println("Shuffle " + (i+1) + ": ");
			for (Ballot b : stv.getBallots()) {
				testWriter.print(b.getBallotNum() + " ");
			}
			testWriter.println();
		}

		testWriter.close();
	}

	/**
	 * Checks to ensure ballots are distributed properly
	 * with no candidate reaching Droop.
	 */
	@Test
	void test_distributeVotes_noWinners() {
		Election elect = null;
		LinkedList<Candidate> test_cans = new LinkedList<>();
		try {
			elect = new Election("stv", 1, new File[0], false);
			elect.setCandidates("test1,test2,test3");
			for (Candidate c : elect.getCandidates()) test_cans.add(c);
		} catch (Exception e) {
			System.out.println(e);
		}

		elect.addBallotToList("1,2,3");
		elect.addBallotToList("3,1,2");
		elect.addBallotToList("2,3,1");
		elect.addBallotToList("1,2,3");
		elect.addBallotToList("3,1,2");
		elect.addBallotToList("2,3,1");

		STV stv = new STV(elect);

		// test normal use, no winners chosen
		stv.distributeVotes();
		assertEquals(0, elect.winners.size());
		assertEquals(0, elect.losers.size());
		assertEquals(3,elect.candidates.size());
		for (Candidate c : elect.getCandidates()) {
			assertEquals(2, c.getVoteCount());
		}
	}

	/**
	 * Checks to ensure ballots are distributed properly
	 * with one candidate reaching Droop. Ensures the
	 * candidate who reached Droop is properly declared
	 * a winner.
	 */
	@Test
	void test_distributeVotes_oneWinner() {
		Election elect = null;
		LinkedList<Candidate> test_cans = new LinkedList<>();
		try {
			elect = new Election("stv", 1, new File[0], false);
			elect.setCandidates("test1,test2,test3");
			for (Candidate c : elect.getCandidates()) test_cans.add(c);
		} catch (Exception e) {
			System.out.println(e);
		}

		elect.addBallotToList("1,2,3");
		elect.addBallotToList("3,1,2");
		elect.addBallotToList("2,3,1");
		elect.addBallotToList("1,2,3");
		elect.addBallotToList("3,1,2");
		elect.addBallotToList("2,3,1");
		elect.addBallotToList("2,1,3");
		elect.addBallotToList("2,1,3");
		elect.addBallotToList("2,1,3");

		STV stv = new STV(elect);
		stv.distributeVotes();
		assertEquals(1, elect.winners.size());
		assertEquals(0, elect.losers.size());
		assertEquals(2, elect.candidates.size());
		for (Candidate c : elect.getCandidates()) {
			assertEquals(2, c.getVoteCount());
		}
		assertEquals(stv.getDroop(),elect.winners.peek().getVoteCount());
	}

	/**
	 * Checks to ensure ballots are distributed properly
	 * with one candidate reaching Droop. Ensures the
	 * candidate who reached Droop is properly declared
	 * a winner. Ensures ballots with that candidate as
	 * the top ranked candidate distributed after the
	 * candidate is declared a winner are given to the
	 * second rank candidate.
	 */
	@Test
	void test_distributeVotes_secondRank() {
		Election elect = null;
		LinkedList<Candidate> test_cans = new LinkedList<>();
		try {
			elect = new Election("stv", 1, new File[0], false);
			elect.setCandidates("test1,test2,test3");
			for (Candidate c : elect.getCandidates()) test_cans.add(c);
		} catch (Exception e) {
			System.out.println(e);
		}

		elect.addBallotToList("1,2,3");
		elect.addBallotToList("3,1,2");
		elect.addBallotToList("2,3,1");
		elect.addBallotToList("1,2,3");
		elect.addBallotToList("3,1,2");
		elect.addBallotToList("2,3,1");
		elect.addBallotToList("2,1,3");
		elect.addBallotToList("2,1,3");
		elect.addBallotToList("2,1,3");

		elect.addBallotToList("2,1,3");
		elect.addBallotToList("2,1,3");

		elect.addBallotToList("2,1,3");  // extra test2 ballots for test1 and test3
		elect.addBallotToList("3,1,2");

		// test second rank choice, one winner chosen
		STV stv = new STV(elect);
		stv.distributeVotes();

		assertEquals(1, elect.winners.size());
		assertEquals(0, elect.losers.size());
        assertEquals(2, elect.candidates.size());
		for (Candidate c : elect.getCandidates()) {
			assertEquals(3, c.getVoteCount());
		}
		assertEquals(stv.getDroop(),elect.winners.peek().getVoteCount());
	}

	/**
	 * Checks to ensure ballots are distributed properly
	 * with two candidates reaching Droop. Ensures the
	 * candidates who reached Droop are properly declared
	 * winners.
	 */
	@Test
	void test_distributeVotes_twoWinners() {
		Election elect = null;
		LinkedList<Candidate> test_cans = new LinkedList<>();
		try {
			elect = new Election("stv", 2, new File[0], false);
			elect.setCandidates("test1,test2,test3");
			for (Candidate c : elect.getCandidates()) test_cans.add(c);
		} catch (Exception e) {
			System.out.println(e);
		}

		elect.addBallotToList("1,2,3");
		elect.addBallotToList("3,1,2");
		elect.addBallotToList("2,3,1");
		elect.addBallotToList("1,2,3");
		elect.addBallotToList("3,1,2");
		elect.addBallotToList("2,3,1");
		elect.addBallotToList("2,1,3");
		elect.addBallotToList("2,1,3");
		elect.addBallotToList("2,1,3");

		elect.addBallotToList("2,1,3");
		elect.addBallotToList("2,1,3");

		elect.addBallotToList("2,1,3");  // extra test2 ballots for test1 and test3
		elect.addBallotToList("3,1,2");

		// test two winners chosen
		STV stv = new STV(elect);
		stv.distributeVotes();
		assertEquals(2, elect.winners.size());
		assertEquals(0, elect.losers.size());
        assertEquals(1, elect.candidates.size());
		for (Candidate c : elect.getCandidates()) {
			assertEquals(3, c.getVoteCount());
		}
//		for (Candidate c : elect.getWinners()) {
//			assertTrue(c == test_cans.get(0) || c == test_cans.get(1)); // winners are test1 and test2
//			assertEquals(stv.getDroop(), c.getVoteCount());
//		}
        assertEquals(test_cans.get(1), elect.winners.remove());
		assertEquals(test_cans.get(1).getVoteCount(), stv.getDroop());
		assertEquals(test_cans.get(0), elect.winners.peek());
        assertEquals(test_cans.get(0).getVoteCount(), stv.getDroop());
	}

	/**
	 * Checks to ensure ballots are distributed properly
	 * with two candidates reaching Droop. Ensures the
	 * candidates who reached Droop are properly declared
	 * winners. Ensures ballots with those candidates
	 * as the top ranked candidates distributed after they
	 * are declared as winners are given to the ballot's
	 * third rank candidate.
	 */
	@Test
	void test_distributeVotes_thirdRank() {
		Election elect = null;
		LinkedList<Candidate> test_cans = new LinkedList<>();
		try {
			elect = new Election("stv", 2, new File[0], false);
			elect.setCandidates("test1,test2,test3");
			for (Candidate c : elect.getCandidates()) test_cans.add(c);
		} catch (Exception e) {
			System.out.println(e);
		}

		elect.addBallotToList("1,2,3");
		elect.addBallotToList("3,1,2");
		elect.addBallotToList("2,3,1");
		elect.addBallotToList("1,2,3");
		elect.addBallotToList("3,1,2");
		elect.addBallotToList("2,3,1");
		elect.addBallotToList("2,1,3");
		elect.addBallotToList("2,1,3");
		elect.addBallotToList("2,1,3");

		elect.addBallotToList("2,1,3");
		elect.addBallotToList("2,1,3");

		elect.addBallotToList("2,1,3");  // extra test2 ballots for test1 and test3
		elect.addBallotToList("3,1,2");

		// test two winners chosen, third rank choice for test3
		elect.addBallotToList("2,1,3");
		STV stv = new STV(elect);
		stv.distributeVotes();
		assertEquals(2, elect.winners.size());
		assertEquals(0, elect.losers.size());
        assertEquals(1, elect.candidates.size());
		for (Candidate c : elect.getCandidates()) {
			assertEquals(4, c.getVoteCount());
		}
//		for (Candidate c : elect.getWinners()) {
////			assertTrue(c == test_cans.get(0) || c == test_cans.get(1)); // winners are test1 and test2
////			assertEquals(stv.getDroop(), c.getVoteCount());
////		}
        assertEquals(test_cans.get(1), elect.winners.remove());
		assertEquals(test_cans.get(1).getVoteCount(), stv.getDroop());
		assertEquals(test_cans.get(0), elect.winners.peek());
        assertEquals(test_cans.get(0).getVoteCount(), stv.getDroop());

	}

	/**
	 * Checks that getLoser returns the proper candidate
	 * with a single loser.
	 */
	@Test
	void test_getLoser_singleLoser() {
		Election elect = null;
		LinkedList<Candidate> test_cans = new LinkedList<>();
		try {
			elect = new Election("stv", 1, new File[0], false);
			elect.setCandidates("test1,test2,test3,test4,test5");
			for (Candidate c : elect.getCandidates()) {
				for (int i = 0; i < Integer.parseInt(c.getName().substring(4)); i++){
					c.addBallot(new Ballot(elect.candidates, "1,2,3,4,5"));
				}
				test_cans.add(c);
			}
		} catch(Exception e) {
			System.out.println(e);
		}

		STV stv = new STV(elect);
		Stack<Candidate> voteOrder = stv.getVoteOrder();
		for (Candidate c : elect.getCandidates()) voteOrder.push(c);

		// test for single loser - general case
		assertEquals(test_cans.get(0), stv.getLoser());
	}

	/**
	 * Checks that getLoser returns the correct candidate
	 * when there are two losers.
	 */
	@Test
	void test_getLoser_twoLosers() {
		Election elect = null;
		LinkedList<Candidate> test_cans = new LinkedList<>();
		try {
			elect = new Election("stv", 1, new File[0], false);
			elect.setCandidates("test1,test2,test3,test4,test5");
			// set vote count to number after "test"
			for (Candidate c : elect.getCandidates()) {
				for (int i = 0; i < Integer.parseInt(c.getName().substring(4)); i++){
					c.addBallot(new Ballot(elect.candidates, "1,2,3,4,5"));
				}
				test_cans.add(c);
			}
		} catch(Exception e) {
			System.out.println(e);
		}

		STV stv = new STV(elect);
		Stack<Candidate> voteOrder = stv.getVoteOrder();
		for (Candidate c : elect.getCandidates()) voteOrder.push(c);

		// test for two losers
		test_cans.get(0).addBallot(new Ballot(elect.candidates, "1,2,3,4,5"));
		//test_cans.get(0).addBallot(new Ballot(elect.candidates, "1,2,3,4,5"));
		assertEquals(test_cans.get(1), stv.getLoser());
	}

	/**
	 * Checks that getLoser returns the correct candidate
	 * when all candidates could be losers.
	 */
	@Test
	void test_getLoser_allLosers() {
		Election elect = null;
		LinkedList<Candidate> test_cans = new LinkedList<>();
		try {
			elect = new Election("stv", 1, new File[0], false);
			elect.setCandidates("test1,test2,test3,test4,test5");
			// set vote count to number after "test"
			for (Candidate c : elect.getCandidates()) {
				test_cans.add(c);
                c.addBallot(new Ballot(elect.candidates, "1,2,3,4,5"));
                c.addBallot(new Ballot(elect.candidates, "1,2,3,4,5"));
                c.addBallot(new Ballot(elect.candidates, "1,2,3,4,5"));
			}
		} catch(Exception e) {
			System.out.println(e);
		}

		STV stv = new STV(elect);
		Stack<Candidate> voteOrder = stv.getVoteOrder();

		// test if all 5 are losers
		for (Candidate c : elect.getCandidates()) {
			voteOrder.push(c);
		}
		assertEquals(test_cans.get(test_cans.size()-1), stv.getLoser());

	}

	/**
	 * Check that breakTie properly returns null if
	 * attempting to break a tie with zero candidates.
	 */
	@Test
	void test_breakTie_zeroCandidates() {
		Election elect = null;
		try {
			elect = new Election("stv", 1, new File[0], false);
		} catch(Exception e) {}

        STV stv = new STV(elect);
		LinkedList<Candidate> losers = new LinkedList<>();

		// test with zero candidates
		assertEquals(null, stv.breakTie(losers));
	}

	/**
	 * Check that breakTie returns the candidate if
	 * attempting to break a tie with one candidate.
	 */
	@Test
	void test_breakTie_oneCandidate() {
		Election elect = null;
		try {
			elect = new Election("stv", 1, new File[0], false);
		} catch(Exception e) {}

        STV stv = new STV(elect);
		LinkedList<Candidate> losers = new LinkedList<>();
        Candidate test1 = new Candidate("test1");

		// test with one candidate
		losers.add(test1);
		assertEquals(test1, stv.breakTie(losers));
	}

	/**
	 * Check that breakTie returns the candidate with
	 * the latest first vote given two candidates with
	 * the same positive number of votes.
	 */
	@Test
	void test_breakTie_twoCandidates() {
		Election elect = null;
		try {
			elect = new Election("stv", 1, new File[0], false);
		} catch(Exception e) {}

        STV stv = new STV(elect);
        LinkedList<Candidate> losers = new LinkedList<>();
		Candidate test1 = new Candidate("test1");
		Candidate test2 = new Candidate("test2");

		// test with two candidates with > 0 votes
		losers.add(test1);
		losers.add(test2);
		Stack<Candidate> voteOrder = stv.getVoteOrder();
		voteOrder.push(test1);
		voteOrder.push(test2);
		assertEquals(test2, stv.breakTie(losers));
	}

	/**
	 * Check that breakTie returns the candidate with
	 * the latest first vote given three candidates with
	 * the same positive number of votes.
	 */
	@Test
	void test_breakTie_threeCandidates() {
		Election elect = null;
		try {
			elect = new Election("stv", 1, new File[0], false);
		} catch(Exception e) {}

        STV stv = new STV(elect);
		LinkedList<Candidate> losers = new LinkedList<>();

        Candidate test1 = new Candidate("test1");
        Candidate test2 = new Candidate("test2");
        Candidate test3 = new Candidate("test3");

		losers.add(test1);
		losers.add(test2);
		losers.add(test3);
		Stack<Candidate> voteOrder = stv.getVoteOrder();

		voteOrder.push(test3);
		voteOrder.push(test1);
		voteOrder.push(test2);
		// test with three candidates with > 0 votes
		assertEquals(test2, stv.breakTie(losers));
	}

	/**
	 * Check that breakTie returns the candidate with
	 * the latest first vote given three candidates with
	 * the same positive number of votes and one candidate
	 * with a higher number of votes (check usage of
	 * voteOrder).
	 */
	@Test
	void test_breakTie_fourCandidates() {
		Election elect = null;
		try {
			elect = new Election("stv", 1, new File[0], false);
		} catch(Exception e) {}

        STV stv = new STV(elect);

        LinkedList<Candidate> losers = new LinkedList<>();

		Candidate test1 = new Candidate("test1");
		Candidate test2 = new Candidate("test2");
		Candidate test3 = new Candidate("test3");

		losers.add(test1);
		losers.add(test2);
		losers.add(test3);
		Stack<Candidate> voteOrder = stv.getVoteOrder();

		// test with four candidates with > 0 votes -  3 losers and one non-loser
		voteOrder.push(test1);
		voteOrder.push(test2);
        voteOrder.push(test3);
		voteOrder.push(new Candidate("winner4"));
		assertEquals(test3, stv.breakTie(losers));
	}

	/**
	 * Check that a candidate is returned randomly
	 * given three candidates with zero votes.
	 */
	@Test
	void test_breakTie_zeroVotes() {
		Election elect = null;
		try {
			elect = new Election("stv", 1, new File[0], false);
		} catch(Exception e) {}

        STV stv = new STV(elect);
        LinkedList<Candidate> losers = new LinkedList<>();

		Candidate test1 = new Candidate("test1");
		Candidate test2 = new Candidate("test2");
		Candidate test3 = new Candidate("test3");

		losers.add(test1);
		losers.add(test2);
		losers.add(test3);
		Stack<Candidate> voteOrder = stv.getVoteOrder();

		// test with three losers with 0 votes
		assertTrue(voteOrder.size() == 0);
		voteOrder.add(new Candidate("winner1"));
		voteOrder.add(new Candidate("winner2"));
		voteOrder.add(new Candidate("winner3"));
		Candidate loser = stv.breakTie(losers);
		assertTrue(loser == test1 || loser == test2 || loser == test3);
	}

	/**
	 * Checks that breakTie returns the candidate with
	 * the latest first vote given three candidates
	 * with the same low positive number of votes and
	 * seven candidates with a higher number of votes
	 * (check usage of voteOrder).
	 */
	@Test
	void test_breakTie_maxCandidates() {
		Election elect = null;
		try {
			elect = new Election("stv", 1, new File[0], false);
		} catch(Exception e) {}

        STV stv = new STV(elect);
        LinkedList<Candidate> losers = new LinkedList<>();

		Candidate test1 = new Candidate("test1");
		Candidate test2 = new Candidate("test2");
		Candidate test3 = new Candidate("test3");

		losers.add(test1);
		losers.add(test2);
		losers.add(test3);
		Stack<Candidate> voteOrder = stv.getVoteOrder();

		// test with more non losers than losers (10 (max) total candidates)
		voteOrder.clear();
		voteOrder.add(test3);
		voteOrder.add(new Candidate("winner1"));
		voteOrder.add(new Candidate("winner2"));
		voteOrder.add(test2);
		voteOrder.add(new Candidate("winner3"));
		voteOrder.add(new Candidate("winner4"));
		voteOrder.add(new Candidate("winner5"));
		voteOrder.add(new Candidate("winner6"));
		voteOrder.add(test1);
		voteOrder.add(new Candidate("winner7"));
		assertEquals(test1, stv.breakTie(losers));
	}

	/**
	 * Check that evaluate calculates the proper results
	 * given an election with four candidates, one seat,
	 * and six ballots (one winner and three losers).
	 */
	@Test
	void test_evaluate() {
		Election elect = null;
		LinkedList<Candidate> test_cans = new LinkedList<>();
		try {
			elect = new Election("stv", 1, new File[0], false);
			elect.setCandidates("test1,test2,test3,test4");
			for (Candidate c : elect.getCandidates()) test_cans.add(c);
		} catch (Exception e) {
			System.out.println(e);
		}

		elect.addBallotToList("1,2,3,4");
		elect.addBallotToList("3,1,2,4");
		elect.addBallotToList("2,3,1,4");
		elect.addBallotToList("1,2,3,4");
		elect.addBallotToList("3,1,2,4");
		elect.addBallotToList("2,3,1,4");

		STV stv = new STV(elect);
		stv.evaluate();

		assertEquals(1, elect.winners.size());
		assertEquals(3, elect.losers.size());
		assertTrue(elect.winners.contains(test_cans.get(0)));
		assertTrue(elect.losers.contains(test_cans.get(1)));
		assertTrue(elect.losers.contains(test_cans.get(2)));
		assertTrue(elect.losers.contains(test_cans.get(3)));
		assertEquals(stv.getDroop(),test_cans.get(0).getBallots().size());
	}
}
