package ru.example.minesweeper.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.example.minesweeper.dao.DaoFieldService;
import ru.example.minesweeper.dto.GameInfoResponseDto;
import ru.example.minesweeper.dto.NewGameRequest;
import ru.example.minesweeper.mapper.GameInfoMapper;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class MinesweeperServiceTest {
    @Autowired
    private final DaoFieldService daoFieldService;

    @Autowired
    private final GameInfoMapper gameInfoMapper;
    @Autowired
    private final GameInfoService gameInfoService;
    @Autowired
    private final FieldService fieldService;
    @Autowired
    private final MinesweeperService minesweeperService;
    private NewGameRequest newGameRequest = new NewGameRequest(3, 2, 2);

    @Test
    void shouldCheckGameInfo() {
        GameInfoResponseDto response = minesweeperService.createGame(newGameRequest);
        assertEquals(newGameRequest.getWidth(), response.getWidth());
        assertEquals(newGameRequest.getHeight(), response.getHeight());
        assertEquals(newGameRequest.getMinesCount(), response.getMinesCount());
        assertTrue(Objects.nonNull(response.getGameId()));

    }

    @Test
    void shouldCheckFieldStartGame() {
        GameInfoResponseDto response = minesweeperService.createGame(newGameRequest);
        List<String[]> field = response.getField();
        assertEquals(field.size(), newGameRequest.getWidth());
        assertEquals(field.get(0).length, newGameRequest.getHeight());
        assertEquals(newGameRequest.getMinesCount(), response.getMinesCount());

    }
}