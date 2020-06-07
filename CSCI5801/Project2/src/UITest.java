import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.io.File;
import java.io.IOException;

/**
 * <h1> UI Class </h1>
 * The UI class is where a user can go to input data to run a election.
 * The UI class uses the election class to process the election that you
 * defined using the user interface.
 */

/**
 * @author Declan Buhrsmith
 *
 */
class UITest {

	@Test
	void testVoteTypePlurality() {
		UI.testing = true; // Must be true for tests to work properly.
		UI.promptVoteType("1");
		assertEquals(UI.voteType, "Plurality");
	}

	@Test
	void testVoteTypeSTV() {
		UI.testing = true; // Must be true for tests to work properly.
		UI.promptVoteType("2");
		assertEquals(UI.voteType, "STV");
	}
	
	/** 
	 * @param value - Values for @ValueSource to be tested when ran.
	 */
	@ParameterizedTest
	@ValueSource(strings = { "0", "1", "2", "4", "11", "999", "1000", "999999", "1000000" })
	void testNumSeats(final String value) {
		UI.testing = true; // Must be true for tests to work properly.
		UI.promptNumSeats(value); // Sets the number of seats in the election to the string value provided by @ValueSource
		assertEquals(UI.numSeats, value);
	}

	@Test
	void testFilesGUI(){
		UI.testing = true; // Must be true for tests to work properly.
		UI.promptFiles("f");
		assertEquals(UI.quitProgram, false);
	}

	@Test
	void testFilesMultiple(){
		String uiFileNames = "";
		String testFileNames = "";
		
		UI.testing = true; // Must be true for tests to work properly.
		File testFile1 = new File("./testing/testWriteFile1.csv");
		File testFile2 = new File("./testing/testWriteFile2.csv");
		testFileNames = testFile1.getName() + " " +
		testFile2.getName() + " ";

		UI.promptFiles("./testing/testWriteFile1.csv ./testing/testWriteFile2.csv");
		for (File f : UI.files) {
			uiFileNames = uiFileNames + f.getName() + " ";
		}
		assertEquals(uiFileNames, testFileNames);
	}

	@Test
	void testFilesMultipleExsitsFileMismatch(){
		UI.testing = true; // Must be true for tests to work properly.
		File testFile1 = new File("./testing/testWriteFile2.csv");
		File testFile2 = new File("./testing/testWriteFile3.csv");
		File[] fileList = new File[]{testFile1,testFile2};
		UI.promptFiles("./testing/testWriteFile1.csv ./testing/testWriteFile2.csv");
		assertNotEquals(UI.files, fileList);
	}

	@Test
	void testFilesExsitsFileMismatch(){
		UI.testing = true; // Must be true for tests to work properly.
		File testFile = new File("./testing/testWriteFile2.csv");
		UI.promptFiles("./testing/testWriteFile1.csv");
		assertNotEquals(UI.files[0], testFile);
	}

	@Test
	void testTypeQuitProgram() {
		UI.testing = true; // Must be true for tests to work properly.
		UI.quitProgram(true); // Quits the program when you pass in true to quitProgram.
		assertEquals(UI.quitProgram, true);
	}

	
	/** 
	 * @throws IOException
	 */
	@Test
	void testDisplayResultsPass() throws IOException {
		//Sets the required parts to what will be passed into the election object.
		UI.testing = true; // Must be true for tests to work properly.
		UI.voteType = "STV";
		UI.numSeats = "1";
		final File file = new File("./testing/testWriteFile2.csv");
		File[] temp = new File[]{file};
		UI.files = temp;
		UI.confirmInput("1"); // passes in a 1 beause that is the value for the confirm results to be true so it will set the election object to the values above.
		
		assertEquals(UI.displayResults(), true);
	}

	
	/** 
	 * @throws IOException
	 */
	@Test
	void testDisplayResultsFails() throws IOException {
		UI.testing = true; // Must be true for tests to work properly.
		assertEquals(UI.displayResults(), false); // Fails because you aren't setting any of the required stuff in the election object so it won't have anything to display.
	}

	@Test
	void testDisplayHelp() {
		UI.testing = true; // Must be true for tests to work properly.
		UI.displayHelp("x"); // if you enter x on the displayHelp prompt it will restart the program.
		assertEquals(UI.restartProgram, true); // Passes because displayHelp sets restartProgram to true if you enter x.
	}

	
	/** 
	 * @param value - Values for @ValueSource to be tested when ran.
	 */
	@ParameterizedTest
	@ValueSource(strings = { "0", "1", "2", "4", "11", "999", "1000", "999999", "1000000" })
	void testIsNumericPass(final String value) {
		assertEquals(UI.isNumeric(value), true); // Checks if the values above are true and will return true because all the values of @ValueSource are only numeric.
	}

	
	/** 
	 * @param value - Values for @ValueSource to be tested when ran.
	 */
	@ParameterizedTest
	@ValueSource(strings = { "asd", "f1f1", "fasg2", "4a2f", "1ff1", "99as9", "100ff0", "999212gh999", "100hfd0000" })
	void testIsNumericFail(final String value) {
		assertEquals(UI.isNumeric(value), false); // Checks if the values above are true and will return false because all the values of @ValueSource are not numeric.
	}

	
	/** 
	 * @throws IOException
	 */
	@Test
	void testConfirmInputRestart() throws IOException {
		UI.testing = true; // Must be true for tests to work properly.
		UI.voteType = "STV";
		UI.numSeats = "1";
		final File file = new File("./testing/testWriteFile2.csv");
		File[] temp = new File[]{file};
		UI.files = temp;

		assertEquals(UI.confirmInput("2"), true); // If you pass in "2" the program will restart setting confirmInput to true because its a valid argument.
	}

	
	/** 
	 * @throws IOException
	 */
	@Test
	void testConfirmInput() throws IOException {
		UI.testing = true; // Must be true for tests to work properly.
		UI.voteType = "STV";
		UI.numSeats = "1";
		final File file = new File("./testing/testWriteFile2.csv");
		File[] temp = new File[]{file};
		UI.files = temp;

		assertEquals(UI.confirmInput("1"), true); // If you pass in "2" the program will restart setting confirmInput to true because its a valid argument.
	}
}
