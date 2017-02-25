package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by Sergey Mokhov on 25/02/17.
 */
public class SelectCategoryPage extends Header{
    public SelectCategoryPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
}
