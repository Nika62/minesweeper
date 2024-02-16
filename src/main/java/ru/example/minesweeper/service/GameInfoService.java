package ru.example.minesweeper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.example.minesweeper.dto.NewGameRequest;
import ru.example.minesweeper.exception.GameSaveException;
import ru.example.minesweeper.exception.NotFoundException;
import ru.example.minesweeper.model.GameInfo;
import ru.example.minesweeper.repository.GameRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameInfoService {

    private final GameRepository gameRepository;

    public GameInfo saveGame(NewGameRequest newGameRequest) {
        String gameId = getNewId();
        try {

            return gameRepository.save(new GameInfo(gameId, newGameRequest.getWidth(), newGameRequest.getHeight(),
                    newGameRequest.getMinesCount(), false,
                    newGameRequest.getWidth() * newGameRequest.getHeight() - newGameRequest.getMinesCount()));
        } catch (Exception e) {
            throw new GameSaveException("An error occurred while saving the game. " + e.getMessage());
        }
    }


    public void updateGame(GameInfo gameInfo, int freeCells, boolean completed) {
        gameInfo.setFreeCells(freeCells);
        gameInfo.setCompleted(completed);
        try {
            gameRepository.save(gameInfo);
        } catch (Exception e) {
            throw new GameSaveException("An error occurred while update the game. " + e.getMessage());
        }
    }

    public GameInfo getGameInfo(String id) {
        return gameRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Game with " + id + " not found")
        );
    }


    private String getNewId() {
        return UUID.randomUUID().toString();
    }

}
