package com.volvo.evse.evse.dao;

import com.volvo.evse.evse.bo.EvseBo;
import com.volvo.evse.evse.bo.LocationBo;
import com.volvo.evse.evse.model.Evse;
import com.volvo.evse.evse.model.Location;

import java.util.List;


public interface EvseMapper {


    int insert(EvseBo evse) ;

    void update(Evse evse);

    Evse selectById(int id);

    void updateStatus(Evse evse);

    List<Evse> query();
}
