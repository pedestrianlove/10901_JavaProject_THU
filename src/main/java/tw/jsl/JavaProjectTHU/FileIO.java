import java.io.*;
import org.apache.poi.xssf.usermodel.*;

class FileIO {
	public XSSFWorkbook Book = null;
	public String Path = null;

	// constructor
	public FileIO () {
		 Book = new XSSFWorkbook ();
	}
	public FileIO (String fileName) throws IOException {
		// verify fileName valid
		if (fileName != null) {
			File excelFile = new File (fileName);
			// verify file exist
			if (excelFile.exists () && !excelFile.isDirectory ()) {
				Path = fileName;
				FileInputStream infile = new FileInputStream (excelFile);
				Book = new XSSFWorkbook (infile);
			}
		}

		if (Book == null) {
			Book = new XSSFWorkbook ();
		}
	}

	// public methods



}
