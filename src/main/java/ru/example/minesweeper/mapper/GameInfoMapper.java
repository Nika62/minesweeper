package ru.example.minesweeper.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.example.minesweeper.dto.GameInfoResponseDto;
import ru.example.minesweeper.model.GameInfo;

@Mapper(componentModel = "spring")
public interface GameInfoMapper {

    GameInfoMapper INSTANCE = Mappers.getMapper(GameInfoMapper.class);

    @Mapping(target = "field", ignore = true)
    GameInfoResponseDto gameInfoToGameInfoRequestDto(GameInfo gameInfo);
}
