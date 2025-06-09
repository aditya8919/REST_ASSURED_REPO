package api_calls;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import basePack.BaseClass;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import jsonPayload.RequestPayloadVO;
import utilities.AllObjects;
import utilities.CommonFunctions;
import utilities.ConfigHelper;
import utilities.ExtentReportsHelper;
import utilities.XMLHelper;

public class API_Calls extends BaseClass
{

	public API_Calls() throws IOException
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public boolean getAllBookingIds()
	{
		boolean testResult = false;
		try
		{
			String basePath = ConfigHelper.readProperty("getAllBookingsBaePath");
			RequestSpecification reqSpec = getRequestSpecification(basePath, AuthType.BEARER_TOKEN);
			ResponseSpecification respSpec = getResponseSpecification(200);

			ValidatableResponse response = given().spec(reqSpec).when().get().then().spec(respSpec);

			if(response != null)
			{
				ExtentReportsHelper.LogPass("Retrieved all booking id's.");
				testResult = true;
				
				Response resp = response.extract().response();

				System.out.println("Full response as String : ");
				System.out.println();

				System.out.println(resp.asPrettyString());

//				System.out.println("JSON PATH");
//				System.out.println();

//				JsonPath jsonPathBody = resp.getBody().jsonPath(); 
//				System.out.println(jsonPathBody.getInt("totalprice"));
//				System.out.println(jsonPathBody.getString("firstname"));
//				Object jsonObjFromResponse = jsonPathBody.obj

				System.out.println("Full response as JSON Array print");
				System.out.println();

				JsonArray dd = CommonFunctions.converResponseStringToJsonArray(resp.asPrettyString());

				System.out.println(dd);

				JsonElement ele = dd.get(0);

				JsonArray respJsonArray = CommonFunctions.converResponseStringToJsonArray(resp.prettyPrint());

				JsonObject firstJsonObj = respJsonArray.get(0).getAsJsonObject();

				int firstBookingId = firstJsonObj.get("bookingid").getAsInt();

				System.out.println("firstBookingId : " + firstBookingId);
			}
			else
			{
				ExtentReportsHelper.LogFail("Failed to retrieve all booking id's.");
			}

			return testResult;

		}
		catch (Exception ex)
		{
			ExtentReportsHelper.LogFail("Exception occurred while getting all bookings: " + ex.getMessage());
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * Retrieves booking details by ID.
	 * 
	 * @param id The booking ID to retrieve details for.
	 */
	public boolean getBookingDetailsById()
	{
		boolean testResultFlag = false;
		String respBody = null; 
		try
		{
			String bookingId = XMLHelper.readData("CreateBookingId");
			String basePath = "booking/"+bookingId;
			RequestSpecification reqSpec = getRequestSpecification(basePath, AuthType.BEARER_TOKEN);
			ResponseSpecification respSpec = getResponseSpecification(200);

			Response resp = given().spec(reqSpec).when().get().then().spec(respSpec).extract().response();

			System.out.println("Response body is : ");

			if (resp != null)
			{
				respBody = resp.getBody().asPrettyString();

				if (respBody != null  && !(respBody.isBlank()) && !(respBody.isEmpty()))
				{
					ExtentReportsHelper.LogPass("Booking details for ID " + bookingId + " retrieved successfully.");
					testResultFlag = true;
				}
				else
				{
					ExtentReportsHelper.LogFail("Failed to retrieve Booking details for ID " + bookingId + ".");
					return false;
				}
			}
			else
			{
				System.out.println("resp is null;");
				ExtentReportsHelper.LogFail("resp is null.");
				return false;
			}

			System.out.println();
			System.out.println(" ******* String to JSON Conversion for response body *******");
			System.out.println();

			System.out.println("Using JSON PATH");
			System.out.println();

			JsonPath jsonPathBody = resp.getBody().jsonPath();
			System.out.println(jsonPathBody.getInt("totalprice"));
			System.out.println(jsonPathBody.getString("firstname"));
//			Object jsonObjFromResponse = jsonPathBody.getJsonObject(url);

			System.out.println();
			System.out.println("***** USING GSON *****");
			System.out.println();

			Gson gson = new Gson();

			Map<String, Object> gsonParse = gson.fromJson(respBody, Map.class);

			gsonParse.get("totalprice");
			gsonParse.get("firstname");

			System.out.println("**** Common Method *****");

			System.out.println("respBody in String is : " + respBody);

			JsonObject jsonObj = CommonFunctions.converResponseStringToJsonObject(respBody);

			System.out.println("JSON OBJ : " + jsonObj);

			System.out.println();
			System.out.println("******** VERIFICATION *********");
			System.out.println();

//			 jsonObj.get("firstname") is likely returning a JSON library object, such as a JsonPrimitive or similar, 
//			 rather than a plain String.
//			 Assert.assertEquals(jsonObj.get("firstname").getAsString(), "Mark");
//			 Assert.assertEquals(jsonObj.get("totalprice").getAsInt(), 548);
		}
		catch (Exception e)
		{
			ExtentReportsHelper.LogFail("Exception in method : 'getBookingDetailsById'" + e.getMessage());
			e.printStackTrace();
			return false;
		}
		return testResultFlag;
	}

	/**
	 * Creates a booking with hardcoded values.
	 */
	public boolean createBooking()
	{
		try
		{
			String basePath =  ConfigHelper.readProperty("createBookingBasePath");
			int bookingId = -1;
			boolean testResult = false;

			HashMap<String, String> dates = new HashMap<String, String>();
			dates.put("checkin", "2018-01-01");
			dates.put("checkout", "2019-01-01");

			RequestPayloadVO requestPayloadVO = new RequestPayloadVO();
			requestPayloadVO.setFirstName("Jim");
			requestPayloadVO.setLastName("Brown");
			requestPayloadVO.setTotalprice(111);
			requestPayloadVO.setDepositpaid(true);
			requestPayloadVO.setBookingdates(dates);

			String reqBody = AllObjects.requestPayloadHelper().getRequestBody(requestPayloadVO);

			RequestSpecification reqSpec = getRequestSpecificationWithBody(basePath, reqBody, AuthType.BEARER_TOKEN);
			ResponseSpecification respSpec = getResponseSpecification(200);

			ValidatableResponse resp = given().spec(reqSpec).when().post().then().spec(respSpec);

			if (resp != null)
			{
				bookingId = resp.extract().path("bookingid");
				ExtentReportsHelper.LogPass("Booking created successfully with ID : " + bookingId);
				XMLHelper.writeNode("CreateBookingId", String.valueOf(bookingId));
				testResult = true;
			} 
			else
			{
				ExtentReportsHelper.LogFail("Response is null. Failed to create booking.");
			}
			return testResult;
		}
		catch (Exception e)
		{
			ExtentReportsHelper.LogFail("Exception in method : createBooking : "+e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Updates a booking with hardcoded values.
	 * 
	 * @param id The booking ID to update.
	 */
	public boolean updateBooking()
	{
		boolean testResult = false;
		try
		{
			String bookingId = XMLHelper.readData("CreateBookingId");
			String basePath = "/booking/" + bookingId;
//			String basePath2 = ConfigHelper.readProperty("createBookingBasePath");
			HashMap<String, String> dates = new HashMap<String, String>();
			dates.put("checkin", "2018-01-01");
			dates.put("checkout", "2019-01-01");

			RequestPayloadVO requestPayloadVO = new RequestPayloadVO();
			requestPayloadVO.setFirstName("Jim");
			requestPayloadVO.setLastName("Brown");
			requestPayloadVO.setTotalprice(111);
			requestPayloadVO.setDepositpaid(true);
			requestPayloadVO.setBookingdates(dates);

			String reqBody = AllObjects.requestPayloadHelper().getRequestBody(requestPayloadVO);

			RequestSpecification reqSpec = getRequestSpecificationWithBody(basePath, reqBody, AuthType.COOKIE);
			ResponseSpecification respSpec = getResponseSpecification(200);

			ValidatableResponse response = given().spec(reqSpec).when().put().then().spec(respSpec);
			
			if(response != null)
			{
				ExtentReportsHelper.LogPass("Booking with ID " + bookingId + " updated successfully.");
				testResult = true;
			}
			else 
			{
				ExtentReportsHelper.LogFail("Failed to update Booking with ID " + bookingId + ".");
			}

			return testResult;
		}
		catch (Exception e)
		{
			ExtentReportsHelper.LogFail("Exception in method : updateBooking : "+e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Method to updates a current booking with a partial payload
	 * 
	 * @param id
	 */
	public boolean partialUpdateBooking()
	{
		boolean testResult = false;
		try
		{
			String bookingId = XMLHelper.readData("CreateBookingId");
			String basePath = "/booking/" + bookingId;
			String newFname = "PQR";
			String newLname = "TUV";
			String reqBody = "{\r\n" + "    \"firstname\" : \"" + newFname + "\",\r\n" + "    \"lastname\" : \"" + newLname
					+ "\"\r\n" + "}";
			RequestSpecification reqSpec = getRequestSpecificationWithBody(basePath, reqBody, AuthType.COOKIE);
			ResponseSpecification respSpec = getResponseSpecification(200);

			ValidatableResponse response = given().spec(reqSpec).when().patch().then().spec(respSpec);
			
			if(response != null)
			{
				ExtentReportsHelper.LogPass("Booking updated with name : " + newFname);
				testResult = true;
			}
			else
			{
				ExtentReportsHelper.LogFail("Failed to update booking.");
			}
			return testResult;
		}
		catch (Exception e)
		{
			ExtentReportsHelper.LogFail("Exception in method : partialUpdateBooking : "+e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Deletes a booking by ID.
	 * 
	 * @param id The booking ID to delete.
	 */
	public boolean deleteBooking()
	{
		boolean testResult = false;
		try
		{
			String bookingId = XMLHelper.readData("CreateBookingId");
			String basePath = "/booking/" + bookingId;
			RequestSpecification reqSpec = getRequestSpecification(basePath, AuthType.COOKIE);
			ResponseSpecification respSpec = getResponseSpecification(201);

			ValidatableResponse response = given().spec(reqSpec).when().delete().then().spec(respSpec);

			if(response != null)
			{
				ExtentReportsHelper.LogPass("Booking with ID " + bookingId + " deleted successfully.");
				testResult = true;
			}
			else
			{
				ExtentReportsHelper.LogFail("Failed to delete booking with ID " + bookingId + ".");
			}
			
			return testResult;
		}
		catch (Exception e)
		{
			ExtentReportsHelper.LogFail("Exception in method deleteBooking : "+e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
}
