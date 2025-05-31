package basePack;

import static io.restassured.RestAssured.given;

import io.restassured.response.ValidatableResponse;

public class BaseClass {
	
	public String baseUrl = "https://restful-booker.herokuapp.com/auth";
	String basePath;
	
	
	/**
	 * @return
	 */
	public String getAuthToken()
	{
		try
		{
			
			ValidatableResponse response = given().baseUri(baseUrl).header("Content-Type", "application/json")
					.body("{\"username\":\"admin\",\"password\":\"password123\"}").when().post().then().statusCode(200);
//						.log().all();
			 String resp = response.extract().response().asString();
			 

			return response.extract().path("token");
		}
		catch (Exception e)
		{
			System.out.println("Exception occurred while getting auth token: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

}
