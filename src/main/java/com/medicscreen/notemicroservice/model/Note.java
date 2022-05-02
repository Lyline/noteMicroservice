package com.medicscreen.notemicroservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Document
public class Note {

  @Id
  private String id;

  @NotNull(message = "Patient Id must be mandatory")
  private Integer patientId;

  @NotEmpty(message = "Note content must be mandatory")
  private String noteContent;

  public Note(){}

  public Note(NoteBuilder builder){
    this.id= builder.id;
    this.patientId = builder.patientId;
    this.noteContent= builder.noteContent;
  }

  public String getId() {
    return id;
  }

  public Integer getPatientId() {
    return patientId;
  }

  public String getNoteContent() {
    return noteContent;
  }

  public static class NoteBuilder{
    private String id;
    private Integer patientId;
    private String noteContent;

    public NoteBuilder id(String id){
      this.id=id;
      return this;
    }

    public NoteBuilder patientId(Integer patientId){
      this.patientId =patientId;
      return this;
    }

    public NoteBuilder noteContent(String noteContent){
      this.noteContent=noteContent;
      return this;
    }

    public Note build(){
      return new Note(this);
    }
  }
}
