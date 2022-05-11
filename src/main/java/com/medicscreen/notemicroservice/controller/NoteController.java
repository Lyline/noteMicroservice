package com.medicscreen.notemicroservice.controller;

import com.medicscreen.notemicroservice.model.Note;
import com.medicscreen.notemicroservice.service.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/noteAPI")
public class NoteController {

  private final NoteService service;

  public NoteController(NoteService service) {
    this.service = service;
  }

  @GetMapping("")
  public ResponseEntity<String> getWelcome(){
    return new ResponseEntity<>("Welcome to Note API", HttpStatus.OK);
  }

  @GetMapping("/patient_notes/{id}")
  public ResponseEntity<List<Note>> getAllNotesByPatient(@PathVariable Integer id){
    List<Note> notes= service.getAllNotesByPatient(id);

    if (!notes.isEmpty()) {
      return new ResponseEntity<>(notes,HttpStatus.OK);
    }return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping("/notes/{id}")
  public ResponseEntity<Note> getNoteById(@PathVariable String id){
    Note note= service.getNoteById(id);

    if (Objects.isNull(note)){
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(note,HttpStatus.OK);
  }

  @PostMapping("/notes")
  public ResponseEntity<Note> addNote(@Valid @RequestBody Note note){
    Note noteSaved= service.addNote(note);
    return new ResponseEntity<>(noteSaved,HttpStatus.CREATED);
  }

  @PutMapping("/notes/{id}")
  public ResponseEntity<Note> updateNote(@PathVariable String id,
                                         @Valid @RequestBody Note note){
    Note noteUpdated= service.updateNote(id,note);

    if (Objects.isNull(noteUpdated)){
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(noteUpdated, HttpStatus.CREATED);
  }

  @DeleteMapping("/notes/{id}")
  public ResponseEntity deleteNoteById(@PathVariable String id){
    boolean noteIsDeleted= service.deleteById(id);

    if (noteIsDeleted){
      return new ResponseEntity<>(true,HttpStatus.OK);
    }return new ResponseEntity<>(false,HttpStatus.OK);
  }
}
