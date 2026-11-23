import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Locale;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        double lat = 59.93;
        double lon = 30.31;
        int limit = 3;

        try {
            if (args.length == 3) {
                lat = Double.parseDouble(args[0]);
                lon = Double.parseDouble(args[1]);
                limit = Integer.parseInt(args[2]);
            } else if (args.length != 0) {
                throw new IllegalArgumentException("Порядок аргументов: lat lon limit.");
            }
            if (!(lat >= -90 && lat <= 90 && lon >= -180 && lon <= 180) || limit < 1) {
                throw new IllegalArgumentException("Широта от -90 до 90, долгота от -180 до 180, limit от 1.");
            }

            HttpResponse<String> response = requestForecast(lat, lon, limit, readApiKey());
            String json = response.body();
            System.out.println(json);

            int status = response.statusCode();
            if (status < 200 || status >= 300) {
                throw new IOException("Яндекс Погода вернула HTTP " + status + ".");
            }
            printTemperatures(json, limit);
        } catch (NumberFormatException exception) {
            exitWithError("Укажите числовые lat и lon и целое число суток limit.");
        } catch (JsonProcessingException exception) {
            exitWithError("Яндекс Погода вернула некорректный JSON.");
        } catch (IllegalArgumentException | IOException exception) {
            String message = exception.getMessage();
            exitWithError(message == null ? "Не удалось подключиться к API Яндекс Погоды." : message);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            exitWithError("Запрос к API прерван.");
        }
    }

    private static String readApiKey() throws IOException {
        String key = System.getenv("YANDEX_WEATHER_KEY");
        Path envFile = Path.of(".env");
        if (Files.exists(envFile)) {
            Properties env = new Properties();
            try (Reader reader = Files.newBufferedReader(envFile)) {
                env.load(reader);
            }
            key = env.getProperty("YANDEX_WEATHER_KEY", key);
        }
        if (key == null || key.isBlank()) {
            throw new IOException("Укажите YANDEX_WEATHER_KEY в файле .env.");
        }
        return key.strip();
    }

    private static HttpResponse<String> requestForecast(double lat, double lon, int limit, String key)
            throws IOException, InterruptedException {
        String url = "https://api.weather.yandex.ru/v2/forecast?lat=" + BigDecimal.valueOf(lat).toPlainString()
                + "&lon=" + BigDecimal.valueOf(lon).toPlainString() + "&limit=" + limit;
        Duration timeout = Duration.ofSeconds(10);
        HttpRequest.Builder request = HttpRequest.newBuilder(URI.create(url))
                .timeout(timeout)
                .GET();
        try {
            request.header("X-Yandex-Weather-Key", key);
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Проверьте строку YANDEX_WEATHER_KEY в .env.");
        }

        HttpClient client = HttpClient.newBuilder().connectTimeout(timeout).build();
        return client.send(request.build(), HttpResponse.BodyHandlers.ofString());
    }

    private static void printTemperatures(String json, int limit) throws IOException {
        ObjectMapper mapper = new ObjectMapper().enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS);
        JsonNode weather = mapper.readTree(json);
        JsonNode currentTemperature = weather.path("fact").path("temp");
        if (!currentTemperature.isNumber()) {
            throw new IOException("В ответе API отсутствует числовое значение fact.temp.");
        }
        System.out.printf("Текущая температура — %s °C%n", currentTemperature.asText());

        JsonNode forecasts = weather.path("forecasts");
        if (!forecasts.isArray()) {
            throw new IOException("В ответе API отсутствует массив forecasts.");
        }

        double sum = 0;
        int days = 0;
        for (int day = 0; day < Math.min(limit, forecasts.size()); day++) {
            JsonNode temperature = forecasts.get(day).path("parts").path("day").path("temp_avg");
            if (temperature.isNumber()) {
                sum += temperature.doubleValue();
                days++;
            }
        }

        if (days == 0) {
            throw new IOException("В прогнозе нет дневных температур parts.day.temp_avg.");
        }
        double average = sum / days;
        System.out.printf(Locale.ROOT, "Средняя дневная температура (%d из %d суток) — %.2f °C%n",
                days, limit, average);
    }

    private static void exitWithError(String message) {
        System.err.println(message);
        System.exit(1);
    }
}
