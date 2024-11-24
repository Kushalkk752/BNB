package com.clone.service;

import com.clone.entity.Rooms;
import com.clone.payload.RoomsDto;
import com.clone.repository.RoomsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class RoomsService {

    private ModelMapper modelMapper;
    private RoomsRepository roomsRepository;

    public RoomsService(ModelMapper modelMapper, RoomsRepository roomsRepository) {
        this.modelMapper = modelMapper;
        this.roomsRepository = roomsRepository;
    }

    public RoomsDto addRooms(RoomsDto dto) {
        Rooms rooms = mapToEntity(dto);
        Rooms save = roomsRepository.save(rooms);
        return mapToDto(save);
    }

    private RoomsDto mapToDto(Rooms save) {
        RoomsDto roomsDto = modelMapper.map(save, RoomsDto.class);
        return roomsDto;
    }

    private Rooms mapToEntity(RoomsDto dto) {
        Rooms rooms = modelMapper.map(dto, Rooms.class);
        return rooms;
    }
}
