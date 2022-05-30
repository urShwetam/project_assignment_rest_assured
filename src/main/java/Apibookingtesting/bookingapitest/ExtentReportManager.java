package Apibookingtesting.bookingapitest;

import java.util.HashMap;
import java.util.Map;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class ExtentReportManager {
	public static ExtentTest test;
	public static Map<Integer,ExtentTest> extentTestMap = new HashMap<Integer,ExtentTest>();
	public static ExtentReports reports;
	
	public static void createReport() {
		reports = new ExtentReports(System.getProperty("user.dir")+"/test-output/extentreport.html");
	}
	
	public static synchronized ExtentTest startTest(String testcasename, String testcasedescription) {
		ExtentReportManager.test = ExtentReportManager.reports.startTest(testcasename,testcasedescription);
		extentTestMap.put((int)Thread.currentThread().getId(), ExtentReportManager.test);
		return ExtentReportManager.test;
	}
	
	public static synchronized void endTest() {
		ExtentReportManager.reports.flush();
	}

	public static synchronized ExtentTest getTest() {
		return (ExtentTest) extentTestMap.get((int)Thread.currentThread().getId());
	}
}
