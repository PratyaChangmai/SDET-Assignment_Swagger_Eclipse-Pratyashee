package sdet19_suite;

import org.testng.annotations.Test;

public class Execute_Test_Cases {
	public String accessToken;
	
	@Test
	public void executeTestCases() {
		
		//Executing TC 001
		accessToken = TC_001.getAccessToken();
		
		//Executing TC 002
		TC_002.login(accessToken);
		TC_002.getAdminAccountDetails(accessToken);
		
		//Executing TC_003
		TC_003.addNewCustomer(accessToken);
		TC_003.getCustomerDetails(accessToken);
		
		//Executing TC_037
		TC_037.addNewManufacturer(accessToken);
		TC_037.addNewProduct(accessToken);
		
		//Logout
		TC_002.logout(accessToken);
	}
}
