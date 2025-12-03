package org.example.space.travel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import java.time.LocalDate;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class,
                    (JsonSerializer<LocalDate>) (src, typeOfSrc, ctx)
                            -> new JsonPrimitive(src.toString()))
            .setPrettyPrinting()
            .create();
    private static final Flyway FLYWAY = Flyway.configure()
            .locations("classpath:db/migration")
            .dataSource("jdbc:h2:file:~/test", "sa", "")
            .load();

    public static void main(String[] args) {
        FLYWAY.migrate();

    }
}
