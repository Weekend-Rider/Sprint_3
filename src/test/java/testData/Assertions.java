package testData;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertTrue;

public class Assertions {

    @Step("Assert the response code, key and String value")
    public void assertCodeAndBodyKeyValue(Response response, int statusCode, String key, String value) {
        response.then()
                .assertThat()
                .statusCode(statusCode)
                .and()
                .body(key, equalTo(value));
    }

    @Step("Assert the response code, key and Matcher value")
    public void assertCodeAndBodyKeyValue(Response response, int statusCode, String key, Matcher<Object> value) {
        response.then()
                .assertThat()
                .statusCode(statusCode)
                .and()
                .body(key, value);
    }

    @Step("Assert the response code and return Ok key value")
    public boolean assertCodeAndReturnOkValue(Response response, int statusCode) {
        return response.then()
                .assertThat()
                .statusCode(statusCode)
                .extract()
                .path("ok");
    }

    @Step("Assert is Ok value equals true")
    public void assertOkTrue(boolean action, String message) {
        assertTrue(message, action);
    }

}