package com.cz.zfy.vhr.service;

import com.cz.zfy.vhr.mapper.PositionMapper;
import com.cz.zfy.vhr.model.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PositionService {
    @Autowired
    PositionMapper positionMapper;
    public List<Position> getAllPosition(){
        return positionMapper.getAllPosition();
    }

    public Integer addPosition(Position position) {
        position.setCreateDate(new Date());
        position.setEnabled(true);
        return positionMapper.insert(position);
    }

    public Integer updatePosition(Position position) {
        return positionMapper.updateByPrimaryKeySelective(position);
    }

    public Integer deletePosition(Integer id) {
        return positionMapper.deleteByPrimaryKey(id);
    }
}
