package api.test;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.util.Assert;
import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.endpoints.UserEndPoints_UsingProperties;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests_UsingProperties {
 
	Faker faker;
	User userPayload;
	
	@BeforeClass
	public void setupData()
	{
		faker = new Faker();
		userPayload = new User();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5,10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());	
	}
	
	@Test(priority = 1)
	public void testPostUser()
	{
		Response response = UserEndPoints_UsingProperties.createUser(userPayload);
		response.then().log().all();
		
		org.testng.Assert.assertEquals(response.getStatusCode(), 200);
		
	}
	
	@Test(priority = 2)
	public void testGetUserByName()
	{
		Response response = UserEndPoints_UsingProperties.readUser(this.userPayload.getUsername());
		response.then().log().all();
		org.testng.Assert.assertEquals(response.getStatusCode(), 200);
		
	}
	
	@Test(priority = 3)
	public void testUpdateUserByName()
	{
		//update data using same payload
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		
		Response response = UserEndPoints_UsingProperties.updateUser(this.userPayload.getUsername(),userPayload);
		//restAssured assertion
		response.then().log().body();
		
		//testNG assertion
		org.testng.Assert.assertEquals(response.getStatusCode(), 200);
		
		//checking data after updation
		Response responseAfterUpdate = UserEndPoints_UsingProperties.readUser(this.userPayload.getUsername());
		org.testng.Assert.assertEquals(responseAfterUpdate.getStatusCode(), 200);
		
	}
	
	@Test(priority = 4)
	public void testDeleteUserByName()
	{
		Response response = UserEndPoints_UsingProperties.deleteUser(this.userPayload.getUsername());
		org.testng.Assert.assertEquals(response.getStatusCode(), 200);
		
	}
}
