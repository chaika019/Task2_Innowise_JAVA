package by.chaika19.task2.parser;

import by.chaika19.task2.composite.TextComponent;
import by.chaika19.task2.composite.TextComponentType;
import by.chaika19.task2.composite.TextComposite;

public class SentenceParser extends AbstractTextParser {
    public SentenceParser(AbstractTextParser nextParser) {
        super(nextParser);
    }

    @Override
    public TextComponent parse(String sentence) {
        TextComponent sentenceComponent = new TextComposite(TextComponentType.SENTENCE);
        String[] lexemes = sentence.split(RegexConstant.REGEX_LEXEME);

        for (String lexeme : lexemes) {
            if(!lexeme.isBlank() && nextParser != null) {
                TextComponent lexemeComponent = nextParser.parse(lexeme.trim());
                sentenceComponent.add(lexemeComponent);
            }
        }

        return sentenceComponent;
    }
}
