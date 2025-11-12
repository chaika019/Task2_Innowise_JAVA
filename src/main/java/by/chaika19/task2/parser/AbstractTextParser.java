package by.chaika19.task2.parser;

import by.chaika19.task2.composite.TextComponent;
import by.chaika19.task2.exception.TextException;

public abstract class AbstractTextParser {
    private AbstractTextParser nextParser;

     public AbstractTextParser(AbstractTextParser nextParser) {
        this.nextParser = nextParser;
    }

    public void setNextParser(AbstractTextParser nextParser) {
        this.nextParser = nextParser;
    }

    public AbstractTextParser getNextParser() {
        return nextParser;
    }

    public abstract TextComponent parse(String text) throws TextException;
}