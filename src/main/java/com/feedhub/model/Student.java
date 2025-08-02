package com.feedhub.model;

public class Student {
    private int id;
    private User user;
    private String name;
    private String rollNo;

    public Student() {}
    public Student(int id, User user, String name, String rollNo) {
        this.id = id;
        this.user = user;
        this.name = name;
        this.rollNo = rollNo;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRollNo() { return rollNo; }
    public void setRollNo(String rollNo) { this.rollNo = rollNo; }
}
