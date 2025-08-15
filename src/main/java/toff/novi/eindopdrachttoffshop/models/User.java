package toff.novi.eindopdrachttoffshop.models;

public class User {

    private static int counter = 1;

    private int id;
    private String name;
    private String email;
    private int phone;
    private String password;

    public User(int id, String name, String email, int phone, String password) {
        this.id = counter++;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        User.counter = counter;
    }


    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone=" + phone +
                ", password='" + password + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }
}
