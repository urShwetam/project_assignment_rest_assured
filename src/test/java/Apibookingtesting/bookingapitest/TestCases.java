package Apibookingtesting.bookingapitest;

import static io.restassured.RestAssured.given;

import java.util.HashMap;

import org.hamcrest.core.IsEqual;
import org.json.simple.JSONObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;


public class TestCases {

	ResponseSpecification res;
	ResponseSpecification res2;
	@BeforeClass
	public void setSpecification() {
		res = RestAssured.expect();
		res.statusLine("HTTP/1.1 200 OK");
		res2 =RestAssured.expect();
		res2.statusLine("HTTP/1.1 201 Created");
		res.contentType(ContentType.JSON);
		ExtentReportManager.createReport();
	}
	
	@Test(description="Checking health of server",testName="testHealthCheck")
	public void testHealthCheck() {
		ExtentReportManager.test = ExtentReportManager.getTest();
		ExtentReportManager.test.log(LogStatus.INFO, "Specifying the base URI","https://restful-booker.herokuapp.com");
		ExtentReportManager.test.log(LogStatus.INFO, "API call","GET");
		ExtentReportManager.test.log(LogStatus.INFO, "Resource route","/ping");
		ExtentReportManager.test.log(LogStatus.INFO, "Value compared","StatusCode");
		RestAssured.baseURI = "https://restful-booker.herokuapp.com";
		given().when().get("/ping").then().spec(res2);
		
	}
	
	@Test(description="Generating auth token",testName="testCreateToken")
	public void testCreateToken() {
		ExtentReportManager.test = ExtentReportManager.getTest();
		ExtentReportManager.test.log(LogStatus.INFO, "Specifying the base URI","https://restful-booker.herokuapp.com");
		ExtentReportManager.test.log(LogStatus.INFO, "API call","GET");
		ExtentReportManager.test.log(LogStatus.INFO, "Resource route","/auth");
		ExtentReportManager.test.log(LogStatus.INFO, "Value compared","StatusCode");
		RestAssured.baseURI = "https://restful-booker.herokuapp.com";
		HashMap<String,String> params = new HashMap<>();
		params.put("username", "admin");
		params.put("password", "password123");
		given().when().contentType("application/json").body(params).post("/auth").then().spec(res);
		
	}
	
	@Test(description="Getting the list of booking ids",testName="testgetbookingid")
	public void testgetbookingid() {
		ExtentReportManager.test = ExtentReportManager.getTest();
		ExtentReportManager.test.log(LogStatus.INFO, "Specifying the base URI","https://restful-booker.herokuapp.com");
		ExtentReportManager.test.log(LogStatus.INFO, "API call","GET");
		ExtentReportManager.test.log(LogStatus.INFO, "Resource route","/booking");
		ExtentReportManager.test.log(LogStatus.INFO, "Value compared","bookingid");
		RestAssured.baseURI = "https://restful-booker.herokuapp.com";
		given().when().get("/booking").then().assertThat().body("[0].bookingid", IsEqual.equalTo(129));
		
	}
	
	@Test(description="Getting specific user booking details",testName="testgetbooking")
	public void testgetbooking() {
		ExtentReportManager.test = ExtentReportManager.getTest();
		ExtentReportManager.test.log(LogStatus.INFO, "Specifying the base URI","https://restful-booker.herokuapp.com");
		ExtentReportManager.test.log(LogStatus.INFO, "API call","GET");
		ExtentReportManager.test.log(LogStatus.INFO, "Id passed","232");
		ExtentReportManager.test.log(LogStatus.INFO, "Resource route","/booking/:id");
		ExtentReportManager.test.log(LogStatus.INFO, "Value compared","firstname");
		RestAssured.baseURI = "https://restful-booker.herokuapp.com";
		given().when().contentType("application/json").get("/booking/25").then().assertThat().body("firstname", IsEqual.equalTo("Sally"));
	}
	
	@SuppressWarnings("unchecked")
	@Test(description="Registering the user",testName="testRegisterUser")
	public void testCreatebooking() {
		ExtentReportManager.test = ExtentReportManager.getTest();
		ExtentReportManager.test.log(LogStatus.INFO, "Specifying the base URI","https://restful-booker.herokuapp.com");
		ExtentReportManager.test.log(LogStatus.INFO, "API call","POST");
		ExtentReportManager.test.log(LogStatus.INFO, "Body passed"," ");
		ExtentReportManager.test.log(LogStatus.INFO, "Resource route","/booking");
		ExtentReportManager.test.log(LogStatus.INFO, "Value compared","booking.additionalneeds");
		RestAssured.baseURI = "https://restful-booker.herokuapp.com";
		JSONObject file = new JSONObject();
		JSONObject bookingdates = new JSONObject();
		bookingdates.put("checkin", "2018-01-01");
		bookingdates.put("checkout", "2019-01-01");
	    file.put("firstname", "Jim");
	    file.put("lastname", "Brown");
	    file.put("totalprice", 111);
	    file.put("depositpaid", true);
	    file.put("bookingdates",bookingdates);
	    file.put("additionalneeds", "Breakfast");
		given().when().contentType("application/json").body(file).post("/booking").then().assertThat().body("booking.additionalneeds", IsEqual.equalTo("Breakfast"));
	}

	
	@AfterClass
	public void closeReport() {
		ExtentReportManager.reports.flush();
	}
}
