package sdet19_suite;

import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class TC_001 {
	public static String accessToken;
	public static String baseURL = "http://rest-api.upskills.in/api/rest_admin";
	
	public static String getAccessToken() {
		RestAssured.baseURI = baseURL;
		Response response = given()
		.header("content-Type","application/json")
		.header("Authorization","Basic dXBza2lsbHNfcmVzdF9hZG1pbl9vYXV0aF9jbGllbnQ6dXBza2lsbHNfcmVzdF9hZG1pbl9vYXV0aF9zZWNyZXQ=")
		
		.when()
		.post("/oauth2/token/client_credentials")
		
		.then()
		.assertThat().statusCode(200).and()
		.extract().response();
		
		String jsonResponse = response.asString();
		JsonPath responseBody = new JsonPath(jsonResponse);
		accessToken = responseBody.get("data.access_token");
		System.out.println("Access token is: " + accessToken);
		
		return accessToken;
	}

}
