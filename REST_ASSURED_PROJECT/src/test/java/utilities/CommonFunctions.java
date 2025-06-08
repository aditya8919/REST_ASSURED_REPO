package utilities;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CommonFunctions {
	
	/**
	 * Method to convert String responseBody i.e. to convert full response into JSON Object
	 * @param responseBody
	 * @return
	 */
	public static JsonObject converResponseStringToJsonObject(String responseBody)
	{
		JsonObject jsonObj = null; 
		if(!(responseBody.isEmpty()) && !(responseBody.isBlank()))
		{
			jsonObj = JsonParser.parseString(responseBody).getAsJsonObject();
		}
		else
		{
			System.out.println("responseBody is null or blank or empty");
		}
		return jsonObj;
	}
	
	/**
	 * Method to convert String responseBody i.e. to convert full response into JSON Array.
	 * @param responseBody
	 * What is JSON Array?? --> Its a list if JSON Objects. 
	 * @return
	 */
	public static JsonArray converResponseStringToJsonArray(String responseBody)
	{
		JsonArray jsonArray = null; 
		if(!(responseBody.isEmpty()) && !(responseBody.isBlank()))
		{
			jsonArray = JsonParser.parseString(responseBody).getAsJsonArray();
		}
		else
		{
			System.out.println("responseBody is null or blank or empty");
		}
		
		return jsonArray;
	}
	
	/**
	 * Method to get the JSON array which is present at a specific key inside JSON Object.
	 * @param jsonObj
	 * @param key
	 * @return JsonArray if element at given key is JsonArray, else returns null.
	 */
	public static JsonArray getJsonArrayFromJsonObjectKey(JsonObject jsonObj, String key)
	{
		JsonArray keyJsonArray = null;
		 
		 if(jsonObj != null && !(key.isEmpty()) && !(key.isBlank()))
		 {
			 if(jsonObj.get(key).isJsonArray())
			 {
				 keyJsonArray = jsonObj.get(key).getAsJsonArray();
			 }
			 else
			 {
				 System.out.println("Key '"+key+"' element is not JSON Array");
			 }
		 }
		 else
		 {
			 System.out.println("Json Object is null or key is blank/empty");
		 }
		 
		 return keyJsonArray;
	}
	

}
