package ru.example.minesweeper.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.example.minesweeper.dao.DaoFieldService;
import ru.example.minesweeper.dto.GameInfoResponseDto;
import ru.example.minesweeper.dto.GameTurnRequest;
import ru.example.minesweeper.dto.NewGameRequest;
import ru.example.minesweeper.model.LineField;
import ru.example.minesweeper.service.MinesweeperService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/minesweeper")
@RequiredArgsConstructor
public class MinesweeperController {

    private final MinesweeperService minesweeperService;
    private final DaoFieldService daoFieldService;

    @PostMapping("/new")
    public GameInfoResponseDto createGame(@Valid @RequestBody NewGameRequest newGameRequest) {
        return minesweeperService.createGame(newGameRequest);
    }

    @PostMapping("/turn")
    public GameInfoResponseDto makeMove(@RequestBody GameTurnRequest gameTurnRequest) {
        return minesweeperService.makeMove(gameTurnRequest);
    }

    @GetMapping("/{id}")
    public List<LineField> getField(@PathVariable String id) {
        return daoFieldService.getPlayingField(id);
    }
}
