import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

/**
 * <h1> GUI Class </h1>
 * The GUI class contains the method
 * that allows the user to search for and choose
 * ballot files for the election through a simple GUI.
 *
 * @author Allison Miller
 *
 */
public class GUI {

	static JFrame frame = new JFrame();
	static boolean opened = false;
	static JPanel panel = new JPanel();
	static JFileChooser chooseFile = new JFileChooser();
	
    /**
     * Sets up and runs a JFileChooser GUI, allowing the
     * user to visually choose which files they would
     * like to include in the election
     *
     * @return Array of chosen files
     */
    static public File[] runFileGUI() {
    	frame = new JFrame();
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();
        panel.setPreferredSize(new java.awt.Dimension(150,150) );
        
        JFileChooser chooseFile = new javax.swing.JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV files", "csv");  // allow filter for CSV files
        File dir = new File(".");
        chooseFile.setFileFilter(filter);
        chooseFile.setMultiSelectionEnabled(true);  // allow user to select multiple files at once
        chooseFile.setCurrentDirectory(dir);
        
        frame.getContentPane().add(java.awt.BorderLayout.CENTER,panel);

        frame.setSize(0,0);
        frame.setVisible(true);
        
        File[] files = null;
        
        int val = chooseFile.showDialog(panel, "Select");
	    if (val == JFileChooser.APPROVE_OPTION) {
	    	files = chooseFile.getSelectedFiles();
	    	frame.setVisible(false);
	    	frame = null;
	    } else if (val == JFileChooser.CANCEL_OPTION) {
	    	frame.setVisible(false);
	    	frame = null;
	    }
        
        if (files != null && files.length == 0)
            files = null;
        return files;
    }
       
}
