package com.oscar.patito.helper;

import com.oscar.patito.model.Position;
import com.oscar.patito.payload.PositionPayload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PositionHelper {
    private static final Logger logger = LogManager.getLogger(PositionHelper.class);

    public Position generatePositions(PositionPayload pp) {
        Position position = new Position();
        position.setName(pp.getName());
        position.setDescription(pp.getDescription());
        logger.info("Position entity created");
        return position;
    }

    public PositionPayload generatePositionsPayload(Position pp) {
        PositionPayload position = new PositionPayload();
        position.setName(pp.getName());
        position.setDescription(pp.getDescription());
        logger.info("Position payload created");
        return position;
    }
}
