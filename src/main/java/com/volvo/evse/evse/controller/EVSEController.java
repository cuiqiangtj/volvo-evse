package com.volvo.evse.evse.controller;

import com.github.pagehelper.PageInfo;
import com.volvo.evse.common.EvseCodeUnit;
import com.volvo.evse.evse.bo.EvseBo;
import com.volvo.evse.evse.model.Evse;
import com.volvo.evse.evse.model.Location;
import com.volvo.evse.evse.service.EvseService;
import com.volvo.evse.evse.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 充电站点
 */
@Controller
@RequestMapping("/evse")
public class EVSEController {


    @Autowired
    private EvseService evseService;

    @Autowired
    private LocationService locationService;

    @PostMapping("/add")
    public ResponseEntity<?> add(EvseBo evseBo) {

        // 判断LocationId是否存在
        int locationId = evseBo.getLocationId();

        Location location = locationService.selectById(locationId);
        if(location==null){
            return new ResponseEntity<>("Location  not found.", HttpStatus.BAD_REQUEST);
        }

        // 验证唯一标识
        String evseCodeId = evseBo.getEvseCode();
        // 验证失败返回400错误请求响应。
        if(!EvseCodeUnit.iSOCPICompliant(evseCodeId)){
            return new ResponseEntity<>("The code does not comply with the rules.", HttpStatus.BAD_REQUEST);
        }

        try {

            // 默认状态 AVAILABLE
            Evse evse= evseService.insert(evseBo);
            return new ResponseEntity<>(evse, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("System error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(EvseBo evseBo) {

        try {
            Evse evse = evseService.update(evseBo);
            return new ResponseEntity<>(evse, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("System error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/changeStatus")
    public ResponseEntity<?> change(EvseBo evseBo) {
        //判断当前状态和要转换的状态
        //无效转换将导致409冲突响应。

        Evse oldEvse = evseService.selectById(evseBo.getId());

        String oldStatus = oldEvse.getStatus();
        String newStatus = evseBo.getStatus();
        boolean isTrueStatus= false;
        if(EvseService.STATE_INITIAL.equalsIgnoreCase(oldStatus) &&
                EvseService.STATE_AVAILABLE.equalsIgnoreCase(newStatus)){
            //可更新 INITIAL → AVAILABLE
            isTrueStatus =true;
        }
        if(EvseService.STATE_AVAILABLE.equalsIgnoreCase(oldStatus) &&
                (EvseService.STATE_BLOCKED.equalsIgnoreCase(newStatus) ||
                  EvseService.STATE_INOPERATIVE.equalsIgnoreCase(newStatus))){
            //可更新 AVAILABLE → BLOCKED, AVAILABLE → INOPERATIVE
            isTrueStatus =true;
        }
        if((EvseService.STATE_BLOCKED.equalsIgnoreCase(oldStatus) ||
                EvseService.STATE_INOPERATIVE.equalsIgnoreCase(oldStatus)) &&
                EvseService.STATE_AVAILABLE.equalsIgnoreCase(newStatus) ){
            //可更新 BLOCKED → AVAILABLE, INOPERATIVE → AVAILABLE
            isTrueStatus =true;
        }
        if(EvseService.STATE_REMOVED.equalsIgnoreCase(newStatus)){
            //可更新 Any state → REMOVED (irreversible)
            isTrueStatus =true;
        }
        if(!isTrueStatus){
            return new ResponseEntity<>("The status modify is conflict.", HttpStatus.CONFLICT);
        }

        try {
            evseService.updateStatus(evseBo);
            return new ResponseEntity<>("The status is chenge.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("System error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
    @GetMapping("/query")
    public ResponseEntity<?> query(int pageNum, int pageSize) {
        //分页查询

        try {

            PageInfo<Evse> evseList = evseService.query(pageNum,pageSize);
            return new ResponseEntity<>(evseList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("System error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}


