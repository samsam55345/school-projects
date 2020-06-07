/**
 * <h1> Unit tests for the Ballot class </h1>
 * BallotTest.java
 * This file tests the functionality of Ballot.java
 * and all of its methods.
 * @author Sami Frank
 */

import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


class BallotTest {
	private static LinkedHashSet<Candidate> candidates;
	private static Candidate sami;
	private static Candidate jake;
	private static Candidate declan;
	private static Candidate allison;
	private static ArrayList<String> voteLst;
	private static Ballot ballot;
	private static Ballot ballot1;
	private static Ballot ballot2;
	private static Ballot ballot3;
	private static Ballot ballot4;
	private static Ballot ballot5;
	private static Ballot ballot6;

	@BeforeAll
	static void setUp(){
		candidates = new LinkedHashSet<>();
		sami = new Candidate("Sami");
		jake = new Candidate("Jake");
		declan = new Candidate("Declan");
		allison = new Candidate("Allison");
		candidates.add(sami);
		candidates.add(jake);
		candidates.add(declan);
		candidates.add(allison);
		voteLst = new ArrayList<>();
		String votes = "1,,,";
		String votes1 = "2,1,3,4";
		String votes2 = ",,,1";
		String votes3 = ",,1,";
		String votes4 = "4,2,1,3";
		String votes5 = "1,,3,2";
		String votes6 = ",1,2,3";
		voteLst.add(votes);
		voteLst.add(votes1);
		voteLst.add(votes2);
		voteLst.add(votes3);
		voteLst.add(votes4);
		voteLst.add(votes5);
		voteLst.add(votes6);
		ballot = new Ballot(candidates, voteLst.get(0));
		ballot1 = new Ballot(candidates, voteLst.get(1));
		ballot2 = new Ballot(candidates, voteLst.get(2));
		ballot3 = new Ballot(candidates, voteLst.get(3));
		ballot4 = new Ballot(candidates, voteLst.get(4));
		ballot5 = new Ballot(candidates, voteLst.get(5));
		ballot6 = new Ballot(candidates, voteLst.get(6));
	}

	/**
	* sets up new instances of ballot class
	* */
	@ParameterizedTest
	@ValueSource(strings = {"1,,,", "1,2,3,4", "3,,2,1", "1,2,,", ",,,1", ",,1,", ",1,,"})
	public void BallotTest(String votes){
		Ballot ballot = generateBallot(candidates, votes);

		assertNotNull(ballot);
	}

	private Ballot generateBallot(LinkedHashSet<Candidate> candidates, String votes) {
		Ballot ballot = new Ballot(candidates, votes);
		return ballot;
	}


	/**
	* tests that the correct output is returned from the ballot classes
	* getVotes()
	* */
	@Test
	void testGetVotes() {

		Map<Integer, Candidate> expect = new HashMap<>();
		Map<Integer, Candidate> expect1 = new HashMap<>();
		Map<Integer, Candidate> expect2 = new HashMap<>();
		Map<Integer, Candidate> expect3 = new HashMap<>();
		Map<Integer, Candidate> expect4 = new HashMap<>();
		Map<Integer, Candidate> expect5 = new HashMap<>();
		Map<Integer, Candidate> expect6 = new HashMap<>();

		expect.put(1,sami);

		expect1.put(2,sami);
		expect1.put(1,jake);
		expect1.put(3,declan);
		expect1.put(4,allison);

		expect2.put(1,allison);

		expect3.put(1,declan);

		expect4.put(4,sami);
		expect4.put(2,jake);
		expect4.put(1,declan);
		expect4.put(3,allison);

		expect5.put(1,sami);
		expect5.put(3,declan);
		expect5.put(2,allison);

		expect6.put(1,jake);
		expect6.put(2,declan);
		expect6.put(3,allison);

		Map<Integer, Candidate> voteMap = ballot.getVotes();
		Map<Integer, Candidate> voteMap1 = ballot1.getVotes();
		Map<Integer, Candidate> voteMap2 = ballot2.getVotes();
		Map<Integer, Candidate> voteMap3 = ballot3.getVotes();
		Map<Integer, Candidate> voteMap4 = ballot4.getVotes();
		Map<Integer, Candidate> voteMap5 = ballot5.getVotes();
		Map<Integer, Candidate> voteMap6 = ballot6.getVotes();

		assertEquals("Sami",voteMap.get(1).getName());

		assertEquals("Jake",voteMap1.get(1).getName());
		assertEquals("Sami",voteMap1.get(2).getName());
		assertEquals("Declan",voteMap1.get(3).getName());
		assertEquals("Allison",voteMap1.get(4).getName());

		assertEquals("Allison",voteMap2.get(1).getName());

		assertEquals("Declan",voteMap3.get(1).getName());

		assertEquals("Sami",voteMap4.get(4).getName());
		assertEquals("Jake",voteMap4.get(2).getName());
		assertEquals("Declan",voteMap4.get(1).getName());
		assertEquals("Allison",voteMap4.get(3).getName());

		assertEquals("Sami",voteMap5.get(1).getName());
		assertEquals("Declan",voteMap5.get(3).getName());
		assertEquals("Allison",voteMap5.get(2).getName());

		assertEquals("Jake",voteMap6.get(1).getName());
		assertEquals("Declan",voteMap6.get(2).getName());
		assertEquals("Allison",voteMap6.get(3).getName());

	}

	/**
	* tests that the correct output is returned from the ballot classes
	* getBallotNum()
	 * each assert should be a number between 1-7 (the number of ballots that
	 * were created) in ascending order.
	* */
	@Test
	void testGetBallotNum() {
		int ballotNum = 1;
		int ballotNum1 = 2;
		int ballotNum2 = 3;
		int ballotNum3 = 4;
		int ballotNum4 = 5;
		int ballotNum5 = 6;
		int ballotNum6 = 7;
		assertEquals(ballotNum, ballot.getBallotNum());
		assertEquals(ballotNum1, ballot1.getBallotNum());
		assertEquals(ballotNum2, ballot2.getBallotNum());
		assertEquals(ballotNum3, ballot3.getBallotNum());
		assertEquals(ballotNum4, ballot4.getBallotNum());
		assertEquals(ballotNum5, ballot5.getBallotNum());
		assertEquals(ballotNum6, ballot6.getBallotNum());
	}
}
