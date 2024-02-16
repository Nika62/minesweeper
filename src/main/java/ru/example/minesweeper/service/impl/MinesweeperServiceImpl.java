package ru.example.minesweeper.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.example.minesweeper.dao.DaoFieldService;
import ru.example.minesweeper.dto.GameInfoResponseDto;
import ru.example.minesweeper.dto.GameTurnRequest;
import ru.example.minesweeper.dto.NewGameRequest;
import ru.example.minesweeper.mapper.GameInfoMapper;
import ru.example.minesweeper.model.GameInfo;
import ru.example.minesweeper.model.LineField;
import ru.example.minesweeper.service.FieldService;
import ru.example.minesweeper.service.GameInfoService;
import ru.example.minesweeper.service.MinesweeperService;
import ru.example.minesweeper.service.Validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ru.example.minesweeper.common.CellValue.BLANC;
import static ru.example.minesweeper.common.CellValue.EXPLODED_MINE;
import static ru.example.minesweeper.common.CellValue.MINE;
import static ru.example.minesweeper.common.CellValue.ZERO;


@Service
@RequiredArgsConstructor
public class MinesweeperServiceImpl implements MinesweeperService {
    private final DaoFieldService daoFieldService;
    private final GameInfoMapper gameInfoMapper;

    private final GameInfoService gameInfoService;

    private final FieldService fieldService;

    private List<String[]> intermediateField;

    public List<String[]> getIntermediateField() {
        return intermediateField;
    }

    public void setIntermediateField(List<String[]> intermediateField) {
        this.intermediateField = intermediateField;
    }

    @Override
    public GameInfoResponseDto createGame(NewGameRequest newGameRequest) {
        Validator.checkQuantityMines(newGameRequest.getWidth(), newGameRequest.getHeight(), newGameRequest.getMinesCount());
        intermediateField = getInitialEmptyField(newGameRequest.getWidth(), newGameRequest.getHeight());
        var gameInfo = gameInfoService.saveGame(newGameRequest);
        GameInfoResponseDto responseDto = gameInfoMapper.gameInfoToGameInfoRequestDto(gameInfo);
        savePlayingField(gameInfo);
        responseDto.setField(intermediateField);
        return responseDto;
    }

    @Override
    public GameInfoResponseDto makeMove(GameTurnRequest gameTurnRequest) {
        int col = gameTurnRequest.getCol();
        int row = gameTurnRequest.getRow();
        GameInfo gameInfo = gameInfoService.getGameInfo(gameTurnRequest.getGameId());
        Validator.checkGameStatus(gameInfo);
        GameInfoResponseDto response = gameInfoMapper.gameInfoToGameInfoRequestDto(gameInfo);
        List<LineField> lines = daoFieldService.getPlayingField(gameInfo.getGameId());
        List<String[]> field = convertToPlayingField(lines, gameInfo.getWidth(), gameInfo.getHeight());

        if (gameInfo.getFreeCells() == gameInfo.getMinesCount() + 1) {
            return getResponseWhenNoFreeCells(col, row, field, gameInfo, response);
        }
        if (field.get(col)[row].equals(MINE.getTitle())) {
            return getResponseGameLost(field, gameInfo, response);
        }
        if (field.get(col)[row].equals(ZERO.getTitle())) {
            return getResponseOpenZeroCells(col, row, field, gameInfo, response);
        }
        return getResponseWithForCellsFrom1To8(col, row, field, gameInfo, response);
    }

    private GameInfoResponseDto getResponseWithForCellsFrom1To8(int col, int row, List<String[]> field, GameInfo gameInfo, GameInfoResponseDto response) {
        intermediateField.get(col)[row] = field.get(col)[row];
        response.setField(intermediateField);
        gameInfoService.updateGame(gameInfo, gameInfo.getFreeCells() - 1, true);
        return response;
    }

    private GameInfoResponseDto getResponseGameLost(List<String[]> field, GameInfo gameInfo, GameInfoResponseDto response) {
        for (String[] array : field) {
            for (int i = 0; i < array.length; i++) {
                if (array[i].equals(MINE.getTitle())) {
                    array[i] = EXPLODED_MINE.getTitle();
                }
            }
        }
        gameInfoService.updateGame(gameInfo, 0, true);
        response.setCompleted(true);
        response.setField(field);
        return response;
    }

    private GameInfoResponseDto getResponseWhenNoFreeCells(int col, int row, List<String[]> field, GameInfo gameInfo, GameInfoResponseDto response) {
        if (field.get(col)[row].equals(MINE.getTitle())) {
            return getResponseGameLost(field, gameInfo, response);
        }
        response.setCompleted(true);
        response.setField(field);
        gameInfoService.updateGame(gameInfo, 0, true);
        return response;
    }

    GameInfoResponseDto getResponseOpenZeroCells(int col, int row, List<String[]> field, GameInfo gameInfo, GameInfoResponseDto response) {
        fieldService.fillIntermediateField(field, intermediateField, col, row, gameInfo);
        response.setField(intermediateField);
        return response;
    }


    private void savePlayingField(GameInfo gameInfo) {

        ArrayList<Integer[]> field = fieldService.getInitialField(gameInfo);
        for (int i = 0; i < gameInfo.getWidth(); i++) {
            Integer[] array = field.get(i);
            for (int j = 0; j < gameInfo.getHeight(); j++) {
                var newLine = new LineField(gameInfo.getGameId(), i, j, array[j]);
                daoFieldService.saveField(newLine);
            }
        }
    }

    public List<String[]> convertToPlayingField(List<LineField> lines, int width, int height) {
        List<String[]> result = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            final int k = i;
            List<LineField> listCols = lines.stream().filter(line -> line.getCol() == k).toList();
            result.add(getRow(listCols, height));
        }

        return result;
    }

    private String[] getRow(List<LineField> list, int height) {
        String[] array = new String[height];
        for (LineField line : list) {
            String letter = String.valueOf(line.getLetter());
            array[line.getHeight()] = letter.equals("-1") ? MINE.getTitle() : letter;
        }
        return array;
    }

    private ArrayList<String[]> getInitialEmptyField(int width, int height) {
        ArrayList<String[]> startField = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            String[] rows = new String[height];
            Arrays.fill(rows, BLANC.getTitle());
            startField.add(rows);
        }
        return startField;
    }


}
