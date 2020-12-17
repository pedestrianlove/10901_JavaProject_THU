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
	public String[][] data;
	public JFrame frame;
	public JLabel[][] label;
	public JTextField textField;
	public JButton[][] l;
	public int BAD;

	
	
	/**
	 * Create the application.
	 */
	public Interface() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		
		frame = new JFrame();
		frame.setBackground(Color.LIGHT_GRAY);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.getContentPane().setLayout(null);
		frame.setBounds(100, 100, 530, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	}
}
