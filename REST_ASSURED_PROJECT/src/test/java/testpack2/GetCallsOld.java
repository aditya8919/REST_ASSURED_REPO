package testpack2;

import static io.restassured.RestAssured.given;

import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.Test;

import groovy.json.JsonParser;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import utilities.ResponseHelper;

public class GetCallsOld
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

			System.out.println(resp.asString());
			
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
	
	@Test
	public static void getBookingDetailsById()
	{
		int id = 1;
		String url = "https://restful-booker.herokuapp.com/booking/" + id;

		 Response resp = given().baseUri(url).header("Content-Type", "application/json").header("Accept", "application/json")
				.header("Authorization", "Bearer " + getAuthToken())
		.when().get()
		.then().extract().response();

//		Assert.assertEquals(resp.getStatusCode(), 200);
//		Assert.assertEquals(resp.getStatusLine(), "HTTP/1.1 200 OK");
//		Assert.assertEquals(resp.header("Server"), "Heroku");
//		Assert.assertTrue(resp.timeIn(TimeUnit.MILLISECONDS) < 2000, "Response time is greater than 2000 MS");
		
		 
		 ResponseHelper.verifyStatusCode(resp, 200);
		 ResponseHelper.verifyStatusLine(resp, "OK");
		 ResponseHelper.isResponseTimeLessThan(resp, 2000);
		 ResponseHelper.verifyHeaderValue(resp, "Server", "Heroku");
		 
		System.out.println("Booking details for ID " + id + " retrieved successfully.");
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

//	public static void main(String[] args) throws InterruptedException
//	{

//		System.setProperty("javax.net.ssl.trustStore", "truststore.jks");
//		System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
//
//		RestAssured.useRelaxedHTTPSValidation(); // disables SSL cert validation (testing only)
//
////		System.out.println(getAuthToken());
//
//		System.out.println();
//		System.out.println("***** getAllBooking ******");
//		System.out.println();
//
//		getAllBookingIds();
//
//		System.out.println();
//		System.out.println("***** getBookingDetailsById ******");
//		System.out.println();
//
//		getBookingDetailsById(1);
//
//		System.out.println();
//		System.out.println("***** createBooking ******");
//		System.out.println();
//
//		int bookingId = createBooking();

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

//	}

}
