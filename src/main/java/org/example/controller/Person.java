package org.example.controller;

public sealed interface Person permits Student, Teacher {

    void run();
}
