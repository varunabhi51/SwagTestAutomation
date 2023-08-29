package apiTestCases;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import requestPojo.CreatePlacePojo;
import requestPojo.LocationChild;
import responsePojo.CreatePlaceResponse;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import helper.Base;

public class CreatePlace {

	String placeId;
	UserInformation user = new UserInformation();

	@Test(priority = 0)
	public void createNewPlaceOnGoogl() throws IOException {
		
		
		
		
		CreatePlacePojo p = new CreatePlacePojo();
		LocationChild c = new LocationChild();
		Faker f = new Faker();

		c.setLat(-38.383494);
		c.setLng(33.427362);

		p.setLocation(c);
		p.setAccuracy(50);
		p.setName(f.name().firstName());
		p.setPhone_number(f.phoneNumber().phoneNumber());
		p.setAddress(f.address().city());

		List<String> typeList = new ArrayList<String>();
		typeList.add("shoe park");
		typeList.add("shop");

		p.setType(typeList);
		p.setWebsite("http://google.com");
		p.setLanguage("English");


		// https://rahulshettyacademy.com/maps/api/place/add/json?key=qaclick123

		

		Response res = given().log().all().spec(Base.setup())
				.body(p).when().post("maps/api/place/add/json").then().log().all().assertThat().statusCode(200).extract()
				.response();
		
		
		CreatePlaceResponse d = res.as(CreatePlaceResponse.class);
		
		
         String actualStatus = d.getStatus();
         Assert.assertEquals(actualStatus,"OK");
         
   String actualScope =   d.getScope();
   
   Assert.assertEquals(actualScope,"APP");
   
        String placeId = d.getId();
        System.out.println(placeId);

		
	}



  @Test(priority = 1) public void updateAddress() throws IOException {
  
  
  
  Response updateRes = given().log().all().queryParam("key",
  "qaclick123").queryParam("place_id", placeId) .spec(Base.setup())
  .body(user).when().put("maps/api/place/update/json").then(
  ) .log().all().assertThat().statusCode(200).body("msg",
  equalTo("Address successfully updated")).extract() .response();
  
  String UpdateRes = updateRes.asString();
  
  JsonPath js2 = new JsonPath(UpdateRes);
  
  String successmessage = js2.get("msg");
  
  Assert.assertEquals(successmessage, "Address successfully updated");
  
 }
  
  
  @Test(priority = 2) public void getData() throws IOException {
  
  Response getResponse = given().log().all().spec(Base.setup()).queryParam("key",
  "qaclick123").queryParam("place_id", placeId)
  .when().get("maps/api/place/get/json").then().log().all().assertThat().
  statusCode(200).extract() .response();
  
  String GetRes = getResponse.asString();
  
  JsonPath js1 = new JsonPath(GetRes);
  
  String address = js1.getString("address");
  
  Assert.assertEquals(address, "Noida");
  
  String Language = js1.getString("language");
  
  Assert.assertEquals(Language, "French-IN");
  
  }
  
  }
 