package at.spengergasse.spring_thymeleaf.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservation")
public class reservation { // Klasse groß schreiben
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "patient_id") // genau die Spalte in DB
    private Patient patient;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "machine_id") // genau die Spalte in DB
    private Machine machine;

    private LocalDateTime datetime;
    private String comment;
    private String bodyregion;


    // getter & setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }

    public Machine getMachine() { return machine; }
    public void setMachine(Machine machine) { this.machine = machine; }

    public LocalDateTime getDatetime() { return datetime; }
    public void setDatetime(LocalDateTime datetime) {
        if(datetime.isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("Reservation can't be before today");
        }
        this.datetime = datetime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getBodyregion() {
        return bodyregion;
    }

    public void setBodyregion(String bodyregion) {
        this.bodyregion = bodyregion;
    }
}