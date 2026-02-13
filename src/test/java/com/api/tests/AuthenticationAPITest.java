package com.api.tests;


import com.api.authetication.AuthService;
import com.api.models.request.LoginRequest;
import com.api.models.response.LoginResponse;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


@Listeners({com.api.listeners.TestListener.class})
public class AuthenticationAPITest {

    AuthService authService = new AuthService();
    Logger logger = LogManager.getLogger(AuthenticationAPITest.class);
    @Test
    public void loginTest()
    {
        //baseURI="http://64.227.160.186:8080";
        // what is the purpose of Request specification ??
       /* RequestSpecification requestSpecification = given().baseUri("http://64.227.160.186:8080");
        Response response = requestSpecification.header("Content-Type", "application/json").body("{\n" +
                "  \"username\": \"gowtham\",\n" +
                "  \"password\": \"Gowtham@7803\"\n" +
                "}").post("/api/auth/login");
        System.out.println(response.asPrettyString());
        Assert.assertEquals(response.getStatusCode(), 200);*/
        LoginRequest loginRequest = new LoginRequest("gowtham","Gowtham@7803");
        /*Response response =  authService.login(loginRequest);
        LoginResponse loginResponse = response.as(LoginResponse.class);
        Assert.assertEquals(response.getStatusCode(), 200);*/
        RequestSpecification requestSpecification = RestAssured.given().baseUri("www.google.com");
        Response response = requestSpecification.contentType("application/json").body(loginRequest).post("/api/auth/login");
        response.asPrettyString();
        response.getStatusCode();
    }

    @Test
    public void validateForgotPassword()
    {
        Response response = authService.forgotPassword("gowtham.kandanuu@gmail.com");
        System.out.println(response.getStatusCode());
    }
}
