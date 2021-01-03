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
import javax.swing.JFormattedTextField;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.DefaultFormatter;

// event
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.EventQueue;
import javax.swing.event.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import java.util.concurrent.ThreadLocalRandom;

// POI
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;

class Interface {
	public boolean SAVED, CONTENT_FILLED, PADDED;
	public JFrame frame;
	public JPanel panel_func, panel_cell;
	public ArrayList<JLabel> cell_alpha, cell_number;
	public ArrayList<ArrayList<JFormattedTextField>> textField;
	public JButton open, save, NEW;
	public FileIO excel;
	public Cell cell;
	public int max_row, max_col, padding;
	
	
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
		CONTENT_FILLED = false;
		PADDED = false;
		// init book
		msg ("Initializing workbook...");
		init_book ();
		SAVED = true;

		// init frame
		msg ("Initializing frame...");
		frame = new JFrame("Final Project presented by s08351050 J.S.LI");
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
			NEW = new JButton ("New");
			NEW.setBackground (Color.CYAN);
			NEW.addActionListener (new ActionListener () {
				public void actionPerformed (ActionEvent e) {
					msg ("----New button clicked----");
					new_func ();
				}		
			});
			panel_func.add (NEW);

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
		padding = 3;	
		panel_cell = new JPanel ();
		panel_cell.setBackground(Color.RED);
		frame.getContentPane().add(panel_cell);
		panel_cell.setLayout(null);
			
			// init textfields and tags
			refresh_TF ();

			// Adjust object size
			Adjust_Obj_Size ();

		CONTENT_FILLED = true;
	}

	public void
	reset_panel_cell () {
		msg ("Resetting the panel...");
		panel_cell.removeAll ();
		panel_cell.revalidate ();
		panel_cell.repaint ();
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
			open.setBounds (0, 0, 100, height / 3);
			save.setBounds (0, height / 3, 100, height / 3);
			NEW.setBounds (0, (2*height) / 3, 100, height / 3);
		width += 100;
		frame.setBounds(0, 0, width, height);
	}

	public void
	File_changed (JFormattedTextField _currentTF, Interface _interfaces, Cell _cell) {
		msg ("Detected file change, refreshing the file...");
		SAVED = false;
		String _data = (String) _currentTF.getValue ();
		// add different data handling
		if (_data == "" || _data == null) {
			msg ("Detected empty string.");
			_cell.setCellType (CellType.STRING);
			_cell.setCellValue ("");
		} else if (isInteger (_data) == 1){
			msg ("Detected integer.");
			_cell.setCellType (CellType.NUMERIC);
			_cell.setCellValue (Integer.parseInt (_data));
		} else if (isInteger (_data) == 2) {
			msg ("Detected float.");
			_cell.setCellType (CellType.NUMERIC);
			_cell.setCellValue (Float.parseFloat (_data));
		} else if (_data.charAt (0) == '=') {
			msg ("Detected formula.");
			//_cell.setCellType (CellType.FORMULA); // case FORMULA BROKEN FIXME
			_cell.setCellFormula (_data.substring (1));
		} else {
			msg ("Detected string.");
			_cell.setCellType (CellType.STRING);
			_cell.setCellValue (_data);
		}
		_interfaces.refresh_TF ();

	}

	// BUTTON FUNCTIONS
	public void
	Open_func () {
		if (!SAVED) {
			msg ("Checking if discarding current changes...");
			int confirmed = JOptionPane.showConfirmDialog (null,
					"You will lost all unsaved progess, continue?",
				       	"Open another file without saving?",
					JOptionPane.YES_NO_OPTION);
			if (confirmed != JOptionPane.YES_OPTION) {
				msg ("User cancelled the operation.");
				return;
			}

			msg ("YES.");
		}
		msg ("Opening file...");
		excel.openFile (null);
		PADDED = false;
		refresh_TF ();
		SAVED = true;
		msg ("Done.");
	}

	public void
	Save_func () {
		msg ("Saving file...");
		excel.writeFile (null);
		SAVED = true;
		msg ("Done.");
	}

	public void
	new_func () {
		if (!SAVED) {
			msg ("Checking if discarding current changes...");
			int confirmed = JOptionPane.showConfirmDialog (null,
					"You will lost all unsaved progess, continue?",
				       	"Open a blank file without saving?",
					JOptionPane.YES_NO_OPTION);
			if (confirmed != JOptionPane.YES_OPTION) {
				msg ("User cancelled the operation.");
				return;
			}

			msg ("YES.");
		}

		msg ("Creating a new file...");
		excel.newFile ();
		XSSFSheet sheet = excel.Book.createSheet ("Sheet 0");
		padding = 10;
		add_padding (sheet);
		padding = 3;
		PADDED = true;
		refresh_TF ();
		if (max_row == 0) msg ("Oops.");
		SAVED = true;
		msg ("Done.");
	}

	public void
	add_padding (XSSFSheet sheet) {
		max_col = 0;
		int last_row = sheet.getLastRowNum ();
		for (int i = 0; i <= last_row + padding; i ++) {
			Row row = sheet.getRow (i);
			if (row == null)
				row = sheet.createRow (i);
			int last_col = row.getLastCellNum ();
			if (max_col < last_col) max_col = last_col;
			for (int j = 0; j < Math.max (last_col, max_col) + padding; j++) {
				Cell cell = row.getCell (j);
				if (cell == null || cell.getCellType () == CellType.BLANK)
					cell = row.createCell (j, CellType.STRING);
			}
		}
		PADDED = true;

	}

	public String
	number_to_str (int x) {
		if (x == 0) return "A";
		String tmp = "";
		while (x > 0) {
			int int_val = x % 26;
			char val = (char) (int_val + 'A');
			tmp = tmp + val;
			x = (x - int_val) / 26;
		}

		return tmp;
	}	



	public void
	refresh_TF () {
		XSSFSheet sheet = excel.Book.getSheetAt (0);
		if (!PADDED) add_padding (sheet);
		if (CONTENT_FILLED) reset_panel_cell ();
		// cell_alpha, cell_number
		msg ("(Re)constructing textfields...");
		
		max_col = 0;
		max_row = 0;
		// ArrayLists
		cell_number = new ArrayList<JLabel> ();
		textField = new ArrayList<ArrayList<JFormattedTextField>> ();
		// Iterators
		Iterator<ArrayList<JFormattedTextField>> textField_row = textField.iterator ();
		Iterator<Row> rowIterator = excel.Book.getSheetAt (0).iterator ();
		int row = 0;
		while (rowIterator.hasNext ()) {
			// labels
			cell_alpha = new ArrayList<JLabel> ();
			JLabel numberLabel = new JLabel (Integer.toString (row + 1), SwingConstants.CENTER); 
			numberLabel.setBounds (0, 50 + row * 50, 50, 50);
			numberLabel.setBackground (Color.GRAY);
			numberLabel.setOpaque (true);
			cell_alpha.add (numberLabel);
			panel_cell.add (numberLabel);
			// textfields
			Row row_obj = rowIterator.next ();
			Iterator<Cell> cellIterator = row_obj.cellIterator ();
			ArrayList<JFormattedTextField> textField_col = new ArrayList<JFormattedTextField> ();
			int col = 0;
			while (cellIterator.hasNext ()) {
				// cells
				final Cell cell = cellIterator.next ();
				
				// labels
				JLabel alphaLabel = new JLabel (number_to_str (col), SwingConstants.CENTER);
				alphaLabel.setBounds (50 + col * 100, 0, 100, 50);
				alphaLabel.setBackground (Color.GRAY);
				alphaLabel.setOpaque (true);
				cell_number.add (alphaLabel);
				panel_cell.add (alphaLabel);
				
				// setup currentTF
				final JFormattedTextField currentTF = new JFormattedTextField (new DefaultFormatter());
				DataFormatter formatter = new DataFormatter ();
				currentTF.setBounds(50 + col*100, 50 + row*50, 100, 50);
				String data = "";
				switch (cell.getCellType ()) {
					case NUMERIC:
					data = formatter.formatCellValue (cell);
					break;

					case STRING:
					data = formatter.formatCellValue (cell);
					break;

					case FORMULA:
					FormulaEvaluator eval 
						= excel.Book.getCreationHelper().createFormulaEvaluator();
					data = formatter.formatCellValue (cell, eval);
					break;
					/*
					switch (eval.evaluateFormulaCell (cell)) {
						case BOOLEAN:
							data = cell.getBooleanCellValue ()+"";
							break;
						case NUMERIC:
							data = cell.getNumericCellValue ()+"";
							break;
						case STRING:
							data = cell.getStringCellValue ();
							break;
					}
					*/

				}
				currentTF.setValue (data);
				panel_cell.add (currentTF);

				//add event handler
				//// attach objects to the document
				currentTF.getDocument ().putProperty ("cell", cell);
				currentTF.getDocument ().putProperty ("currentTF", currentTF);
				currentTF.getDocument ().putProperty ("interface", this);
				//// find designated objects by tags
				/*
				currentTF.getDocument ()
					.addDocumentListener (new DocumentListener () {
					@Override
					public void changedUpdate (DocumentEvent e) {
						msg ("----FILE CONTENT CHANGED----");
						File_changed (e);
					}
					@Override
					public void insertUpdate (DocumentEvent e) {
						msg ("----FILE CONTENT INSERTED----");
						File_changed (e);
					}
					@Override
					public void removeUpdate (DocumentEvent e) {
						msg ("----FILE CONTENT REMOVED----");
						File_changed (e);
					}
				});
				*/
				currentTF.addPropertyChangeListener ("value", new PropertyChangeListener () {
				@Override	
				public void propertyChange (PropertyChangeEvent e) {
					msg ("----Property changed----");
					JFormattedTextField _currentTF = (JFormattedTextField) e.getSource ();
					Cell _cell = (Cell) currentTF.getDocument ().getProperty ("cell");
					Interface _interfaces = (Interface) currentTF.getDocument ().getProperty ("interface");
					File_changed (_currentTF, _interfaces, _cell);

				}});

				// save and move on to the next one
				textField_col.add (currentTF);
				col ++;
				
			}
			if (max_col < col) max_col = col;
			textField.add (textField_col);
			row ++;
		}
		max_row = row;
		//if (CONTENT_FILLED) Adjust_Obj_Size ();
		Adjust_Obj_Size ();
		CONTENT_FILLED = true;
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


	// 1: integer, 2: float, 0: others
	private static int
	isInteger(String str) {
		if(str == null || str.trim().isEmpty()) 
			return 0;
		for (int i = 0; i < str.length(); i++) 
			if(!Character.isDigit(str.charAt(i))) {
				if (str.charAt(i) != '.')
					return 0;
				return 2;
			}
		return 1;
	}
	private static void
	msg (String s) {
		System.out.println (s);
	}

}
