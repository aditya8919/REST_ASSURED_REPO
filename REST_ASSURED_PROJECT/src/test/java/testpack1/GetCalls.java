package testpack1;

import static io.restassured.RestAssured.given;

import java.util.Map;

import com.google.gson.Gson;

import groovy.json.JsonParser;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class GetCalls
{

	/**
	 * @return
	 */
	public static String getAuthToken()
	{
		try
		{
			String url = "https://restful-booker.herokuapp.com/auth";
			
			ValidatableResponse response = given().baseUri(url).header("Content-Type", "application/json")
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

	public static void getAllBookingIds()
	{
		String url = "https://restful-booker.herokuapp.com/booking?";
		try
		{
			ValidatableResponse response = given().baseUri(url).header("Content-Type", "application/json")
					.header("Accept", "application/json").header("Authorization", "Bearer " + getAuthToken()).when().get().then()
					.statusCode(200);

			Response resp = response.extract().response();
			
			System.out.println(resp.asPrettyString());
			
			System.out.println("JSON PATH");
			System.out.println();
			
			JsonPath jsonPathBody = resp.getBody().jsonPath(); 
//			System.out.println(jsonPathBody.getInt("totalprice"));
//			System.out.println(jsonPathBody.getString("firstname"));
//			Object jsonObjFromResponse = jsonPathBody.obj
		}
		catch (Exception ex)
		{
			System.out.println("Exception occurred while getting all bookings: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	/**
	 * Retrieves booking details by ID.
	 * 
	 * @param id The booking ID to retrieve details for.
	 */
	public static void getBookingDetailsById(int id)
	{
		String url = "https://restful-booker.herokuapp.com/booking/" + id;

		 Response resp = given().baseUri(url).header("Content-Type", "application/json").header("Accept", "application/json")
				.header("Authorization", "Bearer " + getAuthToken())
				.when().get()
				.then().extract().response();
		
		 System.out.println("Response body is : ");
		 
		  String respBody = resp.getBody().asPrettyString();
		 System.out.println();
		 
		 
			System.out.println("JSON PATH");
			System.out.println();
			
			JsonPath jsonPathBody = resp.getBody().jsonPath(); 
			System.out.println(jsonPathBody.getInt("totalprice"));
			System.out.println(jsonPathBody.getString("firstname"));
			Object jsonObjFromResponse = jsonPathBody.getJsonObject(url);
			
			System.out.println();
			System.out.println("***** USING GSON *****");
			System.out.println();
			
			Gson gson = new Gson();
			
			 Map<String, Object> gsonParse = gson.fromJson(respBody, Map.class);
			 
			 gsonParse.get("totalprice");
			 gsonParse.get("firstname");
			 
			
//		System.out.println("Booking details for ID " + id + " retrieved successfully.");

	}

	/**
	 * Creates a booking with hardcoded values.
	 */
	public static int createBooking()
	{

		String url = "https://restful-booker.herokuapp.com/booking";
		String body = "{\r\n" + "    \"firstname\" : \"Jim\",\r\n" + "    \"lastname\" : \"Brown\",\r\n"
				+ "    \"totalprice\" : 111,\r\n" + "    \"depositpaid\" : true,\r\n" + "    \"bookingdates\" : {\r\n"
				+ "        \"checkin\" : \"2018-01-01\",\r\n" + "        \"checkout\" : \"2019-01-01\"\r\n" + "    }\r\n" + "}";

		ValidatableResponse resp = given().baseUri(url).header("Content-Type", "application/json")
				.header("Accept", "application/json").header("Authorization", "Bearer " + getAuthToken()).body(body).when()
				.post().then().statusCode(200).log().all();

		System.out.println("Booking created successfully with ID : " + resp.extract().path("bookingid"));

		return resp.extract().path("bookingid");
	}

	/**
	 * Updates a booking with hardcoded values.
	 * 
	 * @param id The booking ID to update.
	 */
	public static void updateBooking(int id)
	{
		String url = "https://restful-booker.herokuapp.com/booking/" + id;

		given().baseUri(url).header("Content-Type", "application/json").header("Accept", "application/json")
				.header("Cookie", "token=" + getAuthToken())
				.body("{\r\n" + "    \"firstname\" : \"Jim\",\r\n" + "    \"lastname\" : \"Brown\",\r\n"
						+ "    \"totalprice\" : 111,\r\n" + "    \"depositpaid\" : true,\r\n" + "    \"bookingdates\" : {\r\n"
						+ "        \"checkin\" : \"2018-01-01\",\r\n" + "        \"checkout\" : \"2019-01-01\"\r\n" + "    }\r\n"
						+ "}")
				.when().put().then().statusCode(200).log().all();

		System.out.println("Booking with ID " + id + " updated successfully.");
	}

	public static void partialUpdateBooking(int id)
	{
		String url = "https://restful-booker.herokuapp.com/booking/" + id;

		given().baseUri(url).header("Content-Type", "application/json").header("Accept", "application/json")
				.header("Cookie", "token=" + getAuthToken())
				.body("{\r\n" + "    \"firstname\" : \"PQR\",\r\n" + "    \"lastname\" : \"TUV\"\r\n" + "}").when().patch()
				.then().statusCode(200).log().all();
	}

	/**
	 * Deletes a booking by ID.
	 * 
	 * @param id The booking ID to delete.
	 */
	public static void deleteBooking(int id)
	{
		String url = "https://restful-booker.herokuapp.com/booking/" + id;

		given().baseUri(url).header("Content-Type", "application/json").header("Accept", "application/json")
				.header("Cookie", "token=" + getAuthToken()).when().delete().then().statusCode(201).log().all();

		System.out.println("Booking with ID " + id + " deleted successfully.");
	}

	public static void main(String[] args) throws InterruptedException
	{

		System.setProperty("javax.net.ssl.trustStore", "truststore.jks");
		System.setProperty("javax.net.ssl.trustStorePassword", "changeit");

		RestAssured.useRelaxedHTTPSValidation(); // disables SSL cert validation (testing only)

//		System.out.println(getAuthToken());

		System.out.println();
		System.out.println("***** getAllBooking ******");
		System.out.println();

		getAllBookingIds();

		System.out.println();
		System.out.println("***** getBookingDetailsById ******");
		System.out.println();

		getBookingDetailsById(1);

//		System.out.println();
//		System.out.println("***** createBooking ******");
//		System.out.println();
//
//		int bookingId = createBooking();
//
//		Thread.sleep(2000);
//
//		System.out.println();
//		System.out.println("***** updateBooking ******");
//		System.out.println();
//
//		updateBooking(bookingId);
//
//		System.out.println();
//		System.out.println("***** partialUpdateBooking ******");
//		System.out.println();
//
//		partialUpdateBooking(bookingId);
//
//		System.out.println();
//		System.out.println("***** deleteBooking ******");
//		System.out.println();
//
//		deleteBooking(bookingId);
//
//		System.out.println();
//		System.out.println("***** getBookingDetailsById after delete ******");
//		System.out.println();
//
//		getBookingDetailsById(bookingId);

	}

}
