package me.ch1ple.hw.employeebook.exception;

public class InvalidNameException extends RuntimeException {
    public InvalidNameException(String name) {
        super(name);
    }
}
