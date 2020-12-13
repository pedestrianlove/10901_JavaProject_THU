import java.io.*;
import org.apache.poi.xssf.usermodel.*;

class FileIO {
	public XSSFWorkbook Book;
	public String Path;

	public FileIO () {
		 Book = new XSSFWorkbook ();
		 Path = null;
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
			else {
				Path = null;
			}
		}
		else {
			Path = null;
		}
	}




}
