package com.medicscreen.notemicroservice;

import com.medicscreen.notemicroservice.model.Note;
import com.medicscreen.notemicroservice.model.Note.NoteBuilder;
import com.medicscreen.notemicroservice.service.NoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class NoteControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private NoteService service;

  private final Note note1= new NoteBuilder()
      .id("1")
      .patientId(2)
      .noteContent("New Note")
      .build();

  private final Note note2= new NoteBuilder()
      .id("2")
      .patientId(3)
      .noteContent("second Note")
      .build();


  @Test
  void DisplayWelcomeMessage() throws Exception {
    mockMvc.perform(get("/noteAPI"))
        .andExpect(status().isOk())
        .andExpect(content().string("Welcome to Note API"));
  }

  @Test
  void givenPatientWithTwoNotesThenGetAllNotesByPatientThenReturnListOfNotesWithStatus200() throws Exception {
    //Given
    Note note= new NoteBuilder()
        .id("4")
        .patientId(2)
        .noteContent("Second New Note")
        .build();

    when(service.getAllNotesByPatient(anyInt())).thenReturn(List.of(note1,note));

    //When:
    mockMvc.perform(get("/noteAPI/patient_notes/2"))
        .andExpect(status().isOk())
        .andExpect(content().json(
            "[{\"id\":\"1\"," +
                "\"patientId\":2," +
                "\"noteContent\":\"New Note\"}," +

                "{\"id\":\"4\"," +
                "\"patientId\":2," +
                "\"noteContent\":\"Second New Note\"}]"
        ));
  }

  @Test
  void givenPatientWithoutNoteThenGetAllNotesByPatientThenReturnStatus204() throws Exception {
    //Given
    when(service.getAllNotesByPatient(anyInt())).thenReturn(Collections.emptyList());

    //When:
    mockMvc.perform(get("/noteAPI/patient_notes/2"))
        .andExpect(status().isNoContent());
  }

  @Test
  void givenANoteExistingWhenGetNoteByIdThenReturnNoteWithStatus200() throws Exception {
    //Given
    when(service.getNoteById(any())).thenReturn(note1);

    //When
    mockMvc.perform(get("/noteAPI/notes/1"))
        .andExpect(status().isOk())
        .andExpect(content().json(
            "{\"id\":\"1\"," +
                "\"patientId\":2," +
                "\"noteContent\":\"New Note\"}"
        ));
  }

  @Test
  void givenNoteNotExistingWhenGetNoteByIdThenReturnStatus204() throws Exception {
    //Given
    when(service.getNoteById(any())).thenReturn(null);

    //When
    mockMvc.perform(get("/noteAPI/notes/1"))
        .andExpect(status().isNoContent());
  }

  @Test
  void givenANewValidNoteWhenAddNoteThenReturnNoteSavedWithStatus201() throws Exception {
    //Given
    when(service.addNote(any())).thenReturn(note1);

    //When
    mockMvc.perform(post("/noteAPI/notes")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{" +
            "\"patientId\":2," +
            "\"noteContent\":\"New Note\"" +
            "}"))
        
        .andExpect(status().isCreated())
        .andExpect(content().json(
            "{\"id\":\"1\"," +
                "\"patientId\":2," +
                "\"noteContent\":\"New Note\"}"
        ));
  }

  @Test
  void givenANewNotValidNoteWhenAddNoteThenReturnStatus422() throws Exception {
    //Given
    //When
    mockMvc.perform(post("/noteAPI/notes")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{" +
                "\"patientId\":null," +
                "\"noteContent\":\"\"" +
                "}"))
        
        .andExpect(status().isUnprocessableEntity())
        .andExpect(content().json(
            "{\"noteContent\":\"Note content must be mandatory\"," +
                "\"patientId\":\"Patient Id must be mandatory\"}"
        ));
  }

  @Test
  void givenANoteExistingWithValidUpdateWhenUpdateNoteThenReturnNoteUpdatedWithStatus201() throws Exception {
    //Given
    Note noteUpdated= new NoteBuilder()
        .id(String.valueOf(1))
        .patientId(2)
        .noteContent("Content updated")
        .build();

    when(service.updateNote(any(),any())).thenReturn(noteUpdated);

    //When
    mockMvc.perform(put("/noteAPI/notes/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"patientId\":2," +
            "\"noteContent\":\"Content updated\"}"))

        .andExpect(status().isCreated())
        .andExpect(content().json(
            "{\"id\":\"1\"," +
            "\"patientId\":2," +
            "\"noteContent\":\"Content updated\"}"));
  }

  @Test
  void givenANoteExistingWithNotNoteValidUpdateWhenUpdateNoteThenReturnStatus422() throws Exception {
    //Given
    //When
    mockMvc.perform(put("/noteAPI/notes/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"patientId\":null," +
                "\"noteContent\":\"\"}"))

        .andExpect(status().isUnprocessableEntity())
        .andExpect(content().json(
            "{\"noteContent\":\"Note content must be mandatory\"," +
                "\"patientId\":\"Patient Id must be mandatory\"}"));
  }

  @Test
  void givenANoteNotExistingWithNoteValidUpdateWhenUpdateNoteThenReturnStatus204() throws Exception {
    //Given
    when(service.updateNote(any(),any())).thenReturn(null);

    //When
    mockMvc.perform(put("/noteAPI/notes/3")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"patientId\":4," +
            "\"noteContent\":\"New Note\"}"))
        .andExpect(status().isNoContent());
  }

  @Test
  void givenANoteExistingWhenDeleteNoteByIdThenNoteDeletedWithStatus200() throws Exception {
    //Given
    when(service.deleteById(any())).thenReturn(true);

    //When
    mockMvc.perform(delete("/noteAPI/notes/1"))
        .andExpect(status().isOk());
  }

  @Test
  void givenANoteNotExistingWhenDeleteNoteByIdThenNoteDeletedWithStatus204() throws Exception {
    //Given
    when(service.deleteById(any())).thenReturn(false);

    //When
    mockMvc.perform(delete("/noteAPI/notes/1"))
        .andExpect(status().isNoContent());
  }
}
