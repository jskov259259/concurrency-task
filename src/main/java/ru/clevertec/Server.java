package ru.clevertec;

import java.util.Random;

public class Server {

    public static Integer function(Integer request) {

        int delay = getRandomNumber(0, 2000);
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 100 - request;
    }

    private static int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
}
