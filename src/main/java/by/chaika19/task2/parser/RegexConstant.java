package by.chaika19.task2.parser;

public class RegexConstant {
    static final String REGEX_PARAGRAPH = "(\\r?\\n){2,}";
    static final String REGEX_SENTENCE = "(?<=[.?!])\\s+";
    static final String REGEX_LEXEME = "\\s+";
    static final String REGEX_PUNCTUATION = "[\\p{Punct}&&[^_']]+";
    static final String REGEX_WORD = "[A-Za-z0-9']+";
}
