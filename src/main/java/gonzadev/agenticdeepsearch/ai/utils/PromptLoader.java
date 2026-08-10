package gonzadev.agenticdeepsearch.ai.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class PromptLoader {

    public static String loadPrompt(String filename) {
        try (InputStream is = PromptLoader.class.getClassLoader().getResourceAsStream("prompts/" + filename)) {
            if (is == null) {
                throw new IllegalArgumentException("Archivo no encontrado: " + filename);
            }
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el prompt: " + filename, e);
        }
    }
}
