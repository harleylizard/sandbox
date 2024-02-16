package com.harleylizard.sandbox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public final class Resources {

    private Resources() {}

    public static InputStream get(String path) throws IOException {
        return Resources.class.getClassLoader().getResource(path).openStream();
    }

    public static String readString(String path) throws IOException {
        try (var reader = new BufferedReader(new InputStreamReader(get(path)))) {
            var builder = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }

            return builder.toString();
        }
    }
}
