package by.chaika19.task2.parser;

import by.chaika19.task2.composite.TextComponent;
import by.chaika19.task2.composite.TextComponentType;
import by.chaika19.task2.composite.TextComposite;
import by.chaika19.task2.exception.TextException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class ParagraphParser extends AbstractTextParser {
    private static final Logger logger = LogManager.getLogger();

    public ParagraphParser(AbstractTextParser nextParser) {
        super(nextParser);
    }

    @Override
    public TextComponent parse(String paragraph) throws TextException {
        logger.info("Starting paragraph parsing. Splitting into sentences");

        TextComponent paragraphComponent = new TextComposite(TextComponentType.PARAGRAPH);
        String[] sentences = paragraph.split(RegexConstant.REGEX_SENTENCE);

        AbstractTextParser nextParser = getNextParser();
        int sentenceCount = 0;

        for (String sentence : sentences) {
            String trimmedSentence = sentence.strip();

            if (!trimmedSentence.isEmpty()) {
                TextComponent sentenceComponent = nextParser.parse(trimmedSentence);
                paragraphComponent.add(sentenceComponent);
                sentenceCount++;
            }
        }
        logger.info("Paragraph parsing completed. Total sentences processed: {}", sentenceCount);
        return paragraphComponent;
    }
}