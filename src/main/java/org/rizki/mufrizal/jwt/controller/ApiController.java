package org.rizki.mufrizal.jwt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1")
public class ApiController {

    @GetMapping(value = "/sample")
    public ResponseEntity<?> sample() {
        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("Success", Boolean.TRUE);
        stringObjectMap.put("Message", "OK Berhasil");

        return new ResponseEntity<>(stringObjectMap, HttpStatus.OK);
    }

}
