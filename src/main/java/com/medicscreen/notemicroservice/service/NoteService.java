package com.medicscreen.notemicroservice.service;

import com.medicscreen.notemicroservice.model.Note;
import com.medicscreen.notemicroservice.model.Note.NoteBuilder;
import com.medicscreen.notemicroservice.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

  private final NoteRepository repository;

  public NoteService(NoteRepository repository) {
    this.repository = repository;
  }

  public List<Note> getAllNotesByPatient(Integer patientId) {
    return repository.findAllByPatientIdOrderByIdDesc(patientId);
  }

  public Note getNoteById(String id) {
    Optional<Note> note= repository.findById(id);

    if (note.isPresent()){
      return note.get();
    }
    return null;
  }

  public Note addNote(Note noteToSave) {
    return repository.save(noteToSave);
  }

  public Note updateNote(String id, Note noteToUpdate){
    if (repository.existsById(id)){

      Note note= new NoteBuilder()
          .id(id)
          .patientId(noteToUpdate.getPatientId())
          .noteContent(noteToUpdate.getNoteContent())
          .build();

      return repository.save(note);
    }
    return null;
  }

  public boolean deleteById(String id) {
    if (repository.existsById(id)){
      repository.deleteById(id);
      return true;
    }
    return false;
  }

  public void deleteAll() {
    repository.deleteAll();
  }
}
