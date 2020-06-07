# Team 1

## Instructions for running the Voting System from the command line

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1. Connect to a CSE lab machine using ssh

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2. Clone our team repository to the machine and change to the Project1 directory

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3. Load any necessary files for your testing into the ./Project1 directory

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4. Change to the src directory and compile all .java files using the command: **javac UI.java Candidate.java Election.java &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ballot.java VotingAlgorithm.java STV.java Plurality.java**

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5. While in the src directory, run the program with the following command: **java UI**

## For Testing

During testing, we made our current methods / fields that are private to be public for testing. Because we have now changed the production level code to use the private visibility, the test classes will not properly compile. Prior to turning our code back to private, the following command would be used to compile all files, including the test files: **javac -cp .:"./jars/\*" \*.java**

Again, because teh visibility has been changed back to private, the following command will not work. However, if visibility is changed back to public, the following command could be run from the src folder directory:

**java -jar ./jars/junit-platform-console-standalone-1.6.1.jar -cp .:"./jars/\*" --select-class TestFileName**

e.g. if we wanted to test CandidateTest.java, we'd use _--select-class CandidateTest_

## Notes

The audit file that keeps track of the election processing information will be in the Project 1 directory after an election has been processed and will be named "ElectionResults_AuditFile.txt".
