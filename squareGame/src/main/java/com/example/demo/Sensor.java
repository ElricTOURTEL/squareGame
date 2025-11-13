package com.example.demo;

public class Sensor {
    private Long id;
    private String name;

    public Sensor(String name, Long id) {
        this.name = name;
        this.id = id;
    }
    public Sensor(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
