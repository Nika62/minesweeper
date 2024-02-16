package ru.example.minesweeper.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "playing_fields")
public class LineField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "line_id")
    private long lineId;
    private String gameInfo;
    @Column
    private int col;
    @Column
    private int height;
    @Column
    private int letter;

    public LineField(String gameInfo, int col, int height, int letter) {
        this.gameInfo = gameInfo;
        this.col = col;
        this.height = height;
        this.letter = letter;
    }

}
