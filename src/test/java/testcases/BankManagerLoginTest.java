package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;

public class BankManagerLoginTest extends BaseTest{
	
	@Test
	public void bankManagerLoginTest() {
		
		click("bmlBtn_CSS");
		Assert.assertTrue(isElementPresent("addCustBtn_CSS"), "Bank Manager not logged in");
		
		
	}
	
	

}
