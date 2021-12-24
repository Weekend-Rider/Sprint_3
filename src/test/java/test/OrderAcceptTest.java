package test;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import testData.*;
import java.util.List;

public class OrderAcceptTest {

    public OrderClient orderClient;
    public CourierClient courierClient;
    public Assertions assertions;
    private int orderNumber;
    private int orderId;
    private int courierId;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        courierClient = new CourierClient();
        assertions = new Assertions();
    }

    @After
    public void tearDown() {
        courierClient.deleteCourier(courierId);
    }

    @Test
    @DisplayName("Check is order accept request returns ok:true")
    public void testOrderAcceptReturnsOkTrue() {
        Order order = Order.generateOrderData(List.of("BLACK"));
        Response newOrderResponse = orderClient.createNewOrder(order);
        Courier courier = Courier.generateRandomCourier();
        courierClient.registerCourier(courier);
        orderNumber = orderClient.getOrderNumber(newOrderResponse);
        orderId = orderClient.getOrderId(orderNumber);
        courierClient.loginCourier(CourierCredentials.getCourierLoginData(courier));
        courierId = courierClient.loginAndReturnCourierId(CourierCredentials.getCourierLoginData(courier));
        Response response = orderClient.acceptOrder(courierId, orderId);
        boolean isAccepted = assertions.assertCodeAndReturnOkValue(response, 200);
        assertions.assertOkTrue(isAccepted, "Order acceptation failed");
    }

    @Test
    @DisplayName("Check is order accept request without a courier ID returns an error")
    public void testOrderAcceptNoCourierIdReturnsError() {
        Order order = Order.generateOrderData(List.of("BLACK"));
        Response newOrderResponse = orderClient.createNewOrder(order);
        orderNumber = orderClient.getOrderNumber(newOrderResponse);
        orderId = orderClient.getOrderId(orderNumber);
        Response response = orderClient.acceptOrderNoCourierId(orderId);
        assertions.assertCodeAndBodyKeyValue(response, 400, "message", "Недостаточно данных для поиска");
    }

    // Ошибка - ответ 404 вместо 400 по документации
    @Test
    @DisplayName("Check is order accept request without an order ID returns an error")
    public void testOrderAcceptNoOrderIdReturnsError() {
        Courier courier = Courier.generateRandomCourier();
        courierClient.registerCourier(courier);
        courierId = courierClient.loginAndReturnCourierId(CourierCredentials.getCourierLoginData(courier));
        Response response = orderClient.acceptOrderNoOrderId(courierId);
        assertions.assertCodeAndBodyKeyValue(response, 400, "message", "Недостаточно данных для поиска");
    }

    @Test
    @DisplayName("Check is order accept request with a wrong courier ID returns an error")
    public void testOrderAcceptWrongCourierIdReturnsError() {
        Order order = Order.generateOrderData(List.of("BLACK"));
        Response newOrderResponse = orderClient.createNewOrder(order);
        orderNumber = orderClient.getOrderNumber(newOrderResponse);
        orderId = orderClient.getOrderId(orderNumber);
        Response response = orderClient.acceptOrder(0,orderId);
        assertions.assertCodeAndBodyKeyValue(response, 404, "message", "Курьера с таким id не существует");
    }

    @Test
    @DisplayName("Check is order accept request with a wrong order ID returns an error")
    public void testOrderAcceptWrongOrderIdReturnsError() {
        Courier courier = Courier.generateRandomCourier();
        courierClient.registerCourier(courier);
        courierId = courierClient.loginAndReturnCourierId(CourierCredentials.getCourierLoginData(courier));
        Response response = orderClient.acceptOrder(courierId,0);
        assertions.assertCodeAndBodyKeyValue(response, 404, "message", "Заказа с таким id не существует");
    }

}
