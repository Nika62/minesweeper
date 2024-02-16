package ru.example.minesweeper.service.impl;

import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.example.minesweeper.dao.DaoFieldService;
import ru.example.minesweeper.dto.GameInfoResponseDto;
import ru.example.minesweeper.dto.GameTurnRequest;
import ru.example.minesweeper.dto.NewGameRequest;
import ru.example.minesweeper.mapper.GameInfoMapperImpl;
import ru.example.minesweeper.repository.GameRepository;
import ru.example.minesweeper.service.FieldService;
import ru.example.minesweeper.service.GameInfoService;
import ru.example.minesweeper.service.Validator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static ru.example.minesweeper.mock.Mocks.getEmptyList;
import static ru.example.minesweeper.mock.Mocks.getGameField;
import static ru.example.minesweeper.mock.Mocks.getGameInfo;

@SpringBootTest(classes = {MinesweeperServiceImpl.class, GameInfoMapperImpl.class, Validator.class, GameInfoMapperImpl.class, DaoFieldService.class, FieldService.class})
class MinesweeperServiceImplTest {

    @Autowired
    MinesweeperServiceImpl minesweeperService;
    @MockBean
    GameRepository gameRepository;
    @MockBean
    DaoFieldService daoFieldService;

    @MockBean
    GameInfoService gameInfoService;

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(gameInfoService, gameRepository, daoFieldService);
    }

    @Test
    @SneakyThrows
    void createGame() {
        var request = new NewGameRequest(4, 2, 1);
        var gameInfo = getGameInfo();
        when(gameInfoService.saveGame(request)).thenReturn(gameInfo);
        var createdGame = minesweeperService.createGame(request);
        verify(gameInfoService).saveGame(request);
        verify(daoFieldService, times(8)).saveField(any());

        var response = new GameInfoResponseDto(null, 4, 2, 1, false);
        assertThat(createdGame).usingRecursiveComparison().ignoringFields("field").isEqualTo(response);
    }

    @Test
    void makeMove() {
        var request = new GameTurnRequest("1", 1, 0);
        var gameInfo = getGameInfo();
        minesweeperService.setIntermediateField(getEmptyList());
        var gameField = getGameField();

        when(gameInfoService.getGameInfo("1")).thenReturn(gameInfo);
        when(daoFieldService.getPlayingField(gameInfo.getGameId())).thenReturn(gameField);

        var createdGame = minesweeperService.makeMove(request);
        assertThat(createdGame.getField().get(0)[0]).isEqualTo("0");
        assertThat(createdGame.getField().get(0)[1]).isEqualTo("0");
        assertThat(createdGame.getField().get(1)[0]).isEqualTo("0");
        assertThat(createdGame.getField().get(1)[1]).isEqualTo("0");
        assertThat(createdGame.getField().get(2)[0]).isEqualTo("1");
        assertThat(createdGame.getField().get(2)[1]).isEqualTo("1");

        verify(gameInfoService).getGameInfo("1");
        verify(daoFieldService).getPlayingField(gameInfo.getGameId());

    }

}