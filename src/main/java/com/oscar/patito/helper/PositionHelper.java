package com.oscar.patito.helper;

import com.oscar.patito.model.Position;
import com.oscar.patito.model.PositionInfo;
import com.oscar.patito.payload.PositionInfoPayload;
import com.oscar.patito.payload.PositionPayload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PositionHelper {

    private static final Logger logger = LogManager.getLogger(PositionHelper.class);

    public Position generatePosition(PositionPayload pp) {
        if(pp!=null) {
            Position position = new Position();
            position.setId(pp.getId());
            position.setName(pp.getName());
            position.setDescription(pp.getDescription());
            logger.info("Position entity created");
            return position;
        }else{
            return null;
        }
    }

    public PositionPayload generatePositionPayload(Position pp) {
        if(pp!=null){
        PositionPayload position = new PositionPayload();
        position.setId(pp.getId());
        position.setName(pp.getName());
        position.setDescription(pp.getDescription());
        logger.info("Position payload created");
        return position;
        }else{
            return null;
        }
    }

    /*
    public PositionInfo generatePositionInfo(PositionInfoPayload p){
        PositionInfo pi= new PositionInfo();
        pi.setCorporateEmail(p.getCorporateEmail());
        pi.setOldPosition(generatePosition(p.getOldPosition()));
        pi.setCurrentPosition(generatePosition(p.getCurrentPosition()));
        pi.setOldSalary(p.getOldSalary());
        pi.setCurrentSalary(p.getCurrentSalary());
        return pi;
    }*/

    public PositionInfoPayload generatePositionInfoPayload(PositionInfo p){
        PositionInfoPayload pip= new PositionInfoPayload();
        pip.setCorporateEmail(p.getCorporateEmail());
        pip.setOldPosition(p.getOldPosition()!=null?generatePositionPayload(p.getOldPosition()):null);
        pip.setCurrentPosition(p.getCurrentPosition()!=null?generatePositionPayload(p.getCurrentPosition()):null);
        pip.setOldSalary(p.getOldSalary());
        pip.setCurrentSalary(p.getCurrentSalary());
        pip.setActive(p.getActive());
        return pip;
    }


}
