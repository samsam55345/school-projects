import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * <h1>ElectionTest Class</h1>
 * This test hosts a number of different unit tests to test the Election class.
 * 
 * @author Jake Waro
 *
 */
class ElectionTest {
	
	// used to keep track of static ballot counts for writeToAudit test
	private static int writeToAuditCount = 0;

	/**
	 * This test checks that the Election constructor is properly initialized,
	 * having set voteType, numSeats, files, and shuffle.
	 * @throws IOException
	 * @result the election object and respective fields are properly
	 * 	initialized and set.
	 */
	@Test
	public void testConstructor() throws IOException {
		// setup
		File[] files = {
				new File("file1.csv")
		};
		
		Election election = new Election("STV", 5, files, false);
		
		// verify each field is set properly by the constructor
		assertEquals("STV", election.getVoteType());
		assertEquals(5, election.getNumSeats());
		assertEquals(files, election.getFiles());
		assertEquals(false, election.getShuffle());
	}
	
	/**
	 * Test that confirms that the candidates get properly set.
	 * @param testHeader - test string of candidates
	 * @throws IOException
	 * @result the candidates are properly set.
	 */
	@ParameterizedTest
	@ValueSource(strings = {
			"", "Jake Waro", "A,B,C,D,E,F", "11,12,13,14", "Allison, Declan, Sami, Jake"
	})
	public void testSetCandidates(String testHeader) throws IOException {
		// setup
		Election election = generateTestElection("STV");
		
		String[] expected = testHeader.split(",");
		
		// set candidates
		election.setCandidates(testHeader);
		LinkedHashSet<Candidate> candidates = election.getCandidates();
		String[] result = new String[candidates.size()];
		int index = 0;
		for (Candidate cand: candidates) {
			result[index] = cand.getName();
			index++;
		}
		
		// assert
		assertTrue(Arrays.equals(expected, result));
		
	}
	
	/**
	 * Test that we can add at least one ballot to the ballot list
	 * @param testValues - test ballot rankings
	 * @throws IOException
	 * @result a ballot can be added to the ballot list.
	 */
	@ParameterizedTest
	@ValueSource(strings = {
			"1,,,", "1,2,3,4", "3,,2,1", "1,2,,", ",,,1"
	})
	public void testAddBallotToListSizeOne(String testValues) throws IOException {
		Election election = generateTestElection("STV");
		election.setCandidates("Jake,Allison,Declan,Sami");
		
		Ballot result = election.addBallotToList(testValues);
		LinkedHashSet<Ballot> resultList = election.getBallotList();
		
		assertTrue(resultList.size() == 1);
		assertTrue(resultList.contains(result));
	}
	
	/**
	 * Test that we can add many ballots to the ballot list.
	 * @throws IOException
	 * @result many ballots are added to the ballot list.
	 */
	@Test
	public void testAddBallotToListSizeMany() throws IOException {
		Election election = generateTestElection("STV");
		election.setCandidates("Jake,Allison,Declan,Sami");
		String record = "1,2,3,4";
		LinkedHashSet<Ballot> expectedList = new LinkedHashSet<Ballot>();
		
		assertTrue(expectedList.equals(election.getBallotList()));
		
		expectedList.add(election.addBallotToList(record));
		LinkedHashSet<Ballot> result1 = election.getBallotList();
		
		assertTrue(expectedList.equals(result1));
		
		expectedList.add(election.addBallotToList(record));
		expectedList.add(election.addBallotToList(record));
		expectedList.add(election.addBallotToList(record));
		expectedList.add(election.addBallotToList(record));
		LinkedHashSet<Ballot> result2 = election.getBallotList();
		
		assertTrue(expectedList.equals(result2));
		
		expectedList.add(election.addBallotToList(record));
		expectedList.add(election.addBallotToList(record));
		expectedList.add(election.addBallotToList(record));
		expectedList.add(election.addBallotToList(record));
		LinkedHashSet<Ballot> result3 = election.getBallotList();
		
		assertTrue(expectedList.equals(result3));
		
	}
	
	/**
	 * Test that the Election Information section header and the candidates
	 * are properly being written to the audit file.
	 * @param testHeaders - test strings of candidates.
	 * @throws IOException
	 * @result the election information header and candidates are properly
	 * 	written to the audit file.
	 */
	@ParameterizedTest
	@ValueSource(strings = {
			"Jake,Declan,Sami,Allison", "Bob,Pam,Sia", "A", "Amy,Ben,Carl,Dean,Erin,Frank"
	})
	public void testWritingHeaders(String testHeaders) throws IOException {
		// setup
		Election election = generateTestElection("Plurality");
		election.setTesting("../testing/testOutputs/testWritingHeaders_output.txt");
		StringBuilder sb = new StringBuilder(" - Candidates: ");
		String[] split = testHeaders.split(",");
		
		// Get the candidates that should be expected
		for (int i = 0; i < split.length; i++) {
			if (i == split.length - 1) {
				sb.append(split[i]);
			} else {
				sb.append(split[i] + ", ");
			}
		}
		
		String expected = sb.toString();
		
		// call
		election.setCandidates(testHeaders);
		
		// open VotingSystem_AuditFile_Group1.txt
		BufferedReader fileReader = new BufferedReader(new FileReader("../testing/testOutputs/testWritingHeaders_output.txt"));
		assertEquals("-------------------------------------------------------", fileReader.readLine());
		assertEquals("-----------    ELECTION INFORMATION   -----------------", fileReader.readLine());
		assertEquals("-------------------------------------------------------", fileReader.readLine());
		String header = fileReader.readLine();
		fileReader.close();
		
		assertEquals(expected, header);
		
	}
	
	/**
	 * This test checks that we correctly add numBallots amount of ballots to
	 * the audit file.
	 * @param numBallots - number of ballots to write to the audit file.
	 * @throws IOException
	 * @result numBallots are written to the test audit file
	 */
	@ParameterizedTest
	@ValueSource(ints = { 1, 2, 4, 7, 11, 99, 101, 999, 1001, 99999, 100001})
	public void testWriteToAudit(int numBallots) throws IOException {
		File[] files = {
				new File("../testing/testWriteFile1.csv")
		};
		String[] ballotVals = {
				"1,2,3,4",
				"4,3,2,1",
				",3,2,1",
				"1,,2,",
				"4,3,2,1",
				",3,2,1",
				"1,,2,",
				",,,1",
				"4,1,2,3",
				"3,,1,2",
		};
		Election election =  new Election("STV", 1, files, false);
				
		// set candidates & test output file
		election.setCandidates("Jake,Declan,Sami,Allison");
		election.setTesting("../testing/testOutputs/testWriteToAudit_output.txt");
		
		// right numBallots amount of ballots to the audit file
		for (int i = 0; i < numBallots; i++) {
			Ballot bal = election.addBallotToList(ballotVals[(i+1)%10]);
			writeToAuditCount++;
			election.writeToAudit(bal, "Initial reading of ballot: ");
		}
		
		// read the ballots from the file that ballots should be written to
		BufferedReader fileReader = new BufferedReader(new FileReader("../testing/testOutputs/testWriteToAudit_output.txt"));
		for (int i = 1; i < numBallots; i++) fileReader.readLine();
		String line = fileReader.readLine();
		fileReader.close();
		
		// use Ballot's getVotes to format what the expected value should be at
		// the last line
		String expected;
		if (numBallots == 0) expected = "Initial reading of ballot:  ID=(" + numBallots + ")";
		else expected = "Initial reading of ballot:  ID=(" + writeToAuditCount + ")";
		Ballot tmp = new Ballot(election.getCandidates(), ballotVals[numBallots%10]);
		writeToAuditCount++; // adjust count for the tmp dummy ballot
		Map<Integer, Candidate> votes = tmp.getVotes();
		for (int i = 1; i < 5; i++) {
			if (votes.containsKey(i)) {
				expected += "  " + votes.get(i).getName() + ": " + i;
			}
		}
		
		fileReader.close();
		assertEquals(expected, line);
		
	}
	
	/**
	 * This test checks the writeToAudit overloaded method that only takes a 
	 * String description correctly write numLines descriptions to the audit
	 * file.
	 * @param numLines - number of description lines to write to file.
	 * @throws IOException
	 * @result Each line read from the test overload file is equal to the test
	 * 	string, and there are numLines amount of lines in the test file.
	 */
	@ParameterizedTest
	@ValueSource(ints = {1, 2, 4, 7, 11, 99, 999, 1001, 99999})
	public void testWriteToAuditOverloaded(int numLines) throws IOException {
		// setup
		File[] files = {
				new File("../testing/testWriteFile1.csv")
		};
		Election election =  new Election("STV", 1, files, false);
		
		// set to testing file
		election.setTesting("../testing/testOutputs/test_OverloadWriteToAudit_output.txt");
		
		// write numLines lines to the audit file.
		String testString = "thisIsATestString";
		for (int i = 0; i < numLines; i++) {
			election.writeToAudit(testString);
		}
		
		// read back in the audit file
		BufferedReader fileReader = new BufferedReader(new FileReader("../testing/testOutputs/test_OverloadWriteToAudit_output.txt"));
		
		int count = 0;
		String line;
		while ((line = fileReader.readLine()) != null) {
			assertEquals(testString, line);
			count++;
		}
		
		fileReader.close();
		
		// number of lines should match the count
		assertEquals(numLines, count);
	}
	
	/**
	 * This test verifies the block that gets called during processing an
	 * election. If it's an STV election is processed, "STV" is returned, if
	 * it's a Plurality election, "Plurality" is returned, any other election
	 * type should cause an illegal argument exception.
	 * @param testValue - test vote type string
	 * @throws IOException
	 * @result the string returned matches the testValue if STV or Plurality and
	 * 	throws an exception otherwise.
	 */
	@ParameterizedTest
	@ValueSource(strings = { "STV", "Plurality", "aslfjasd#@%", "Neither STV nor Plurality"})
	public void testProcessElection(String testValue) throws IOException {
		// setup
		File[] files = {
				new File("../testing/testWriteFile1.csv") 
		};
		
		Election election = new Election(testValue, 1, files, false);
		
		// verify correct value is returned from election.processElection()
		if (testValue.equals("STV") || testValue.equals("Plurality")) {
			String result = election.processElection();
			assertEquals(testValue, result);
		} else {
			assertThrows(IllegalArgumentException.class, () -> {
				election.processElection();
			});		
		}	
	}
	
	/**
	 * This test checks that a ballot list is correctly created from the input
	 * files.
	 * @throws IOException
	 * @result each ballot in the ballot list matches the respective ballot read
	 * 	from the test file.
	 */
	@Test
	public void testCreateBallotList() throws IOException {
		// setup
		File[] files = {
				new File("../testing/testWriteFile1.csv"),
				new File("../testing/testWriteFile2.csv"),
				new File("../testing/testWriteFile3.csv")
		};
		Election election = new Election("STV", 2, files, true);
		election.setTesting("../testing/testOutputs/testCreateBallotList_output.txt");
		
		// Call to create the list of ballots
		election.createBallotList();
		
		// get the ballots and open the file that ballots should have been
		// written to
		LinkedHashSet<Ballot> ballots = election.getBallotList();
		BufferedReader fileReader = new BufferedReader(new FileReader("../testing/testOutputs/testCreateBallotList_output.txt"));
		
		// read header lines that get written due to testing
		fileReader.readLine();
		fileReader.readLine();
		fileReader.readLine();
		fileReader.readLine();
		
		// validate each ballot in the ballot list has been written to a file
		// with proper formatting
		for (Ballot bal: ballots) {
			StringBuilder sb = new StringBuilder("Initial reading of ballot: ");
			sb.append(" ID=(" + bal.getBallotNum() + ")");
			
			Map<Integer, Candidate> votes = bal.getVotes();
			
			for (Integer rank: votes.keySet()) {
				sb.append("  " + votes.get(rank).getName() + ": " + String.valueOf(rank));
			}
			
			String line = fileReader.readLine();
			
			assertEquals(sb.toString(), line);
			
		}
		
		fileReader.close();
		
		// verify the number of ballots matches the length of the ballot list
		assertEquals(election.getNumBallots(), ballots.size());
	}
	
	/**
	 * This test checks that the final results of an election are correctly
	 * written to the audit file.
	 * @throws IOException
	 * @result each string in the expected list matches the respective line
	 * 	read from the test audit file.
	 */
	@Test
	public void testWriteFinalResults() throws IOException {
		// setup
		writeToAuditCount = 9;
		File[] files = {
				new File("../testing/testWriteFile2.csv")
		};
		Election election = new Election("STV", 1, files, false);
		election.processElection();
		int droop = (9/(1 + 1)) + 1;
		
		//StringBuilder expected = new StringBuilder();
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("-------------------------------------------------------");
		expected.add("-----------    ELECTION INFORMATION   -----------------");
		expected.add("-------------------------------------------------------");
		expected.add(" - Candidates: Jake, Declan, Sami, Allison");
		expected.add(" - Number of candidates: 4");
		expected.add(" - Number of election seats: 1");
		expected.add(" - Type of election: STV");
		expected.add(" - Number of ballots: 9");
		expected.add("");
		expected.add("-------------------------------------------------------");
		expected.add("-----------------    RESULTS    -----------------------");
		expected.add("-------------------------------------------------------");
		expected.add("");
		expected.add("");
		expected.add(" Droop Quota: " + droop);
		expected.add("");
		expected.add(" Winner(s):");
		expected.add("  (In order of winning)");
		expected.add("  1. Jake");
		expected.add("");
		expected.add(" Loser(s):");
		expected.add("  (In order of losing)");
		expected.add("  1. Allison");
		expected.add("  2. Sami");
		expected.add("  3. Declan");
		expected.add("");
		expected.add("-------------------------------------------------------");
		expected.add("----------------    PROCESSING   ----------------------");
		expected.add("-------------------------------------------------------");
		
		//StringBuilder result = new StringBuilder();
		int index = 0;
		
		// read lines that were written to the audit file
		// it only checks up to the Processing header, as we've verified
		// in previous tests that the writing during processing already works.
		try {
			BufferedReader fileReader = new BufferedReader(new FileReader("../ElectionResults_AuditFile.txt"));

			String line = null;
			boolean done = false;

			// Read each ballot record from tempFile
			while ((line = fileReader.readLine()) != null && !done) {
				assertEquals(expected.get(index), line);
				if (line.equals("----------------    PROCESSING   ----------------------")) {
					line = fileReader.readLine();
					index++;
					assertEquals(expected.get(index), line);
					done = true;
				}
				index++;
			}

			fileReader.close(); // close reader stream

		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * This test checks that the audit write value is not set to a new value
	 * when testing is not in process and the current process does not require
	 * the audit writer to write to a different file.
	 * @throws IOException
	 * @result the setAuditWriter does not set the auditWriter to a new value.
	 */
	@Test
	public void testSetAuditWriterFalse() throws IOException {
		File[] files = {
				new File("../testing/testWriteFile1.csv")
		};
		Election election = new Election("STV", 2, files, true);
		
		String result = election.setAuditWriter(false);
		
		assertEquals(result, "");
	}
	
	/**
	 * This test checks that when we call the audit writer with a true value,
	 * the audit writer gets set to write to the temporary audit file.
	 * @throws IOException
	 * @result auditWriter writes to the temporary audit file.
	 */
	@Test
	public void testSetAuditWriterTrue() throws IOException {
		File[] files = {
				new File("../testing/testWriteFile1.csv")
		};
		Election election = new Election("STV", 2, files, true);
		
		String result = election.setAuditWriter(true);
		
		assertEquals(result, "temp");
	}
	
	/**
	 * This test checks that when testing is in process, the auditWriter writes
	 * to the test audit file, and the audit file is overwritten as this is the
	 * first time the audit writer is set to write to this audit file.
	 * @throws IOException
	 * @result auditWriter writes to the test audit file, and the audit test
	 * 	file will be overwritten.
	 */
	@Test
	public void testSetAuditWriterTestSet() throws IOException {
		File[] files = {
				new File("../testing/testWriteFile1.csv")
		};
		Election election = new Election("STV", 2, files, true);
		election.setTesting("../testing/testOutputs/test_setAuditWriter_output.txt");
		String result = election.setAuditWriter(false);
		
		assertEquals(result, "testSet");
	}
	
	/**
	 * This test checks that when testing is in process, the auditWriter writes
	 * to the test audit file by appending to it.
	 * @throws IOException
	 * @result auditWriter appends to the test audit file
	 */
	@Test
	public void testSetAuditWriterTest() throws IOException {
		File[] files = {
				new File("../testing/testWriteFile1.csv")
		};
		Election election = new Election("STV", 2, files, true);
		election.setTesting("../testing/testOutputs/test_setAuditWriter_output.txt");
		election.setAuditWriter(false);
		String result = election.setAuditWriter(false);
		
		assertEquals(result, "test");
	}
	
	/**
	 * This is a manual test. This test runs an STV election using the provided
	 * test files in files variable. The results are saved to
	 * VotingSystem_AuditFile_Group1.txt and should be visually inspected to
	 * verify the functionality is working.
	 * @throws IOException
	 * @result STV election processed and a full audit file is available
	 */
	@Test
	public void runSTVelection() throws IOException {
		File[] files = {
				new File("../testing/testWriteFile1.csv"),
				new File("../testing/testWriteFile2.csv"),
				new File("../testing/testWriteFile3.csv")
		};
		Election election = new Election("STV", 2, files, true);
		election.processElection();
		assertTrue(true);
	}
	
	/**
	 * This is a manual test. This test runs a Plurality election using the
	 * provided test file in the files variable. The results are saved to
	 * VotingSystem_AuditFile_Group1.txt and should be visually inspected to
	 * verify the functionality is working.
	 * @throws IOException
	 * @result Plurality election processed and a full audit file is available
	 */
	@Test
	public void runPluralityElection() throws IOException {
		File[] files = {
				new File("../testing/electionPluralityTest1.csv")
		};
		Election election = new Election("Plurality", 2, files, true);
		election.processElection();
		assertTrue(true);
	}
	
	// -------------------------------------------------------------------------
	// Test getters
	// -------------------------------------------------------------------------
	
	/**
	 * This test checks that the voteType variable is properly retrieved from
	 * the election object.
	 * @param voteType - vote type testing values
	 * @throws IOException
	 * @result the vote type matches the vote type value passed to the election
	 * 	constructor.
	 */
	@ParameterizedTest
	@ValueSource(strings = { "STV", "Plurality", "aslfjasd#@%", ""})
	public void testGetVoteType(String voteType) throws IOException {
		Election election = generateTestElection(voteType);
		String result = election.getVoteType();
		assertEquals(voteType, result);
	}
	
	/**
	 * This test checks that the numSeats variable is properly retrieved from
	 * the election object
	 * @param testValue - number of seats testing values
	 * @throws IOException
	 * @result the number of seats matches the number of seats passed to the
	 * election constructor.
	 */
	@ParameterizedTest
	@ValueSource(ints = {0, 1, 2, 3, 4, 7, 11, 99, 999})
	public void testGetNumSeats(int testValue) throws IOException {
		File[] files = {
				new File("../testing/testWriteFile1.csv"), 
				new File("../testing/testWriteFile2.csv")
		};
		Election election = new Election("STV", testValue, files, true);
		
		int result = election.getNumSeats();
		
		assertEquals(testValue, result);
	}
	
	/**
	 * This test checks that the number of candidates matches the number of 
	 * candidates found in the first line of the files.
	 * @param testValue - test string of candidates, comma delimited.
	 * @throws IOException
	 * @result the number of candidates matches the number of candidates in the
	 * 	comma delimited string passed to election.setCandidates().
	 */
	@ParameterizedTest
	@ValueSource(strings = {
			"a,b,c,d,e,f", "ab,cd", "Jake, Sami, Declan, Allison",
			"a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p"
	
	})
	public void testGetNumCandidates(String testValue) throws IOException {
		// setup
		Election election = generateTestElection("STV");
		
		String[] split = testValue.split(",");
		int expected = split.length;
		
		// set candidates
		election.setCandidates(testValue);
		LinkedHashSet<Candidate> candidates = election.getCandidates();
		
		assertEquals(expected, candidates.size());
	}
		
	/**
	 * This test checks that the shuffle value matches the value passed to the
	 * election constructor.
	 * @param testValue - test shuffle values
	 * @throws IOException
	 * @result election.getShuffle() matches the value passed to election's
	 * 	constructor.
	 */
	@ParameterizedTest
	@ValueSource(booleans = { true, false })
	public void testGetShuffle(boolean testValue) throws IOException {
		File[] files = {
				new File("../testing/testWriteFile1.csv"), 
				new File("../testing/testWriteFile2.csv")
		};
		Election election = new Election("STV", 2, files, testValue);
		
		assertEquals(testValue, election.getShuffle());
	}

	/**
	 * This is a helper method to create a general test election.
	 * 
	 * @param voteType - the type of election to use
	 * @throws IOException
	 * @return an election object
	 */
	private Election generateTestElection(String voteType) throws IOException {
		File[] files = new File[2];
		Election election = new Election(voteType, 4, files, true);
		return election;
	}
	
}
