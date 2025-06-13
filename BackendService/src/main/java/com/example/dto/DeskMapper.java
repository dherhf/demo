package com.example.dto;

import com.example.model.desk.Desk;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeskMapper {
    Desk toEntity(DeskDTO dto);
    DeskDTO toDTO(Desk desk);
}
