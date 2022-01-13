package test;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import testData.*;

import java.util.List;

import static org.hamcrest.Matchers.*;

@RunWith(Parameterized.class)

public class OrderCreateParameterisedTest {

    public OrderClient orderClient;
    public Assertions assertions;

    private final List<String> color;
    private final Matcher<Object> expected;

    public OrderCreateParameterisedTest(List<String> color, Matcher<Object> expected) {
        this.color = color;
        this.expected = expected;
    }

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        assertions = new Assertions();
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][] {
                {List.of("BLACK"),notNullValue()},
                {List.of("BLACK", "GREY"),notNullValue()},
                {List.of("GREY"),notNullValue()},
                {null,notNullValue()}
        };
    }

    @Test
    @DisplayName("Check is a new order created with different or no colors")
    public void testCreateOrderParameterisedColors() {
        Order order = Order.generateOrderData(color);
        Response response = orderClient.createNewOrder(order);
        assertions.assertCodeAndBodyKeyValue(response,201,"track", expected);
    }

}
