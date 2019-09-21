package com.ujjwal.backgroundservicedemo;

public class RandomNumberEvent {
    private final int randomNumber;

    public RandomNumberEvent(int randomNumber) {
        this.randomNumber=randomNumber;
    }

    public int getRandomNumber() {
        return randomNumber;
    }
}
