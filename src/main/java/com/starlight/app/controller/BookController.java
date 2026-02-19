package com.starlight.app.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.starlight.app.model.entity.Books;
import com.starlight.app.service.BookService;

@CrossOrigin(origins = "http://localhost:3000") // Allow requests from the React frontend
@RestController
@RequestMapping("/starlightFolklore") //http://localhost:8080/starlightFolklore
public class BookController {

    private final BookService service;
    public BookController(BookService service) {
        this.service = service;
    }

    /**
     * Search by any combination of firstName, lastName, title, and genre
     * Example search http://localhost:8080/starlightFolklore?lastName=kuang (GET)
     */

    //query search
    @GetMapping
    public ResponseEntity<?> search(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String genre) {

        if (isBlank(firstName) && isBlank(lastName) && isBlank(title) && isBlank(genre)) {
            return ResponseEntity.badRequest().body(
                    "At least one of the search parameters must be provided: author first name, author last name, book title, or book genre"
            );
        }

        List<Books> result = service.search(firstName, lastName, title, genre);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/charts/genre")
    public ResponseEntity<Map<String, String>> genreChart() {
        List<Books> allBooks = service.getFullList();

        Map<String, Long> genreCounts = allBooks.stream()
                .filter(b -> b.getBookGenre() != null && !b.getBookGenre().isBlank())
                .collect(Collectors.groupingBy(Books::getBookGenre, Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b2) -> a,
                        LinkedHashMap::new
                ));

        String labels = genreCounts.keySet().stream()
                .map(g -> "\"" + g.replace("\"", "\\\"") + "\"")
                .collect(Collectors.joining(","));

        String data = genreCounts.values().stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        // Build Chart.js config for QuickChart
        String chartConfig = "{"
                + "\"type\":\"bar\","
                + "\"data\":{"
                +   "\"labels\":[" + labels + "],"
                +   "\"datasets\":[{"
                +     "\"label\":\"Books by Genre\","
                +     "\"data\":[" + data + "],"
                +     "\"backgroundColor\":\"rgba(107,76,230,0.75)\","
                +     "\"borderColor\":\"rgba(107,76,230,1)\","
                +     "\"borderWidth\":2,"
                +     "\"borderRadius\":6"
                +   "}]"
                + "},"
                + "\"options\":{"
                +   "\"plugins\":{"
                +     "\"title\":{\"display\":true,\"text\":\"Starlight Collection by Genre\",\"color\":\"#e8eaf0\",\"font\":{\"size\":16}},"
                +     "\"legend\":{\"display\":false}"
                +   "},"
                +   "\"scales\":{"
                +     "\"y\":{\"beginAtZero\":true,\"ticks\":{\"color\":\"#9ca3af\"},\"grid\":{\"color\":\"rgba(255,255,255,0.05)\"}},"
                +     "\"x\":{\"ticks\":{\"color\":\"#9ca3af\"},\"grid\":{\"color\":\"rgba(255,255,255,0.05)\"}}"
                +   "},"
                +   "\"backgroundColor\":\"#1e1b29\""
                + "}"
                + "}";

        String chartUrl = "https://quickchart.io/chart?w=700&h=400&bkg=%231e1b29&c="
                + java.net.URLEncoder.encode(chartConfig, java.nio.charset.StandardCharsets.UTF_8);

        Map<String, String> response = new LinkedHashMap<>();
        response.put("chartUrl", chartUrl);
        return ResponseEntity.ok(response);
    }

    //get entire books list
    @GetMapping("/getAll")
    public List<Books> getAll() {
        return  service.getFullList();
    }

    private boolean isBlank(String s) {
        return s == null || s.isBlank();
    }
}
