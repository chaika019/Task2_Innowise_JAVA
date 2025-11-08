package by.chaika19.task2.parser;

import by.chaika19.task2.composite.TextComponent;
import by.chaika19.task2.composite.TextComponentType;
import by.chaika19.task2.composite.TextComposite;
import by.chaika19.task2.composite.TextLeaf;
import by.chaika19.task2.exception.TextException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LexemeParser extends AbstractTextParser {
    public LexemeParser() {
        super(null);
    }

    @Override
    public TextComponent parse(String lexeme) throws TextException {
        TextComponent  lexemeComponent = new TextComposite(TextComponentType.LEXEME);
        String wordOrPunctuationRegex = "(" + RegexConstant.REGEX_WORD + "|" + RegexConstant.REGEX_PUNCTUATION + ")";

        Pattern pattern = Pattern.compile(wordOrPunctuationRegex);
        Matcher matcher = pattern.matcher(lexeme);

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

        return lexemeComponent;
    }
}
