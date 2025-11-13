package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sensors")
public class SensorController {
    @Autowired
    private SensorService sensorService;

    @GetMapping
    public List<Sensor> getAllSensors(){
        return sensorService.getAllSensors();
    }

    @GetMapping("/{id}")
    public Sensor getSensorById(@PathVariable Long id){
        return sensorService.getSensorById(id);
    }

    @PostMapping
    public Sensor createSensor(@RequestBody Sensor sensor){
        return sensorService.createSensor(sensor);
    }

    @PutMapping("{id}")
    public Sensor updateSensor(@PathVariable Long id, @RequestBody Sensor sensor){
        return sensorService.updateSensor(id, sensor);
    }

    @DeleteMapping("/{id}")
    public boolean deleteSensor(@PathVariable Long id){
        return sensorService.deleteSensor(id);
    }
}
