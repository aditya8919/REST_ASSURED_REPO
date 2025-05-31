package utilities;

import java.util.concurrent.TimeUnit;

import io.restassured.response.Response;


/**
 * Verification of :
 *  Status Code
 *  Status Line
 *  Response Time
 *  Response Headers
 */
public class ResponseHelper {
	
	
	public static boolean verifyStatusCode(Response resp, int expectedStatusCode)
	{
		boolean flag = false;
		
		if(resp != null)
		{
			int actualStatusCode = resp.getStatusCode();
			
			if(actualStatusCode == expectedStatusCode)
			{
				System.out.println("Status code '"+actualStatusCode+"' matched with expected status code.");
				flag= true;
			}
			else
			{
				System.out.println("Status code '"+actualStatusCode+"' did not match with expected status code.");
			}
		}
		else
		{
			System.out.println("Response is null.");
		}

		
		return flag;
	}
	
	public static boolean verifyStatusLine(Response resp, String expectedStatusLine)
	{
		String statusLine = null;
		boolean flag = false;
		
		if(resp != null)
		{
			statusLine = resp.getStatusLine();
			if(!statusLine.isEmpty() && statusLine != null)
			{
				if(statusLine.contains(expectedStatusLine))
				{
					System.out.println("Status Line '"+statusLine+"' matched with expected line.");
					flag = true;
				}
				else
				{
					System.out.println("Status Line '"+statusLine+"' did not matched with expected line.");
				}
			}
			else
			{
				System.out.println("Status Line is empty.");
			}
			
		}
		else
		{
			System.out.println("Response is null.");
		}
		
		return flag;
	}
	
	private static long getResponseTime(Response resp)
	{
		return resp.timeIn(TimeUnit.MILLISECONDS);
	}
	
	
	public static boolean isResponseTimeLessThan(Response resp, long maxTimeInMiliseconds)
	{
		if(getResponseTime(resp) < maxTimeInMiliseconds)
		{
			System.out.println("Response time is less than '"+maxTimeInMiliseconds+"' miliseconds.");
			return true;
		}
		else
		{
			System.out.println("Response time is more than '"+maxTimeInMiliseconds+"' miliseconds.");
			return false;
		}
	}
	
	
	
	public static boolean verifyHeaderValue(Response resp, String headerName, String headerValue)
	{
		boolean flag = false;
		
		if(resp != null)
		{
			if(resp.headers().size() > 0)
			{
				if(resp.header(headerName).equalsIgnoreCase(headerValue))
				{
					System.out.println("Header Name '"+headerName+"' matched with expected header vlaue '"+headerName+"'.");
					flag = true;
				}
				else
				{
					System.out.println("Header Name '"+headerName+"' did not match with expected header vlaue '"+headerName+"'.");
				}
			}
			else
			{
				System.out.println("No headers in Response.");
			}
		}
		else
		{
			System.out.println("Response is null.");
		}
		
		return flag;
	}
	
	
	
	
	
	
	

}
