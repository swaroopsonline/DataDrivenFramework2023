package testcases;

import org.testng.annotations.Test;
import org.testng.annotations.Test;
import org.testng.annotations.Test;

import base.BaseTest;
import utilities.DataUtil;

public class LoginTest extends BaseTest {
	
	@Test(dataProviderClass = DataUtil.class, dataProvider = "dp")
	public void doLogin(String username){
		
		
//		type("username_ID","trainer@way2automation.com");
		type("username_ID",username);
		click("nextBtn_XPATH");
//		type("pass_CSS","asfdas");		
		
	}	

}
