package acn.test;

import java.util.ArrayList;

//import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

import acn.pageobjects.JiraHomepage;
import common.BaseTest;

public class JiraTest extends BaseTest {
	// final public String xcelSheetName = "Test Sheet";
	final public String xcelSheetName = "Test Sheet";
	final public String xcelFilePath = "C:\\Workspace\\SeleniumJIRA\\src\\acn\\testdata\\SampleTestData.xlsx";

	@Test
	public void jiratest() throws Exception {
		// System.setProperty("webdriver.firefox.bin", "C:\\Program Files\\Mozilla
		// Firefox\\firefox.exe");
		System.setProperty("webdriver.chrome.driver",
				"C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");


		ArrayList<String> testCaseList = new ArrayList<String>();
		for (int populateCtr = 2; populateCtr < 65; populateCtr++) {
			setExcelFile(xcelFilePath, xcelSheetName);
			String cellValue = getCellData(populateCtr, 0);
			System.out.println("Adding cell value to list " + cellValue);
			testCaseList.add(cellValue);
		}
		
		// set limit of counter to number of JIRA tickets to create
		for (int counter = 0; counter < testCaseList.size(); counter++) {

			startTest(xcelFilePath, xcelSheetName, testCaseList.get(counter));
			
			JiraHomepage homepage = new JiraHomepage(driver);
			homepage.enterUsername(2);
			homepage.enterPassword(3);
			homepage.clickLogin();
			Thread.sleep(100);
			homepage.clickCreate();
			homepage.enterSummary(11, 4);
			homepage.enterDescription(11,4,5,6,7,8,9,10);
			homepage.selectEpic(8);
			homepage.createStory();
			Thread.sleep(1000);
			
		}
	}
}