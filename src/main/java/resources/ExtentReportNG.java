package resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportNG {
	
	static ExtentReports extent;
	
	public static ExtentReports getReportObject()
	{
		String path = System.getProperty("user.dir")+"\\reports\\index.html";
		ExtentSparkReporter Reporter = new ExtentSparkReporter(path);
		Reporter.config().setReportName("Web Automation Results");
		Reporter.config().setDocumentTitle("Test Results");
		
		extent = new ExtentReports();
		extent.attachReporter(Reporter);
		extent.setSystemInfo("Tester", System.getProperty("user"));
		return extent;
	}

}
