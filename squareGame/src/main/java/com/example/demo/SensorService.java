package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SensorService {
    @Autowired
    private SensorRepository sensorRepository;

    public List<Sensor> getAllSensors(){
        return sensorRepository.findAll();
    }

    public Sensor getSensorById(Long id){
        return sensorRepository.findById(id);
    }

    public Sensor createSensor(Sensor sensor){
        return sensorRepository.save(sensor);
    }

    public Sensor updateSensor(Long id, Sensor sensor){
        Sensor existSensor = sensorRepository.findById(id);
        if(existSensor != null){
            existSensor.setName(sensor.getName());
            return sensorRepository.save(existSensor);
        }
        return null;
    }

    public boolean deleteSensor(Long id){
        return sensorRepository.deleteById(id);
    }
}
