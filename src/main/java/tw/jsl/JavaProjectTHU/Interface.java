package tw.jsl.JavaProjectTHU;

// general
import java.util.ArrayList;
import java.util.Iterator;

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

// POI
//import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;

class Interface {
	public JFrame frame;
	public JPanel panel_func, panel_cell;
	public ArrayList<ArrayList<JTextField>> textField;
	public JButton[] l;
	public FileIO excel;
	
	
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
		
		// init book
		init_book ();

		// init frame
		msg ("Initializing frame...");
		frame = new JFrame();
		frame.setBackground(Color.LIGHT_GRAY);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.getContentPane().setLayout(null);
		frame.setBounds(100, 100, 100 + 600, 100 + 800);
		//frame.setExtendedState  (JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		
		// init panel_func (panel that stores function buttons)
		msg ("Initializing panel_func...");
		panel_func = new JPanel ();
		panel_func.setBounds(0, 0, 600, 40);
		frame.getContentPane().add(panel_func);
		panel_func.setLayout(null);
	
		// init panel_cell (panel that stores cell textfields)
		msg ("Initializing panel_cell...");
		panel_cell = new JPanel ();
		panel_cell.setBounds(0, 40, 600, 800);
		frame.getContentPane().add(panel_cell);
		panel_cell.setLayout(null);

		// init workbook
		msg ("Initializing workbook...");
		init_book ();

		// init textfields
		msg ("Initializing textfields...");

		textField = new ArrayList<ArrayList<JTextField>> ();
		Iterator<ArrayList<JTextField>> textField_row = textField.iterator ();
		Iterator<Row> rowIterator = excel.Book.getSheetAt (0).iterator ();
		int row = 0;
		while (rowIterator.hasNext ()) {
			Iterator<Cell> cellIterator = rowIterator.next ().cellIterator ();
			ArrayList<JTextField> textField_col = new ArrayList<JTextField> ();
			int col = 0;
			while (cellIterator.hasNext ()) {
				Cell cell = cellIterator.next ();
				
				// setup currentTF
				JTextField currentTF = new JTextField ();
				currentTF.setBounds(row*50,col*50, 50, 50);
				panel_cell.add (currentTF);
				switch (cell.getCellType ()) {
					case NUMERIC:
					currentTF.setText (cell.getNumericCellValue () + "t");
					break;

					case STRING:
					currentTF.setText (cell.getStringCellValue () + "t");
					break;
					
				}

				// save and move on to the next one
				textField_col.add (currentTF);
				col ++;
			}
			textField.add (textField_col);
			row ++;
		}


	}



	private void
	refresh_file () {
		System.out.println ("refreshing file...");
	}	

	private void
	init_book () {
		// init excel object and open a file with GUI
		excel = new FileIO ();
		String filename =
			"/home/jsl/CODE/10901_JavaProject_THU/src/test/resources/realExcel.xlsx";
		// need to change to null upon release FIXME
		excel.openFile (filename);
	}


	private void
	msg (String s) {
		System.out.println (s);
	}

}
