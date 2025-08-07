package com.feedhub.model;

public class Teacher {
    private int id;
    private User user;
    private String name;

    public Teacher() {}
    public Teacher(int id, User user, String name) {
        this.id = id;
        this.user = user;
        this.name = name;
    }

    @Override
	public String toString() {
		return "Teacher [id=" + id + ", user=" + user + ", name=" + name + "]";
	}
	public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
