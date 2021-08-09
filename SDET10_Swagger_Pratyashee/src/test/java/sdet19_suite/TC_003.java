package sdet19_suite;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import io.restassured.RestAssured;

public class TC_003 {
	public static String baseURL = "http://rest-api.upskills.in/api/rest_admin";
	public static String firstname, lastname, email, pwd, confirmpwd, phone;
	
	public static void addNewCustomer(String accessToken) {
		String[][] resultArray = Get_Data.getDataFromExcel("Customer");
		RestAssured.baseURI = baseURL;
		
		int rows = resultArray.length;
		int columns = resultArray[0].length;
		
		for(int i=1; i<rows; i++) {
			for(int j=0; j<columns; j++) {
				if(j==0)
					firstname = resultArray[i][j];
				else if(j==1)
					lastname = resultArray[i][j];
				else if(j==2)
					email = resultArray[i][j];
				else if(j==3)
					pwd = resultArray[i][j];
				else if(j==4)
					confirmpwd = resultArray[i][j];
				else if(j==5)
					phone = resultArray[i][j];
			}
			given()
			.header("content-Type","application/json")
			.header("Authorization","bearer " + accessToken)
			.body("{\r\n"
					+ "  \"firstname\": \"" + firstname + "\",\r\n"
					+ "  \"lastname\": \"" + lastname + "\",\r\n"
					+ "  \"email\": \"" + email + "\",\r\n"
					+ "  \"password\": \"" + pwd + "\",\r\n"
					+ "  \"confirm\": \"" + confirmpwd + "\",\r\n"
					+ "  \"telephone\": \"" + phone + "\"\r\n"
					+ "}")
			.when()
			.post("/customers")
			
			.then()
			.assertThat().statusCode(200);
			System.out.println("New Customer added: " + email);
		}	
	}
	
	public static void getCustomerDetails(String accessToken) {
		RestAssured.baseURI = baseURL;
		
		given()
		.header("content-Type","application/json")
		.header("Authorization","bearer " + accessToken)
		
		.when()
		.get("/customers/limit/6/page/1")
		
		.then()
		.assertThat().statusCode(200).and()
		.header("x-pagination-limit", equalTo("6"));
	}

}
