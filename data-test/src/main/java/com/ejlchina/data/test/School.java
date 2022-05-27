package com.ejlchina.data.test;

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

}
