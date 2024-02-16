package ru.example.minesweeper.service;

import org.springframework.stereotype.Service;
import ru.example.minesweeper.dto.GameInfoResponseDto;
import ru.example.minesweeper.dto.GameTurnRequest;
import ru.example.minesweeper.dto.NewGameRequest;

@Service
public interface MinesweeperService {

    GameInfoResponseDto createGame(NewGameRequest newGameRequest);

    GameInfoResponseDto makeMove(GameTurnRequest gameTurnRequest);

}
