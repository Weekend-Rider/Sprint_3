package testData;

import com.github.javafaker.Faker;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
import java.util.List;

public class Order {

    Faker faker = new Faker();

    public final String firstName;
    public final String lastName;
    public final String address;
    public final int metroStation;
    public final String phone;
    public final int rentTime;
    public final String deliveryDate;
    public final String comment;
    public final List<String> color;

    // Возможно, будет логичнее сделать без рандомных данных здесь, встречал разные рекомендации.
    public Order(List<String> color) {
        this.firstName = faker.address().firstName();
        this.lastName = faker.address().lastName();
        this.address = faker.address().streetAddress();
        this.metroStation = faker.number().numberBetween(1,237);
        this.phone = faker.phoneNumber().phoneNumber();
        this.rentTime = faker.number().numberBetween(1,3);
        this.deliveryDate = generateDate();
        this.comment = faker.lorem().sentence(5);
        this.color = color;
    }

    public String generateDate() {
        return new SimpleDateFormat("yyyy-MM-dd")
        .format(faker.date().future(3, TimeUnit.DAYS));
    }

    public static Order generateOrderData(List<String> color) {
        return new Order(color);
    }

    @Override
    public String toString() {
        return "Order{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", metroStation=" + metroStation +
                ", phone='" + phone + '\'' +
                ", rentTime=" + rentTime +
                ", deliveryDate='" + deliveryDate + '\'' +
                ", comment='" + comment + '\'' +
                ", color=" + color +
                '}';
    }

}



