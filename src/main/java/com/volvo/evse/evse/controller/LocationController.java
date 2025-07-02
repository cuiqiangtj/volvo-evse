package com.volvo.evse.evse.controller;

import com.volvo.evse.evse.bo.LocationBo;
import com.volvo.evse.evse.model.Location;
import com.volvo.evse.evse.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

/**
 * 充电站点
 */
@Controller
@RequestMapping("/location")
public class LocationController {

    @Autowired
    private LocationService locationService;


    /**
     * 新增站点
     *
     * @param location
     * @return
     */
    @PostMapping("/addLocation")
    public ResponseEntity<?> addLocation(@RequestBody LocationBo location) {


        try {
            //新增
            Location insert = locationService.insert(location);
            return new ResponseEntity<>(insert, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("System error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * 修改站点
     *
     * @param location
     * @return
     */
    @PostMapping("/updateLocation")
    public ResponseEntity<?> updateLocation(@RequestBody LocationBo location) {

        try {
            //修改
            Location insert = locationService.update(location);;
            return new ResponseEntity<>(insert, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("System error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
