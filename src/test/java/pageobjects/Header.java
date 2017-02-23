package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by Sergey Mokhov on 28/01/17.
 */
public class Header extends GenericPage {
    @FindBy(how = How.XPATH, using = "//a[@data-qa-id='header-link-my-kijiji']")
    private WebElement myKijijiMenuButton;
    @FindBy(how = How.LINK_TEXT, using = "My Ads")
    private WebElement myAdsLink;

    public Header(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public MyAdsPage goToMyAds(){
        clickOn(myKijijiMenuButton);
        clickOn(myAdsLink);
        return new MyAdsPage(getDriver());
    }
}
