package com.starlight.app.IntegrationTests.UnitTests;

import com.starlight.app.controller.BookController;
import com.starlight.app.model.entity.Books;
import com.starlight.app.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    void null_params_test() throws Exception {
        mockMvc.perform(get("/starlightFolklore/api"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString(
                        "At least one of the search parameters must be provided")));
    }

    @Test
    void search_happy_test() throws Exception {
        Books book = new Books();
        when(bookService.search("Rebecca", "Kuang", null, null))
                .thenReturn(List.of(book));

        mockMvc.perform(get("/starlightFolklore/api")
                        .param("firstName", "Rebecca")
                        .param("lastName", "Kuang")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void search_by_title() throws Exception {
        Books book = new Books();
        when(bookService.search(null, null, "Babel", null))
                .thenReturn(List.of(book));

        mockMvc.perform(get("/starlightFolklore/api")
                        .param("title", "Babel")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void search_by_genre() throws Exception {
        Books book = new Books();
        when(bookService.search(null, null, null, "Fantasy"))
                .thenReturn(List.of(book));

        mockMvc.perform(get("/starlightFolklore/api")
                        .param("title", "Fantasy")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void getAll_happy_Test() throws Exception {
        when(bookService.getFullList()).thenReturn(List.of(new Books(), new Books()));

        mockMvc.perform(get("/starlightFolklore/api/getAll")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}
