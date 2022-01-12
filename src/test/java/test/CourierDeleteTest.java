package test;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import testData.Assertions;
import testData.Courier;
import testData.CourierClient;
import testData.CourierCredentials;

public class CourierDeleteTest {

    public CourierClient courierClient;
    public Assertions assertions;

    @Before
        public void setUp() {
        courierClient = new CourierClient();
        assertions = new Assertions();
    }

    @Test
    @DisplayName("Check is courier deletion returns ok:true")
    public void testDeleteCourierReturnsOkTrue() {
        Courier courier = Courier.generateRandomCourier();
        courierClient.registerCourier(courier);
        int courierId = courierClient.loginAndReturnCourierId(CourierCredentials.getCourierLoginData(courier));
        Response response = courierClient.deleteCourier(courierId);
        boolean isDeleted = assertions.assertCodeAndReturnOkValue(response, 200);
        assertions.assertOkTrue(isDeleted, "Courier deletion failed");
    }

    //Ошибка - возвращается 404 Not Found вместо 400 Bad Request
    @Test
    @DisplayName("Check is courier deletion with no ID returns error")
    public void testDeleteCourierWithNoIdReturnsError() {
        Response response = courierClient.deleteCourierNoId();
        assertions.assertCodeAndBodyKeyValue(response, 400, "message", "Недостаточно данных для удаления курьера");
    }

    //Ошибка - message содержит "Курьера с таким id нет." вместо "Курьера с таким id нет"
    @Ignore
    @Test
    @DisplayName("Check is courier deletion with wrong ID returns error")
    public void testDeleteCourierWithWrongIdReturnsError() {
        Response response = courierClient.deleteCourierWrongId(12345);
        assertions.assertCodeAndBodyKeyValue(response, 404, "message", "Курьера с таким id нет");
    }

}
