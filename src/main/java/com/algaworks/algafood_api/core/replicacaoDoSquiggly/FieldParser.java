package com.algaworks.algafood_api.core.replicacaoDoSquiggly;

import java.util.ArrayList;
import java.util.List;

public class FieldParser {

    public static List<String> parse(String campos) {
        List<String> result = new ArrayList<>();
        if (campos == null || campos.isBlank()) return result;

        parseRecursive("", campos.trim(), result);
        return result;
    }

    private static void parseRecursive(String prefix, String input, List<String> result) {
        int i = 0;
        StringBuilder token = new StringBuilder();

        while (i < input.length()) {
            char c = input.charAt(i);

            if (c == '[') {
                int closeIndex = findClosingBracket(input, i);
                String inner = input.substring(i + 1, closeIndex);
                String newPrefix = prefix.isEmpty() ? token.toString() : prefix + "." + token;

                // Separa os campos internos por vírgula e chama recursivamente
                for (String part : inner.split(",")) {
                    parseRecursive(newPrefix, part.trim(), result);
                }

                token.setLength(0);
                i = closeIndex;
            } else if (c == ',') {
                if (token.length() > 0) {
                    String full = prefix.isEmpty() ? token.toString() : prefix + "." + token;
                    result.add(full.trim());
                    token.setLength(0);
                }
            } else {
                token.append(c);
            }
            i++;
        }

        if (token.length() > 0) {
            String full = prefix.isEmpty() ? token.toString() : prefix + "." + token;
            result.add(full.trim());
        }
    }

    private static int findClosingBracket(String input, int openIndex) {
        int depth = 0;
        for (int i = openIndex; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == '[') depth++;
            else if (c == ']') depth--;
            if (depth == 0) return i;
        }
        throw new IllegalArgumentException("Colchete não fechado em: " + input);
    }
}
