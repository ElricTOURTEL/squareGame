package com.example.demo;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RandomHeartBeat implements HeartBeatSensor {

    public int get(){
        Random random = new Random();
        int randomWithNextInt = random.nextInt();
        return randomWithNextInt;
    }
}
