import java.io.File;
import java.io.IOException;
import java.util.Scanner;

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
public class UI {
    // boolean testing is used when unit testing the program, remove the need
    // for user input.
    public static boolean testing = false;
    // Used for testing so you know before the program exits whether or not
    // you are supposed to exit.
    private static boolean quitProgram;
    // Keeps track throughout the entire program on whether or not you want to
    // restart the program or not.
    private static boolean restartProgram;
    private static String voteType; // type of vote
    private static String numSeats; // number of seats up for election
    private static File[] files; // files of ballots
    private static Election election; // Election object that will store user input
    private static Scanner sc = new Scanner(System.in); // Scanner for user input
    private static String helpMessage = "For help please type 'h' and press enter.\n"
            + "To exit please type 'x' and press enter."; // Message that gets called in every prompt

    /**
     * This is the UI Class' main function that will be run when you run this file
     * it is in a do-while loop in case restart program is toggled on else it will
     * just end.
     * 
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        do {
            runUI();
        } while (restartProgram);
    }

    /**
     * runUI runs the acutual election, this will go through each prompt and ask for
     * user input, or take in a string if you are in testing mode.
     * 
     * @throws IOException
     */
    private static void runUI() throws IOException {

        // Sets this to false until the displayHelp() is called.
        restartProgram = false;
        quitProgram = false;

        // Prompts the the of vote.
        promptVoteType("");
        if (restartProgram) // breaks if restartProgram is toggled to true by the method above.
            return;
        // Prompts the number of seats up for election
        promptNumSeats("");
        if (restartProgram) // breaks if restartProgram is toggled to true by the method above.
            return;
        // Prompts the ballots files you are using in the election.
        promptFiles("");
        if (restartProgram) // breaks if restartProgram is toggled to true by the method above.
            return;
        // Prompts whether what you entered is correct, if not restart the whole
        // process.
        confirmInput("");
        if (restartProgram) // breaks if restartProgram is toggled to true by the method above.
            return;
        // Prints out the results from the election.
        displayResults();
    }

    /**
     * This method prompts which ballot files are going to be in the election.
     * 
     * @param input - used during testing, the file(s) name being passed in.
     */
    private static void promptFiles(String input) {
        System.out.println("");
    	String message = "Please enter the file name(s) to be used \n(File names should be separated by space):\n"
                + helpMessage;
        System.out.println(message);
        boolean invalid = false; // invalid is a boolean that is used to check if the sysInput is valid for its
                                 // given context.
        String sysInput; // system input String
        File[] filesArr; // array of File objects used if you enter multiple files.
        do {
            if (testing) { // UITest
                sysInput = input;
            } else {
                sysInput = sc.nextLine();
            }
            if (sysInput.equals("h")) { // Help
                displayHelp(null);
                invalid = false;
                return;
            } else if (sysInput.equals("x")) { // Quit
                quitProgram(true);
                invalid = false;
                return;
            } else { // Strings of the name of ballot files to be converted to ballot files.
                String[] inputFiles = sysInput.split(" ");
                filesArr = new File[inputFiles.length];
                for (int i = 0; i < inputFiles.length; i++) {
                    File file = new File("../" + inputFiles[i]); // Pulls from ./Project/ directory to
                                                                                 // access the Ballot files there.
                    if (!file.exists()) { // If the file does not exits Print(illegalArg(fileName))
                        System.out.println(illegalArg(sysInput));
                        invalid = true; // invalid is set to true so it will reloop again ask for the file(s) again.
                    } else { // Adds the current file to the array of files.
                        filesArr[i] = file;
                        invalid = false;
                    }
                }
            }
        } while (invalid);
        files = filesArr; // Sets the global variable of files to the contents of filesArr
    }

    /**
     * This method prompts what type of election is to be had.
     * 
     * @param input - used during testing, either a 1 or 2 depending on the election
     *              type.
     */
    private static void promptVoteType(String input) {
        System.out.println("Welcome to the Voting System.\n\n"
                + "Please enter the [1] for Plurality, or [2] for STV :\n" + helpMessage);
        boolean invalid; // If you enter an illegal character for the string it will set invalid to be
                         // true and have you reloop and try again.
        String sysInput;
        do {
            if (testing) { // UITest
                sysInput = input;
            } else {
                sysInput = sc.nextLine();
            }
            if (sysInput.equals("1")) { // Plurality
                voteType = "Plurality"; // Sets voteType to Plurality.
                invalid = false;
            } else if (sysInput.equals("2")) { // STV
                voteType = "STV"; // Sets voteType to STV.
                invalid = false;
            } else if (sysInput.equals("h")) { // displayHelp
                displayHelp(null);
                invalid = false;
                return;
            } else if (sysInput.equals("x")) { // quitProgram
                quitProgram(true);
                invalid = false;
                return;
            } else { // If it does not match any of the cases above then it is considered invalid and
                     // will ask you to reloop.
                invalid = true;
                System.out.println(illegalArg(sysInput));
            }
        } while (invalid);
    }

    /**
     * This method asks for the number of seats in the election and sets numSeats to
     * that number.
     * 
     * @param input - used during testing, the number of seats up during the
     *              election.
     */
    private static void promptNumSeats(String input) {
    	System.out.println("");
        String message = "Please enter the number of seat(s): \n" + helpMessage;

        System.out.println(message);
        boolean invalid; // If you enter an illegal character for the string it will set invalid to be
                         // true and have you reloop and try again.
        String sysInput;
        do {
            if (testing) { // UITest
                sysInput = input;
            } else {
                sysInput = sc.nextLine();
            }
            if (isNumeric(sysInput)) { // isNumeric will return true if the string passed in by sysInput is a number or
                                       // not.
                numSeats = sysInput; // Sets the number of seats to sysInput.
                invalid = false;
            } else if (sysInput.equals("h")) { // displayHelp
                displayHelp(null);
                invalid = false;
                return;
            } else if (sysInput.equals("x")) { // quitProgram
                quitProgram(true);
                invalid = false;
                return;
            } else {
                invalid = true;
                System.out.println(illegalArg(sysInput)); // If it does not match any of the cases above then it is
                                                          // considered invalid and will ask you to reloop.
            }
        } while (invalid);
    }

    /**
     * This method is ran after all election prompts have gone. It will ask for a
     * confirmation that the information entered before is correct.
     * 
     * @param input - used during testing, either a 1 or 2 depending on the election
     *              type.
     * @return boolean - used during testing, if confirmation runs fine then it will
     *         return true, else false that something unexpected happened.
     * @throws IOException
     */
    private static boolean confirmInput(String input) throws IOException {
    	System.out.println("");
        boolean returnValue; // return value at the end that gets changed depending on whether or not the
                             // method runs properly.
        String fileNames = ""; // names of the files that you set in promptFiles.
        for (File file : files) {
            fileNames = fileNames + file.getName() + " ";
        }

        String message = "Please confirm data entered:\n" + "Algorithm type: " + voteType + "\n"
                + "Number of seats to fill: " + numSeats + "\n" + "file(s) entered: " + fileNames + "\n"
                + "1. Yes, run 2. No, re-enter data";

        System.out.println(message);
        boolean invalid; // If you enter an illegal character for the string it will set invalid to be
                         // true and have you reloop and try again.
        String sysInput;
        do {
            if (testing) { // UITest
                sysInput = input;
            } else {
                sysInput = sc.nextLine();
            }
            if (sysInput.equals("1")) { // Confirm the input you entered above and creates a new election with that
                                        // information.
                election = new Election(voteType, Integer.parseInt(numSeats), files, true);
                invalid = false;
                election.processElection();
                returnValue = true; // Sets to true so method will return true at the end.
            } else if (sysInput.equals("2")) { // Confirms that the input you entered above is not correct and restrats
                                               // the program.
                restartProgram = true;
                invalid = false;
                returnValue = true;
            } else {
                invalid = true;
                returnValue = false;
                System.out.println(illegalArg(sysInput)); // If it does not match any of the cases above then it is
                                                          // considered invalid and will ask you to reloop.
            }
        } while (invalid);
        return returnValue;
    }

    /**
     * This method displays the helpmenu that will go over how to enter information
     * in each prompt.
     * 
     * @param input - used during testing, passes in a 'x' which means you should
     *              restart the program to the beginning.
     */
    private static void displayHelp(String input) {
        boolean invalid = false; // If you enter an illegal character for the string it will set invalid to be
                                 // true and have you reloop and try again.
        String message = "Help Menu.\n" + "(Type 'x' and press enter to exit help menu)" + "\n"
                + "Algorithm type: The only input accepted will be the number 1 or the number 2.\n"
                + "1 indicates the Plurality algorithm.\n" + "2 indicates the STV algorithm\n"
                + "After the section is inputted into the prompt, perss enter to continue.\n"
                + "Entering the number of seats to fill: Only integers will be accepted."
                + "After the number is inputted into the promt, press enter to continue."
                + "Entering file names(s): Files should be inputted into the prompt as one string"
                + "where each file name is separated by a space. "
                + "press enter when complete to move onto the next step\n"
                + "Confirming entered data: the only input accepted will be the number 1 or the number 2.\n"
                + "After the selection is inputted into the prompt, press enter to continue.\n"
                + "1 indicates that all the data is correct, and the next step will be to run "
                + "the specified algorithm given the data inputted.\n"
                + "2 indicates that some or all of the data is incorrect. "
                + "This selection will restart the initial data entry\n.";
        System.out.println(message);
        String sysInput;
        do {
            if (testing) { // UITest
                sysInput = input;
            } else {
                sysInput = sc.nextLine();
            }
            if (sysInput.equals("x")) { // Sets invalid to false so you can exit the while-loop and set restartProgram
                                        // to true.
                invalid = false;
            } else {
                invalid = true;
                System.out.println(illegalArg(sysInput)); // If it does not match any of the cases above then it is
                                                          // considered invalid and will ask you to reloop.
            }
        } while (invalid);
        restartProgram = true; // Sets restartProgram to true so you restart the program from the first prompt.
    }

    /**
     * This method ends the program.
     * 
     * @param quit - used during testing, this is used if the program is exited
     *             properly then it will return true.
     */
    private static void quitProgram(boolean quit) {
        // Logout out of comandprompt

        // This conditional is only to be run
        // if you are testing the method.
        if (testing) {
            quitProgram = quit;
            return; // If you are testing you can't quit the program the regular way because the
                    // test will not pass/ fail.
        }
        if (quit) { // Closes program if quit is true, which is should always be if you call this
                    // method.
            System.exit(0);
        } else { // If you use this method and pass in false as the param then you will be
                 // greeted with an illegalArgumentExpection.
            String boolString = "" + quit;
            System.out.println(illegalArg(boolString));
        }
    }

    /**
     * This method displays the Results of the election, getting all the information
     * about the election and printing it on screen.
     * 
     * @return boolean - used during testing, this is used to see if the function
     *         has a return type of true meaning it ran successfully, or if an error
     *         is caught it will return false meaning that the method failed at some
     *         point.
     */
    private static boolean displayResults() {
        boolean returnValue; // return value used during testing.
        String winners = "", losers = ""; // Strings of election winners and losers.
        try {
            for (Candidate candidate : election.getWinners()) {
                winners = winners + candidate.getName() + " ";
            }
            for (Candidate candidate : election.getLosers()) {
                losers = losers + candidate.getName() + " ";
            }

            String message = "Results:\n" + "\n" + "Election Type: " + election.getVoteType() + "\n"
                    + "Number of Ballots: " + election.getNumBallots() + "\n" + "Number of Seats: "
                    + election.getNumSeats() + "\n" + "Number of Candidates: " + election.getNumCandidates() + "\n"
                    + "Winner(s): " + winners + "\n" + "Loser(s): " + losers + "\n"
                    + "See \"ElectionResults_AuditFile.txt\" in the Project1 directory "
                    + "for an audit log of the Voting System's tabulation.\n";
            System.out.println(message);
            returnValue = true; // if it gets to here it sets returnValue to true because no erros were thrown
                                // when trying to pull the information.
        } catch (Exception e) {
            returnValue = false; // error caught while trying to get the print message, sets returnValue to false
                                 // because the method did not run how it was intended too.
        }
        return returnValue;
    }

    /**
     * This method takes in a str and checks if the string is a number or not
     * 
     * @param str - string to be checked if it a str of just numbers or not.
     * @return boolean - returns true if it is a number, and false if it is not a
     *         number.
     */
    private static boolean isNumeric(final String str) {

        // null or empty
        if (str == null || str.length() == 0) {
            return false;
        }

        return str.chars().allMatch(Character::isDigit);

    }

    /**
     * This method is used to print all the IllegalArgumentExceptions throughout the
     * program.
     * 
     * @param sysInput - string to be added to the meesage thrown by
     *                 IllegalArgumentException.
     * @return Throwable - throws a new IllegalArgumentException if the argument
     *         you're trying to use does not match one of the predetermined cases.
     */
    private static Throwable illegalArg(String sysInput) {
        return new IllegalArgumentException(sysInput + " is not a valid argument.\nTry again.");
    }
}
