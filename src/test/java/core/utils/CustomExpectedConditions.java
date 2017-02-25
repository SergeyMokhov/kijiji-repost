package core.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;

/**
 * Created by sergey on 2015-11-26.
 */
public class CustomExpectedConditions {
    /**
     * Expectation for element to stop moving.
     * @param element element to track movements and expect him to stop moving
     * @return true if element stopped moving
     */
    public static ExpectedCondition<Boolean> elementHasStoppedMoving(final WebElement element) {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    Point initialLocation = element.getLocation();

                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                    }

                    Point finalLocation = element.getLocation();
                    return initialLocation.equals(finalLocation);
                } catch (WebDriverException ee) {
                    return false;
                } catch (NullPointerException eee){
                    return true;
                }
            }
        };
    }

    public static ExpectedCondition<Boolean> elementStoppedResizing(final WebElement element) {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                Dimension initialLocation = element.getSize();

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                }

                Dimension finalLocation = element.getSize();
                return initialLocation.equals(finalLocation);
            }
        };
    }

    /**
     *  Expectation for javascript execution without exceptions.
     * @param javaScript String Javascript to execute.
     * @return true if executed without exceptions.
     */
    public static ExpectedCondition<Boolean> javascriptWithoutException(final String javaScript) {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    ((JavascriptExecutor) driver).executeScript(javaScript);
                } catch (Exception e) {
                    return false;
                }
                return true;
            }
        };
    }

    /**
     * This expectation refreshes the page every poll and tries to find an element on it, until timeout is reached or element found.
     * Useful for situations when desired content appears on the page not every time, or for waiting until webserver start.
     * @param path URL which will be refreshed every poll
     * @param elementOnPage element on the page, which shows that page loaded correctly.
     * @return Element located by elementOnPage parameter.
     */
    public static ExpectedCondition<WebElement> pageIsAvailable(final String path, final By elementOnPage) {
        return new ExpectedCondition<WebElement>() {
            @Override
            public WebElement apply(WebDriver driver) {
                driver.get(path);
                try {
                    driver.get(path);
                    return driver.findElement(elementOnPage);
                } catch (NoSuchElementException e) {
                    throw e;
                } catch (WebDriverException e) {
                    throw e;
                }
            }
        };
    }

    public static ExpectedCondition<Boolean> inVisibilityOf(final WebElement element) {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try{
                    return !element.isDisplayed();
                }catch (NullPointerException e){
                    return true;
                }catch (NoSuchElementException ee){
                    return true;
                }catch (StaleElementReferenceException eee){
                    return true;
                }
            }
        };
    }

    public static ExpectedCondition<Boolean> elementToBeEnabled(final WebElement element) {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try{
                    return element.isEnabled();
                }catch (NullPointerException e){
                    return false;
                }catch (NoSuchElementException ee){
                    return false;
                }catch (StaleElementReferenceException eee){
                    return false;
                }
            }
        };
    }

    public static ExpectedCondition<Boolean> elementToContainAttributeValue(final WebElement element,
                                                                         final String attributeName,
                                                                         final String attributeValue) {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try{
                    return element.getAttribute(attributeName).contains(attributeValue);
                }catch (NullPointerException e){
                    e.printStackTrace();
                    return false;
                }catch (NoSuchElementException ee){
                    ee.printStackTrace();
                    return false;
                }catch (StaleElementReferenceException eee){
                    eee.printStackTrace();
                    return false;
                }
            }
        };
    }
}
