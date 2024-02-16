package ru.example.minesweeper.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.example.minesweeper.model.LineField;
import ru.example.minesweeper.repository.FieldRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DaoFieldService {

    private final FieldRepository fieldRepository;


    public List<LineField> getPlayingField(String gameId) {
        return fieldRepository.findAllByGameId(gameId);
    }

    public void saveField(LineField lineField) {
        fieldRepository.save(lineField);
    }
}
