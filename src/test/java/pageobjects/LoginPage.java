package pageobjects;

import businessObjects.User;
import core.ApiController;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by Sergey Mokhov (sergey) on 2017-01-10.
 */
public class LoginPage extends GenericPage {
    private String pageUrl =  ApiController.getPath("/t-login.html");

    @FindBy(how = How.NAME, using = "emailOrNickname")
    private WebElement usernameField;
    @FindBy(how = How.NAME, using = "password")
    private WebElement passwordField;
    @FindBy(how = How.ID, using = "SignInButton")
    private WebElement signInButton;

    public LoginPage(WebDriver driver) {
        super(driver);
        driver.get(pageUrl);
        PageFactory.initElements(driver, this);
    }

    public HomePage login(User user){
        typeInto(usernameField, user.getUsername());
        typeInto(passwordField, user.getPassword());
        clickOn(signInButton);
        return new HomePage(getDriver());
    }
}
