package base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.log4testng.Logger;

import extentlisteners.ExtentListeners;
import utilities.DbManager;
import utilities.ExcelReader;
import utilities.MonitoringMail;

public class BaseTest {

	public static WebDriver driver;
	private static Properties OR = new Properties();
	private static Properties config = new Properties();
	private static Logger log = Logger.getLogger(BaseTest.class);
	private static WebDriverWait wait;
	private static FileInputStream fis;
	public static ExcelReader excel = new ExcelReader("./src/test/resources/excel/testdata.xlsx");
	public static MonitoringMail mail = new MonitoringMail();

	public void click(String locatorKey) {

		if (locatorKey.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locatorKey))).click();
//			driver.findElement(By.xpath("//*[@id='identifierNext']/div/button/span")).click();

		} else if (locatorKey.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(locatorKey))).click();

		} else if (locatorKey.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(locatorKey))).click();

		}

		log.info("Clicking on an Element" + locatorKey);
		ExtentListeners.test.info("Clicking on an Element : " + locatorKey);

	}

	public boolean isElementPresent(String locatorKey) {

		try {

			if (locatorKey.endsWith("_XPATH")) {
				driver.findElement(By.xpath(OR.getProperty(locatorKey)));
//				driver.findElement(By.xpath("//*[@id='identifierNext']/div/button/span")).click();

			} else if (locatorKey.endsWith("_ID")) {
				driver.findElement(By.id(OR.getProperty(locatorKey)));

			} else if (locatorKey.endsWith("_CSS")) {
				driver.findElement(By.cssSelector(OR.getProperty(locatorKey)));

			}

		} catch (Throwable t) {
			
			log.info("Error while finding an Element" + locatorKey);
			ExtentListeners.test.info("Error while finding an Element : " + locatorKey);
			
			return false;

		}

		log.info("Finding an Element" + locatorKey);
		ExtentListeners.test.info("Finding an Element : " + locatorKey);
		
		return true;

	}

	public void type(String locatorKey, String value) {

		if (locatorKey.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locatorKey))).sendKeys(value);

		} else if (locatorKey.endsWith("_ID")) {
			System.out.println(OR.getProperty(locatorKey));
			driver.findElement(By.id(OR.getProperty(locatorKey))).sendKeys(value);
//			driver.findElement(By.id("identifierId")).sendKeys(value);

		} else if (locatorKey.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(locatorKey))).sendKeys(value);
//			driver.findElement(By.cssSelector("#password134 > div.aCsJod.oJeWuf > div > div.Xb9hP > input")).sendKeys("asdfd");

		}
		log.info("Typing in an element : " + locatorKey + " entered the values as : " + value);
		ExtentListeners.test.info("Typing in an element : " + locatorKey + " entered the values as : " + value);

	}

	@BeforeSuite
	public void setUp() {

		if (driver == null) {

			PropertyConfigurator.configure("./src/test/resources/properties/log4j.properties");
			log.info("Test execution started !!!");
			try {
				fis = new FileInputStream("./src/test/resources/properties/Config.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				config.load(fis);
				log.info("Config file loaded");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				fis = new FileInputStream("./src/test/resources/properties/OR.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				OR.load(fis);
				log.info("OR properties file loaded");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (config.getProperty("browser").equals("chrome")) {

				driver = new ChromeDriver();
				log.info("Chrome Browser launched");
			} else if (config.getProperty("browser").equals("firefox")) {

				driver = new FirefoxDriver();
				log.info("Firefox Browser launched");
			}
			driver.get(config.getProperty("testsiteurl"));
			log.info("Navigate to : " + config.getProperty("testsiteurl"));
			driver.manage().window().maximize();
			driver.manage().timeouts()
					.implicitlyWait(Duration.ofSeconds(Integer.parseInt(config.getProperty("implicit.wait"))));

			try {
				DbManager.setMysqlDbConnection();
				log.info("DB  Connection established !!! ");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(config.getProperty("explicit.wait"))));
		}

	}

	@AfterSuite
	public void tearDown() {

		driver.quit();
		log.info("Test execution completed !!");

	}

}
