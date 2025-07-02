package com.volvo.evse.evse.dao;

import com.volvo.evse.evse.bo.LocationBo;
import com.volvo.evse.evse.model.Location;


public interface LocationMapper {


    int insert(LocationBo location) ;

    void update(LocationBo location);

    Location selectById(int id);
}
