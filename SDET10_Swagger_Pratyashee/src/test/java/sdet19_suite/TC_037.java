package sdet19_suite;

import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class TC_037 {
	
	public static String baseURL = "http://rest-api.upskills.in/api/rest_admin";
	public static String mfName;
	public static int mfId;
	public static String model, qty, price;
	
	public static void addNewManufacturer(String accessToken) {
		String[][] resultArray = Get_Data.getDataFromExcel("Manufacturer");
		RestAssured.baseURI = baseURL;
		
		int rows = resultArray.length;
		
		for(int i=1; i<rows; i++) {
			mfName = resultArray[i][0];
			Response response = given()
			.header("content-Type","application/json")
			.header("Authorization","bearer " + accessToken)
			.body("{\r\n"
					+ "  \"name\": \"" + mfName + "\",\r\n"
					+ "  \"image\": \"\",\r\n"
					+ "  \"sort_order\": 0,\r\n"
					+ "  \"manufacturer_store\": [\r\n"
					+ "    \"0\"\r\n"
					+ "  ]\r\n"
					+ "}")
			.when()
			.post("/manufacturers")
			
			.then()
			.assertThat().statusCode(200)
			.extract().response();
			
			String jsonResponse = response.asString();
			JsonPath responseBody = new JsonPath(jsonResponse);
			mfId = responseBody.get("data.id");
			System.out.println("New Manufacturer added: " + mfName + " with ID: " + mfId);
		}
	}
	
	public static void addNewProduct(String accessToken) {
		String[][] resultArray = Get_Data.getDataFromExcel("Product");
		RestAssured.baseURI = baseURL;
		
		int rows = resultArray.length;
		int columns = resultArray[0].length;
		
		for(int i=1; i<rows; i++) {
			for(int j=0; j<columns; j++) {
				if(j==0)
					model = resultArray[i][j];
				else if(j==1)
					qty = resultArray[i][j];
				else if(j==2)
					price = resultArray[i][j];
			}
			given()
			.header("content-Type","application/json")
			.header("Authorization","bearer " + accessToken)
			.body("{\r\n"
					+ "  \"model\": \"" + model + "\",\r\n"
					+ "  \"quantity\": \"" + qty + "\",\r\n"
					+ "  \"price\": \"" + price + "\",\r\n"
					+ "  \"manufacturer_id\": \"" + mfId + "\",\r\n"
					+ "  \"product_description\": [\r\n"
					+ "    {\r\n"
					+ "      \"name\": \"Product name\",\r\n"
					+ "      \"description\": \"This is a new product\",\r\n"
					+ "      \"meta_title\": \"Meta title of the product\"\r\n"
					+ "    }\r\n"
					+ "  ]\r\n"
					+ "}")
			.when()
			.post("/products")
			
			.then()
			.assertThat().statusCode(200);
			System.out.println("Product created for manufacturer id: " + mfId);
		}	
	}
}
