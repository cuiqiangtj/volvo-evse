package com.volvo.evse;

import com.volvo.evse.evse.bo.ConnectorBo;
import com.volvo.evse.evse.bo.LocationBo;
import com.volvo.evse.evse.controller.ConnectorController;
import com.volvo.evse.evse.controller.LocationController;
import com.volvo.evse.evse.model.Connector;
import com.volvo.evse.evse.model.Evse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

//@RunWith(SpringRunner.class)
@SpringBootTest
public class ConnectorControllerTest {

    @Autowired
    private ConnectorController connectorController;


    @Test
    public void testLocation(){

        ConnectorBo bo = new ConnectorBo();
        bo.setEvseId(1);
        bo.setStandard("GBI17625.1-2012");
        bo.setPowerLevel("100-240VAC");
        bo.setVoltage("200W");


        ResponseEntity<?> map = connectorController.addConnector(bo);

        System.out.println(map.getStatusCode());
        System.out.println(map.getBody());
        Connector connector = (Connector) map.getBody();
        BeanUtils.copyProperties(connector,bo);

        bo.setEvseId(1);
        bo.setStandard("GBI17625.1-2022");
        bo.setPowerLevel("200-240VAC");
        bo.setVoltage("210W");


        map = connectorController.updateConnector(bo);

        System.out.println(map.getStatusCode());
        System.out.println(map.getBody());
    }

}
