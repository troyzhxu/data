package com.ejlchina.data.test;

import java.util.Objects;

/**
 * @author Troy.Zhou @ 2022/5/27
 * @since v
 */
public class School {

    private int id;
    private String name;

    public School() {
    }

    public School(int id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        School school = (School) o;
        return id == school.id && Objects.equals(name, school.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

}
