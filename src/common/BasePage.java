package common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.Select;

public abstract class BasePage {

	
    /** Timeout for finding page element in seconds */
    protected static final long FIND_ELEMENT_TIMEOUT = 30L;
	
	/** The Selenium2 web driver. */
    protected WebDriver driver;
    
    public BasePage(WebDriver driver)
    {
        this.driver = driver;
    }
    
    
    /**
     * Find the element in the DOM by id.
     * 
     * @param elementId the element id
     * @return the element found
     */
    public WebElement findElement(String elementId, String locType)
    {
    	WebElement element = null;

        try
        {
        	if(locType.equalsIgnoreCase("id")){
            return new WebDriverWait(driver, FIND_ELEMENT_TIMEOUT).until(ExpectedConditions
                    .visibilityOfElementLocated(By.id(elementId)));
            
        	} else if (locType.equalsIgnoreCase("name")){        		
            return new WebDriverWait(driver, FIND_ELEMENT_TIMEOUT).until(ExpectedConditions
                    .visibilityOfElementLocated(By.name(elementId)));
            
            } else if (locType.equalsIgnoreCase("linktext")){        		
                return new WebDriverWait(driver, FIND_ELEMENT_TIMEOUT).until(ExpectedConditions
                        .visibilityOfElementLocated(By.linkText(elementId)));
                
            } else if (locType.equalsIgnoreCase("partiallinktext")){        		
                return new WebDriverWait(driver, FIND_ELEMENT_TIMEOUT).until(ExpectedConditions
                        .visibilityOfElementLocated(By.partialLinkText(elementId)));
                
            } else if (locType.equalsIgnoreCase("css")){        		
                return new WebDriverWait(driver, FIND_ELEMENT_TIMEOUT).until(ExpectedConditions
                        .visibilityOfElementLocated(By.cssSelector(elementId)));
                
            } else if (locType.equalsIgnoreCase("class")){        		
                return new WebDriverWait(driver, FIND_ELEMENT_TIMEOUT).until(ExpectedConditions
                        .visibilityOfElementLocated(By.className(elementId)));
                
            } else{        		
            return new WebDriverWait(driver, FIND_ELEMENT_TIMEOUT).until(ExpectedConditions
                    .visibilityOfElementLocated(By.xpath(elementId)));
            }
        }
        catch (Exception e)
        {
            System.out.println("Element is not found");
            return element;
            
        	}
    	}

    
	    /**
	     * This method will click a web element
	     * 
	     * @param locator
	     * 
	     */
        
        public void click(String locator, String locType)
        {
        	
    	 WebElement element = findElement(locator, locType);
    	 element.click();
        	
        }
        
        /**
         * This method will enter text in a web element
         * 
         * @param locator
         * @param value
         *
         * @author lenard.g.magpantay
            element.submit();
         * 
         */
        public void enterText(String locator, String locType, String value){
    	
    		WebElement element = findElement(locator, locType);
    		element.clear();
    		element.sendKeys(value);

        }
        
        /**
         * This method will select a text value from a dropdown
         * 
         * @param locator
         * @param value
         *
         * @author lenard.g.magpantay
         * 
         */
        public void selectDropdownByVisibleText(String locator, String locType, String value){
        	
        new Select(findElement(locator, locType)).selectByVisibleText(value);
        }

        /**
         * This method will verify if the element is displayed (with waiting command)
         * 
         * @param locator
         * @param locType
         * @param objectName
         *
         * @author timothy.b.orencio
         * 
         */
        public void verifyDisplayed(String locator, String locType, String objectName, long waitMilliSec)throws Exception
        {
    		WebElement element = findElement(locator, locType);
    		element.wait(waitMilliSec);
    		element.isDisplayed();
    		if (element.isDisplayed()) {
    			BaseTest.log(objectName+" is displayed.");
    		}
    		else {
    			BaseTest.log(objectName+" is not displayed.");
    		}

        }
        
        public void selectchoice(String locator, String locType)
        {
    		WebElement element = findElement(locator, locType);
    		element.sendKeys(Keys.DOWN);
    		element.sendKeys(Keys.RETURN); 
        }
}
	
	

