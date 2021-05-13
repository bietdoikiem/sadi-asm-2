package com.rmit.demo.model;

import org.springframework.beans.factory.annotation.Configurable;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="receive_note")
public class ReceivingNote {

    @Id
    @Column
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @Column
    private Date date;
    /*@Column
    private Staff staff;
    @Column
    private ReceiveDetail receiveDetail;*/

    public ReceivingNote() {}

    public ReceivingNote(int id, Date date/*, Staff staff, OrderDetail orderDetail*/) {
        this.id = id;
        this.date = date;
        /*this.staff = staff;
        this.orderDetail = orderDetail;*/
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

    /*public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public OrderDetail getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(OrderDetail orderDetail) {
        this.orderDetail = orderDetail;
    }*/


}
