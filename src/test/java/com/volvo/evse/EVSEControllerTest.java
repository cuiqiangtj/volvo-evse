package com.volvo.evse;

import com.volvo.evse.evse.bo.EvseBo;
import com.volvo.evse.evse.controller.EVSEController;
import com.volvo.evse.evse.model.Evse;
import com.volvo.evse.evse.service.EvseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

//@RunWith(SpringRunner.class)
@SpringBootTest
public class EVSEControllerTest {

    @Autowired
    private EVSEController evseController;
    @Test
    public void add(){

        EvseBo evseBo = new EvseBo();
        evseBo.setLocationId(1);
        evseBo.setEvseCode("US*ABC*EVSE123456");
        ResponseEntity<?> map = evseController.add(evseBo);

        System.out.println(map.getStatusCode());
        System.out.println(map.getBody());
    }

    @Test
    public void changeStatus(){

        //1. NITIAL → AVAILABLE
        EvseBo evseBo = new EvseBo();
        evseBo.setLocationId(1);
        evseBo.setEvseCode("US*ABC*EVSE123456");
        ResponseEntity<?> map = evseController.add(evseBo);

        System.out.println(map.getStatusCode());
        System.out.println(map.getBody());
        Evse evse = (Evse) map.getBody();
        BeanUtils.copyProperties(evse,evseBo);


        //2. AVAILABLE ↔ BLOCKED
        evseBo.setStatus(EvseService.STATE_BLOCKED);
        map = evseController.change(evseBo);

        System.out.println(map.getStatusCode());
        System.out.println(map.getBody());

        evseBo.setStatus(EvseService.STATE_AVAILABLE);
        map = evseController.change(evseBo);

        System.out.println(map.getStatusCode());
        System.out.println(map.getBody());

        //3. AVAILABLE ↔ INOPERATIVE
        evseBo.setStatus(EvseService.STATE_INOPERATIVE);
        map = evseController.change(evseBo);

        System.out.println(map.getStatusCode());
        System.out.println(map.getBody());

        evseBo.setStatus(EvseService.STATE_AVAILABLE);
        map = evseController.change(evseBo);

        System.out.println(map.getStatusCode());
        System.out.println(map.getBody());

        //4. AVAILABLE → REMOVED
        evseBo.setStatus(EvseService.STATE_INOPERATIVE);
        map = evseController.change(evseBo);

        System.out.println(map.getStatusCode());
        System.out.println(map.getBody());

        //5. error  REMOVED ->AVAILABLE
        evseBo.setStatus(EvseService.STATE_AVAILABLE);
        map = evseController.change(evseBo);

        System.out.println(map.getStatusCode());
        System.out.println(map.getBody());
    }

    @Test
    public void query(){
        ResponseEntity<?> map = evseController.query(1,10);

        System.out.println(map.getStatusCode());
        System.out.println(map.getBody());


    }
}
