package org.abner.samples.musicgathering;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public final class TestUtils {

    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private TestUtils() {
    }

    public static String getResourceAsString(String file) {
        var inputStream = TestUtils.class.getClassLoader().getResourceAsStream(file);
        return new BufferedReader(new InputStreamReader(inputStream)).lines()
                .parallel().collect(Collectors.joining("\n"));
    }

    public static <T> T getResourceJsonAsObject(String file, Class<T> clazz) {
        var jsonString = getResourceAsString(file);
        try {
            return OBJECT_MAPPER.readValue(jsonString, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
