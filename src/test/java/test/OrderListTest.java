package test;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import testData.Assertions;
import testData.Order;
import testData.OrderClient;
import java.util.List;

import static org.hamcrest.Matchers.notNullValue;

public class OrderListTest {

    public OrderClient orderClient;
    public Assertions assertions;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        assertions = new Assertions();
    }

    @Test
    @DisplayName("Check is order list returned")
    public void testOrderListReturnsListOfOrders() {
        Response response = orderClient.getOrderList();
        assertions.assertCodeAndBodyKeyValue(response,200, "orders", notNullValue());
    }

    @Test
    @DisplayName("Check is order returned if get by number")
    public void testOrderGetByNumberReturnsOrder() {
        Order order = Order.generateOrderData(List.of("BLACK"));
        Response newOrderResponse = orderClient.createNewOrder(order);
        int orderNumber = orderClient.getOrderNumber(newOrderResponse);
        Response response = orderClient.getOrderByNumber(orderNumber);
        assertions.assertCodeAndBodyKeyValue(response,200, "order", notNullValue());
    }

    @Test
    @DisplayName("Check is error returned if trying to get order without number")
    public void testOrderGetNoNumberReturnsError() {
        Response response = orderClient.getOrderNoNumber();
        assertions.assertCodeAndBodyKeyValue(response,400, "message", "Недостаточно данных для поиска");
    }

    @Test
    @DisplayName("Check is error returned if trying to get order with wrong number")
    public void testOrderGetWrongNumberReturnsError() {
        Response response = orderClient.getOrderByNumber(12345);
        assertions.assertCodeAndBodyKeyValue(response,404, "message", "Заказ не найден");
     }

}
