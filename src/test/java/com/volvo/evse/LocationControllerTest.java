package com.volvo.evse;

import com.volvo.evse.evse.bo.EvseBo;
import com.volvo.evse.evse.bo.LocationBo;
import com.volvo.evse.evse.controller.EVSEController;
import com.volvo.evse.evse.controller.LocationController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

//@RunWith(SpringRunner.class)
@SpringBootTest
public class LocationControllerTest {

    @Autowired
    private LocationController locationController;
    @Test
    public void addLocation(){

        LocationBo bo = new LocationBo();
        bo.setName("testLocation");
        bo.setCoordinates("124.12345,56.12345");
        bo.setBusinessHours("0-24");
        bo.setAddress("XX市XX区XX路1号");
        ResponseEntity<?> map = locationController.addLocation(bo);

        System.out.println(map.getStatusCode());
        System.out.println(map.getBody());
    }

    @Test
    public void updateLocation(){

        LocationBo bo = new LocationBo();
        bo.setId(1);
        bo.setName("testLocation");
        bo.setCoordinates("124.12345,56.12345");
        bo.setBusinessHours("9-18");
        bo.setAddress("XX市XX区XX路1号");
        ResponseEntity<?> map = locationController.updateLocation(bo);

        System.out.println(map.getStatusCode());
        System.out.println(map.getBody());
    }

}
