package by.chaika19.task2.parser;

import by.chaika19.task2.composite.TextComponent;
import by.chaika19.task2.composite.TextComponentType;
import by.chaika19.task2.composite.TextComposite;
import by.chaika19.task2.exception.TextException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class TextParser extends AbstractTextParser {
    private static final Logger logger = LogManager.getLogger();

    public TextParser(AbstractTextParser nextParser) {
        super(nextParser);
    }

    @Override
    public TextComponent parse(String text) throws TextException {
        logger.info("Starting text parsing. Splitting text into paragraphs.");
        TextComponent textComponent = new TextComposite(TextComponentType.TEXT);
        String[] paragraphs = text.split(RegexConstant.REGEX_PARAGRAPH);
        int paragraphCount = 0;

        AbstractTextParser next = getNextParser();

        for (String paragraph : paragraphs) {
            String trimmedParagraph = paragraph.strip();

            if (!trimmedParagraph.isEmpty()) {
                TextComponent paragraphComponent = next.parse(trimmedParagraph);
                textComponent.add(paragraphComponent);
                paragraphCount++;
            }
        }

        logger.info("Text parsing completed. Total paragraphs processed: {}", paragraphCount);
        return textComponent;
    }
}