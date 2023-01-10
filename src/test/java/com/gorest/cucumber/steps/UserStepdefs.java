package com.gorest.cucumber.steps;

import com.gorest.info.UserSteps;
import com.gorest.util.TestUtils;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.ValidatableResponse;
import net.thucydides.core.annotations.Steps;

import org.junit.Assert;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

public class UserStepdefs {

    static ValidatableResponse response;

    static int userId;
    static String name = null;
    static String email;



    @Steps
    UserSteps userSteps;

    @When("^User sends a GET request to list endpoint$")
    public void userSendsAGETRequestToListEndpoint() {
        response = userSteps.getAllUsersInfo();


    }

    @Then("^User must get back a valid status code 200$")
    public void userMustGetBackAValidStatusCode() {
        response.statusCode(200);
    }

    @When("^I create a new user by providing the information name\"([^\"]*)\" email \"([^\"]*)\" gender \"([^\"]*)\" status \"([^\"]*)\"$")
    public void iCreateANewUserByProvidingTheInformationNameEmailGenderStatus(String name, String email, String gender, String status) {
       // name = TestUtils.getRandomValue()+ name;
        response = userSteps.createUser(name,email,gender,status);

    }

    @Then("^I verify that the user with \"([^\"]*)\" is created$")
    public void iVerifyThatTheUserWithIsCreated(String field)  {
        response.statusCode(201);
        HashMap<String, Object> userMap = userSteps.getUserInfoByName(name);
        userId = (int) userMap.get("id");
        Assert.assertThat(userMap, hasValue(name));
    }

    @Given("^User application is running$")
    public void userApplicationIsRunning() {
    }

    @And("^I update the user with information name\"([^\"]*)\" email \"([^\"]*)\" gender \"([^\"]*)\" status \"([^\"]*)\"$")
    public void iUpdateTheUserWithInformationNameEmailGenderStatus(String _name, String email, String gender, String status)  {
        name = name + "_updated";
        response = userSteps.updateUser(userId, name, email,gender,status);


    }

    @And("^The user with name \"([^\"]*)\" is updated successfully$")
    public void theUserWithNameIsUpdatedSuccessfully(String _name)  {
        HashMap<String, Object> userMap = userSteps.getUserInfoByName(name);
        Assert.assertThat(userMap,hasValue(name));

    }

    @And("^I delete the user that created with name \"([^\"]*)\"$")
    public void iDeleteTheUserThatCreatedWithName(String name)  {
        response = userSteps.deleteUser(userId);
    }

    @Then("^The user deleted successfully from the information$")
    public void theUserDeletedSuccessfullyFromTheInformation() {
        response.statusCode(204);
        userSteps.getUserById(userId).statusCode(404);
    }
}
