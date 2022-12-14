package ru.job4j.controller;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.Main;
import ru.job4j.model.Accident;
import ru.job4j.model.AccidentType;
import ru.job4j.model.Rule;
import ru.job4j.service.AccidentService;

import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
class AccidentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccidentService accidentService;

    @Test
    @WithMockUser
    void create() throws Exception {
        this.mockMvc.perform(get("/createAccident"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("createAccident"));
    }

    @Test
    @WithMockUser
    void whenUpdateExistingAccident() throws Exception {
        Optional<Accident> optAccident = Optional.of(new Accident(
                1,
                "name",
                "text",
                "address",
                new AccidentType(),
                Set.of(new Rule())));
        when(accidentService.findById(1)).thenReturn(optAccident);
        this.mockMvc.perform(get("/formUpdateAccident")
                        .param("id", "1")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("formUpdateAccident"));
    }

    @Test
    @WithMockUser
    void whenUpdateNotExistingAccident() throws Exception {
        this.mockMvc.perform(get("/formUpdateAccident")
                        .param("id", String.valueOf(-1))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("404"));
    }

    @Test
    @WithMockUser
    public void whenCreateThenReturnSetValues() throws Exception {
        String[] rIds = {"1", "2"};
        this.mockMvc.perform(post("/saveAccident")
                        .param("rIds", rIds)
                        .param("type.id", String.valueOf(1))
                        .param("name", "Name")
                        .param("text", "Text")
                        .param("address", "Address"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/createAccident?fail=true"));
        ArgumentCaptor<Accident> argument = ArgumentCaptor.forClass(Accident.class);
        verify(accidentService).create(argument.capture(), eq(1), eq(rIds));
        assertThat(argument.getValue().getName()).isEqualTo("Name");
        assertThat(argument.getValue().getText()).isEqualTo("Text");
        assertThat(argument.getValue().getAddress()).isEqualTo("Address");
    }

    @Test
    @WithMockUser
    public void whenUpdateThenReturnSetValues() throws Exception {
        String[] rIds = {"1", "2"};
        this.mockMvc.perform(post("/updateAccident")
                        .param("rIds", rIds)
                        .param("type.id", String.valueOf(1))
                        .param("name", "Name")
                        .param("text", "Text")
                        .param("address", "Address"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/formUpdateAccident?fail=true"));
        ArgumentCaptor<Accident> argument = ArgumentCaptor.forClass(Accident.class);
        verify(accidentService).update(argument.capture(), eq(1), eq(rIds));
        assertThat(argument.getValue().getName()).isEqualTo("Name");
        assertThat(argument.getValue().getText()).isEqualTo("Text");
        assertThat(argument.getValue().getAddress()).isEqualTo("Address");
    }
}