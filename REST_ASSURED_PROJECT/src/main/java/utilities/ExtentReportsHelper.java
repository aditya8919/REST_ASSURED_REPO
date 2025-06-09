package utilities;
import java.io.File;
import java.io.IOException;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import basePack.BaseClass;

public class ExtentReportsHelper extends BaseClass
{
	private static ExtentReports extent;
	public static ExtentTest extentTest;
	static String reportFolderPath;
	static String reportPath;

	public ExtentReportsHelper() throws IOException
	{
		reportPath = ProjectPaths.projectDirectory + "\\TestResults";
		reportFolderPath = reportPath + "\\TestResults" + dateTimeStamp;
		File reportPath = new File(reportFolderPath + "\\TestResult" + dateTimeStamp + ".html");
		ExtentSparkReporter htmlReporter = new ExtentSparkReporter(reportPath);
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
	}

	public static void StartTest(String testName)
	{
		extentTest = extent.createTest(testName);
	}

	public static void LogInfo(String message)
	{
		extentTest.log(Status.INFO, message);
	}

	public static void LogWarning(String message)
	{
		extentTest.log(Status.WARNING, message);
	}

	public static void LogPass(String message)
	{
		extentTest.log(Status.PASS, message);
	}

	public static void LogFail(String message)
	{
		extentTest.log(Status.FAIL, message);
	}

	public static void EndTest()
	{
		extent.flush();
	}
}
