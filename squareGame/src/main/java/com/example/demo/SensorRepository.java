package com.example.demo;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SensorRepository {
    private Map<Long, Sensor> sensors = new HashMap<>();
    private Long currentId = 1L;

    public List<Sensor> findAll(){
        return new ArrayList<>(sensors.values());
    }
    public Sensor findById(Long id){
        return sensors.get(id);
    }
    public Sensor save (Sensor sensor){
        if(sensor.getId() == null){
            sensor.setId(currentId++);
        }
        sensors.put(sensor.getId(), sensor);
        return sensor;
    }
    public boolean deleteById(Long id){
        return sensors.remove(id) != null;
    }
}
