package org.jahia.pageobject.tests;

import businessObjects.Ad;
import businessObjects.User;
import core.DriverController;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import pageobjects.LoginPage;

/**
 * Created by Sergey Mokhov (sergey) on 2017-01-10.
 */
public class RepostKijijiTest {

    @Test
    public void repostTest(){
        WebDriver driver = new DriverController().initDriver("chrome", null);
        User root = new User("071522@gmail.com", "qwas500500");
        Ad adToRepost = new Ad("New 28 inch Samsung TV UN28H4000AF (Model 2016)");

        new LoginPage(driver)
                .login(root)
                .goToMyAds()
                .deleteAdUsingCheckbox(adToRepost);
    }
}
