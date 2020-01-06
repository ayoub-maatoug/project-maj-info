package com.example.demo;

import com.example.model.Light;
import com.example.model.Status;
import com.example.repository.LightDao;
import com.example.repository.RoomDao;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController  // 1.
@RequestMapping("/api/lights") // 2.
@Transactional // 3.
public class LightController {

    @Autowired
    private LightDao lightDao; // 4.
    @Autowired
    private RoomDao roomDao;


    @GetMapping // 5.
    public List<LightDto> findAll() {
        return lightDao.findAll().stream().map(LightDto::new).collect(Collectors.toList());
               /* .stream()
                .map(LightDto::new)
                .collect(Collectors.toList());*/
    }

    @GetMapping(path = "/{id}")
    public LightDto findById(@PathVariable Integer id) {
        return lightDao.findById(id).map(light -> new LightDto(light)).orElse(null);
    }

    @PutMapping(path = "/{id}/switch")
    public List<RoomDto> switchStatus(@PathVariable Integer id) throws MqttException {
        // publish
        Light light = lightDao.findById(id).orElseThrow(IllegalArgumentException::new);

        MqttClient publisher = new MqttClient("tcp://max.isasecret.com:1723","1");
        MqttConnectOptions options = new MqttConnectOptions();
        MqttMessage msg = new MqttMessage();
        options.setUserName("majinfo2019");
        options.setPassword("Y@_oK2".toCharArray());
        publisher.connect(options);
        if (publisher.isConnected()) {
            publisher.publish("switchLight",msg);
        }
        light.setStatus(light.getStatus() == Status.ON ? Status.OFF: Status.ON);
        return roomDao.findAll()
                .stream()
                .map(RoomDto::new)
                .collect(Collectors.toList());
    }

    @PostMapping
    public LightDto create(@RequestBody LightDto dto) {
        Light light = null;
        if (dto.getId() != null) {
            light = lightDao.findById(dto.getId()).orElse(null);
        }

        if (light == null) {
            light = lightDao.save(new Light(dto.getLevel(), dto.getStatus(), roomDao.getOne(dto.getRoomId())));
        }
        else {
            light.setLevel(dto.getLevel());
            light.setStatus(dto.getStatus());
            lightDao.save(light);
        }

        return new LightDto(light);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Integer id) {
        lightDao.deleteById(id);
    }
}

