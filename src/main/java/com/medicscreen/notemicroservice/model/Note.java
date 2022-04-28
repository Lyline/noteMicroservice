package com.medicscreen.notemicroservice.model;

public class Note {

  private final Integer id;
  private final Integer patientId;
  private final String noteContent;

  public Note(NoteBuilder builder){
    this.id= builder.id;
    this.patientId= builder.patientId;
    this.noteContent= builder.noteContent;
  }

  public Integer getId() {
    return id;
  }

  public Integer getPatientId() {
    return patientId;
  }

  public String getNoteContent() {
    return noteContent;
  }

  public static class NoteBuilder{
    private Integer id;
    private Integer patientId;
    private String noteContent;

    public NoteBuilder id(Integer id){
      this.id=id;
      return this;
    }

    public NoteBuilder patientId(Integer patientId){
      this.patientId=patientId;
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
