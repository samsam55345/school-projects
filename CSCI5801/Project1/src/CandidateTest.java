import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.Test;
import java.util.LinkedHashSet;

/**
 * <h1>CandidateTest Class</h1>
 * This test hosts a number of different unit tests to test the Candidate class.
 * 
 * @author Jake Waro
 *
 */
class CandidateTest {
	
	// -------------------------------------------------------------------------
	// Tests for constructor and getName() 
	// -------------------------------------------------------------------------
	
	/**
	 * This test checks that the candidate object is instantiated, and that its
	 * respective fields get set correctly (the candidate’s name, voteCount,
	 * isWinner, and ballots).
	 * @result Candidate is created, and name, voteCount, isWinner, and ballots
	 * 	are correctly set.
	 */
	@ParameterizedTest
	@ValueSource(strings = {
			"", "J", "Ja", "Jake", "Samantha", "Peter Shii", "James Gorch"
	})
	public void testConstructorGetName(String testValue) {
		Candidate candidate = new Candidate(testValue);
		assertEquals(testValue, candidate.getName());
		assertEquals(0, candidate.getVoteCount());
		assertEquals(false, candidate.isWinner());
		assertEquals(new LinkedHashSet<Ballot>(), candidate.getBallots());
	}
	
	/**
	 * This test specifically checks that the Candidate constructor can
	 * correctly initialize the name field to larger names. This test checks
	 * that the candidate object is instantiated, and that its name field is
	 * initialized to the correct value.
	 * @result Candidate object is created, and its fields are initialized with
	 * 	values.
	 */
	@Test
	public void testConstructorGetBigNames() {
		// String name length 999
		String name999 = makeString(999);
		Candidate candidate1 = new Candidate(name999);
		assertEquals(name999, candidate1.getName());
		
		// String name length 1000
		String name1000 = makeString(1000);
		Candidate candidate2 = new Candidate(name1000);
		assertEquals(name1000, candidate2.getName());
		
		// String name length 999,999
		String name999999 = makeString(999999);
		Candidate candidate3 = new Candidate(name999999);
		assertEquals(name999999, candidate3.getName());
		
		// String name length 1,000,000
		String name1000000 = makeString(1000000);
		Candidate candidate4 = new Candidate(name1000000);
		assertEquals(name1000000, candidate4.getName());
	}
	
	/* 
	 * Helper method for testConstructorGetBigNames. Creates and returns a
	 * string of size length.
	 */
	private String makeString(int length) {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < length; i++) {
			sb.append('j');
		}
		
		return sb.toString();
	}

	
	// -------------------------------------------------------------------------
	// Tests for isWinner()
	// -------------------------------------------------------------------------
	
	/**
	 * This test verifies that a candidate is not a winner right after it is
	 * initialized.
	 * @result Candidate’s isWinner field is set to false.
	 */
	@Test
	public void testIsWinnerFalse() {
		Candidate candidate = new Candidate("Jake");
		assertFalse(candidate.isWinner());
	}
	
	/**
	 * Verifies candidate.setWinner() sets the candidate’s isWinner field to
	 * true.
	 * @result Candidate’s isWinner field is set to true.
	 */
	@Test
	public void testIsWinnerTrue() {
		Candidate candidate = new Candidate("Jake");
		candidate.setWinner();
		assertTrue(candidate.isWinner());
	}
	
	/**
	 * This test tests that calling setWinner method multiple times does not
	 * corrupt the isWinner field i.e. once setWinner is called, any subsequent
	 * calls should not change the value of isWinner, and it should stay true
	 * for the rest of the program.
	 * @result Candidate’s isWinner field is set to true.
	 */
	@Test
	public void testIsWinnerMultipleSets() {
		Candidate candidate = new Candidate("Jake");
		candidate.setWinner();
		candidate.setWinner();
		assertTrue(candidate.isWinner());
		candidate.setWinner();
		assertTrue(candidate.isWinner());
	}
	
	// -------------------------------------------------------------------------
	// Tests for getVoteCount
	// -------------------------------------------------------------------------
	
	/**
	 * Test that a candidate’s voteCount field matches the number of ballots
	 * they have assigned to them.
	 * @result Candidate’s voteCount matches the number of ballots they have.
	 */
	@ParameterizedTest
	@ValueSource(ints = {
			0, 1, 2, 4, 11, 999, 1000, 999999, 1000000
	})
	public void testGetVoteCount(int testValue) {
		Candidate candidate = new Candidate("Jake");
		LinkedHashSet<Candidate> cands = new LinkedHashSet<Candidate>();
		cands.add(candidate);
		
		// add value amount of ballots to the candidate
		for (int i = 0; i < testValue; i++) {
			candidate.addBallot(new Ballot(cands, "1"));
		}
		
		assertEquals(testValue, candidate.getVoteCount());
		assertFalse(14 == candidate.getVoteCount());
	
	}
	
	// -------------------------------------------------------------------------
	// Tests for add / get ballots
	// -------------------------------------------------------------------------
	
	/**
	 * Test that we can add ballots to a candidate and that we can retrieve
	 * ballots from them.
	 * @result Candidate.java class file exists in /src directory.
	 */
	@ParameterizedTest
	@ValueSource(ints = {
			0, 1, 2, 4, 7, 10, 11, 999, 1000, 9999, 10001, 999999, 1000000
	})
	public void testAddGetBallots(int testValue) {
		Candidate candidate = new Candidate("Jake");
		LinkedHashSet<Candidate> cands = new LinkedHashSet<Candidate>();
		cands.add(candidate);
		LinkedHashSet<Ballot> ballots = new LinkedHashSet<Ballot>();
		
		
		// add value amount of ballots
		for (int i = 0; i < testValue; i++) {
			Ballot bal = new Ballot(cands, "1");
			candidate.addBallot(bal);
			ballots.add(bal);
		}
		
		// verify that the ballots retrieved from candidate are the same that we
		// added
		assertEquals(ballots, candidate.getBallots());
	
	}
	
	// -------------------------------------------------------------------------
	// Test for removing ballots
	// -------------------------------------------------------------------------
	
	/**
	 * Test that we can set a candidate’s ballots to an empty list. This should
	 * set their voteCount to 0, and we should be able to retrieve the
	 * candidate’s list of ballots before the previous steps occur.
	 * @result Candidate’s voteCount field is 0, their ballot list is empty, and
	 * we have the value of their ballots field prior to it getting cleared.
	 */
	@ParameterizedTest
	@ValueSource(ints = {
			0, 1, 2, 4, 7, 10, 11, 999, 1000, 9999, 10001, 999999, 1000000
	})
	public void testRemoveBallots(int testValue) {
		Candidate candidate = new Candidate("Jake");
		LinkedHashSet<Candidate> cands = new LinkedHashSet<Candidate>();
		cands.add(candidate);
		LinkedHashSet<Ballot> ballots = new LinkedHashSet<Ballot>();
		
		// add value amount of ballots
		for (int i = 0; i < testValue; i++) {
			Ballot bal = new Ballot(cands, "1");
			candidate.addBallot(bal);
			ballots.add(bal);
		}
		
		// calling remove ballots should return the candidate's ballots
		assertEquals(ballots, candidate.removeBallots());
		
		// once the candidate's ballots have been removed, their voteCount should be 0
		assertEquals(0, candidate.getVoteCount());
		
		// verify that the candidate's ballot hash set is now empty
		LinkedHashSet<Ballot> empty = new LinkedHashSet<Ballot>();
		assertEquals(empty, candidate.getBallots());
	}
}
