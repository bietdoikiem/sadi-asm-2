package com.rmit.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "staff_id", referencedColumnName = "id")
    private Staff staff;

    @OneToMany(mappedBy = "receivingNote", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    private List<ReceiveDetail> receiveDetail;

    public ReceivingNote() {}

    public ReceivingNote(int id, Date date, Staff staff, List<ReceiveDetail> receiveDetail) {
        this.id = id;
        this.date = date;
        this.staff = staff;
        this.receiveDetail = receiveDetail;
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

    public List<ReceiveDetail> receiveDetail() {
        return receiveDetail;
    }

    public void setReceiveDetail(List<ReceiveDetail> receiveDetail) {
        this.receiveDetail = receiveDetail;
    }


}
