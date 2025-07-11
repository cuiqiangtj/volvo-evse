package com.volvo.evse.evse.controller;

import com.volvo.evse.evse.bo.ConnectorBo;
import com.volvo.evse.evse.model.Connector;
import com.volvo.evse.evse.model.Evse;
import com.volvo.evse.evse.model.Location;
import com.volvo.evse.evse.service.ConnectorService;
import com.volvo.evse.evse.service.EvseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 充电站点
 */
@Controller
@RequestMapping("/connector")
public class ConnectorController {

    @Autowired
    private ConnectorService connectorService;

    @Autowired
    private EvseService evseService;


    @PostMapping ("/addConnector")
    public ResponseEntity<?> addConnector(@RequestBody ConnectorBo connectorBo) {
        // 判断evseId是否存在
        Evse evse = evseService.selectById(connectorBo.getEvseId());

        if(evse==null){
            return new ResponseEntity<>("The EVSE is not found.", HttpStatus.BAD_REQUEST);
        }
        try {
            //新增
            Connector insert = connectorService.insert(connectorBo);
            return new ResponseEntity<>(insert, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("System error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping ("/updateConnector")
    public ResponseEntity<?> updateConnector(@RequestBody ConnectorBo connectorBo) {

        try {
            //修改
            Connector insert = connectorService.update(connectorBo);;
            return new ResponseEntity<>(insert, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("System error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
