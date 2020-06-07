import java.util.*;
import java.io.*;
import java.lang.Math;


/**
 * <h1>Election Class</h1>
 * The Election class is the main hub for the Voting System program. The
 * Election class is initialized from the UI class. The Election class is built
 * to manage the processing of an election, and is a source of data and
 * operations for the STV and Plurality classes.
 * <p>
 * Election creates a list of ballots from the given files, kicks off the
 * process for either an STV or Plurality election respectively, and handles
 * all of the actions surrounding writing to the audit file.
 * 
 * @author  Jake Waro
 * 
 * */
public class Election {
	private String voteType; 	// type of vote
	protected int numSeats;		// number of seats up for election	
	private File[] files;		// files of ballots
	protected boolean shuffle = true;	// switch to control shuffling (STV)
	protected int numBallots;	// number of ballots from files
	protected Queue<Candidate> winners;
	protected Stack<Candidate> losers;
	protected LinkedHashSet<Candidate> candidates;
	protected int numCandidates = 0;
	private LinkedHashSet<Ballot> ballotList;
	protected File auditFile;		// audit file report
	private File invalidatedFile;	// invalidated ballots
	private int invalidatedCount = 0;
	protected int droopQuota = 0;	// STV only
	protected File tempFile;		// temporary audit report during processing
	protected PrintWriter auditWriter;
	private PrintWriter invalidatedWriter = null;// object to write the report
	private String[] testing = {"", ""};	// testing switch

	/**
	 * This is Election's constructor. The constructor is responsible for
	 * setting the majority of the class level variables.
	 * 
	 * @param voteType - the type of election (STV or Plurality).
	 * @param numSeats - number of seats up for election.
	 * @param files - the files holding the ballots for election processing.
	 * @param shuffle - switch for shuffling the ballots
	 */
	Election(String voteType, int numSeats, File[] files, boolean shuffle) throws FileNotFoundException {
		this.voteType = voteType;
		this.numSeats = numSeats;
		this.files = files;
		this.shuffle = shuffle;

		// initialize list(s), stack(s), queue(s)
		losers = new Stack<Candidate>();
		ballotList = new LinkedHashSet<Ballot>();
		candidates = new LinkedHashSet<Candidate>();
		// since queue is an interface, we instantiate specifically as a LL
		winners = new LinkedList<Candidate>();
		
		// Create new files
		auditFile = new File("../ElectionResults_AuditFile.txt");
		tempFile = new File("../VotingSystem_InProcess.txt");
		invalidatedFile = new File("../Invalidated_Ballots.txt");
		
		// clear anything from the files that may be left over from previous testing
		PrintWriter clearer = new PrintWriter("../ElectionResults_AuditFile.txt");
		clearer.print("");
		clearer.close();
		clearer = new PrintWriter("../VotingSystem_InProcess.txt");
		clearer.print("");
		clearer.close();
		clearer = new PrintWriter("../Invalidated_Ballots.txt");
		clearer.print("");
		clearer.close();

	}

	/**
	 * This method kicks off the processing of the election. This is a public
	 * method, and should be called from the UI class once the input has been
	 * confirmed.
	 * 
	 * @return string value describing the type of election used. For testing
	 * purposed.
	 * @throws IOException for opening / closing files.
	 */
	public String processElection() throws IOException {
		// read from files, and generate the ballot list and candidates
		createBallotList();

		// set writer object for STV and Plurality to use
		try {
			auditWriter = new PrintWriter(new BufferedWriter(new FileWriter(tempFile, true)));
		} catch (IOException e) {
			System.out.println(e);
		}
		
		if (voteType.equals("STV")) {					// STV
			STV stv = new STV(this);
			stv.evaluate();
			auditWriter.close();
			writeFinalResults();
			return "STV";
		} else if (voteType.equals("Plurality")) {		// Plurality
			Plurality pl = new Plurality(this);
			pl.distributeVotes();
			auditWriter.close();
			writeFinalResults();
			return "Plurality";
		} else {										// Invalid Election
			throw new IllegalArgumentException("Invalid vote type");
		}

	}

	// set to private after testing
	/**
	 * This method is responsible for creating the original ballot list, by
	 * reading each ballot from files and creating new ballot objects. Upon
	 * completion of this method, the candidates object will be set to a value,
	 * each ballot will have a unique ID, and each ballot will be written to the
	 * temporary file.
	 * 
	 * @throws IOException for opening / closing files.
	 *
	 * */
	private void createBallotList() throws IOException {

		boolean header = false; // flip to true once candidates are set

		// 1. create the ballotList
		// 2. write the initial ordering of ballots to the temporary file
		for (File file: files) {

			// verify the file is actually of type File
			if (file.isFile()) {
				int lineNumber = 2;
				try {
					BufferedReader fileReader = new BufferedReader(new FileReader(file));

					// ignore the header after we've set candidates
					if (header) {
						fileReader.readLine();
					} else {
						header = true;
						setCandidates(fileReader.readLine());
					}

					// the line from the fileReader
					String line = null;

					// set the audit writer
					try {
						auditWriter = new PrintWriter(new BufferedWriter(new FileWriter(tempFile, true)));
					} catch (IOException e) {
						System.out.println(e);
					}

					if (voteType.equals("STV")) {
						// Read each ballot record from the STV file
						while ((line = fileReader.readLine()) != null) {
							// ballot will only be added to the list to be evaluated if it is valid
							boolean valid = isValid(line);
							if (valid) {
								Ballot bal = addBallotToList(line);
								writeToAudit(bal, "Initial reading of ballot: ");
							} else {
								writeInvalidatedBallot(line, lineNumber, file.getName());
							}
							lineNumber++;
						}
					} else {
						// Read each ballot record from the plurality file
						while ((line = fileReader.readLine()) != null) {
							Ballot bal = addBallotToList(line);
							writeToAudit(bal, "Initial reading of ballot: ");
						}
					}

					// turn of the audit writer if testing
					if (testing[0].equals("")) auditWriter.close();

					fileReader.close(); // close reader stream

				} catch (Exception e) {
					throw e;
				}
			} else {
				throw new FileNotFoundException("Files contains element that is not a file.");
			}

		}
		
		// close invalidated writer
		if (invalidatedWriter != null) {
			invalidatedWriter.close();
		}
		
		// set the number of ballots
		numBallots = ballotList.size();
	}
	
	// set to private after testing
	/**
	 * This method will take a ballot line from an STV input file
	 * and determine if it is valid or invalid. To be invalid at this point,
	 * the ballot would need to have less than half of the candidates ranked.
	 * For example if there are 4 candidates running, 2 or more candidates
	 * must be ranked. If there are an odd number of candidates running, the
	 * ceiling of (# candidates / 2) should be used.
	 *
	 * @param line - a ballot line that has been read from an input file.
	 * @return true/false - will return true if the ballot is valid, will
	 * 						return false if the ballot is invalid.
	 * */
	private boolean isValid(String line) {
		double numRanked = 0;
		// Uses the ceiling to account for the case with an odd number of candidates
		double halfCand = Math.ceil((double) numCandidates / 2.0);

		String voteLine[] = line.split(",", -1);
		for(int i = 0; i < voteLine.length; i++){
			if(!voteLine[i].equals("") && voteLine[i] != null ){
				numRanked++;
			}
		}
		if (numRanked >= halfCand){
			return true;
		} else {
			return false;
		}

	}

	// set to private after testing
	/**
	 * This method takes the first line from the first file as the list of 
	 * candidates. It parses the candidates and sets the value of candidates
	 * to include each respective candidate.
	 * 
	 * @param header - string of candidates, separated by commas
	 * @return String value of the candidate line that appears in the audit
	 * 			file for testing.
	 * @throws IOException for opening / closing files.
	 */
	private String setCandidates(String header) throws IOException {
		// set audit writer
		if (!testing[0].equals("")) {
			setAuditWriter(false);
		} else {
			auditWriter = new PrintWriter(new BufferedWriter(new FileWriter(auditFile)));
		}

		StringBuilder sb = new StringBuilder(" - Candidates: ");
		String[] split = header.split(",");
		
		// build audit report string of candidates
		for (int i = 0; i < split.length; i++) {
			this.candidates.add(new Candidate(split[i]));
			if (i == split.length - 1) {
				sb.append(split[i]);
			} else {
				sb.append(split[i] + ", ");
			}
		}
		
		numCandidates = candidates.size();
		
		// write the candidates to the file
		auditWriter.println("-------------------------------------------------------");
		auditWriter.println("-----------    ELECTION INFORMATION   -----------------");
		auditWriter.println("-------------------------------------------------------");
		auditWriter.println(sb.toString());
		auditWriter.close();

		return sb.toString();

	}

	// change to private after testing
	/**
	 * This method creates a new ballot object created from a line from the
	 * files containing the ballots, and adds it to the ballot list.
	 * 
	 * @param record - ballot line from the ballot files.
	 * @return ballot object to be written to the temporary audit file.
	 */
	private Ballot addBallotToList(String record) {
		Ballot bal = new Ballot(candidates, record);
		ballotList.add(bal);

		return bal;
	}

	/**
	 * This method writes a description, along with the associated ballot to the
	 * temporary audit file. This is used while creating both the original 
	 * readings of ballots as well as during processing by STV and Plurality
	 * classes.
	 * 
	 * @param bal - ballot to be written
	 * @param description - description that prefixes the ballot
	 */
	protected void writeToAudit(Ballot bal, String description) {

		setAuditWriter(false); // set the audit writer

		StringBuilder sb = new StringBuilder(description);
		sb.append(" ID=(" + bal.getBallotNum() + ")"); // extract the ballot ID

		// get the ballot's votes
		Map<Integer, Candidate> votes = bal.getVotes();

		// format the ballot description
		for (Integer rank: votes.keySet()) {
			sb.append("  " + votes.get(rank).getName() + ": " + String.valueOf(rank));
		}
		auditWriter.println(sb.toString()); // write to file

		// if testing, turn off the audit writer
		if (!testing[0].equals("")) {
			auditWriter.close();
		}

	}

	/**
	 * This is an overloaded method, accepting only a description. This method
	 * is to be used by STV or Plurality when only a description is needed to be
	 * written to the audit file.
	 * 
	 * @param description - description of what happened during election
	 * 			processing.
	 */
	protected void writeToAudit(String description) {
		setAuditWriter(true);

		auditWriter.println(description);

		auditWriter.close();
	}
	
	// set to private after testing
	/**
	 * This method writes invalidated ballots to the invalidated ballots report
	 * file.
	 * 
	 * @param description - description of what happened during election
	 * 			processing.
	 */
	private void writeInvalidatedBallot(String description, int lineNumber, String filename) throws IOException {
		invalidatedCount++;
		
		if (invalidatedWriter == null) {
			invalidatedWriter = new PrintWriter(new BufferedWriter(new FileWriter(invalidatedFile)));
			invalidatedWriter.println("-------------------------------------------------------");
			invalidatedWriter.println("-------------    Invalidated Ballots    ---------------");
			invalidatedWriter.println("-------------------------------------------------------");
			invalidatedWriter.println();
		}
		StringBuilder output = new StringBuilder();
		output.append("Invalid ballot number " + invalidatedCount);
		output.append(": " + description);
		output.append(" | Line number " + lineNumber);
		output.append(" of file: " + filename);
		
		invalidatedWriter.println(output.toString());
	}

	// set to private after testing
	/**
	 * This method writes the final results of the election to the audit file.
	 * It contains all pertinent information to the election: type of election,
	 * number of seats, number of candidates, number of ballots, the winners,
	 * the losers, etc.
	 * 
	 * The final audit file is saved in "VotingSystem_AuditFile_Group1.txt"
	 * 
	 * @throws IOException for opening / closing files.
	 */
	private void writeFinalResults() throws IOException {
		try {
			auditWriter = new PrintWriter(new BufferedWriter(new FileWriter(auditFile, true)));
		} catch (IOException e) {
			throw e;
		}
		
		// General Election information
		auditWriter.println(" - Number of candidates: " + numCandidates);
		auditWriter.println(" - Number of election seats: " + numSeats);
		auditWriter.println(" - Type of election: " + voteType);
		auditWriter.println(" - Number of ballots: " + numBallots);
		auditWriter.println();
		
		// Election results
		auditWriter.println("-------------------------------------------------------");
		auditWriter.println("-----------------    RESULTS    -----------------------");
		auditWriter.println("-------------------------------------------------------");
		auditWriter.println();
		if (voteType.equals("STV")) {
			auditWriter.println();
			auditWriter.println(" Droop Quota: " + droopQuota);
			auditWriter.println();
		}
		auditWriter.println(" Winner(s):");
		int spot = 1;
		if (voteType.equals("STV")) {
			auditWriter.println("  (In order of winning)");
			for (Candidate candidate: winners) {
				auditWriter.println("  " + spot + ". " + candidate.getName());
				spot++;
			}
		} else {
			for (Candidate candidate: winners) {
				auditWriter.println("  " + spot + ". " + candidate.getName() + "  (" + candidate.getVoteCount() + " votes)");
				spot++;
			}
		}
		auditWriter.println();
		auditWriter.println(" Loser(s):");
		spot = 1;
		if (voteType.equals("STV")) {
			auditWriter.println("  (In order of losing)");
			for (Candidate candidate: losers) {
				auditWriter.println("  " + spot + ". " + candidate.getName());
				spot++;
			}
		} else {
			for (Candidate candidate: losers) {
				auditWriter.println("  " + spot + ". " + candidate.getName() + "  (" + candidate.getVoteCount() + " votes)");
				spot++;
			}
		}

		// copy over election processing information from the temporary file.
		auditWriter.println();
		auditWriter.println("-------------------------------------------------------");
		auditWriter.println("----------------    PROCESSING   ----------------------");
		auditWriter.println("-------------------------------------------------------");
		auditWriter.println();

		// copy over from tempFile
		try {
			BufferedReader fileReader = new BufferedReader(new FileReader(tempFile));

			String line = null;

			// Read each ballot record from tempFile
			while ((line = fileReader.readLine()) != null) {
				auditWriter.println(line);
			}

			fileReader.close(); // close reader stream

		} catch (Exception e) {
			throw e;
		}

		auditWriter.flush();
		auditWriter.close(); // close auditWriter
	}
	
	// set to private after testing
	/**
	 * This method is used to set the audit writer to write to the correct file.
	 * If testing, it will write to the test file location, otherwise it will
	 * write to the temporary file
	 * 
	 * @param alwaysSet - decide if we should always be setting a new audit
	 * 			writer.
	 * @return string value to indicate where the audit writer is set. Used for
	 * 			testing.
	 */
	private String setAuditWriter(boolean alwaysSet) {
		if (!testing[0].equals("")) {
			try {
				// we should overwrite file
				if (testing[1].equals("")) {
					auditWriter = new PrintWriter(new BufferedWriter(new FileWriter(testing[0])));
					testing[1] = "set"; // set so we do not overwrite next time.
					return "testSet";
				} else { // append to file
					auditWriter = new PrintWriter(new BufferedWriter(new FileWriter(testing[0], true)));
					return "test";
				}
			} catch (IOException e) {
				System.out.println(e);
			}
		} else if (alwaysSet) {
			try {
				auditWriter = new PrintWriter(new BufferedWriter(new FileWriter(tempFile, true)));
				return "temp";
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return "";
	}

	/**
	 * Getter method for voteType.
	 * @return type of election.
	 */
	public String getVoteType() {
		return voteType;
	}

	/**
	 * Getter method for numSeats.
	 * @return number of seats in election.
	 */
	public int getNumSeats() {
		return numSeats;
	}

	/**
	 * Getter method for numBallots
	 * @return number of ballots.
	 */
	public int getNumBallots() {
		return numBallots;
	}

	/**
	 * Getter method for numCandidates.
	 * @return number of candidates.
	 */
	public int getNumCandidates() {
		return numCandidates;
	}

	/**
	 * Getter method for winners.
	 * @return winners queue.
	 */
	public Queue<Candidate> getWinners() {
		return winners;
	}

	/**
	 * Getter method for losers.
	 * @return losers stack.
	 */
	public Stack<Candidate> getLosers() {
		return losers;
	}
	
	/**
	 * Getter method for ballotList
	 * @return list of ballots.
	 */
	public LinkedHashSet<Ballot> getBallotList() {
		return ballotList;
	}

	/**
	 * For testing. Gets the files object.
	 * @return files.
	 */
	public File[] getFiles() {
		return files;
	}

	/**
	 * For testing. Gets the shuffle object.
	 * @return shuffle.
	 */
	public boolean getShuffle() {
		return shuffle;
	}

	/**
	 * For testing. Gets the candidates object.
	 * @return candidates.
	 */
	public LinkedHashSet<Candidate> getCandidates() {
		return candidates;
	}

	/**
	 * For testing. Sets the audit file location to be a test file path.
	 * @param testFile - path to test file.
	 */
	public void setTesting(String testFile) {
		this.testing[0] = testFile;
	}

}
