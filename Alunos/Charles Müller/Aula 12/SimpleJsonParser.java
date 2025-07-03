import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Parser JSON simples para extrair valores específicos
 * sem usar bibliotecas externas
 *
 * @author Charles Müller
 * @version 1.0
 */
public class SimpleJsonParser {
    private final String json;

    public SimpleJsonParser(String json) {
        this.json = json != null ? json : "";
    }

    /**
     * Verifica se uma chave existe no JSON
     */
    public boolean hasKey(String key) {
        String pattern = "\"" + escapeKey(key) + "\"\\s*:";
        return Pattern.compile(pattern).matcher(json).find();
    }

    /**
     * Obtém um valor string do JSON
     */
    public String getString(String key, String defaultValue) {
        try {
            String value = extractValue(key);
            if (value == null)
                return defaultValue;

            // Remove aspas e escape characters
            if (value.startsWith("\"") && value.endsWith("\"")) {
                value = value.substring(1, value.length() - 1);
            }

            return value.replace("\\\"", "\"").replace("\\\\", "\\");
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * Obtém um valor double do JSON
     */
    public double getDouble(String key, double defaultValue) {
        try {
            String value = extractValue(key);
            if (value == null || value.equals("null"))
                return defaultValue;

            // Remove aspas se existirem
            if (value.startsWith("\"") && value.endsWith("\"")) {
                value = value.substring(1, value.length() - 1);
            }

            return Double.parseDouble(value);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * Obtém o tamanho de um array JSON
     */
    public int getArraySize(String arrayKey) {
        try {
            String pattern = "\"" + escapeKey(arrayKey) + "\"\\s*:\\s*\\[";
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(json);

            if (!m.find())
                return 0;

            int start = m.end() - 1; // Posição do '['
            int count = 0;
            int level = 0;
            boolean inString = false;
            boolean escaped = false;

            for (int i = start; i < json.length(); i++) {
                char c = json.charAt(i);

                if (escaped) {
                    escaped = false;
                    continue;
                }

                if (c == '\\') {
                    escaped = true;
                    continue;
                }

                if (c == '"') {
                    inString = !inString;
                    continue;
                }

                if (inString)
                    continue;

                if (c == '[' || c == '{') {
                    level++;
                } else if (c == ']' || c == '}') {
                    level--;
                    if (level == 0 && c == ']') {
                        break; // Fim do array
                    }
                } else if (c == ',' && level == 1) {
                    count++;
                }
            }

            // Se encontrou elementos, adiciona 1 (pois contamos vírgulas)
            return level == 0 && count >= 0 ? count + 1 : 0;

        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Extrai o valor bruto de uma chave (pode incluir notação de array)
     */
    private String extractValue(String key) {
        try {
            // Suporte para notação de array como "days[0].temp"
            if (key.contains("[") && key.contains("]")) {
                return extractArrayValue(key);
            }

            // Chave simples
            String pattern = "\"" + escapeKey(key) + "\"\\s*:\\s*";
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(json);

            if (!m.find())
                return null;

            int start = m.end();
            return extractJsonValue(start);

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Extrai valor de array usando notação como "days[0].temp"
     */
    private String extractArrayValue(String key) {
        try {
            String[] parts = key.split("\\.");
            String arrayPart = parts[0]; // ex: "days[0]"
            String valuePart = parts.length > 1 ? parts[1] : null; // ex: "temp"

            // Extrair nome do array e índice
            int bracketStart = arrayPart.indexOf('[');
            int bracketEnd = arrayPart.indexOf(']');

            if (bracketStart == -1 || bracketEnd == -1)
                return null;

            String arrayName = arrayPart.substring(0, bracketStart);
            int arrayIndex = Integer.parseInt(arrayPart.substring(bracketStart + 1, bracketEnd));

            // Encontrar o array
            String arrayPattern = "\"" + escapeKey(arrayName) + "\"\\s*:\\s*\\[";
            Pattern p = Pattern.compile(arrayPattern);
            Matcher m = p.matcher(json);

            if (!m.find())
                return null;

            // Encontrar o elemento do array no índice especificado
            int arrayStart = m.end() - 1; // Posição do '['
            String arrayElement = getArrayElement(arrayStart, arrayIndex);

            if (arrayElement == null)
                return null;

            // Se não há parte de valor, retorna o elemento inteiro
            if (valuePart == null)
                return arrayElement;

            // Buscar o valor dentro do elemento
            SimpleJsonParser elementParser = new SimpleJsonParser(arrayElement);
            return elementParser.extractValue(valuePart);

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Obtém um elemento específico de um array JSON
     */
    private String getArrayElement(int arrayStart, int targetIndex) {
        try {
            int currentIndex = 0;
            int level = 0;
            boolean inString = false;
            boolean escaped = false;
            int elementStart = -1;

            for (int i = arrayStart; i < json.length(); i++) {
                char c = json.charAt(i);

                if (escaped) {
                    escaped = false;
                    continue;
                }

                if (c == '\\') {
                    escaped = true;
                    continue;
                }

                if (c == '"') {
                    inString = !inString;
                    continue;
                }

                if (inString)
                    continue;

                if (c == '[') {
                    level++;
                    if (level == 1 && elementStart == -1) {
                        elementStart = i + 1; // Início do primeiro elemento
                    }
                } else if (c == ']') {
                    level--;
                    if (level == 0) {
                        // Fim do array
                        if (currentIndex == targetIndex && elementStart != -1) {
                            return json.substring(elementStart, i).trim();
                        }
                        break;
                    }
                } else if (c == '{' || c == '[') {
                    level++;
                } else if (c == '}' || c == ']') {
                    level--;
                } else if (c == ',' && level == 1) {
                    // Fim do elemento atual
                    if (currentIndex == targetIndex && elementStart != -1) {
                        return json.substring(elementStart, i).trim();
                    }
                    currentIndex++;
                    elementStart = i + 1;
                }
            }

            return null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Extrai um valor JSON a partir de uma posição
     */
    private String extractJsonValue(int start) {
        if (start >= json.length())
            return null;

        // Pular espaços em branco
        while (start < json.length() && Character.isWhitespace(json.charAt(start))) {
            start++;
        }

        if (start >= json.length())
            return null;

        char firstChar = json.charAt(start);

        if (firstChar == '"') {
            // String value
            return extractStringValue(start);
        } else if (firstChar == '{') {
            // Object value
            return extractObjectValue(start);
        } else if (firstChar == '[') {
            // Array value
            return extractArrayValue(start);
        } else if (firstChar == 't' || firstChar == 'f') {
            // Boolean value
            return extractBooleanValue(start);
        } else if (firstChar == 'n') {
            // Null value
            return extractNullValue(start);
        } else {
            // Number value
            return extractNumberValue(start);
        }
    }

    private String extractStringValue(int start) {
        StringBuilder sb = new StringBuilder();
        boolean escaped = false;

        for (int i = start; i < json.length(); i++) {
            char c = json.charAt(i);

            if (escaped) {
                sb.append(c);
                escaped = false;
            } else if (c == '\\') {
                escaped = true;
                sb.append(c);
            } else if (c == '"') {
                sb.append(c);
                if (i > start) {
                    return sb.toString();
                }
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    private String extractObjectValue(int start) {
        int level = 0;
        boolean inString = false;
        boolean escaped = false;

        for (int i = start; i < json.length(); i++) {
            char c = json.charAt(i);

            if (escaped) {
                escaped = false;
                continue;
            }

            if (c == '\\') {
                escaped = true;
                continue;
            }

            if (c == '"') {
                inString = !inString;
                continue;
            }

            if (inString)
                continue;

            if (c == '{') {
                level++;
            } else if (c == '}') {
                level--;
                if (level == 0) {
                    return json.substring(start, i + 1);
                }
            }
        }

        return null;
    }

    private String extractArrayValue(int start) {
        int level = 0;
        boolean inString = false;
        boolean escaped = false;

        for (int i = start; i < json.length(); i++) {
            char c = json.charAt(i);

            if (escaped) {
                escaped = false;
                continue;
            }

            if (c == '\\') {
                escaped = true;
                continue;
            }

            if (c == '"') {
                inString = !inString;
                continue;
            }

            if (inString)
                continue;

            if (c == '[') {
                level++;
            } else if (c == ']') {
                level--;
                if (level == 0) {
                    return json.substring(start, i + 1);
                }
            }
        }

        return null;
    }

    private String extractNumberValue(int start) {
        StringBuilder sb = new StringBuilder();

        for (int i = start; i < json.length(); i++) {
            char c = json.charAt(i);

            if (Character.isDigit(c) || c == '.' || c == '-' || c == '+' || c == 'e' || c == 'E') {
                sb.append(c);
            } else {
                break;
            }
        }

        return sb.toString();
    }

    private String extractBooleanValue(int start) {
        if (json.substring(start).startsWith("true")) {
            return "true";
        } else if (json.substring(start).startsWith("false")) {
            return "false";
        }
        return null;
    }

    private String extractNullValue(int start) {
        if (json.substring(start).startsWith("null")) {
            return "null";
        }
        return null;
    }

    /**
     * Escapa caracteres especiais na chave
     */
    private String escapeKey(String key) {
        return key.replace("\\", "\\\\")
                .replace(".", "\\.")
                .replace("*", "\\*")
                .replace("+", "\\+")
                .replace("?", "\\?")
                .replace("^", "\\^")
                .replace("$", "\\$")
                .replace("(", "\\(")
                .replace(")", "\\)")
                .replace("[", "\\[")
                .replace("]", "\\]")
                .replace("{", "\\{")
                .replace("}", "\\}")
                .replace("|", "\\|");
    }
}
