package utilities;

import java.io.IOException;

import api_calls.API_Calls;
import jsonPayload.RequestPayloadHelper;

public class AllObjects
{
	private static RequestPayloadHelper _requestPayloadHelper;
	public static RequestPayloadHelper requestPayloadHelper()
	{
		if(_requestPayloadHelper == null)
		{
			_requestPayloadHelper = new RequestPayloadHelper();
		}
		
		return _requestPayloadHelper;
	}

	private static API_Calls _API_Calls;
	public static API_Calls api_Calls() throws IOException
	{
		if(_API_Calls == null)
		{
			_API_Calls = new API_Calls();
		}
		
		return _API_Calls;
	}
}
