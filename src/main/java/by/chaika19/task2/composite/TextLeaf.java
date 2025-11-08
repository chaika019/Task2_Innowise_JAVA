package by.chaika19.task2.composite;

import by.chaika19.task2.exception.TextException;

import java.util.Collections;
import java.util.List;

public class TextLeaf implements TextComponent {
    private final String textContent;
    private final TextComponentType type;

    public TextLeaf(String textContent, TextComponentType type) throws TextException {
        if (type != TextComponentType.WORD && type != TextComponentType.PUNCTUATION) {
            throw new TextException("TextLeaf cannot be created with component type: "
                    + type
                    + ". Only WORD or PUNCTUATION types are allowed.");
        }
        this.textContent = textContent;
        this.type = type;
    }

    @Override
    public void add(TextComponent component) {
        throw new UnsupportedOperationException("Cannot add component to a TextLeaf");
    }

    @Override
    public void remove(TextComponent component) {
        throw new UnsupportedOperationException("Cannot remove component from a TextLeaf");
    }

    @Override
    public List<TextComponent> getChildren() {
        return Collections.emptyList();
    }

    @Override
    public TextComponentType getType() {
        return type;
    }

    @Override
    public String compose() {
        return textContent;
    }
}
