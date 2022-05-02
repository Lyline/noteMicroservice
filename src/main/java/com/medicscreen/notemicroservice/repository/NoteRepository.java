package com.medicscreen.notemicroservice.repository;

import com.medicscreen.notemicroservice.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NoteRepository extends MongoRepository<Note, String> {

  List<Note> findAllByPatientIdOrderByIdDesc(Integer patientId);

}
