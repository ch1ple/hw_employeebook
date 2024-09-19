package me.ch1ple.hw.employeebook.exception;

public class InvalidSurnameException extends RuntimeException {
    public InvalidSurnameException(String surname) {
        super(surname);
    }
}
