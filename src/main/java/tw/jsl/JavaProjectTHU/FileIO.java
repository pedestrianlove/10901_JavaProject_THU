package tw.jsl.JavaProjectTHU;

import java.io.*;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.xssf.usermodel.*;

class FileIO {
	public XSSFWorkbook Book = null;
	public File excelFile = null;
	public String Path = null;
	public JFileChooser Chooser = null;

	// constructor
	public FileIO () {
		emptyWorkbook ();
	}

	// public methods


	// private methods
	private void chooseFile_UI () {
		// Select file and return File object from window
		Chooser = new JFileChooser (); 
		FileNameExtensionFilter filter = new FileNameExtensionFilter("xlsx");
		Chooser.setFileFilter (filter);
		int returnVal = Chooser.showOpenDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			excelFile = Chooser.getSelectedFile();
		}
	}

	private void extractExcelFromFile () {
		try {
			// Select file by manually selecting path
			if (excelFile != null) {
				// verify file exist
				if (excelFile.exists () && !excelFile.isDirectory ()) {
					FileInputStream infile = new FileInputStream (excelFile);
					Book = new XSSFWorkbook (infile);
				}
			}
			if (Book == null) {
				Book = new XSSFWorkbook ();
			}
		
		} catch (IOException e) {
			e.printStackTrace ();
		}
	}

	private void emptyWorkbook () {
		Book = new XSSFWorkbook ();
	}

}
