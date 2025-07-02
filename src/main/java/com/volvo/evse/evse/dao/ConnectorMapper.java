package com.volvo.evse.evse.dao;

import com.volvo.evse.evse.bo.ConnectorBo;
import com.volvo.evse.evse.model.Connector;


public interface ConnectorMapper {


    int insert(ConnectorBo connectorBo) ;

    void update(ConnectorBo connectorBo);

    Connector selectById(int id);
}
