package by.chaika19.task2.parser;

import by.chaika19.task2.composite.TextComponent;
import by.chaika19.task2.composite.TextComponentType;
import by.chaika19.task2.composite.TextComposite;
import by.chaika19.task2.exception.TextException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SentenceParser extends AbstractTextParser {
    private static final Logger logger = LogManager.getLogger();

    public SentenceParser(AbstractTextParser nextParser) {
        super(nextParser);
    }

    @Override
    public TextComponent parse(String sentence) throws TextException {
        logger.info("Starting sentence parsing. Splitting into lexemes.");

        TextComponent sentenceComponent = new TextComposite(TextComponentType.SENTENCE);
        String[] lexemes = sentence.split(RegexConstant.REGEX_LEXEME);

        AbstractTextParser nextParser = getNextParser();
        int lexemeCount = 0;
        for (String lexeme : lexemes) {
            if (!lexeme.isEmpty()) {
                TextComponent lexemeComponent = nextParser.parse(lexeme);
                sentenceComponent.add(lexemeComponent);
                lexemeCount++;
            }
        }
        logger.info("Sentence parsing completed. Total lexemes processed: {}", lexemeCount);
        return sentenceComponent;
    }
}
