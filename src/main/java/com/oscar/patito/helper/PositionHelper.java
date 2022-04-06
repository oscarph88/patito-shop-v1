package com.oscar.patito.helper;

import com.oscar.patito.model.Position;
import com.oscar.patito.model.PositionInfo;
import com.oscar.patito.dto.PositionInfoDTO;
import com.oscar.patito.dto.PositionDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PositionHelper {

    private static final Logger logger = LogManager.getLogger(PositionHelper.class);

    public Position generatePosition(PositionDTO pp) {
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

    public PositionDTO generatePositionPayload(Position pp) {
        if(pp!=null){
        PositionDTO position = new PositionDTO();
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

    public PositionInfoDTO generatePositionInfoPayload(PositionInfo p){
        PositionInfoDTO pip= new PositionInfoDTO();
        pip.setCorporateEmail(p.getCorporateEmail());
        pip.setOldPosition(p.getOldPosition()!=null?generatePositionPayload(p.getOldPosition()):null);
        pip.setCurrentPosition(p.getCurrentPosition()!=null?generatePositionPayload(p.getCurrentPosition()):null);
        pip.setOldSalary(p.getOldSalary());
        pip.setCurrentSalary(p.getCurrentSalary());
        pip.setActive(p.getActive());
        return pip;
    }


}
