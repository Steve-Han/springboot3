package org.example.controller;

public final class Teacher implements Person {

    private String name;

    @Override
    public void run() {
        System.out.println("teacher run");

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
