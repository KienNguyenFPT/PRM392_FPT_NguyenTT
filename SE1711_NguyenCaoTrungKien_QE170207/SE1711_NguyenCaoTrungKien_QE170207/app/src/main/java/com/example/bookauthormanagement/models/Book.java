package com.example.bookauthormanagement.models;

public class Book {
    private int id;
    private String name;
    private String publishDate;
    private String genre;
    private int authorId;

    // Constructors, getters and setters
    public Book(int id, String name, String publishDate, String genre, int authorId) {
        this.id = id;
        this.name = name;
        this.publishDate = publishDate;
        this.genre = genre;
        this.authorId = authorId;
    }

    public int getId() {
        return id;
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

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }
}