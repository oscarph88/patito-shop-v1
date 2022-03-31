package com.oscar.patito.service;

import com.oscar.patito.helper.PositionHelper;
import com.oscar.patito.model.Position;
import com.oscar.patito.model.PositionInfo;
import com.oscar.patito.payload.PositionInfoPayload;
import com.oscar.patito.payload.PositionPayload;
import com.oscar.patito.repository.PositionInfoRepository;
import com.oscar.patito.repository.PositionsRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PositionService {
    @Autowired
    private PositionsRepository positionRepository;
    @Autowired
    private PositionInfoRepository positionInfoRepository;

    private static final Logger logger = LogManager.getLogger(PositionService.class);
    PositionHelper ph= new PositionHelper();

    public List<Position> savePositions(List<PositionPayload> positions)throws DataIntegrityViolationException {
        List<Position> positionSaved = new ArrayList<>();
        for(PositionPayload pos: positions) {
            Position position= ph.generatePosition(pos);
            logger.info("Saving position "+position.getDescription());
            positionSaved.add(positionRepository.save(position));
        }
        return positionSaved;
    }

    public List<PositionPayload> listPositions(){
        List<Position> positions = positionRepository.findAll();
        logger.info("Positions list completed");
        return positions.stream().map(p -> new PositionPayload(p.getId(), p.getName(), p.getDescription())).collect(Collectors.toList());
    }

    public List<PositionInfoPayload> listPositionsInfo(){
        List<PositionInfo> positionsInfo = positionInfoRepository.findAllActive(true);
        //List<PositionInfo> positionsInfo = positionInfoRepository.findAll();
        logger.info("Positions info list completed");
        return positionsInfo.stream().map(p -> new PositionInfoPayload(p.getCorporateEmail(),
                ph.generatePositionPayload(p.getOldPosition()),
                ph.generatePositionPayload(p.getCurrentPosition()),
                p.getOldSalary(), p.getCurrentSalary(), p.getActive())).collect(Collectors.toList());
    }


}
