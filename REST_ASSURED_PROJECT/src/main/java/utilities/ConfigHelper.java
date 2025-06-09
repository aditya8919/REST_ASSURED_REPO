package utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigHelper
{
	private static BufferedWriter w = null;

	public static String readProperty(String propertyName) throws IOException
	{
		try
		{
			FileInputStream file = new FileInputStream(ProjectPaths.configfilePath);
			Properties prop = new Properties();
			prop.load(file);
			String data = prop.getProperty(propertyName);

			return data;
		}
		catch (IOException e)
		{
			ExtentReportsHelper.LogFail("Exception in method : 'writeProperty'" + e.getMessage());
			e.printStackTrace();
			return null;
		}

	}

	public static void writeProperty(String propertyName, String propertyValue)
	{
//		String path = System.getProperty("user.dir") + "\\Config.properties";
		LinkedHashMap<String, String> properties = new LinkedHashMap<>();

		// Add properties to LinkedHashMap
		properties.put(propertyName, propertyValue);

		try
		{
			// read already existing file (Below code is changing the sequence of keyes in
			// properties file after updating the property)

//	            Properties loadedProperties = new Properties();
//	            FileInputStream fis = new FileInputStream(path);
//	            loadedProperties.load(fis);
//	            fis.close();

//	            Read already existing properties file as below to fix above issue
//	    	 
//			To maintain the sequence of keys in the properties file, you can read the existing properties into a 
//	    	 LinkedHashMap instead of Properties. 
//	    	 This way, the order of keys will be preserved. Here's the modified code:

			LinkedHashMap<String, String> loadedProperties = new LinkedHashMap<>();
			BufferedReader reader = new BufferedReader(new FileReader(ProjectPaths.configfilePath));
			String line;
			while ((line = reader.readLine()) != null)
			{
				int separatorIndex = line.indexOf('=');
				if (separatorIndex != -1)
				{
					String key = line.substring(0, separatorIndex).trim();
					String value = line.substring(separatorIndex + 1).trim();
					loadedProperties.put(key, value);
				}
			}
			reader.close();

			// Update existing properties with new values, skip if key not present
			for (String key : properties.keySet())
			{
				if (loadedProperties.containsKey(key))
				{
					loadedProperties.put(key, properties.get(key));
				}
			}

			// Write updated properties back to file without escaping characters
			BufferedWriter writer = new BufferedWriter(new FileWriter(ProjectPaths.configfilePath));

			for (Map.Entry<String, String> entry : loadedProperties.entrySet())
			{
				writer.write(entry.getKey() + "=" + entry.getValue() + "\n");
			}
			writer.close();

		}
		catch (Exception e)
		{
			ExtentReportsHelper.LogFail("Exception in method : 'writeProperty'" + e.getMessage());
			
		}

	}
}
