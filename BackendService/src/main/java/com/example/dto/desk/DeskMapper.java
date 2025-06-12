package com.example.dto.desk;

import com.example.model.desk.Desk;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeskMapper {
    Desk toEntity(DeskRequest deskRequest);
    DeskDTO toDTO(Desk desk);
}
