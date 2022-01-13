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

import static org.hamcrest.Matchers.notNullValue;

public class CourierLoginTest {

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
    @DisplayName("Check is the courier can authorise")
    public void testIsCourierAuthorised() {
        Courier courier = Courier.generateRandomCourier();
        courierClient.registerCourier(courier);
        Response response = courierClient.loginCourier(CourierCredentials.getCourierLoginData(courier));
        courierId = courierClient.getCourierId(response);
        assertions.assertCodeAndBodyKeyValue(response, 200, "id", notNullValue());
    }

    @Test
    @DisplayName("Check is the courier cannot authorise without login")
    public void testCourierIsNotAuthorisedWithNoLogin() {
        Courier courier = Courier.generateRandomCourier();
        courierClient.registerCourier(courier);
        Response response = courierClient.loginCourier(CourierCredentials.getCourierLoginDataNoLogin(courier));
        courierId = courierClient.loginAndReturnCourierId(CourierCredentials.getCourierLoginData(courier));
        assertions.assertCodeAndBodyKeyValue(response, 400, "message","Недостаточно данных для входа");
    }

    // Ошибка - ответ 504 вместо 400 по документации
    @Test
    @DisplayName("Check is the courier cannot authorise without password")
    public void testCourierIsNotAuthorisedWithNoPassword() {
        Courier courier = Courier.generateRandomCourier();
        courierClient.registerCourier(courier);
        Response response = courierClient.loginCourier(CourierCredentials.getCourierLoginDataNoPassword(courier));
        courierId = courierClient.loginAndReturnCourierId(CourierCredentials.getCourierLoginData(courier));
        assertions.assertCodeAndBodyKeyValue(response, 400,"message", "Недостаточно данных для входа");
    }

    @Test
    @DisplayName("Check is the courier cannot authorise with wrong login")
    public void testCourierIsNotAuthorisedWithWrongLogin() {
        Courier courier = Courier.generateRandomCourier();
        courierClient.registerCourier(courier);
        Response response = courierClient.loginCourier(CourierCredentials.getCourierLoginDataCustomFields("qwe123", courier.password));
        courierId = courierClient.loginAndReturnCourierId(CourierCredentials.getCourierLoginData(courier));
        assertions.assertCodeAndBodyKeyValue(response, 404, "message","Учетная запись не найдена");
    }

    @Test
    @DisplayName("Check is the courier cannot authorise with wrong password")
    public void testCourierIsNotAuthorisedWithWrongPassword() {
        Courier courier = Courier.generateRandomCourier();
        courierClient.registerCourier(courier);
        Response response = courierClient.loginCourier(CourierCredentials.getCourierLoginDataCustomFields(courier.login,"qwe123"));
        courierId = courierClient.loginAndReturnCourierId(CourierCredentials.getCourierLoginData(courier));
        assertions.assertCodeAndBodyKeyValue(response, 404, "message","Учетная запись не найдена");
    }

}
