package testData;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestAssuredClient {

    private static final String ORDER_PATH = "/api/v1/orders/";

    @Step("Create a new order")
    public Response createNewOrder(Order order) {
        return  given()
                .spec(getBaseSpec())
                .body(order)
                .post(ORDER_PATH);
    }

    @Step("Get order number")
    public int getOrderNumber(Response response) {
        return  response.then()
                .assertThat()
                .statusCode(201)
                .extract()
                .path("track");
    }

    @Step("Get order ID")
    public int getOrderId(int orderNumber) {
        return  given()
                .spec(getBaseSpec())
                .queryParam("t", orderNumber)
                .get(ORDER_PATH + "track")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("order.id");
    }

    @Step("Get orders list")
    public Response getOrderList() {
        return  given()
                .spec(getBaseSpec())
                .get(ORDER_PATH);
    }

    @Step("Get order by number")
    public Response getOrderByNumber(int orderNumber) {
        return  given()
                .spec(getBaseSpec())
                .queryParam("t", orderNumber)
                .get(ORDER_PATH + "track");
    }

    @Step("Try to get an order with no number")
    public Response getOrderNoNumber() {
        return  given()
                .spec(getBaseSpec())
                .queryParam("t")
                .get(ORDER_PATH + "track");
    }

    @Step("Order acceptation")
    public Response acceptOrder(int courierId, int orderId) {
        return  given()
                .spec(getBaseSpec())
                .queryParam("courierId", courierId)
                .put(ORDER_PATH + "accept/{orderId}" ,orderId);
    }

    @Step("Try to accept an order without courier ID input")
    public Response acceptOrderNoCourierId(int orderId) {
        return  given()
                .spec(getBaseSpec())
                .put(ORDER_PATH + "accept/{orderId}?courierId=", orderId);
    }

    @Step("Try to accept an order without order ID input")
    public Response acceptOrderNoOrderId(int courierId) {
        return  given()
                .spec(getBaseSpec())
                .queryParam("courierId", courierId)
                .put(ORDER_PATH + "accept/");
    }

}
