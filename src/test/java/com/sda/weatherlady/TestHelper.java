package com.sda.weatherlady;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Objects;
import org.apache.commons.io.IOUtils;

public final class TestHelper {

    private TestHelper() {}

    public static String getTestDataFromFile(String filename, Class clazz) {
        try {
            return IOUtils.toString(Objects.requireNonNull(clazz.getClassLoader().getResourceAsStream(filename)));
        } catch (IOException e) {
            return "";
        }
    }

    public static <T> T getTestDataFromFile(String filename, Class clazz, Class<T> toObject) {
        try {
            String json = IOUtils.toString(Objects.requireNonNull(clazz.getClassLoader().getResourceAsStream(filename)));
            return new ObjectMapper().readValue(json, toObject);
        } catch (IOException e) {
            return null;
        }
    }

}
