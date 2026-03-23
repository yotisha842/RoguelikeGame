package org.example.datalayer.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;

import java.io.File;
import java.io.IOException;

public class JsonManager {
    private final ObjectMapper objectMapper;
    private final String saveDirectory;

    public JsonManager(String saveDirectory){
        this.saveDirectory = saveDirectory;
        this.objectMapper = new ObjectMapper();

        PolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder()
                .allowIfBaseType(Object.class)
                .allowIfSubType("org.example.datalayer.dto")
                .allowIfSubType("java.util.ArrayList")
                .allowIfSubType("java.util.HashMap")
                .allowIfSubType("java.util.LinkedHashMap")
                .allowIfSubType("java.util.Collections")
                .build();

        objectMapper.activateDefaultTyping(ptv, ObjectMapper.DefaultTyping.NON_FINAL);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        new File(saveDirectory).mkdirs();
    }

    public <T> void saveToFile(String filename, T data) throws IOException {
        File file = new File(saveDirectory, filename);
        objectMapper.writeValue(file, data);
    }

    public <T> T loadFromFile(String filename, Class<T> valueType) throws IOException {
        File file = new File(saveDirectory, filename);
        return objectMapper.readValue(file, valueType);
    }

    public boolean fileExists(String filename) {
        File file = new File(saveDirectory, filename);
        return file.exists() && file.isFile();
    }
}