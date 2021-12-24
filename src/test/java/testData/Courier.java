package testData;

import com.github.javafaker.Faker;

public class Courier {

    public final String login;
    public final String password;
    public final String firstName;

    public Courier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    // Забавный момент. Сначала сделал генерацию логина с именем покемона - тесты стали рандомно падать с ошибкой при создании курьера "Этот логин уже используется"
    // Поменял на собачьи клички - падения прекартились. Количество имен покмонов сильно ограничено, видимо, и их использовал кто-то ещё.
    // Буду рад совету - как деалют обычно? Может быть просто буквенно-цифровую последовательность использовать?
    public static Courier generateRandomCourier() {
        Faker faker = new Faker();
        final String login = faker.dog().name();
        final String password = faker.internet().password();
        final String firstName = faker.name().firstName();
        return new Courier(login, password, firstName);
    }

    public static Courier generateRandomCourierWithPasswordIsNotFilled() {
        Faker faker = new Faker();
        final String login = faker.dog().name();
        final String password = null;
        final String firstName = faker.name().firstName();
        return new Courier(login, password ,firstName);
    }

    public static Courier generateRandomCourierWithLoginIsNotFilled() {
        Faker faker = new Faker();
        final String login = null;
        final String password = faker.internet().password();
        final String firstName = faker.name().firstName();
        return new Courier(login, password, firstName);
    }

    @Override
    public String toString() {
        return "Courier{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                '}';
    }
}