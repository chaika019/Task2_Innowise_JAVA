package by.chaika19.task2.service;

import by.chaika19.task2.composite.TextComponent;
import by.chaika19.task2.exception.TextException;

import java.util.List;

public interface TextService {
    int findMaxSentencesWithSameWords(TextComponent textRoot) throws TextException;
    List<TextComponent> sortSentencesByLexemeCount(TextComponent textRoot) throws TextException;
    public List<TextComponent> swappedFirstAndLastLexeme(TextComponent textComponent) throws TextException;
}
