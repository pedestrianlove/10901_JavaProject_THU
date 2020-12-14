package tw.jsl.JavaProjectTHU;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

// class test dependency
import org.apache.poi.xssf.usermodel.*;
import java.io.*;

/**
 * Unit test for simple App.
 */
public class FileIOTest 
	extends TestCase
{
	/**
	 * Create the test case
	 *
	 * @param testName name of the test case
	 */
	public FileIOTest( String testName )
	{
		super( testName );
	}
	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite()
	{
		return new TestSuite( FileIOTest.class );
	}
	/**
	 * Rigourous Test :-)
	 */
	public void testFileIO_plainOpen()
	{
		FileIO excel = new FileIO ();
		Class cls = XSSFWorkbook.class;
		assertTrue( excel.Path == null );
		assertTrue( excel.Book instanceof XSSFWorkbook );
		assertTrue( cls.isInstance (excel.Book) );
	}
	public void testFileIO_newFile()
	{
		FileIO excel = new FileIO ();
		excel.newFile ();
		Class cls = XSSFWorkbook.class;
		assertTrue( excel.excelFile == null );
		assertTrue( excel.Book instanceof XSSFWorkbook );
		assertTrue( cls.isInstance (excel.Book) );
	}
	public void testFileIO_openFile_str_isDirectory()
	{
		String filePath = "src/test/resources/dirExcel.xlsx";
		FileIO excel = new FileIO ();
		excel.openFile (filePath);
		Class cls = XSSFWorkbook.class;
		assertTrue( excel.excelFile == null );
		assertTrue( excel.Book instanceof XSSFWorkbook );
		assertTrue( cls.isInstance (excel.Book) );
	}
	public void testFileIO_openFile_str_isReal ()
	{
		String filePath = "src/test/resources/realExcel.xlsx";
		FileIO excel = new FileIO ();
		excel.openFile (filePath);
		Class cls = XSSFWorkbook.class;
		assertTrue( excel.excelFile.exists () );
		assertTrue( excel.Book instanceof XSSFWorkbook );
		assertTrue( cls.isInstance (excel.Book) );
	}
	public void testFileIO_writeFile_str_target ()
	{
		String filePath = "src/test/resources/realExcel.xlsx";
		FileIO excel = new FileIO ();
		excel.writeFile ("src/test/resources/targetExcel.xlsx");
		assertTrue ( excel.excelFile.exists () );
	}
	/*
	public void testFileIO_writeFile_str_nullFile()
	{
		String filePath = "src/test/resources/dirExcel.xlsx";
		FileIO excel = new FileIO ();
		excel.writeFile (filePath);
		Class cls = XSSFWorkbook.class;
		assertTrue( excel.Path == null );
		assertTrue( excel.Book instanceof XSSFWorkbook );
		assertTrue( cls.isInstance (excel.Book) );
	}
	public void testFileIO_nullPath()
	{
		FileIO excel = new FileIO (null);
		Class cls = XSSFWorkbook.class;
		assertTrue( excel.Path == null );
		assertTrue( excel.Book instanceof XSSFWorkbook );
		assertTrue( cls.isInstance (excel.Book) );

	}
	public void testFileIO_isDirectory ()
	{
		String filePath = "src/test/resources/dirExcel.xlsx";
		FileIO excel = new FileIO (filePath);
		Class cls = XSSFWorkbook.class;
		assertTrue( excel.Path == null );
		assertTrue( excel.Book instanceof XSSFWorkbook );
		assertTrue( cls.isInstance (excel.Book) );
	}
	*/
}
