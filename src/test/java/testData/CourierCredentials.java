package testData;

public class CourierCredentials {

    public final String login;
    public final String password;

    public CourierCredentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public static CourierCredentials getCourierLoginData(Courier courier) {
        return new CourierCredentials(courier.login, courier.password);
    }

    public static CourierCredentials getCourierLoginDataNoLogin(Courier courier) {
        return new CourierCredentials(null, courier.password);
    }

    public static CourierCredentials getCourierLoginDataNoPassword(Courier courier) {
        return new CourierCredentials(courier.login, null);
    }

    public static CourierCredentials getCourierLoginDataCustomFields(String login, String password) {
        return new CourierCredentials(login, password);
    }

    @Override
    public String toString() {
        return "CourierCredentials{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
