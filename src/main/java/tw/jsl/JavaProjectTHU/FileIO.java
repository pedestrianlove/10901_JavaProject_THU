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
	public void newFile () {
		emptyWorkbook ();
		excelFile = null;
	}
	public void openFile (String fileName) {
		if (fileName == null)
			openFile_UI ();
		else
			pathToFile (fileName);
		extractExcelFromFile ();	
	}
	public void writeFile (String fileName) {
		if (fileName == null)
			saveFile_UI ();
		else
			pathToFile (fileName);
		writeExcelToFile ();
	}

	// private methods
	private void saveFile_UI () {
		if (Chooser == null) {
			Chooser = new JFileChooser ();
		}
		FileNameExtensionFilter filter = new FileNameExtensionFilter ("xlsx");
		Chooser.setFileFilter (filter);
		int returnVal = Chooser.showSaveDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			excelFile = Chooser.getSelectedFile();
		}

	}
	private void openFile_UI () {
		// Select file and return File object from window
		Chooser = new JFileChooser (); 
		FileNameExtensionFilter filter = new FileNameExtensionFilter("xlsx");
		Chooser.setFileFilter (filter);
		int returnVal = Chooser.showOpenDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			excelFile = Chooser.getSelectedFile();
		}
	}

	private void pathToFile (String filePath) {
		excelFile = new File (filePath);
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
				else {
					excelFile = null;
					Book = new XSSFWorkbook ();
				}
			}
			if (Book == null) {
				Book = new XSSFWorkbook ();
				excelFile = null;
			}
		
		} catch (IOException e) {
			e.printStackTrace ();
		}
	}
	private void writeExcelToFile () {
		try {
			FileOutputStream outfile = new FileOutputStream (excelFile);
			Book.write (outfile);
			outfile.close ();

		} catch (IOException e) {
			e.printStackTrace ();
		}
	}

	private void emptyWorkbook () {
		Book = new XSSFWorkbook ();
	}

}
