package com.example.medicaltec.more;

import java.util.Random;

public class RandomLineGenerator {
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String generateRandomLine(long number) {
        String numberString = String.valueOf(number);

        if (numberString.length() != 8) {
            throw new IllegalArgumentException("Number must be 8 digits long.");
        }

        StringBuilder sb = new StringBuilder(numberString.length());

        for (int i = 0; i < numberString.length(); i++) {
            int randomIndex = (int) (number % CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
            number /= CHARACTERS.length();
        }

        return sb.reverse().toString();
    }

    public static long convertToNumber(String randomLine) {
        if (randomLine.length() != 8) {
            throw new IllegalArgumentException("Random line must be 8 characters long.");
        }

        long number = 0;

        for (int i = 0; i < randomLine.length(); i++) {
            char c = randomLine.charAt(i);
            int index = CHARACTERS.indexOf(c);
            if (index == -1) {
                throw new IllegalArgumentException("Invalid character in random line: " + c);
            }
            number = number * CHARACTERS.length() + index;
        }

        return number;
    }

    /*public static void main(String[] args) {
        long originalNumber = 73222256;

        String randomLine = generateRandomLine(originalNumber);
        System.out.println("Random Line: " + randomLine);

        long convertedNumber = convertToNumber(randomLine);
        System.out.println("Converted Number: " + convertedNumber);


    }*/

}
