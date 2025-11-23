package model;

public class Book {
    private int id;
    private String name;
    private String author;
    private int publishingYear;
    private String isbn;
    private String publisher;
    private String visitorName;

    public Book() {}

    public Book(String name, String author, int publishingYear, String isbn, String publisher, String visitorName) {
        this.name = name;
        this.author = author;
        this.publishingYear = publishingYear;
        this.isbn = isbn;
        this.publisher = publisher;
        this.visitorName = visitorName;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public int getPublishingYear() { return publishingYear; }
    public void setPublishingYear(int publishingYear) { this.publishingYear = publishingYear; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }
    public String getVisitorName() { return visitorName; }
    public void setVisitorName(String visitorName) { this.visitorName = visitorName; }

    @Override
    public String toString() {
        return String.format("Книга: %s, Автор: %s, Год: %d, ISBN: %s, Издатель: %s, Владелец: %s",
                name, author, publishingYear, isbn, publisher, visitorName);
    }
}