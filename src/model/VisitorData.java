package model;

import java.util.List;

public class VisitorData {
    private String name;
    private String surname;
    private String phone;
    private boolean subscribed;
    private List<Book> favoriteBooks;

    // Конструкторы
    public VisitorData() {}

    public VisitorData(String name, String surname, String phone, boolean subscribed, List<Book> favoriteBooks) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.subscribed = subscribed;
        this.favoriteBooks = favoriteBooks;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public boolean isSubscribed() { return subscribed; }
    public void setSubscribed(boolean subscribed) { this.subscribed = subscribed; }
    public List<Book> getFavoriteBooks() { return favoriteBooks; }
    public void setFavoriteBooks(List<Book> favoriteBooks) { this.favoriteBooks = favoriteBooks; }

    @Override
    public String toString() {
        return String.format("Посетитель: %s %s, Телефон: %s, Подписка: %s",
                name, surname, phone, subscribed ? "активна" : "неактивна");
    }
}