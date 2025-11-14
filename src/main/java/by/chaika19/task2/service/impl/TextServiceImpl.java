package by.chaika19.task2.service.impl;

import by.chaika19.task2.composite.TextComponent;
import by.chaika19.task2.composite.TextComponentType;
import by.chaika19.task2.composite.TextComposite;
import by.chaika19.task2.composite.TextLeaf;
import by.chaika19.task2.exception.TextException;
import by.chaika19.task2.parser.DelimiterConstants;
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

        List<TextComponent> allSentencesCopy = new ArrayList<>(extractSentences(textRoot));

        allSentencesCopy.sort(Comparator.comparingInt(sentence -> sentence.getChildren().size()));

        logger.info("Task 2 completed. Sorted {} sentences by lexeme count.", allSentencesCopy.size());
        return allSentencesCopy;
    }

    @Override
    public TextComponent swapFirstAndLastLexemeInSentences(TextComponent textComponent) throws TextException {
        logger.info("Starting Task 3: Swapping first and last lexeme");

        TextComposite result = new TextComposite(TextComponentType.TEXT);

        if (textComponent.getType() != TextComponentType.TEXT) {
            throw new TextException("Root component must be of type TEXT");
        }

        for (TextComponent paragraph : textComponent.getChildren()) {
            TextComposite newParagraph = new TextComposite(TextComponentType.PARAGRAPH);
            for (TextComponent sentence : paragraph.getChildren()) {
                TextComponent newSentence = swapLexemesInSentence(sentence);
                newParagraph.add(newSentence);
            }
            result.add(newParagraph);
        }

        logger.info("Task 3 completed. Swapped first and last lexeme");
        return result;
    }

    private TextComponent swapLexemesInSentence(TextComponent sentence) {
        List<TextComponent> originalLexemes = sentence.getChildren();
        List<TextComponent> newLexemes = new ArrayList<>(originalLexemes);

        int size = newLexemes.size();
        if (size >= 2) {
            Collections.swap(newLexemes, 0, size - 1);
        }

        TextComposite newSentence = new TextComposite(TextComponentType.SENTENCE);

        newLexemes.forEach(newSentence::add);

        return newSentence;
    }

    @Override
    public String compose(TextComponent textRoot) {
        logger.info("Starting text composition (restoration).");
        String result = composeComponent(textRoot);
        logger.info("Text composition completed.");
        return result;
    }

    private String composeComponent(TextComponent component) {
        if (component instanceof TextLeaf) {
            return ((TextLeaf) component).getTextContent();
        }

        TextComponentType type = component.getType();

        String delimiter = switch (type) {
            case TEXT -> DelimiterConstants.TEXT_DELIMITER;
            case PARAGRAPH -> DelimiterConstants.PARAGRAPH_DELIMITER;
            case SENTENCE -> DelimiterConstants.SENTENCE_DELIMITER;
            case LEXEME -> DelimiterConstants.LEXEME_DELIMITER;
            default -> "";
        };

        return component.getChildren().stream()
                .map(this::composeComponent)
                .collect(Collectors.joining(delimiter));
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
            TextLeaf wordLeaf = (TextLeaf) component;
            words.add(wordLeaf.getTextContent());
            return words;
        }

        for(TextComponent child : component.getChildren()) {
            words.addAll(extractWords(child));
        }

        return words;
    }
}