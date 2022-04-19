package com.oscar.patito.service;

import com.oscar.patito.dto.EmployeeDTO;
import com.oscar.patito.dto.PositionInfoHistoryDTO;
import com.oscar.patito.helper.PositionHelper;
import com.oscar.patito.model.Position;
import com.oscar.patito.model.PositionInfo;
import com.oscar.patito.dto.PositionInfoDTO;
import com.oscar.patito.dto.PositionDTO;
import com.oscar.patito.model.PositionInfoHistory;
import com.oscar.patito.repository.EmployeeRepository;
import com.oscar.patito.repository.PositionInfoHistoryRepository;
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
    @Autowired
    private PositionInfoHistoryRepository positionInfoHistoryRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    private static final Logger logger = LogManager.getLogger(PositionService.class);
    PositionHelper ph= new PositionHelper();

    public List<Position> savePositions(List<PositionDTO> positions)throws DataIntegrityViolationException {
        List<Position> positionSaved = new ArrayList<>();
        for(PositionDTO pos: positions) {
            Position position= ph.generatePosition(pos);
            logger.info("Saving position "+position.getDescription());
            positionSaved.add(positionRepository.save(position));
        }
        return positionSaved;
    }

    public List<PositionDTO> listPositions(){
        List<Position> positions = positionRepository.findAll();
        logger.info("Positions list completed");
        return positions.stream().map(p -> new PositionDTO(p.getId(), p.getName(), p.getDescription())).collect(Collectors.toList());
    }

    public List<PositionInfoDTO> listPositionsInfo(){
        List<PositionInfo> positionsInfo = positionInfoRepository.findAllActive(true);
        //List<PositionInfo> positionsInfo = positionInfoRepository.findAll();
        logger.info("Positions info list completed");
        return positionsInfo.stream().map(p -> new PositionInfoDTO(p.getCorporateEmail(),
                ph.generatePositionPayload(p.getOldPosition()),
                ph.generatePositionPayload(p.getCurrentPosition()),
                p.getOldSalary(), p.getCurrentSalary(), p.getActive())).collect(Collectors.toList());
    }

    public List<PositionInfoHistoryDTO> listPositionsInfoHistory(){
        List<String> emails= employeeRepository.findEmailAllActive(true);
        logger.info("Emails actives "+emails.size());
        //List<PositionInfoHistory> positionsInfoHistory = positionInfoHistoryRepository.findAll();
        List<PositionInfoHistory> positionsInfoHistory = new ArrayList<>();
        List<PositionInfoHistory> positionsInfoHistoryAux= new ArrayList<>();
        for(String s: emails) {
            positionsInfoHistoryAux.clear();
            positionsInfoHistoryAux = positionInfoHistoryRepository.findActiveCurrentHistory(s);
            positionsInfoHistory.addAll(positionsInfoHistoryAux);
        }
        logger.info("Positions info history list completed");
        return positionsInfoHistory.stream().map(p -> new PositionInfoHistoryDTO(p.getId(),p.getCorporateEmail(),
                new PositionInfoDTO(p.getPositionInfo().getCorporateEmail(),
                        ph.generatePositionPayload(p.getPositionInfo().getOldPosition()),
                        ph.generatePositionPayload(p.getPositionInfo().getCurrentPosition()),
                        p.getPositionInfo().getOldSalary(), p.getPositionInfo().getCurrentSalary(), p.getPositionInfo().getActive()),
                p.getLast_modified(),
                p.getCurrent())).collect(Collectors.toList());
    }

    public String getWelcomeMessage() {
        return "Hello World!";
    }

}
