package com.starlight.app.StepDefinition;

import io.cucumber.java.en.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class steps {

    @Autowired
    private TestRestTemplate restTemplate;

    private ResponseEntity<String> response;

    @Given("the data file {string} exists")
    public void the_data_file_exists(String fileName) {
        File file = new File("src/main/resources/" + fileName);
        assertTrue(file.exists(), "File not found: " + fileName);
    }

    @When("I make a GET request to {string}")
    public void i_make_a_get_request_to(String endpoint) {
        response = restTemplate.getForEntity(endpoint, String.class);
        assertNotNull(response, "Response should not be null");
    }

    @Then("the response should contain {string}")
    public void the_response_should_contain(String expected) {
        assertEquals(200, response.getStatusCodeValue(), "Expected HTTP 200 OK");
        assertTrue(response.getBody().contains(expected),
                "Response should contain: " + expected + "\nBody: " + response.getBody());
    }

    @Then("the error message {string} should appear")
    public void the_error_message_should_appear(String expectedError) {
        assertTrue(response.getBody().contains(expectedError),
                "Expected error message not found: " + expectedError);
        assertEquals(400, response.getStatusCodeValue(), "Expected HTTP 400 Bad Request");
    }
}
