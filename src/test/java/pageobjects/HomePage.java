package pageobjects;

import core.ApiController;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by Sergey Mokhov on 19/01/17.
 */
public class HomePage extends Header{
    private String pageURL = ApiController.getPath("/");


    public HomePage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver, this);
    }
}
