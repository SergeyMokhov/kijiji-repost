package pageobjects;

import core.utils.CustomExpectedConditions;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

/**
 * Created by Sergey Mokhov (sergey) on 2017-01-10.
 */
public class GenericPage {
    private WebDriver driver;
    private static Logger logger = LoggerFactory.getLogger(GenericPage.class);

    public GenericPage(WebDriver driver){
        this.driver = driver;
    }

    protected WebDriver getDriver(){
        return driver;
    }

    protected WebDriverWait createWaitDriver() {
        return new WebDriverWait(driver,30, 300);
    }

    protected WebDriverWait createWaitDriver(int timeOutSeconds, int sleepInMillis) {
        return new WebDriverWait(driver,timeOutSeconds, sleepInMillis);
    }

    protected WebDriverWait createWaitDriver(int timeOutSeconds) {
        return new WebDriverWait(driver,timeOutSeconds, 300);
    }

    protected void executeJavascript(String script){
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript(script);
    }

    protected void clickOn(WebElement toClickOn) {
        WebElement el = waitForElementToBeClickable(toClickOn);
        el.click();
    }

    /**
     * Click on field, clear it and type text into it.
     * @param field WebElement, filed to send keys into it
     * @param text String, text to send into input field
     * @return boolean, true if input's value was changed, false if not (already had desired value).
     */
    protected boolean typeInto(WebElement field,
                               String      text){
        boolean inputFieldValueChanged = false;
        Long maxMilliSecondsToWait = 5000L;
        Long start = new Date().getTime();

        while(!getValueFromInput(field).equals(text)){
            inputFieldValueChanged = true;
            try{
                waitForElementToStopMoving(field);
                clickOn(field);
                field.clear();
                field.sendKeys(text);
                Thread.sleep(200L);
            }catch (InterruptedException e){}
            catch (StaleElementReferenceException ee){}
            if(new Date().getTime() - start >= maxMilliSecondsToWait){
                getLogger().error("Text you are tried to type did not reach target's value in 5 sec.");
                break;
            }
        }
        return inputFieldValueChanged;
    }

    /**
     * Returns input's value, even if it is hidden input.
     * @param input tag element
     * @return String. Input's value
     */
    protected String getValueFromInput(WebElement input){
        String jsResponse = null;
        String javascriptToExecute = "return arguments[0].value;";
        try {
            jsResponse = (String) ((JavascriptExecutor) driver).executeScript(javascriptToExecute, input);
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
        return jsResponse;
    }

    protected String generateUglyXpath(WebElement childElement, String current) {
        String childTag = childElement.getTagName();
        if(childTag.equals("html")) {
            return "/html[1]"+current;
        }
        WebElement parentElement = childElement.findElement(By.xpath(".."));
        List<WebElement> childrenElements = parentElement.findElements(By.xpath("*"));
        int count = 0;
        for(int i=0;i<childrenElements.size(); i++) {
            WebElement childrenElement = childrenElements.get(i);
            String childrenElementTag = childrenElement.getTagName();
            if(childTag.equals(childrenElementTag)) {
                count++;
            }
            if(childElement.equals(childrenElement)) {
                return generateUglyXpath(parentElement, "/" + childTag + "[" + count + "]"+current);
            }
        }
        return null;
    }

    protected Logger getLogger(){
        return logger;
    }

    public WebElement findBy(By by){
        return createWaitDriver().until(ExpectedConditions.presenceOfElementLocated(by));
    }

    protected boolean isElementPresentOnPage(By by, int timeout){
        try {
            List<WebElement> elements = createWaitDriver(timeout).until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
        }catch (TimeoutException expected){
            return false;
        }
        return true;
    }

    protected WebElement waitForElementToBeClickable(WebElement element) {
        return createWaitDriver().until(ExpectedConditions.elementToBeClickable(element));
    }

    protected WebElement waitForElementToBeVisible(WebElement element) {
        return createWaitDriver().until(ExpectedConditions.visibilityOf(element));
    }

    protected boolean waitForElementToBeInvisible(WebElement element){
        return createWaitDriver().until(CustomExpectedConditions.inVisibilityOf(element));
    }

    protected boolean waitForElementToBeInvisible(WebElement element, int secondsToWait){
        return createWaitDriver(secondsToWait, 300).until(CustomExpectedConditions.inVisibilityOf(element));
    }

    protected List<WebElement> waitForElementsToBeVisible(List<WebElement> element) {
        return createWaitDriver().until(ExpectedConditions.visibilityOfAllElements(element));
    }

    protected boolean waitForElementToStopMoving(WebElement element){
        return createWaitDriver().until(CustomExpectedConditions.elementHasStoppedMoving(element));
    }

    protected boolean waitForElementToDisappear(WebElement element, int secondsToWait) {
        return createWaitDriver(secondsToWait, 300).until(ExpectedConditions.stalenessOf(element));
    }

    protected boolean waitForElementToBeEnabled(WebElement element, int secondsToWait){
        return createWaitDriver(secondsToWait, 300).until(CustomExpectedConditions.elementToBeEnabled(element));
    }

    protected boolean waitForElementAttributeToContainValue(WebElement element,int secondsToWait, String attrName, String attrValue){
        return createWaitDriver(secondsToWait, 300).until(CustomExpectedConditions.elementToContainAttributeValue(element, attrName, attrValue));
    }
}
