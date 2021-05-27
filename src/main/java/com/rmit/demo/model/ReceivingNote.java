package com.rmit.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Configurable;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="receiving_note")
public class ReceivingNote {

    @Id
    @Column
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @Column
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "staff_id", referencedColumnName = "id")
    private Staff staff;

    @OneToMany(mappedBy = "receivingNote", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<ReceiveDetail> receiveDetailList;

    public ReceivingNote() {}

    public ReceivingNote(int id, Date date, Staff staff, List<ReceiveDetail> receiveDetailList) {
        this.id = id;
        this.date = date;
        this.staff = staff;
        this.receiveDetailList = receiveDetailList;
    }

    // For testing
    public ReceivingNote(int id, Date date) {
        this.id = id;
        this.date = date;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public List<ReceiveDetail> getReceiveDetailList() {
        return receiveDetailList;
    }

    public void setReceiveDetailList(List<ReceiveDetail> receiveDetailList) {
        this.receiveDetailList = receiveDetailList;
    }

    public void setAll(ReceivingNote receivingNote) {
        this.id = receivingNote.getId();
        this.date = receivingNote.getDate();
        this.staff = receivingNote.getStaff();
        this.receiveDetailList = receivingNote.getReceiveDetailList();

    }

}
