/**
 *
 * This file is part of Jahia: An integrated WCM, DMS and Portal Solution
 * Copyright (C) 2002-2009 Jahia Limited. All rights reserved.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 *
 * As a special exception to the terms and conditions of version 2.0 of
 * the GPL (or any later version), you may redistribute this Program in connection
 * with Free/Libre and Open Source Software ("FLOSS") applications as described
 * in Jahia's FLOSS exception. You should have recieved a copy of the text
 * describing the FLOSS exception, and it is also available here:
 * http://www.jahia.com/license"
 *
 * Commercial and Supported Versions of the program
 * Alternatively, commercial and supported versions of the program may be used
 * in accordance with the terms contained in a separate written agreement
 * between you and Jahia Limited. If you are unsure which license is appropriate
 * for your use, please contact the sales department at sales@jahia.com.
 *//*

package core;

import org.jahia.modules.testng.listeners.LogListener;
import org.jahia.modules.testng.listeners.ScreenshotListener;
import org.jahia.modules.tests.core.DriverController;
import org.jahia.modules.tests.utils.CustomExpectedConditions;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Listeners;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

*/
/**
 * Created by IntelliJ IDEA.
 *
 * @author : rincevent
 * @since : JAHIA 6.1
 * Created : 12/12/14
 * This class
 *//*

@Listeners({LogListener.class, ScreenshotListener.class})
//, ScreenRecordListener.class})
public abstract class ModuleTest extends DriverController {
    private WebDriver originalDriver;

    protected void testXpath(String xpathToTest){
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        try {
            xpath.compile(xpathToTest);
        } catch (XPathExpressionException e) {
            Assert.fail("Invalid XPath syntax for expression: "+xpathToTest+"\n"+e.getMessage());
        }
    }

    protected void clickOn(WebElement toClickOn) {
        try {
            WebElement el = waitForElementToBeClickable(toClickOn);
            el.click();
        } catch (TimeoutException e) {
            getLogger().error(e.getMessage());
            e.getMessage();
        }
    }

    protected void clickOn(By toClickOn) {
        try {
            WebElement el = createWaitDriver().ignoring(NoSuchElementException.class, NullPointerException.class).until(ExpectedConditions.elementToBeClickable(toClickOn));
            el.click();
        } catch (TimeoutException e) {
            getLogger().error(e.getMessage());
            e.getMessage();
        }
    }

    */
/**
     * Clicks on specific element using Actions or .click()
     * @param toClickOn Web element to click on
     * @param useAction boolean. True to use Actions, otherwise False
     *//*

    protected void clickOn(WebElement toClickOn, boolean useAction) {
        try {
            WebElement el = createWaitDriver(30, 300).until(ExpectedConditions.elementToBeClickable(toClickOn));
            if (useAction){
                new Actions(getDriver()).moveToElement(el).click(el).build().perform();
            }else{
                el.click();
            }
        } catch (TimeoutException e) {
            getLogger().error(e.getMessage());
            e.getMessage();
        }
    }

    */
/**
     * Clicks on specific element using Actions or .click()
     * @param toClickOn By. locator
     * @param useAction boolean. True to use Actions, otherwise False
     *//*

    protected void clickOn(By toClickOn, boolean useAction) {
        try {
            WebElement el = createWaitDriver(30, 300).until(ExpectedConditions.elementToBeClickable(toClickOn));
            if (useAction){
                new Actions(getDriver()).moveToElement(el).click(el).build().perform();
            }else{
                el.click();
            }
        } catch (TimeoutException e) {
            getLogger().error(e.getMessage());
            e.getMessage();
        }
    }

    protected WebElement findByID(String id) {
        try {
            return createWaitDriver().ignoring(NoSuchElementException.class, NullPointerException.class).until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
        } catch (TimeoutException e) {
            getLogger().error(e.getMessage());
            return null;
        }
    }

    protected WebElement findByCssSelector(String cssSelector) {
        try {
            return createWaitDriver().ignoring(NoSuchElementException.class, NullPointerException.class).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(cssSelector)));
        } catch (TimeoutException e) {
            getLogger().error(e.getMessage());
            return null;
        }
    }

    protected WebElement findByCssSelectorFromElement(WebElement element, String cssSelector) {
        try {
            return createWaitDriver().ignoring(NoSuchElementException.class, NullPointerException.class).until(ExpectedConditions.visibilityOf(element.findElement(By.cssSelector(cssSelector))));
        } catch (TimeoutException e) {
            getLogger().error(e.getMessage());
            return null;
        } catch (NoSuchElementException ee) {
            getLogger().error(ee.getMessage());
            return null;
        }
    }

    protected List<WebElement> findElementsByCssSelector(String cssSelector) {
        try {
            return createWaitDriver().ignoring(NoSuchElementException.class, NullPointerException.class).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(cssSelector)));
        } catch (TimeoutException e) {
            getLogger().error(e.getMessage());
            return null;
        }
    }

    protected List<WebElement> findElementsByCssSelectorFromElement(WebElement element, String cssSelector) {
        try {
            return createWaitDriver().ignoring(NoSuchElementException.class, NullPointerException.class).until(ExpectedConditions.visibilityOfAllElements(element.findElements(By.cssSelector(cssSelector))));
        } catch (TimeoutException e) {
            getLogger().error(e.getMessage());
            return null;
        } catch (NoSuchElementException ee) {
            getLogger().error(ee.getMessage());
            return null;
        }
    }

    protected WebElement findByIDFromElement(WebElement element, String id) {
        try {
            return createWaitDriver().ignoring(NoSuchElementException.class, NullPointerException.class).until(ExpectedConditions.visibilityOf(element.findElement(By.id(id))));
        } catch (TimeoutException e) {
            getLogger().error(e.getMessage());
            return null;
        } catch (NoSuchElementException ee) {
            getLogger().error(ee.getMessage());
            return null;
        }
    }

    protected WebElement findByName(String name) {
        try {
            return createWaitDriver().ignoring(NoSuchElementException.class, NullPointerException.class).until(ExpectedConditions.presenceOfElementLocated(By.name(name)));
        } catch (TimeoutException e) {
            getLogger().error(e.getMessage());
            return null;
        }
    }

    protected WebElement findByNameFromElement(WebElement element, String name) {
        try {
            return createWaitDriver().ignoring(NoSuchElementException.class, NullPointerException.class).until(ExpectedConditions.visibilityOf(element.findElement(By.name(name))));
        } catch (TimeoutException e) {
            getLogger().error(e.getMessage());
            return null;
        } catch (NoSuchElementException ee) {
            getLogger().error(ee.getMessage());
            return null;
        }
    }

    protected List<WebElement> findElementsByName(String name) {
        try {
            return createWaitDriver().ignoring(NoSuchElementException.class, NullPointerException.class).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.name(name)));
        } catch (TimeoutException e) {
            getLogger().error(e.getMessage());
            return null;
        }
    }

    protected WebElement findByXpath(String xpath) {
        try {
            return createWaitDriver().ignoring(NoSuchElementException.class, NullPointerException.class).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
        } catch (TimeoutException e) {
            getLogger().error(e.getMessage());
            return null;
        }
    }

    protected WebElement findByXpathFromElement(WebElement element, String xpath) {
        try {
            return createWaitDriver().ignoring(NoSuchElementException.class, NullPointerException.class).until(ExpectedConditions.visibilityOf(element.findElement(By.xpath(xpath))));
        } catch (TimeoutException e) {
            getLogger().error(e.getMessage());
            return null;
        } catch (NoSuchElementException ee) {
            getLogger().error(ee.getMessage());
            return null;
        }
    }

    protected List<WebElement> findElementsByXpath(String xpath) {
        try {
            return createWaitDriver().ignoring(NoSuchElementException.class, NullPointerException.class).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(xpath)));
        } catch (TimeoutException e) {
            getLogger().error(e.getMessage());
            return null;
        }
    }

    protected WebElement findByClassName(String className) {
        try {
            return createWaitDriver().ignoring(NoSuchElementException.class, NullPointerException.class).until(ExpectedConditions.presenceOfElementLocated(By.className(className)));
        } catch (TimeoutException e) {
            getLogger().error(e.getMessage());
            return null;
        }
    }

    protected WebElement findByClassNameFromElement(WebElement element, String className) {
        try {
            return createWaitDriver().ignoring(NoSuchElementException.class, NullPointerException.class).until(ExpectedConditions.visibilityOf(element.findElement(By.className(className))));
        } catch (TimeoutException e) {
            getLogger().error(e.getMessage());
            return null;
        } catch (NoSuchElementException ee) {
            getLogger().error(ee.getMessage());
            return null;
        }
    }

    protected List<WebElement> findElementsByClassNameFromElement(WebElement element, String className) {
        try {
            return createWaitDriver().ignoring(NoSuchElementException.class, NullPointerException.class).until(ExpectedConditions.visibilityOfAllElements(element.findElements(By.className(className))));
        } catch (TimeoutException e) {
            getLogger().error(e.getMessage());
            return null;
        } catch (NoSuchElementException ee) {
            getLogger().error(ee.getMessage());
            return null;
        }
    }

    protected List<WebElement> findElementsByClassName(String className) {
        try {
            return createWaitDriver().ignoring(NoSuchElementException.class, NullPointerException.class).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className(className)));
        } catch (TimeoutException e) {
            getLogger().error(e.getMessage());
            return null;
        }
    }

    protected WebElement findByTagName(String tagName) {
        try {
            return createWaitDriver().ignoring(NoSuchElementException.class, NullPointerException.class).until(ExpectedConditions.presenceOfElementLocated(By.tagName(tagName)));
        } catch (TimeoutException e) {
            getLogger().error(e.getMessage());
            return null;
        }
    }

    protected WebElement findByTagNameFromElement(WebElement element, String tagName) {
        try {
            return createWaitDriver().ignoring(NoSuchElementException.class, NullPointerException.class).until(ExpectedConditions.visibilityOf(element.findElement(By.tagName(tagName))));
        } catch (TimeoutException e) {
            getLogger().error(e.getMessage());
            return null;
        } catch (NoSuchElementException ee) {
            getLogger().error(ee.getMessage());
            return null;
        }
    }

    protected List<WebElement> findElementsByTagNameFromElement(WebElement element, String tagName) {
        try {
            return createWaitDriver().ignoring(NoSuchElementException.class, NullPointerException.class).until(ExpectedConditions.visibilityOfAllElements(element.findElements(By.tagName(tagName))));
        } catch (TimeoutException e) {
            getLogger().error(e.getMessage());
            return null;
        } catch (NoSuchElementException ee) {
            getLogger().error(ee.getMessage());
            return null;
        }
    }

    protected WebElement findByLink(String linkText) {
        try {
            return createWaitDriver().ignoring(NoSuchElementException.class, NullPointerException.class).until(ExpectedConditions.presenceOfElementLocated(By.linkText(linkText)));
        } catch (TimeoutException e) {
            getLogger().error(e.getMessage());
            return null;
        }
    }

    protected List<WebElement> findElementsByLink(String linkText) {
        try {
            return createWaitDriver().ignoring(NoSuchElementException.class, NullPointerException.class).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.linkText(linkText)));
        } catch (TimeoutException e) {
            getLogger().error(e.getMessage());
            return null;
        }
    }

    protected WebElement findByPartialLink(String partialLinkText) {
        try {
            return createWaitDriver().ignoring(NoSuchElementException.class, NullPointerException.class).until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText(partialLinkText)));
        } catch (TimeoutException e) {
            getLogger().error(e.getMessage());
            return null;
        }
    }

    //Get list of elements by partial link text
    protected List<WebElement> findElementsByPartialLink(String partialLinkText) {
        try {
            return createWaitDriver().ignoring(NoSuchElementException.class, NullPointerException.class).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.partialLinkText(partialLinkText)));
        } catch (TimeoutException e) {
            getLogger().error(e.getMessage());
            return null;
        }
    }


    public boolean isElementPresent(By by) {
        return createWaitDriver().ignoring(NoSuchElementException.class, NullPointerException.class).until(ExpectedConditions.presenceOfAllElementsLocatedBy(by)).size() != 0;
    }

    public WebElement waitForElementToBeClickable(WebElement element) {
        return createWaitDriver().ignoring(NoSuchElementException.class, NullPointerException.class).until(ExpectedConditions.elementToBeClickable(element));
    }

    public WebElement waitForElementToBeVisible(WebElement element) {
        return createWaitDriver().ignoring(NoSuchElementException.class, NullPointerException.class).until(ExpectedConditions.visibilityOf(element));
    }

    public boolean waitForElementToBeInvisible(By by){
        return createWaitDriver().ignoring(NoSuchElementException.class, NullPointerException.class).until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public boolean waitForElementToBeInvisible(WebElement element){
        try {
            return createWaitDriver().until(CustomExpectedConditions.inVisibilityOf(element));
        }catch (TimeoutException e){
            getLogger().error(e.getMessage());
            return false;
        }
    }

    public boolean waitForElementToBeInvisible(WebElement element, int secondsToWait){
        try {
            return createWaitDriver(secondsToWait, 300).until(CustomExpectedConditions.inVisibilityOf(element));
        }catch (TimeoutException e){
            getLogger().error(e.getMessage());
            return false;
        }
    }

    public List<WebElement> waitForElementsToBeVisible(List<WebElement> element) {
        return createWaitDriver().ignoring(NoSuchElementException.class, NullPointerException.class).until(ExpectedConditions.visibilityOfAllElements(element));
    }

    public boolean waitForElementToStopMoving(WebElement element){
        return createWaitDriver().ignoring(NoSuchElementException.class).until(CustomExpectedConditions.elementHasStoppedMoving(element));
    }

    public boolean isInvisible(By by) {
        return createWaitDriver().ignoring(NoSuchElementException.class, NullPointerException.class).until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public boolean waitForElementToDisappear(WebElement element, int secondsToWait) {
        try{
            return createWaitDriver(secondsToWait, 300).until(ExpectedConditions.stalenessOf(element));
        }catch(TimeoutException e){
            getLogger().error(e.getMessage());
            return false;
        }
    }

    public boolean waitForElementToBeEnabled(WebElement element, int secondsToWait){
        try{
            return createWaitDriver(secondsToWait, 300).until(CustomExpectedConditions.elementToBeEnabled(element));
        }catch(TimeoutException e){
            getLogger().error(e.getMessage());
            return false;
        }
    }

    public boolean waitForElementAttributeToContainValue(WebElement element,int secondsToWait, String attrName, String attrValue){
        try{
            return createWaitDriver(secondsToWait, 300).until(CustomExpectedConditions.elementToContainAttributeValue(element, attrName, attrValue));
        }catch(TimeoutException e){
            getLogger().error(e.getMessage());
            return false;
        }
    }

    public boolean isVisible(WebElement element) {
        try {
            return waitForElementToBeVisible(element) != null;
        } catch (TimeoutException e) {
            getLogger().error(e.getMessage());
            return false;
        }
    }

    public boolean isVisible(WebElement element, int secondsToWait) {
        try {
            createWaitDriver(secondsToWait, 300).until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (TimeoutException e) {
            getLogger().error(e.getMessage());
            return false;
        }
    }

    public boolean isVisible(By by) {
        try {
            WebElement el = driver.findElement(by);
            return isVisible(el);
        }catch(NoSuchElementException e){
            return false;
        }
    }

    public boolean isVisible(By by, int secondsToWait) {
        try {
            WebElement el = createWaitDriver(secondsToWait, 300).until(ExpectedConditions.visibilityOfElementLocated(by));
            return true;
        }catch(TimeoutException e){
            getLogger().error(e.getMessage());
            return false;
        }
    }

    public WebElement noWaitingFindBy(By by){
        List <WebElement> elements = driver.findElements(by);
        if(elements.isEmpty()){
            return null;
        }else {
            return elements.get(0);
        }
    }

    public List<WebElement> noWaitingFindsBy(By by){
        return driver.findElements(by);

    }

    protected void performSelectDropdownValue(WebElement webElement, String value){
        Select selectByValue = new Select(webElement);
        selectByValue.selectByValue(value);
    }

    protected void performSelectDropdownVisibleText(WebElement webElement, String visibleText){
        Select selectByVisibleText = new Select(webElement);
        selectByVisibleText.selectByVisibleText(visibleText);
    }

    public String getSelectedOptionInDropDown(WebElement element) {
        Select select = new Select(element);
        WebElement selectedElement = select.getFirstSelectedOption();
        String selectedOption = selectedElement.getText();
        return selectedOption;
    }

    protected void pageScrollUp(){
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-1500);", "");
    }

    protected void doubleClickElement(WebElement elem){
        try {
            builder.moveToElement(elem).doubleClick().build().perform();
        }catch (Exception e){
            e.getMessage();
        }


    }

    protected void assertEqualsCaseInsensitive(String webElement, String userValue){
        try {
            assertThat(webElement).isEqualToIgnoringCase(userValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    protected void assertEqualsCaseSensitive(String webElement, String userValue){
        try {
            assertThat(webElement).isEqualTo(userValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void performSendKeys(WebElement webElement, String userKeys){
        webElement=(new WebDriverWait(driver, 30)).until(ExpectedConditions.visibilityOf(webElement));
        webElement.clear();
        builder.sendKeys(webElement, userKeys).build().perform();
    }

    protected void performKeyboardEvents(Keys userKeys){
        try {
            builder.sendKeys(userKeys).build().perform();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void performInputWithJavascript(WebElement webElement, String userKeys){
        webElement=(new WebDriverWait(driver, 30)).until(ExpectedConditions.visibilityOf(webElement));
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].value = '" + userKeys + "'", webElement);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void performClickWithJavascript(WebElement webElement){
        webElement=(new WebDriverWait(driver, 30)).until(ExpectedConditions.visibilityOf(webElement));
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click()", webElement);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected static void assertTitle(String expectedTitle){
        try {
            Assert.assertEquals(driver.getTitle(), expectedTitle, "Current title doesn't match expected title");
        } catch (Exception e) {
            e.getMessage();
        }
    }

    protected static void assertURL(String expectedURL){
        try {
            Assert.assertEquals(driver.getCurrentUrl(), expectedURL, "Current URL doesn't match expected URL");
        } catch (Exception e) {
            e.getMessage();
        }
    }

    protected void switchToFrame(WebElement byClassName) {
        if(originalDriver==null) {
            originalDriver = driver;
        }

        driver = new WebDriverWait((originalDriver),30).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(byClassName));
        WebElement el = (WebElement) ((JavascriptExecutor) driver).executeScript("return window.frameElement");
        System.out.println("el = " + el.getAttribute("class"));
    }

    */
/**
     * Log into Jahia with a created User
     * @param username  : username to login
     * @param password  : password of the username to login
     *//*

    protected void loginUser(String username, String password) {
        driver.get(getPath("/cms/login"));
        WebElement loginForm = createWaitDriver().until(CustomExpectedConditions.pageIsAvailable(getPath("/cms/login"), By.name("loginForm")));
        Assert.assertNotNull(loginForm, "loginForm not found");

        WebElement usernameField = createWaitDriver().until(ExpectedConditions.elementToBeClickable(By.name("username")));
        WebElement passwordField = createWaitDriver().until(ExpectedConditions.elementToBeClickable(By.name("password")));

        usernameField.sendKeys(username);
        passwordField.sendKeys(password);

        WebElement loginBtn = findByXpath("//button[contains(@class, 'button buttonBlue')] | //a[contains(@href, '#login')]");
        Assert.assertNotNull(loginBtn, "login button not found.");
        clickOn(loginBtn);
        Assert.assertFalse(isVisible(By.xpath("/*/
/*[contains(text(), 'Invalid username')]"), 3), "User or Password is invalid");
    }

    */
/**
     * Click on field, clear it and type text into it.
     * @param field WebElement, filed to send keys into it
     * @param text String, text to send into input field
     * @return boolean, true if input's value was changed, false if not (already had desired value).
     *//*

    protected boolean typeInto(WebElement  field,
                               String      text){
        boolean inputFieldValueChanged = false;
        Long maxMilliSecondsToWait = 5000L;
        String xPath;
        try {
            xPath = generateUglyXpath(field, "");
        }catch (StaleElementReferenceException eee){
            getLogger().error("Ugly xPath generation failed. Re trying.");
            xPath = generateUglyXpath(field, "");
        }

        Long start = new Date().getTime();

        while(!getValueFromInput(xPath).equals(text)){
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

    */
/**
     * Returns input's value, even if it is hidden input.
     * @param xPathToInput String. xPath to input element
     * @return String. Input's value
     *//*

    protected String getValueFromInput(String xPathToInput){
        String jsResponse = null;
        String javascriptToExecute = "" +
                "function getElementByXpath(path) {" +
                "    return document.evaluate(path, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;" +
                "}" +

                "node = getElementByXpath(\""+xPathToInput+"\");" +
                "return node.value;";
        try {
            jsResponse = (String) ((JavascriptExecutor) driver).executeScript(javascriptToExecute);
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
        return jsResponse;
    }

    private String generateUglyXpath(WebElement childElement, String current) {
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

    protected WebDriver getMainDriver(){
        return originalDriver!=null?originalDriver:driver;
    }
}

*/
