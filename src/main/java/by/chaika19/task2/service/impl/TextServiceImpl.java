package by.chaika19.task2.service.impl;

import by.chaika19.task2.composite.TextComponent;
import by.chaika19.task2.composite.TextComponentType;
import by.chaika19.task2.exception.TextException;
import by.chaika19.task2.service.TextService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

public class TextServiceImpl implements TextService {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public int findMaxSentencesWithSameWords(TextComponent textRoot) throws TextException {
        logger.info("Task 1. Finding max count of sentences with same words");

        List<TextComponent> allSentences = extractSentences(textRoot);

        Map<String, List<TextComponent>> sentencesGroupedByWord = new HashMap<>();

        for (TextComponent sentence : allSentences) {
            Set<String> uniqueWords = extractWords(sentence).stream()
                    .map(String::toLowerCase)
                    .collect(Collectors.toSet());

            for (String word : uniqueWords) {
                sentencesGroupedByWord
                        .computeIfAbsent(word, k -> new ArrayList<>())
                        .add(sentence);
            }
        }

        int max = sentencesGroupedByWord.values().stream()
                .mapToInt(List::size)
                .max()
                .orElse(0);

        logger.info("Task 1 completed. Max sentences found: {}", max);
        return max;
    }

    @Override
    public List<TextComponent> sortSentencesByLexemeCount(TextComponent textRoot) throws TextException {
        logger.info("Starting Task 2: Sorting sentences by lexeme count.");

        List<TextComponent> allSentences = extractSentences(textRoot);

        allSentences.sort(Comparator.comparingInt(sentence -> sentence.getChildren().size()));

        logger.info("Task 2 completed. Sorted {} sentences by lexeme count.", allSentences.size());
        return allSentences;
    }

    public List<TextComponent> swappedFirstAndLastLexeme(TextComponent textComponent) throws TextException {
        logger.info("Starting Task 3: Swapping first and last lexeme");

        List<TextComponent> allSentences = extractSentences(textComponent);

        for (TextComponent sentence : allSentences) {
            List<TextComponent> lexemes = sentence.getChildren();
            int size = lexemes.size();
            if (lexemes.size() >= 2) {
                Collections.swap(lexemes, 0, size - 1);
            }
        }

        logger.info("Task 3 completed. Swapped first and last lexeme");
        return allSentences;
    }

    private List<TextComponent> extractSentences(TextComponent text) throws TextException {
        if (text.getType() != TextComponentType.TEXT) {
            throw new TextException("Root component must be of type TEXT");
        }

        return text.getChildren().stream()
                .flatMap(paragraph -> paragraph.getChildren().stream())
                .collect(Collectors.toList());
    }

    private Set<String> extractWords(TextComponent component) {
        Set<String> words = new HashSet<>();

        if(component.getType() == TextComponentType.WORD) {
            words.add(component.compose());
            return words;
        }

        for(TextComponent child : component.getChildren()) {
            words.addAll(extractWords(child));
        }

        return words;
    }
}