package model;

public class Visitor {
    private int id;
    private String name;
    private String surname;
    private String phone;
    private boolean subscribed;

    public Visitor() {}

    public Visitor(String name, String surname, String phone, boolean subscribed) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.subscribed = subscribed;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public boolean isSubscribed() { return subscribed; }
    public void setSubscribed(boolean subscribed) { this.subscribed = subscribed; }

    @Override
    public String toString() {
        return String.format("Посетитель: %s %s, Телефон: %s, Подписка: %s",
                name, surname, phone, subscribed ? "активна" : "неактивна");
    }
}