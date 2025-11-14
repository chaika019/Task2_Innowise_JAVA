package by.chaika19.task2.reader;

import by.chaika19.task2.exception.TextException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TextReader {
    private static final Logger logger = LogManager.getLogger(TextReader.class);

    public String readText(String filePath) throws TextException {
        Path path = Paths.get(filePath);
        logger.info("Attempting to read file from path: {}", path);

        try {
            String text = Files.readString(path);
            logger.info("Successfully read {} characters from file.", text.length());
            return text;
        } catch (IOException e) {
            logger.error("Error reading file at path {}, {}", filePath, e.getMessage(), e);
            throw new TextException("Failed to read file from path: " + path, e);
        }
    }
}
