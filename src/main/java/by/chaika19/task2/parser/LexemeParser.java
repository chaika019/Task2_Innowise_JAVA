package by.chaika19.task2.parser;

import by.chaika19.task2.composite.TextComponent;
import by.chaika19.task2.composite.TextComponentType;
import by.chaika19.task2.composite.TextComposite;
import by.chaika19.task2.composite.TextLeaf;
import by.chaika19.task2.exception.TextException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LexemeParser extends AbstractTextParser {
    private static final Logger logger = LogManager.getLogger();
    private static final Pattern WORD_PUNCTUATION_PATTERN = Pattern.compile(
            "(" + RegexConstant.REGEX_WORD + "|" + RegexConstant.REGEX_PUNCTUATION + ")"
    );

    public LexemeParser() {
        super(null);
    }

    @Override
    public TextComponent parse(String lexeme) throws TextException {
        logger.info("Starting lexeme parsing for: {}", lexeme);

        TextComponent  lexemeComponent = new TextComposite(TextComponentType.LEXEME);
        Matcher matcher = WORD_PUNCTUATION_PATTERN.matcher(lexeme);

        while (matcher.find()) {
            String componentContent = matcher.group();
            TextComponentType type;

            if (componentContent.matches(RegexConstant.REGEX_PUNCTUATION)) {
                type = TextComponentType.PUNCTUATION;
            } else {
                type = TextComponentType.WORD;
            }
            lexemeComponent.add(new TextLeaf(componentContent, type));
        }
        logger.info("Lexeme parsing completed. Total parts found: {}", lexemeComponent.getChildren().size());
        return lexemeComponent;
    }
}
