package com.medicscreen.notemicroservice;

import com.medicscreen.notemicroservice.model.Note;
import com.medicscreen.notemicroservice.model.Note.NoteBuilder;
import com.medicscreen.notemicroservice.service.NoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@Profile("dev")
@SpringBootApplication
public class NoteMicroserviceApplicationDev implements CommandLineRunner {

  @Autowired
  private NoteService service;

  public static void main(String[] args) {
    SpringApplication.run(NoteMicroserviceApplicationDev.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    Logger logger= LoggerFactory.getLogger(CommandLineRunner.class);

    logger.info("dev environment");
    service.deleteAll();

    Note note=new NoteBuilder().patientId(1).noteContent("Test note").build();
    service.addNote(note);

    Note note1= new NoteBuilder().patientId(2).noteContent("Second note").build();
    service.addNote(note1);
  }
}
