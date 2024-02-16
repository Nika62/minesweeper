package ru.example.minesweeper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.example.minesweeper.common.Common;
import ru.example.minesweeper.model.GameInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static ru.example.minesweeper.common.Common.FIRST_COL;
import static ru.example.minesweeper.common.Common.FIRST_ROW;

@Service
@RequiredArgsConstructor
public class FieldService {

    public ArrayList<Integer[]> getInitialField(GameInfo gameInfo) {
        int width = gameInfo.getWidth();
        int height = gameInfo.getHeight();
        int mines = gameInfo.getMinesCount();
        ArrayList<Integer[]> playingField = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            Integer[] rows = new Integer[height];
            Arrays.fill(rows, 0);
            playingField.add(rows);
        }
        addMines(width, height, mines, playingField);
        return playingField;
    }

    public void fillIntermediateField(List<String[]> field, List<String[]> intermediateField, int col, int row, GameInfo gameInfo) {
        int lastRowNumber = gameInfo.getHeight() - 1;
        int lastColNumber = gameInfo.getWidth() - 1;
        if (row == FIRST_ROW) {
            setFieldFromUpperRow(field, intermediateField, col, row, gameInfo);
            return;
        }
        if (row == lastRowNumber) {
            setFieldFromLowerRow(field, intermediateField, col, row, gameInfo);
            return;
        }
        if (col == FIRST_COL) {
            for (int i = row; i <= lastRowNumber; i++) {
                addCellToRight(col, i, field, intermediateField, gameInfo);
            }
            for (int i = row; i <= lastRowNumber; i--) {
                addCellToRight(col, i, field, intermediateField, gameInfo);
            }
            return;
        }

        if (col == lastColNumber) {
            for (int i = row; i <= lastRowNumber; i++) {
                addCellToLeft(col, i, field, intermediateField, gameInfo);
            }
            for (int i = row; i <= lastRowNumber; i--) {
                addCellToLeft(col, i, field, intermediateField, gameInfo);
            }
            return;
        }
        for (int i = row; i <= lastRowNumber; i++) {
            addCellToLeft(col, i, field, intermediateField, gameInfo);
            addCellToRight(col, i, field, intermediateField, gameInfo);
        }
        for (int i = row; i <= lastRowNumber; i--) {
            addCellToLeft(col, i, field, intermediateField, gameInfo);
            addCellToRight(col, i, field, intermediateField, gameInfo);
        }
    }

    private void setFieldFromUpperRow(List<String[]> field, List<String[]> intermediateField, int col, int row, GameInfo gameInfo) {
        int lastColNumber = gameInfo.getWidth() - 1;
        int lastRowNumber = gameInfo.getHeight() - 1;
        if (col == FIRST_COL) {
            for (int i = row; i <= lastRowNumber; i++) {
                addCellToRight(col, i, field, intermediateField, gameInfo);
            }

        } else if (col == lastColNumber) {
            for (int i = row; i <= lastRowNumber; i++) {
                addCellToLeft(col, i, field, intermediateField, gameInfo);
            }

        } else {
            for (int i = row; i <= lastRowNumber; i++) {
                addCellToLeft(col, i, field, intermediateField, gameInfo);
                addCellToRight(col, i, field, intermediateField, gameInfo);
            }
        }
    }

    private void setFieldFromLowerRow(List<String[]> field, List<String[]> intermediateField, int col, int row, GameInfo gameInfo) {
        int lastColNumber = gameInfo.getWidth() - 1;
        int lastRowNumber = gameInfo.getHeight() - 1;
        if (col == FIRST_COL) {
            for (int i = row; i <= lastRowNumber; i--) {
                addCellToRight(col, i, field, intermediateField, gameInfo);
            }
        } else if (col == lastColNumber) {
            for (int i = row; i <= lastRowNumber; i--) {
                addCellToLeft(col, i, field, intermediateField, gameInfo);
            }
        } else {
            for (int i = row; i <= lastRowNumber; i--) {
                addCellToLeft(col, i, field, intermediateField, gameInfo);
                addCellToRight(col, i, field, intermediateField, gameInfo);
            }
        }
    }

    private void addCellToRight(int col, int row, List<String[]> field, List<String[]> intermediateField, GameInfo gameInfo) {
        int lastColNumber = gameInfo.getWidth() - 1;
        String cell = field.get(col)[row];
        intermediateField.get(col)[row] = cell;
        if (col < lastColNumber && cell.equals("0")) {
            addCellToRight(col + 1, row, field, intermediateField, gameInfo);
        }
    }

    private void addCellToLeft(int col, int row, List<String[]> field, List<String[]> intermediateField, GameInfo gameInfo) {
        String cell = field.get(col)[row];
        intermediateField.get(col)[row] = cell;
        if (col > FIRST_ROW && cell.equals("0")) {
            addCellToRight(col - 1, row, field, intermediateField, gameInfo);
        }
    }


    private void addMines(int width, int height, int mines, ArrayList<Integer[]> playingField) {
        Random random = new Random();
        for (int i = 0; i < mines; i++) {
            int col = random.nextInt(width);
            int row = random.nextInt(height);
            playingField.get(col)[row] = Common.MINE;
            addCellsNearMine(col, row, playingField, width, height);
        }
    }

    private void addCellsNearMine(int col, int row, ArrayList<Integer[]> playingField, int width, int height) {
        int lastColNumber = width - 1;
        int lastRowNumber = height - 1;
        if (row == FIRST_ROW) {
            addCellsNearUpperMine(col, row, playingField, lastColNumber);
            return;
        }
        if (row == lastRowNumber) {
            addCellsNearLowerMine(col, row, playingField, lastColNumber);
            return;
        }
        if (col == FIRST_COL) {
            addCellsNearLeftMine(col, row, playingField);
            return;
        }
        if (col == lastColNumber) {
            addCellsNearRightMine(col, row, playingField);
            return;
        }

        calculateCallValue(col, row + 1, playingField);
        calculateCallValue(col, row - 1, playingField);

        calculateCallValue(col + 1, row, playingField);
        calculateCallValue(col + 1, row - 1, playingField);
        calculateCallValue(col + 1, row + 1, playingField);

        calculateCallValue(col - 1, row, playingField);
        calculateCallValue(col - 1, row + 1, playingField);
        calculateCallValue(col - 1, row - 1, playingField);
    }

    void addCellsNearUpperMine(int col, int row, ArrayList<Integer[]> playingField, int lastColNumber) {
        calculateCallValue(col, row + 1, playingField);
        if (col == FIRST_COL) {

            calculateCallValue(col + 1, row, playingField);
            calculateCallValue(col + 1, row + 1, playingField);

        } else if (col == lastColNumber) {
            calculateCallValue(col - 1, row, playingField);
            calculateCallValue(col - 1, row + 1, playingField);
        } else {
            calculateCallValue(col + 1, row, playingField);
            calculateCallValue(col - 1, row, playingField);
            calculateCallValue(col + 1, row + 1, playingField);
            calculateCallValue(col - 1, row + 1, playingField);
        }
    }

    private void addCellsNearLowerMine(int col, int row, ArrayList<Integer[]> playingField, int lastColNumber) {
        calculateCallValue(col, row - 1, playingField);
        if (col == FIRST_COL) {
            calculateCallValue(col + 1, row, playingField);
            calculateCallValue(col + 1, row - 1, playingField);

        } else if (col == lastColNumber) {
            calculateCallValue(col - 1, row, playingField);
            calculateCallValue(col - 1, row - 1, playingField);
        } else {
            calculateCallValue(col + 1, row, playingField);
            calculateCallValue(col - 1, row, playingField);
            calculateCallValue(col + 1, row - 1, playingField);
            calculateCallValue(col - 1, row - 1, playingField);
        }
    }

    private void addCellsNearLeftMine(int col, int row, ArrayList<Integer[]> playingField) {
        calculateCallValue(col, row - 1, playingField);
        calculateCallValue(col, row + 1, playingField);
        calculateCallValue(col + 1, row - 1, playingField);
        calculateCallValue(col + 1, row, playingField);
        calculateCallValue(col + 1, row - 1, playingField);
    }

    private void addCellsNearRightMine(int col, int row, ArrayList<Integer[]> playingField) {
        calculateCallValue(col, row - 1, playingField);
        calculateCallValue(col, row + 1, playingField);
        calculateCallValue(col - 1, row - 1, playingField);
        calculateCallValue(col - 1, row, playingField);
        calculateCallValue(col - 1, row - 1, playingField);
    }

    private void calculateCallValue(int col, int row, List<Integer[]> playingField) {
        int valueCall = playingField.get(col)[row];
        if (valueCall != -1) {
            playingField.get(col)[row]++;
        }
    }

}
