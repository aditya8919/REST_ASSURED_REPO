package basePack;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import org.hamcrest.Matchers;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import utilities.ConfigHelper;
import utilities.ExtentReportsHelper;

/**
 * This class contains all functions and variables which will be used throughout
 * the project and all packages
 */
public class BaseClass
{

	protected String baseUrl;
	protected  String authTokenBasePath;
	protected static String contentType = "Content-Type";
	protected static String appJson = "application/json";
	protected static RequestSpecification reqSpec = null;
	protected static RequestSpecification respSpec = null;
	public static ExtentReportsHelper extentReportsHelper;
	public static LocalDateTime time;
	public static DateTimeFormatter formatter;
	public static String dateTimeStamp;

	protected BaseClass() throws IOException
	{
		baseUrl = ConfigHelper.readProperty("baseUrl");
		authTokenBasePath = ConfigHelper.readProperty("authTokenBasePath");
	}

	protected enum AuthType
	{
		BEARER_TOKEN, 
		COOKIE, 
		NO_AUTH
	};
	
	

	/**
	 * @return
	 */
	public String getAuthToken()
	{
		try
		{
			String userId = ConfigHelper.readProperty("UserId");
			String password = ConfigHelper.readProperty("Password");
			reqSpec = getRequestSpecification(authTokenBasePath, AuthType.NO_AUTH);
			ValidatableResponse response = given().spec(reqSpec)
					.body("{\"username\":\"" + userId + "\",\"password\":\"" + password + "\"}").when().post().then()
					.statusCode(200);

			return response.extract().path("token");
		}
		catch (Exception e)
		{
			System.out.println("Exception occurred while getting auth token: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Method to get getRequestSpecifications, since basepath can change depending
	 * on which endpoint we are calling, we can take it as an Argument.
	 * 
	 * @param basePath
	 * @return RequestSpecification object if its built properly, else returns null.
	 */
	public RequestSpecification getRequestSpecification(String basePath, AuthType authType)
	{
		RequestSpecification reqSpec = null;
		RequestSpecBuilder reqBuilder = null;

		reqBuilder = new RequestSpecBuilder().setBaseUri(baseUrl).setBasePath(basePath).addHeader(contentType, appJson)
				.addHeader("Accept", appJson);

		switch (authType)
		{
		case BEARER_TOKEN:

			reqBuilder.addHeader("Authorization", "Bearer " + getAuthToken());
			break;

		case COOKIE:

			reqBuilder.addHeader("Cookie", "token=" + getAuthToken());
			break;

		case NO_AUTH:

			break;

		default:
			break;
		}

		reqSpec = reqBuilder.build();

		return reqSpec;
	}

	public RequestSpecification getRequestSpecificationWithBody(String basePath, String body, AuthType authType)
	{
		RequestSpecification reqSpec = null;
		RequestSpecBuilder reqBuilder = null;

		reqBuilder = new RequestSpecBuilder().setBaseUri(baseUrl).setBasePath(basePath).addHeader(contentType, appJson)
				.addHeader("Accept", appJson).setBody(body);

		switch (authType)
		{
		case BEARER_TOKEN:

			reqBuilder.addHeader("Authorization", "Bearer " + getAuthToken());
			break;

		case COOKIE:

			reqBuilder.addHeader("Cookie", "token=" + getAuthToken());
			break;

		default:
			break;
		}

		reqSpec = reqBuilder.build();

		return reqSpec;
	}

	public static ResponseSpecification getResponseSpecification(int statusCode) throws IOException
	{
		ResponseSpecification respSpec = null;

		respSpec = new ResponseSpecBuilder().expectStatusCode(statusCode)
				.expectHeader(ConfigHelper.readProperty("respHeaderServer"), ConfigHelper.readProperty("respHeaderServerValue"))
				.expectHeader(ConfigHelper.readProperty("respHeaderVia"), ConfigHelper.readProperty("respHeaderViaValue"))
				.expectHeader(ConfigHelper.readProperty("respHeaderX-Powered-By"),
						ConfigHelper.readProperty("respHeaderX-Powered-ByValue"))
				.expectResponseTime(Matchers.lessThan(5000L), TimeUnit.MILLISECONDS)
				.expectBody(Matchers.not(Matchers.blankOrNullString())).build();

		return respSpec;

	}
	
	@BeforeSuite
	public void inItTest() throws IOException
	{
		time = LocalDateTime.now();
		formatter = DateTimeFormatter.ofPattern("ddMMyyyy_HH_mm_ss");
		dateTimeStamp = "_" + time.format(formatter);
		extentReportsHelper = new ExtentReportsHelper();
	}
	
	@AfterMethod
	public void tearDown() 
	{
		try
		{
			ExtentReportsHelper.EndTest();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
