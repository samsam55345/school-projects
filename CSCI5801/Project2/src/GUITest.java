import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.PrintWriter;

/**
 * <h1>GUI Class</h1>
 * Contains unit tests for testing the GUI.
 *
 * @author Allison Miller
 *
 */
class GUITest {

    /**
     * Manual test to check that the files returned by
     * the GUI are the same files selected by the user.
     *
     * File names written to output file ./testing/test_getFiles.
     */
    @Test
    void test_getFiles() {
        File[] files = GUI.runFileGUI();
        try {
            PrintWriter print = new PrintWriter(new File("./testing/test_getFiles"));
            for (File f : files) print.println(f.getName());
            print.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    /**
     * Check that null is returned when the cancel button is pressed.
     *
     * User should select some files and then press the cancel button.
     *
     * Note: tests must be run one at a time
     */
    @Test
    void test_getFilesNull() {
        File[] files = GUI.runFileGUI();
        assertEquals(null, files);
    }

}
