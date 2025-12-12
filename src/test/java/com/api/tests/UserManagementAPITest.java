package com.api.tests;

import com.api.authetication.AuthService;
import com.api.authetication.UserprofileManagementService;
import com.api.models.request.LoginRequest;
import com.api.models.response.LoginResponse;
import com.api.models.response.UserUpdateProfileRequest;
import com.api.models.response.UserProfileResponse;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;

public class UserManagementAPITest {

    AuthService authService = new AuthService();
    UserprofileManagementService userprofileManagementService= new UserprofileManagementService();
    LoginRequest loginRequest;
    UserUpdateProfileRequest userUpdateProfileRequest;
    LoginResponse loginResponse;
    String token;

    @BeforeTest
    public void getAuthentication()
    {
        System.out.println("Creating POJO object for the Login Json request");
        loginRequest = new LoginRequest("gowtham","Gowtham@7803");
        userUpdateProfileRequest = new UserUpdateProfileRequest("gowtham","Daivik","gowtham.kandanuru@gmail.com","9490636648");
        Response response = authService.login(loginRequest);
        loginResponse = response.as(LoginResponse.class);
      //  System.out.println(response.asPrettyString());
        System.out.println("Deserialized of Login Json response to Java class");
        token = loginResponse.getToken();
        System.out.println("Captured Live authentication token from the login response");
    }
    @Test(priority = 1,description = "First updating profile using Update profile API in the below Test case and then this method gets the updated profile")
    public void getProfileInfoTest()
    {
        System.out.println("passing Live authentication token in the Get request call");
        Response response = userprofileManagementService.getProfile(token);
        System.out.println("Deserializing of Login Json response to Java class");
        UserProfileResponse userProfileResponse = response.as(UserProfileResponse.class);
     //   System.out.println(response.asPrettyString());
        Assert.assertEquals(userProfileResponse.getId(), loginResponse.getId());
        Assert.assertEquals(userProfileResponse.getLastName(), userUpdateProfileRequest.getLastName());
    }
    @Test(priority = 0,description = "This will test the Update Profile API")
    public void updateProfile()
    {
        System.out.println("passing Live authentication token in the put request call");
        System.out.println("Updating Profile account");
        Response response = userprofileManagementService.updateProfile(userUpdateProfileRequest,token);
     //   System.out.println("Updated Profile response "+response.asPrettyString());
    }
}
