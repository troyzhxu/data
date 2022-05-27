package com.ejlchina.data.test;

import java.util.Objects;

/**
 * @author Troy.Zhou @ 2022/5/27
 * @since v
 */
public class User {

    private int id;
    private String name;
    private School school;
    private boolean deleted;

    public User() {
    }

    public User(int id, String name, School school, boolean deleted) {
        this.id = id;
        this.name = name;
        this.school = school;
        this.deleted = deleted;
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && name.equals(user.name) && school.equals(user.school);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, school);
    }

}
