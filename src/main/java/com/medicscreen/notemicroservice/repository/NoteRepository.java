package com.medicscreen.notemicroservice.repository;

import com.medicscreen.notemicroservice.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NoteRepository extends MongoRepository<Note, Integer> {

}
