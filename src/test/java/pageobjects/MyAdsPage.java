package pageobjects;

import businessObjects.Ad;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.*;

import java.util.List;

/**
 * Created by Sergey Mokhov on 28/01/17.
 */
public class MyAdsPage extends Header{
    @FindAll({@FindBy( how = How.ID, using = "CtaDelete")})
    private List<WebElement> deleteButtons;
    @FindBy(how = How.ID, using = "DeleteSurveyOK")
    private WebElement deleteThisAdButton;
    @FindBy(xpath = "header-link-post-ad")
    private WebElement postAdButton;

    public MyAdsPage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * Selects Ad be setting checkbox 'On' than clicks Delete button. Assumes that all ads have unique title
     * @param ad Ad to delete
     * @return same instance of MyAdsPage
     */
    public MyAdsPage deleteAdUsingCheckbox(Ad ad){
        selectAd(ad);
        clickOn(deleteButtons.get(0));
        waitForElementToBeEnabled(deleteThisAdButton, 1);
        clickOn(deleteThisAdButton);
        return this;
    }

    public boolean adExists(Ad ad){
        return isElementPresentOnPage(By.xpath("//a[contains(text(),  '"+ad.getTitle()+"')]"), 1);
    }

    public SelectCategoryPage clickPostAd(){
        clickOn(postAdButton);
        return new SelectCategoryPage(getDriver());
    }

    /**
     * Selects Ad be setting checkbox 'On'. Assumes that all ads have unique title
     * @param ad Ad to select
     * @return same instance of MyAdsPage
     */
    public MyAdsPage selectAd (Ad ad){
        WebElement adsCheckbox = findBy(By.xpath("//a[contains(text(),  '"+ad.getTitle()+"')]" +
                "/ancestor::div[contains(@class, 'table-row')]//input[@type='checkbox']"));
        waitForElementToBeEnabled(adsCheckbox, 1);
        waitForElementToBeVisible(adsCheckbox);
        clickOn(adsCheckbox);
        return this;
    }
}
