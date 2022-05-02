package com.medicscreen.notemicroservice;

import com.medicscreen.notemicroservice.model.Note;
import com.medicscreen.notemicroservice.model.Note.NoteBuilder;
import com.medicscreen.notemicroservice.repository.NoteRepository;
import com.medicscreen.notemicroservice.service.NoteService;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class NoteServiceTest {
  private final NoteRepository repository=mock(NoteRepository.class);

  private final NoteService classUnderTest= new NoteService(repository);

  private final Note note1= new NoteBuilder()
      .id("1")
      .patientId(2)
      .noteContent("new note")
      .build();

  private final Note note2= new NoteBuilder()
      .id("2")
      .patientId(3)
      .noteContent("second note")
      .build();

  private final Note noteToUpdate= new NoteBuilder()
      .id("1")
      .patientId(4)
      .noteContent("note updated")
      .build();

  @Test
  void givenTwoNotesWhenGetAllNotesThenReturnListOfNotes() {
    //Given
    when(repository.findAll()).thenReturn(List.of(note1, note2));

    //When
    List<Note>actual= classUnderTest.getAllNotes();

    //Then
    assertThat(actual.size()).isEqualTo(2);
    verify(repository,times(1)).findAll();
  }

  @Test
  void givenNoNoteWhenGetAllNotesThenReturnEmptyList() {
    //Given
    when(repository.findAll()).thenReturn(Collections.emptyList());

    //When
    List<Note> actual= classUnderTest.getAllNotes();

    //Then
    assertThat(actual.size()).isEqualTo(0);
    verify(repository,times(1)).findAll();
  }

  /*@Test
  void givenAPatientWithTwoNotesWhenGetAllNotesByPatientThenReturnListOfPatientNotes() {
    //Given
    Note note= new NoteBuilder()
        .id("4")
        .patientId(2)
        .noteContent("second note of Doe")
        .build();

    //when(repository.findAllBy)

    //When
    //Then
  }*/

  @Test
  void givenANoteExistingWhenGetNoteByIdThenReturnNote() {
    //Given
    when(repository.findById(any())).thenReturn(Optional.ofNullable(note1));

    //When
    Note actual= classUnderTest.getNoteById("1");

    //Then
    noteAssertionVerification(actual);
    verify(repository,times(1)).findById("1");
  }

  @Test
  void givenANoteNoteExistingWhenGetNoteByIdThenReturnNull() {
    //Given
    when(repository.findById(any())).thenReturn(Optional.empty());

    //When
    Note actual= classUnderTest.getNoteById("1");

    //Then
    assertNull(actual);
    verify(repository,times(1)).findById("1");
  }

  @Test
  void givenANewNoteWhenAddNoteThenNoteSaved() {
    //Given
    Note noteToSave= new NoteBuilder()
        .patientId(2)
        .noteContent("new note")
        .build();

    when(repository.save(any())).thenReturn(note1);

    //When
    Note actual= classUnderTest.addNote(noteToSave);

    //Then
    noteAssertionVerification(actual);
    verify(repository,times(1)).save(noteToSave);
  }

  @Test
  void givenANoteExistingWhenUpdateNoteThenNoteUpdated() {
    //Given
    when(repository.existsById(any())).thenReturn(true);
    when(repository.save(any())).thenReturn(note1);

    //When
    Note actual= classUnderTest.updateNote("1",noteToUpdate);

    //Then
    noteAssertionVerification(actual);
    verify(repository, times(1)).existsById("1");
  }

  @Test
  void givenANoteNotExistingWhenUpdateNoteThenReturnNull(){
    //Given
    when(repository.existsById(any())).thenReturn(false);
    when(repository.save(any())).thenReturn(note1);

    //When
    Note actual= classUnderTest.updateNote("3", noteToUpdate);

    //Then
    assertNull(actual);
    verify(repository,times(1)).existsById("3");
    verify(repository,times(0)).save(noteToUpdate);
  }

  @Test
  void givenANoteExistingWhenDeleteByIdNoteThenNoteDeletedAndReturnTrue() {
    //Given
    when(repository.existsById(any())).thenReturn(true);

    //When
    boolean actual= classUnderTest.deleteById("1");

    //Then
    assertTrue(actual);
    verify(repository, times(1)).deleteById("1");
  }

  @Test
  void givenANoteNotExistingWhenDeleteByIdNoteThenReturnFalse() {
    //Given
    when(repository.existsById(any())).thenReturn(false);

    //When
    boolean actual= classUnderTest.deleteById("1");

    //Then
    assertFalse(actual);
    verify(repository, times(0)).deleteById("1");
  }
  private void noteAssertionVerification(Note actual) {
    assertThat(actual.getId()).isEqualTo("1");
    assertThat(actual.getPatientId()).isEqualTo(2);
    assertThat(actual.getNoteContent()).isEqualTo("new note");
  }
}
