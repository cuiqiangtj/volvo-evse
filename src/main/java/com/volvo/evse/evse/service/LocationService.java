package com.volvo.evse.evse.service;

import com.volvo.evse.evse.bo.LocationBo;
import com.volvo.evse.evse.dao.LocationMapper;
import com.volvo.evse.evse.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationService {

    @Autowired
    private LocationMapper locationMapper;

    /**
     * 新增站点
     * @param location
     * @return
     */
    public Location insert(LocationBo location) {
        locationMapper.insert(location);

        return locationMapper.selectById(location.getId());
    }

    /**
     * 修改站点
     * @param location
     * @return
     */
    public Location update(LocationBo location) {
        locationMapper.update(location);
        return locationMapper.selectById(location.getId());
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    public Location selectById(int id){
        return locationMapper.selectById(id);
    }
}
