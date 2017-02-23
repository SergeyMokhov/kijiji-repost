package core;

import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * This class controls WebDriver behaviour, instantiates and closes driver, sets some driver properties.
 */
public final class DriverController{
    private static WebDriver driver;
    private static String browser;
    private static Boolean remoteTest;
    private static String downloadsSaveFolder = ApiController.getPropertyValue("selenium.downloads.path", "target/documents/downloads");
    private static String downloadsGetFolder = ApiController.getPropertyValue("selenium.shared.folder", downloadsSaveFolder);
    private static String screenshotsFolder = ApiController.getPropertyValue("selenium.screenshots.path", "/tmp");
    private static String seleniumHubPort = ApiController.getPropertyValue("selenium.hub.port", "4444");
    private static String seleniumHubIp = ApiController.getPropertyValue("selenium.hub.ip", "192.168.1.3");
    private static final String CHROME = "chrome";
    private static final String FIREFOX = "firefox";
    private static final String IE = "ie";
    public File chromedriver;

    /**
     * This method will maximize the browser up to the screen size of the window.
     */
    public void browserMaximize() {
        if (driver != null) {
            if (browser.equals("chrome")) {
                Toolkit toolkit = Toolkit.getDefaultToolkit();
                Dimension screenResolution = new Dimension((int)
                        toolkit.getScreenSize().getWidth(), (int)
                        toolkit.getScreenSize().getHeight());
                driver.manage().window().setSize(screenResolution);
            } else {
                driver.manage().window().maximize();
                driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
                driver.manage().deleteAllCookies();
            }
        }
    }

    /**
     * Simply refreshes the browser
     */
    public void refreshBrowser(){
        driver.navigate().refresh();
    }

    /**
     * Closes all the browser windows and quits the webdriver gracefully
     */
    public void closeDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    /**
     *
     * @param browserName Browser you want to use: chrome, firefox, ie
     * @param remoteUrl Url to your selenium grid Hub or BrowserStack. Ignored when selenium.jahia.remote = false
     */
    public WebDriver initDriver(String browserName, String remoteUrl) {
        if (driver == null) {
            browser = browserName;
            remoteTest = Boolean.valueOf(ApiController.getPropertyValue("selenium.remote", "false"));
            DesiredCapabilities desiredCapabilities = null;

            switch (browserName) {
                case FIREFOX:
                    FirefoxProfile firefoxProfile = new FirefoxProfile();
                    firefoxProfile.setAssumeUntrustedCertificateIssuer(true);
                    firefoxProfile.setEnableNativeEvents(true);
                    firefoxProfile.setPreference("browser.download.folderList", 2);
                    if (remoteTest){
                        firefoxProfile.setPreference("browser.download.dir", "/home/application/Downloads");
                    }else{
                        firefoxProfile.setPreference("browser.download.dir", new File(getDownloadsFolderToSave()).getAbsolutePath());
                    }
                    firefoxProfile.setPreference("browser.download.manager.showWhenStarting",false);
                    firefoxProfile.setPreference("browser.download.manager.showAlertOnComplete", false);
                    firefoxProfile.setPreference("browser.download.panel.shown", true); //true means 'do not show' the panel
                    firefoxProfile.setPreference("browser.helperApps.alwaysAsk.force", false);
                    firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk", "text/plain,application/pdf,application/csv,application/x-gzip,application/zip,text/csv,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                    firefoxProfile.setPreference("pdfjs.disabled", true);
                    firefoxProfile.setPreference("browser.download.manager.closeWhenDone", true);
                    firefoxProfile.setPreference("browser.helperApps.neverAsk.openFile", true);
                    firefoxProfile.setPreference("privacy.clearOnShutdown.downloads", false);
                    firefoxProfile.setPreference("privacy.cpd.downloads", false);
                    firefoxProfile.setPreference("browser.safebrowsing.downloads.enabled", false);

                    desiredCapabilities = DesiredCapabilities.firefox();
                    desiredCapabilities.setCapability(FirefoxDriver.PROFILE, firefoxProfile);
                    if(!remoteTest){
                        System.out.println("Executing Local Test with firefox");
                        driver = new FirefoxDriver(desiredCapabilities);
                        browserMaximize();
                    }
                    break;

                case CHROME:
                    desiredCapabilities = DesiredCapabilities.chrome();
                    ChromeOptions options = new ChromeOptions();

                    if(detectOS().equals("Mac")){
                        chromedriver = new File("src/test/resources/drivers/chromedriverMac");
                        options.addArguments("--kiosk");
                    }
                    else if(detectOS().equals("Linux")){
                        chromedriver = new File("src/test/resources/drivers/chromedriverLinux");
                    }
                    else {
                        chromedriver = new File("src/test/resources/drivers/chromedriver.exe");
                    }

                    System.setProperty("webdriver.chrome.driver", chromedriver.getAbsolutePath());
                    Map<String, Object> prefs = new HashMap<String, Object>();

                    prefs.put("download.prompt_for_download", false);
                    if (remoteTest){
                        prefs.put("download.default_directory", "/home/application/Downloads");
                    }else{
                        prefs.put("download.default_directory", getDownloadsFolderToSave());
                    }
                    options.setExperimentalOption("prefs", prefs);
                    options.addArguments("--start-maximized");
                    options.addArguments("--no-sandbox");

                    desiredCapabilities.getCapability(CapabilityType.SUPPORTS_JAVASCRIPT);
                    desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, options);
                    if(!remoteTest){
                        System.out.println("Executing Local Test with chrome");
                        driver = new ChromeDriver(desiredCapabilities);
                    }
                    break;

                case IE:
                    desiredCapabilities = DesiredCapabilities.internetExplorer();
                    if(remoteTest){
                        System.out.println("Executing Local Test with ie");
//                        desiredCapabilities.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
                        desiredCapabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
                        desiredCapabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
//                        desiredCapabilities.setCapability(InternetExplorerDriver.ENABLE_ELEMENT_CACHE_CLEANUP,true);
                        desiredCapabilities.setJavascriptEnabled(true);
                        desiredCapabilities.setCapability(InternetExplorerDriver.FORCE_CREATE_PROCESS, true);
                        desiredCapabilities.setCapability(InternetExplorerDriver.IE_SWITCHES,"-private -k");
                    }else{
//                        driver = new InternetExplorerDriver(desiredCapabilities);
                        throw new IllegalArgumentException("You cannot run IE tests locally, unless you are on Windows machine. Do you really?");
                    }
                    break;
//                case "android":
//                    desiredCapabilities = DesiredCapabilities.android();
//                    driver = new AndroidDriver();
//                    if(remoteTest){
//                        throw new IllegalArgumentException("Android browser is not supported by the grid");
//
//                    }
//                    break;
            }

            if (desiredCapabilities != null) {
                if(remoteTest) {
                    System.out.println("Executing Remote Test on " + remoteUrl +" with browser: " + desiredCapabilities.getBrowserName());
                    try {
                        RemoteWebDriver driverRemote = new RemoteWebDriver(new URL(remoteUrl), desiredCapabilities);
                        driverRemote.setFileDetector(new LocalFileDetector());
                        driver = driverRemote;
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return getDriver();
    }

    private static String detectOS() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return "Windows";
        } else if (os.contains("nux") || os.contains("nix")) {
            return "Linux";
        } else if (os.contains("mac")) {
            return "Mac";
        } else {
            return "Other";
        }
    }

    public WebDriver getDriver(){
        return driver;
    }

    public String getBrowser(){
        return browser;
    }

    public String getDownloadsFolder(){
        return downloadsGetFolder;
    }

    public String getDownloadsFolderToSave(){
        return downloadsSaveFolder;
    }

    public String getRemoteURL(){
        return "http://"+seleniumHubIp+":"+seleniumHubPort+"/wd/hub";
    }

    public String getScreenshotsFolder() {
        return screenshotsFolder;
    }
}
