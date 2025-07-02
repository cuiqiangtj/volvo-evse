package com.volvo.evse.evse.service;

import com.volvo.evse.evse.bo.ConnectorBo;
import com.volvo.evse.evse.dao.ConnectorMapper;
import com.volvo.evse.evse.model.Connector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConnectorService {

    @Autowired
    private ConnectorMapper connectorMapper;

    /**
     * 新增连接器
     * @param connectorBo
     * @return
     */
    public Connector insert(ConnectorBo connectorBo) {
        connectorMapper.insert(connectorBo);

        return connectorMapper.selectById(connectorBo.getId());
    }

    /**
     * 修改连接器
     * @param connectorBo
     * @return
     */
    public Connector update(ConnectorBo connectorBo) {
        connectorMapper.update(connectorBo);
        return connectorMapper.selectById(connectorBo.getId());
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    public Connector selectById(int id){
        return connectorMapper.selectById(id);
    }
}
