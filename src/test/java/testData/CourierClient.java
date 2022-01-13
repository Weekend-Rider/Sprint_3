package testData;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierClient extends RestAssuredClient {

    private static final String COURIER_PATH = "/api/v1/courier/";

    @Step("Register a new courier")
    public Response registerCourier(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH);
    }

    @Step("Login and get the courier ID")
    public int loginAndReturnCourierId(CourierCredentials courierLoginData) {
        return given()
                .spec(getBaseSpec())
                .body(courierLoginData)
                .when()
                .post(COURIER_PATH + "login")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("id");
    }

    @Step("Login the courier")
    public Response loginCourier(CourierCredentials courierLoginData) {
        return given()
                .spec(getBaseSpec())
                .body(courierLoginData)
                .when()
                .post(COURIER_PATH + "login");
    }

    @Step("Delete the courier")
    public Response deleteCourier(int courierId) {
        return given()
                .spec(getBaseSpec())
                .when()
                .delete(COURIER_PATH + courierId);
    }

    @Step("Try to delete a courier without ID")
    public Response deleteCourierNoId() {
        return given()
                .spec(getBaseSpec())
                .when()
                .delete(COURIER_PATH);
    }

    @Step("Try to delete a courier with wrong ID")
    public Response deleteCourierWrongId(int wrongIntId) {
        return given()
                .spec(getBaseSpec())
                .when()
                .delete(COURIER_PATH + wrongIntId);
    }

    @Step("Get the courier ID")
    public int getCourierId(Response response) {
        return response.then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("id");
    }

}
