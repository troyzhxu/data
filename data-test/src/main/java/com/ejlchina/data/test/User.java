package com.ejlchina.data.test;

/**
 * @author Troy.Zhou @ 2022/5/27
 * @since v
 */
public class User {

    private int id;
    private String name;
    private School school;

    public User() {
    }

    public User(int id, String name, School school) {
        this.id = id;
        this.name = name;
        this.school = school;
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

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

}
