package by.chaika19.task2.parser;

import by.chaika19.task2.composite.TextComponent;
import by.chaika19.task2.composite.TextComponentType;
import by.chaika19.task2.composite.TextComposite;

public class ParagraphParser extends AbstractTextParser {
    public ParagraphParser(AbstractTextParser nextParser) {
        super(nextParser);
    }

    @Override
    public TextComponent parse(String paragraph) {
        TextComponent paragraphComponent = new TextComposite(TextComponentType.PARAGRAPH);
        String[] sentences = paragraph.split(RegexConstant.REGEX_SENTENCE);

        for (String sentence : sentences) {
            if (!sentence.isBlank() && nextParser != null) {
                TextComponent sentenceComponent = nextParser.parse(sentence.trim());
                paragraphComponent.add(sentenceComponent);
            }
        }
        return paragraphComponent;
    }
}

