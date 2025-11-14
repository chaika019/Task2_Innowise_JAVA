package by.chaika19.task2;

import by.chaika19.task2.composite.TextComponent;
import by.chaika19.task2.composite.TextComponentType;
import by.chaika19.task2.composite.TextLeaf;
import by.chaika19.task2.exception.TextException;
import by.chaika19.task2.parser.*;
import by.chaika19.task2.reader.TextReader;
import by.chaika19.task2.service.TextService;
import by.chaika19.task2.service.impl.TextServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TextServiceImplIntegrationTest {

    private TextService service;
    private TextComponent textRoot;
    private static final String RESOURCE_FILE_NAME = "text.txt";

    @BeforeEach
    void setUp() throws Exception {
        service = new TextServiceImpl();
        TextReader reader = new TextReader();

        URL resource = getClass().getClassLoader().getResource(RESOURCE_FILE_NAME);
        assertNotNull(resource, "Resource file 'text.txt' not found in classpath.");

        String filePath = Path.of(resource.toURI()).toString();

        String content = reader.readText(filePath);

        AbstractTextParser lexemeParser = new LexemeParser();
        AbstractTextParser sentenceParser = new SentenceParser(lexemeParser);
        AbstractTextParser paragraphParser = new ParagraphParser(sentenceParser);
        AbstractTextParser textParser = new TextParser(paragraphParser);

        textRoot = textParser.parse(content);
        assertNotNull(textRoot);
    }

    @Test
    void findMaxSentencesWithSameWords() throws TextException {
        int expectedMax = 5;

        int result = service.findMaxSentencesWithSameWords(textRoot);

        assertEquals(expectedMax, result, "Max count of sentences with same words is incorrect.");
    }

    @Test
    void sortSentencesByLexemeCount_IsSortedAscending() throws TextException {
        List<TextComponent> sorted = service.sortSentencesByLexemeCount(textRoot);

        int totalSentenceCount = getSentenceCount(textRoot);
        assertEquals(totalSentenceCount, sorted.size(), "Sorted list size must equal total sentence count.");

        for (int i = 1; i < sorted.size(); i++) {
            int currentLexemeCount = sorted.get(i).getChildren().size();
            int previousLexemeCount = sorted.get(i - 1).getChildren().size();

            assertTrue(currentLexemeCount >= previousLexemeCount,
                    "Sentences are not sorted by lexeme count in ascending order.");
        }
    }

    @Test
    void swapFirstAndLastLexemeInSentences_SwapOccurredAndTypeIsCorrect() throws TextException {
        TextComponent resultRoot = service.swapFirstAndLastLexemeInSentences(textRoot);

        assertEquals(TextComponentType.TEXT, resultRoot.getType());

        TextComponent firstParagraph = textRoot.getChildren().get(0);
        TextComponent firstSentence = firstParagraph.getChildren().get(0);

        String originalFirstLexemeWord = ((TextLeaf) firstSentence.getChildren().get(0).getChildren().get(0)).getTextContent();
        String originalLastLexemeWord = ((TextLeaf) firstSentence.getChildren().get(firstSentence.getChildren().size() - 1).getChildren().get(0)).getTextContent();

        TextComponent resultFirstParagraph = resultRoot.getChildren().get(0);
        TextComponent resultFirstSentence = resultFirstParagraph.getChildren().get(0);

        String newFirstLexemeWord = ((TextLeaf) resultFirstSentence.getChildren().get(0).getChildren().get(0)).getTextContent();
        String newLastLexemeWord = ((TextLeaf) resultFirstSentence.getChildren().get(resultFirstSentence.getChildren().size() - 1).getChildren().get(0)).getTextContent();

        assertEquals(originalLastLexemeWord, newFirstLexemeWord, "First lexeme was not swapped to the last position's value.");
        assertEquals(originalFirstLexemeWord, newLastLexemeWord, "Last lexeme was not swapped to the first position's value.");
    }

    private int getSentenceCount(TextComponent text) {
        int count = 0;
        for (TextComponent p : text.getChildren()) {
            count += p.getChildren().size();
        }
        return count;
    }
}