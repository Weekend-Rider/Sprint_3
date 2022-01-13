package test;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import testData.Assertions;
import testData.Courier;
import testData.CourierClient;
import testData.CourierCredentials;

public class CourierRegisterTest {

    public CourierClient courierClient;
    public Assertions assertions;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        assertions = new Assertions();
    }

    @After
    public void tearDown() {
        courierClient.deleteCourier(courierId);
    }

    @Test
    @DisplayName("Check is a new courier registered")
    public void testCourierIsRegistered() {
        Courier courier = Courier.generateRandomCourier();
        Response response = courierClient.registerCourier(courier);
        courierId = courierClient.loginAndReturnCourierId(CourierCredentials.getCourierLoginData(courier));
        boolean isRegistered = assertions.assertCodeAndReturnOkValue(response, 201);
        assertions.assertOkTrue(isRegistered, "Courier registration failed");
    }


    // Здесь ошибка - фактическое значение message отличается от документации
    @Test
    @DisplayName("Check is two couriers with the same login cannot be registered")
    public void testTwoSameCouriersAreNotRegistered() {
        Courier courier = Courier.generateRandomCourier();
        courierClient.registerCourier(courier);
        Response response = courierClient.registerCourier(courier);
        courierId = courierClient.loginAndReturnCourierId(CourierCredentials.getCourierLoginData(courier));
        assertions.assertCodeAndBodyKeyValue(response, 409, "message", "Этот логин уже используется");
    }

    @Test
    @DisplayName("Check is a courier cannot register without password")
    public void testCourierIsNotRegisteredWithNoPassword() {
        Courier courier = Courier.generateRandomCourierWithPasswordIsNotFilled();
        Response response = courierClient.registerCourier(courier);
        assertions.assertCodeAndBodyKeyValue(response, 400, "message", "Недостаточно данных для создания учетной записи");
    }

    @Test
    @DisplayName("Check is a courier cannot register without login")
    public void testCourierIsNotRegisteredWithNoLogin() {
        Courier courier = Courier.generateRandomCourierWithLoginIsNotFilled();
        Response response = courierClient.registerCourier(courier);
        assertions.assertCodeAndBodyKeyValue(response, 400, "message", "Недостаточно данных для создания учетной записи");
    }

}
