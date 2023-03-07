package org.example.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public non-sealed class Student implements Person {

    private String name;

    @Override
    public void run() {
        System.out.println("student run");
    }


}
