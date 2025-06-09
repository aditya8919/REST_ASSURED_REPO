package utilities;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import basePack.BaseClass;

public class ListenersClass implements ITestListener
{
	public static String currentTestName;

	public void onTestStart(ITestResult result)
	{
		try
		{
			String testName = result.getName();
			currentTestName = testName;
			ExtentReportsHelper.StartTest(testName);
			ExtentReportsHelper.LogInfo("Starting Test Case execution : " + testName);
		}
		catch (Exception e)
		{
			ExtentReportsHelper.LogInfo("Exception while login : " + e.getStackTrace());
		}
	}

	public void onTestSuccess(ITestResult result)
	{
//		try
//		{
//			ExtentReportsHelper.EndTest();
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
	}

	public void onTestFailure(ITestResult result)
	{

//		try
//		{
//			ExtentReportsHelper.EndTest();
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
	
	}

	public void onTestSkipped(ITestResult result)
	{
		try
		{
//			if(isLoginSuccessful)
//			{
//				ExtentReportsHelper.EndTest();
//			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result)
	{
	}

	public void onTestFailedWithTimeout(ITestResult result)
	{
	}

	public void onStart(ITestContext context)
	{
		try
		{
//			XMLHelper.writeNode("dateTimeStamp", BaseClass.dateTimeStamp);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void onFinish(ITestContext context)
	{

	}


}
