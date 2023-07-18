package com.RESTAPI.Tests;

import static org.hamcrest.Matchers.lessThan;

import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.RESTAPI.POJOS.CreateRecordPOJO;
import com.RESTAPI.POJOS.ResponseAllRecordsPOJO;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class FetchRecordsAndPrintInConcole {

	int ID = 0;

	@BeforeClass
	public void init() {
		RestAssured.baseURI = "https://dummy.restapiexample.com/api/v1";
	}

	@Test(enabled = false)
	public void GetAllRecord() {
		RequestSpecification req = RestAssured.given();
		Response res = req.when().get("/employees");

		// converting the json into object(POJO)
//	ResponseAllRecordsPOJO ob=new ResponseAllRecordsPOJO();

//	ResponseAllRecordsPOJO[] list=res.as(ResponseAllRecordsPOJO[].class);

		res.then().contentType(ContentType.JSON);
		res.then().statusCode(200);
		res.prettyPrint();

		// Validating status code,success Message
		int statusCode = res.statusCode();
		System.out.println("Status code=" + statusCode);

		String mssg = res.body().jsonPath().getString("status");
		System.out.println("Message=" + mssg);

	}

	@Test(enabled = false)
	public void CreateRecord() {
		System.out.println("Inside createRecord method");
		CreateRecordPOJO data = new CreateRecordPOJO();
		data.setName("test");
		data.setSalary("123");
		data.setAge("23");
		Response res = RestAssured.given().contentType(ContentType.JSON).body(data).when().post("/create");
		res.then().statusCode(200).contentType(ContentType.JSON);
		res.prettyPrint();

		// Verifying status code,response body satus
		int statusCode = res.statusCode();
		System.out.println("Status code=" + statusCode);

		String SuccessMesg = res.body().jsonPath().getString("status");
		System.out.println("Message=" + SuccessMesg);

		ID = res.body().jsonPath().getInt("data.id");
		System.out.println("id=" + ID);

	}

	@Test(dependsOnMethods = "CreateRecord", enabled = false)
	public void deleteRecord() {
		System.out.println("Inside deleteRecord method");
		System.out.println("Extracted Id=" + ID);

		Response res = RestAssured.given().pathParam("id", ID).when().delete("/delete");

//		RequestSpecification req=RestAssured.given();
//		Response res=req.when()
//		.delete("/delete/"+ID);
		res.then().statusCode(200);
		res.prettyPrint();
		String status = res.body().jsonPath().get("status");
		System.out.println("Status=" + status);

		String data = res.body().jsonPath().getString("data");
		System.out.println("data=" + data);

		String message = res.body().jsonPath().getString("message");
		System.out.println("Message=" + message);

	}

	@Test
	public void deleteZeroRecord() {
		System.out.println("Inside delete zero record");
		RequestSpecification req = RestAssured.given();
		Response res = req.when().delete("/delete/0");
		
		res.then().contentType(ContentType.JSON);
		res.then().statusCode(400);
		res.prettyPrint();
		
		// Validating status code,success Message
		int statusCode = res.statusCode();
		System.out.println("Status code=" + statusCode);

		String mssg = res.body().jsonPath().getString("status");
		System.out.println("Message=" + mssg);
	}

	@Test
	public void getSingleUser() {
		System.out.println("Inside getSingleUser method");
		RequestSpecification req = RestAssured.given();
		Response res = req.when().get("/employee/2");

		res.then().contentType(ContentType.JSON);
		res.then().statusCode(200);
		res.prettyPrint();

		// Validating status code,success Message
		int statusCode = res.statusCode();
		System.out.println("Status code=" + statusCode);

		String mssg = res.body().jsonPath().getString("status");
		System.out.println("Message=" + mssg);

	}
}