package ru.example.minesweeper.common;

public enum CellValue {
    BLANC(" "),
    ZERO("0"),
    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    ELEVEN("8"),
    MINE("M"),
    EXPLODED_MINE("X");

    private final String title;

    CellValue(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
