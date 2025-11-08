package by.chaika19.task2.parser;

import by.chaika19.task2.composite.TextComponent;
import by.chaika19.task2.exception.TextException;

public abstract class AbstractTextParser {
    protected AbstractTextParser nextParser;

    public AbstractTextParser(AbstractTextParser nextParser) {
        this.nextParser = nextParser;
    }

    public abstract TextComponent parse(String text) throws TextException;
}
