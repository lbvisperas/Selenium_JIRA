package common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;

public class BaseTest {

	public static WebDriver driver;
	public static XSSFSheet ExcelWSheet;
	public static XSSFWorkbook ExcelWBook;
	public static XSSFCell Cell;
	public static XSSFRow Row;
	public static int TestCaseRow;

	public void startTest(String ExcelFilePath, String ExcelSheetName, String TestCaseName) {

		// TODO - replace testCaseName with col 0
		// String TestCaseName = this.getClass().getSimpleName();

		log("");
		log(">>>>> Executing " + TestCaseName + " <<<<<");
		log("");

		try {

			setExcelFile(ExcelFilePath, ExcelSheetName);
			TestCaseRow = getRowContains(0, TestCaseName);
			System.out.println("TestCaseRow value is " + TestCaseRow);
			String webappUrl = getCellData(TestCaseRow, 1);

			/*
			 * log("Running test in Firefox"); driver = new FirefoxDriver();
			 */

			log("Running test in Chrome");
			driver = new ChromeDriver();

			driver.get(webappUrl);

			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			driver.manage().window().maximize();

			log("Opened " + webappUrl);

		} catch (Exception e) {

			e.printStackTrace();

		}
	}

	@AfterClass
	public void endTest() {
		String TestCaseName = this.getClass().getSimpleName();
		log("");
		log(">>>>> Terminating " + TestCaseName + " <<<<<");
		log("");

		driver.close();
		driver.quit();

	}

	public static void log(String message) {
		System.out.println(message);
		Reporter.log(message);
	}

	public void takescreenshot() throws IOException {
		try {
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File("C:\\Workspace\\SeleniumJIRA\\test-reports\\screenshots\\"
					+ getFileName(this.getClass().getSimpleName())));
		} catch (Exception e) {
			log("Screenshot is not created.");
			e.printStackTrace();
		}
	}

	private String getFileName(String nameTest) throws IOException {
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy_hh.mm.ss");
		Date date = new Date();
		return dateFormat.format(date) + "_" + nameTest + ".png";
	}

	public void assertTextPresentInElement(String locator, String locType, int colNum) throws Exception {

		String valueToCheck = getCellData(TestCaseRow, colNum);
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		try {

			if (locType.equalsIgnoreCase("id")) {
				Assert.assertTrue(driver.findElement(By.id(locator)).getText().equals(valueToCheck));
				log("The text " + valueToCheck + "is present in the web element.");
				System.out.println(valueToCheck);

			} else if (locType.equalsIgnoreCase("name")) {

				Assert.assertTrue(driver.findElement(By.name(locator)).getText().equals(valueToCheck));
				log("The text " + valueToCheck + " is present in the web element.");

			} else if (locType.equalsIgnoreCase("class")) {

				Assert.assertTrue(driver.findElement(By.className(locator)).getText().equals(valueToCheck));
				log("The text " + valueToCheck + " is present in the web element.");

			} else {

				Assert.assertTrue(driver.findElement(By.xpath(locator)).getText().equals(valueToCheck));
				log("The text " + valueToCheck + " is present in the web element.");

			}
		} catch (Exception e) {

			log("Text is not present in the web element.");
			e.printStackTrace();

		}

	}

	public void assertElementPresentInPage(String locator, String locType) {
		try {

			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

			Assert.assertTrue(isElementPresent(locator, locType));
			log("Element is present in the page.");

		} catch (Exception e) {

			log("Element is not present in the page.");
			e.printStackTrace();

		}

	}

	public Boolean isElementPresent(String locator, String locType) {

		try {
			if (locType.equalsIgnoreCase("id")) {

				driver.findElement(By.id(locator));

			} else if (locType.equalsIgnoreCase("name")) {

				driver.findElement(By.name(locator));

			} else if (locType.equalsIgnoreCase("class")) {

				driver.findElement(By.className(locator));

			} else {

				driver.findElement(By.xpath(locator));

			}

		} catch (NoSuchElementException e) {
			return false;
		}

		return true;
	}

	// This method is to read the test data from the Excel cell, in this we are
	// passing parameters as Row num and Col num
	public static String getCellData(int RowNum, int ColNum) throws Exception {
		String CellData = null;

		try {
			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
			Cell.setCellType(Cell.CELL_TYPE_STRING);
			CellData = Cell.getStringCellValue();
			System.out.println("Cell data is " + CellData);
			return CellData;

		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Cell data is empty");
			return "";
		}
	}

	// This method is to set the File path and to open the Excel file, Pass Excel
	// Path and Sheetname as Arguments to this method
	public static void setExcelFile(String Path, String SheetName) throws Exception {
		try {
			// Open the Excel file
			FileInputStream ExcelFile = new FileInputStream(Path);
			// Access the required test data sheet
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
		} catch (Exception e) {
			throw (e);
		}
	}

	public static int getRowContains(int colNum, String sTestCaseName) throws Exception {
		int i;

		try {

			int rowCount = getRowUsed();
			outer: for (i = 1; i < rowCount; i++) {
				if (getCellData(i, colNum).equalsIgnoreCase(sTestCaseName)) {
					break outer;
				}
			}
			return i;
		} catch (Exception e) {
			throw (e);
		}
	}

	public static int getRowUsed() throws Exception {
		try {
			int RowCount = ExcelWSheet.getLastRowNum();
			return RowCount;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw (e);
		}

	}

	public static String setCellValue(String xcelFilePath, int RowNum, int ColNum, String text) throws Exception {
		String text1 = "exception error";
		try {
			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
			System.out.println("setCellValue Row Number: " + RowNum + " and colnum " + ColNum);
			Cell.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
			String filepath = xcelFilePath;
			FileOutputStream webdata = new FileOutputStream(filepath);
			Cell.setCellValue(text);
			ExcelWBook.write(webdata);
			return text1;
		} catch (Exception e) {
			return text1;
		}
	}

}