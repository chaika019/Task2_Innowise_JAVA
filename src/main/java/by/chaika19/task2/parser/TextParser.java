package by.chaika19.task2.parser;

import by.chaika19.task2.composite.TextComponent;
import by.chaika19.task2.composite.TextComponentType;
import by.chaika19.task2.composite.TextComposite;

public class TextParser extends AbstractTextParser {
    public TextParser(AbstractTextParser nextParser) {
        super(nextParser);
    }

    @Override
    public TextComponent parse(String text) {
        TextComponent textComponent = new TextComposite(TextComponentType.TEXT);
        String[] paragraphs = text.split(RegexConstant.REGEX_PARAGRAPH);

        for (String paragraph : paragraphs) {
            if (!paragraph.isBlank() && nextParser != null) {
                TextComponent paragraphComponent = nextParser.parse(paragraph.trim());
                textComponent.add(paragraphComponent);
            }
        }
        return textComponent;
    }
}
