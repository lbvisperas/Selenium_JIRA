
package acn.pageobjects;

import org.openqa.selenium.WebDriver;
import common.BasePage;
import common.BaseTest;
/**
 * @author katherine.a.y.oropel
 *
 */
public class JiraHomepage extends BasePage
{
	public JiraHomepage(WebDriver driver)
	{
		super(driver);
	}
	
	public void enterUsername(int colNum) throws Exception
	{
		String value = BaseTest.getCellData(BaseTest.TestCaseRow, colNum);
		enterText("login-form-username","id", value);
		BaseTest.log("Entered Username");
	}
	
	public void enterPassword(int colNum) throws Exception
	{
		String value = BaseTest.getCellData(BaseTest.TestCaseRow, colNum);
		enterText("login-form-password","id", value);
		BaseTest.log("Entered Password");
	}
	
	public void clickLogin() throws Exception
	{
		click("login-form-submit","id");
		BaseTest.log("Logged in");
	}
	
	public void clickCreate() throws Exception
	{
		click("create_link","id");
		BaseTest.log("Clicked Create");
	}
	
	public void enterSummary(int colNum1, int colNum2) throws Exception
	{
		String value1 = BaseTest.getCellData(BaseTest.TestCaseRow, colNum1);
		String value2 = BaseTest.getCellData(BaseTest.TestCaseRow, colNum2);
		enterText("summary","id", value1 + " " + value2);
		BaseTest.log("Entered Summary");
	}
	
	public void enterDescription(int colNum1, int colNum2, int colNum3, int colNum4, int colNum5, int colNum6, int colNum7, int colNum8) throws Exception
	{
		String value1 = BaseTest.getCellData(BaseTest.TestCaseRow, colNum1);
		String value2 = BaseTest.getCellData(BaseTest.TestCaseRow, colNum2);
		String value3 = BaseTest.getCellData(BaseTest.TestCaseRow, colNum3);
		String value4 = BaseTest.getCellData(BaseTest.TestCaseRow, colNum4);
		String value5 = BaseTest.getCellData(BaseTest.TestCaseRow, colNum5);
		String value6 = BaseTest.getCellData(BaseTest.TestCaseRow, colNum6);
		String value7 = BaseTest.getCellData(BaseTest.TestCaseRow, colNum7);
		String value8 = BaseTest.getCellData(BaseTest.TestCaseRow, colNum8);
		enterText("description","id", value1 + " " + value2 + " " + value3 + " " + value4 + "\n" + value5 + " " + value6 + "\n" + value7 + " " + value8);
		BaseTest.log("Entered Description");
	}
	
	//Column M + <space> + D + <space> + B + <space> + C + <new line> + E + <space> + O + <new line> + H + <space> + K

	/*public void selectEpic(int colNum) throws Exception
	{		
			String value = BaseTest.getCellData(BaseTest.TestCaseRow, colNum);
			selectDropdownByVisibleText("customfield_10007-field", "id", value);
			BaseTest.log("Selected Epic");
	}*/
	
	public void selectEpic(int colNum) throws Exception
	{
		String value = BaseTest.getCellData(BaseTest.TestCaseRow, colNum);
		enterText("customfield_10007-field", "id", value);
		selectchoice("customfield_10007-field", "id");
		BaseTest.log("Selected Epic");
	}
	
	public void createStory() throws Exception
	{
		click("create-issue-submit","id");
		BaseTest.log("Clicked Create");
	}
}