package tw.jsl.JavaProjectTHU;

// objects
import javax.swing.JFrame;
import java.awt.TextField;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Panel;
import java.awt.Point;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JToggleButton;
import java.awt.Button;
import javax.swing.JTextField;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

// event
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import java.util.concurrent.ThreadLocalRandom;

class Interface {
	public JFrame frame;
	public JPanel panel_func, panel_cell;
	public JTextField[][] textField;
	public JButton[] l;
	public FileIO excel;
	public int ROW_SIZE = 100;
	public int COL_SIZE = 100;
	
	
	/**
	 * Create the application.
	 */
	public
	Interface() {
		msg ("Hello, User!");
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void
	initialize() {
		// init frame
		msg ("Initializing frame...");
		frame = new JFrame();
		frame.setBackground(Color.LIGHT_GRAY);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.getContentPane().setLayout(null);
		frame.setBounds(100, 100, 100 + 600, 100 + 800);
		//frame.setExtendedState  (JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		
		// init panels
		msg ("Initializing panel_func...");
		panel_func = new JPanel ();
		panel_func.setBounds(0, 0, 600, 40);
		frame.getContentPane().add(panel_func);
		panel_func.setLayout(null);
		
		msg ("Initializing panel_cell...");
		panel_cell = new JPanel ();
		panel_cell.setBounds(0, 40, 600, 800);
		frame.getContentPane().add(panel_cell);
		panel_cell.setLayout(null);


		// init textfields
		msg ("Initializing textfields...");
		JTextField currentTF;
		//excel = new FileIO ();
		//excel.openFile (null);
		textField = new JTextField[ROW_SIZE][COL_SIZE];
		for (int row = 0; row < ROW_SIZE; row++) {
			for (int col = 0; col < COL_SIZE; col++) {
				currentTF = new JTextField ();
				currentTF.setBounds(row*50,col*50, 50, 50);
				panel_cell.add (currentTF);
				textField[row][col] = currentTF;

			}
		}


	}
	

	private void
	msg (String s) {
		System.out.println (s);
	}

}
