package com.volvo.evse.evse.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.volvo.evse.evse.bo.EvseBo;
import com.volvo.evse.evse.bo.LocationBo;
import com.volvo.evse.evse.dao.EvseMapper;
import com.volvo.evse.evse.dao.LocationMapper;
import com.volvo.evse.evse.model.Evse;
import com.volvo.evse.evse.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EvseService {

    @Autowired
    private EvseMapper evseMapper;

    public static final String STATE_INITIAL      ="Initial";
    public static final String STATE_AVAILABLE    ="Available";
    public static final String STATE_BLOCKED      ="Blocked";
    public static final String STATE_INOPERATIVE  ="Inoperative";
    public static final String STATE_REMOVED      ="Removed";

    /**
     * 新增设备
     * @param evseBo
     * @return
     */
    public Evse insert(EvseBo evseBo) {

        //默认状态为AVAILABLE
        evseBo.setStatus(STATE_AVAILABLE);

        evseMapper.insert(evseBo);

        return evseMapper.selectById(evseBo.getId());
    }

    /**
     * 修改设备(只修改内容,不改状态)
     * @param evseBo
     * @return
     */
    public Evse update(EvseBo evseBo) {

        Evse evse = new Evse();
        evse.setId(evseBo.getId());
        evse.setLocationId(evseBo.getLocationId());
        evse.setEvseCode(evseBo.getEvseCode());
        evseMapper.update(evse);
        return evseMapper.selectById(evseBo.getId());
    }
    /**
     * 修改设备状态
     * @param evseBo
     * @return
     */
    public void updateStatus(EvseBo evseBo) {


        Evse evse = new Evse();
        evse.setId(evseBo.getId());
        evse.setStatus(evseBo.getStatus());
        evseMapper.updateStatus(evse);
    }


    /**
     * 根据id查询
     * @param id
     * @return
     */
    public Evse selectById(int id){
        return evseMapper.selectById(id);
    }

    /**
     * 根据id查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageInfo<Evse> query(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<Evse>  evseList = evseMapper.query();
        return new PageInfo<>(evseList);
    }

}
