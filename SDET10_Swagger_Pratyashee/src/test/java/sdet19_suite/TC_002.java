package sdet19_suite;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class TC_002 {
	public static String baseURL = "http://rest-api.upskills.in/api/rest_admin";
	public static String username;
	
	public static void login(String accessToken) {
		String[][] resultArray = Get_Data.getDataFromExcel("Login");
		username = resultArray[1][0];
		String pwd = resultArray[1][1];
		RestAssured.baseURI = baseURL;
		
		given()
		.header("content-Type","application/json")
		.header("Authorization","bearer " + accessToken)
		.body("{\r\n"
				+ "  \"username\": \"" + username + "\",\r\n"
				+ "  \"password\": \"" + pwd + "\"\r\n"
				+ "}")
		.when()
		.post("/login")
		
		.then()
		.assertThat().statusCode(200).and()
		.body("data.username",equalTo(username));
		System.out.println("Successfully logged in!");
	}
	
	public static void getAdminAccountDetails(String accessToken) {
		RestAssured.baseURI = baseURL;
		
		Response response = given()
		.header("content-Type","application/json")
		.header("Authorization","bearer " + accessToken)
		
		.when()
		.get("/user")
		
		.then()
		.assertThat().statusCode(200).and()
		.body("data.username",equalTo(username))
		.extract().response();
		
		String jsonResponse = response.asString();
		JsonPath responseBody = new JsonPath(jsonResponse);
		String usernameResp = responseBody.get("data.username");
		System.out.println("the username of the admin is: " + usernameResp);
	}
	
	public static void logout(String accessToken) {
		RestAssured.baseURI = baseURL;
		given()
		.header("content-Type","application/json")
		.header("Authorization","bearer " + accessToken)
		
		.when()
		.post("/logout")
		
		.then()
		.assertThat().statusCode(200);
		System.out.println("User successfully logged out!");
	}

}
