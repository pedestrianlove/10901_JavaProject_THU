package tw.jsl.JavaProjectTHU;

// general
import java.util.ArrayList;
import java.util.Iterator;

// objects
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import java.awt.Container;
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
import javax.swing.text.*;
import javax.swing.JTextField;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ScrollPaneConstants;

// event
import java.awt.EventQueue;
import javax.swing.event.*;
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
	public boolean SAVED;
	public JFrame frame;
	public JPanel panel_func, panel_cell;
	public ArrayList<ArrayList<JTextField>> textField;
	public JButton open, save;
	public FileIO excel;
	public Cell cell;
	public int max_row, max_col;
	
	
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
		msg ("Initializing workbook...");
		init_book ();

		// init frame
		msg ("Initializing frame...");
		frame = new JFrame();
		frame.setBackground(Color.LIGHT_GRAY);
		frame.setContentPane (new JScrollPane (
					ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, 
					ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED));
		frame.getContentPane().setBackground(Color.WHITE);
		frame.getContentPane().setLayout(null);
		//frame.setExtendedState  (JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		
		// init panel_func (panel that stores function buttons)
		msg ("Initializing panel_func...");
	
		panel_func = new JPanel ();
		panel_func.setBackground(Color.BLUE);
		frame.getContentPane().add(panel_func);
		panel_func.setLayout(null);
			// init buttons
			open = new JButton ("Open");
			open.setBackground (Color.YELLOW);
			open.addActionListener (new ActionListener () {
				public void actionPerformed (ActionEvent e) {
					msg ("----Open button clicked----");
					Open_func ();
				}		
			});
			panel_func.add (open);

			save = new JButton ("Save");
			save.setBackground (Color.GREEN);
			save.addActionListener (new ActionListener () {
				public void actionPerformed (ActionEvent e) {
					msg ("----Save button clicked----");
					Save_func ();
				}		
			});
			panel_func.add (save);
				
		// init panel_cell (panel that stores cell textfields)
		msg ("Initializing panel_cell...");
		panel_cell = new JPanel ();
		panel_cell.setBackground(Color.RED);
		frame.getContentPane().add(panel_cell);
		panel_cell.setLayout(null);

			// init field tags
			
		
			// init textfields
			refresh_TF ();

			// Adjust object size
			Adjust_Obj_Size ();

		
	}


	public void
	Adjust_Obj_Size () {
		int height = 0;
		int width = 0;
		// Adjust the object size dynamically
		
		panel_cell.setBounds(width, height, max_col * 100 + 50, max_row * 50 + 50);
		width = max_col * 100 + 50;
		height = max_row * 50 + 50;
		panel_func.setBounds(width, 0, 100, height);
			open.setBounds (0, 0, 100, height / 2);
			save.setBounds (0, height / 2, 100, height / 2);
		width += 100;
		frame.setBounds(0, 0, width, height);
	}

	public void
	File_changed (DocumentEvent e) {
		msg ("Detected file change, refreshing the file...");
		SAVED = false;
		Interface _interfaces = (Interface) e.getDocument ().getProperty ("interface");
		JTextField _currentTF = (JTextField) e.getDocument ().getProperty ("currentTF");
		Cell _cell = (Cell) e.getDocument ().getProperty ("cell");
		String _data = _currentTF.getText ();
		// add different data handling FIXME
		_cell.setCellValue (_data);
		_interfaces.refresh_TF ();

	}

	public void
	Open_func () {
		if (!SAVED) {
			msg ("Checking if discard current changes...");
			int confirmed = JOptionPane.showConfirmDialog (null,
					"You will lost all unsaved progess, continue?",
				       	"Quit?",
					JOptionPane.YES_NO_OPTION);
			if (confirmed != JOptionPane.YES_OPTION) {
				msg ("User cancelled the operation.");
				return;
			}

			msg ("YES.");
		}
		msg ("Opening file...");
		excel.openFile (null);
		refresh_TF ();
		SAVED = true;
		msg ("Done.");
	}

	public void
	Save_func () {
		msg ("Saving file...");
		SAVED = true;
		excel.writeFile (null);
		msg ("Done.");
	}




	public void
	refresh_TF () {
		msg ("(Re)constructing textfields...");
		
		max_col = 0;
		max_row = 0;
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
				currentTF.setBounds(50 + col*100, 50 + row*50, 100, 50);
				switch (cell.getCellType ()) {
					case NUMERIC:
					currentTF.setText (cell.getNumericCellValue () + "");
					break;

					case STRING:
					currentTF.setText (cell.getStringCellValue ());
					break;
					
				}
				panel_cell.add (currentTF);

				//add event handler
				//// attach objects to the document
				currentTF.getDocument ().putProperty ("cell", cell);
				currentTF.getDocument ().putProperty ("currentTF", currentTF);
				currentTF.getDocument ().putProperty ("interface", this);
				//// find designated objects by tags
				currentTF.getDocument ()
					.addDocumentListener (new DocumentListener () {
					@Override
					public void changedUpdate (DocumentEvent e) {
						msg ("----FILE CHANGED----");
						File_changed (e);
					}
					@Override
					public void insertUpdate (DocumentEvent e) {
						msg ("----FILE CHANGED----");
						File_changed (e);
					}
					@Override
					public void removeUpdate (DocumentEvent e) {
						msg ("----FILE CHANGED----");
						File_changed (e);
					}
				});

				// save and move on to the next one
				textField_col.add (currentTF);
				col ++;
			}
			if (max_col < col) max_col = col;
			textField.add (textField_col);
			row ++;
		}
		max_row = row;
	}	

	private void
	init_book () {
		// init excel object and open a file with GUI
		excel = new FileIO ();
		String filename =
			"src/test/resources/realExcel.xlsx";
		// need to change to null upon release FIXME
		msg ("Opening file from predesignated path...");
		excel.openFile (filename);
	}


	private static void
	msg (String s) {
		System.out.println (s);
	}

}
