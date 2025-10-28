package com.starlight.app.repository;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

public class CsvLoader {
    private static final Logger log = LoggerFactory.getLogger(CsvLoader.class);
    private CsvLoader() {}

    /**
     * @param type     Target class (Book.class)
     * @param fileName Classpath resource name ("books.csv")
     */

    public static <T> List<T> booksRowMapper(Class<T> type, String fileName) {
        try (InputStream inputStream = new ClassPathResource(fileName).getInputStream()) {
            CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
            CsvMapper booksMapper = new CsvMapper();

            MappingIterator<T> iterator = booksMapper
                    .readerFor(type)
                    .with(bootstrapSchema)
                    .readValues(inputStream);
            return iterator.readAll();

        } catch (Exception e) {
            log.error("An error occurred when loading objects from the file: {}", fileName, e);
            return Collections.emptyList();
        }
    }
}