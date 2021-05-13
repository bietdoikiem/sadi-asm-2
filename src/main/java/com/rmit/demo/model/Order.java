package com.rmit.demo.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="order_table")
public class Order {

    @Id
    @Column
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column
    private Date date;
    @Column(name="string_date")
    private String stringDate;
    /*@Column
    private Staff staff;
    @Column
    private Provider provider;
    @Column
    private OrderDetail orderDetail;*/

    public Order(){}

    public Order(int id/*, Date date*/, String stringDate/*, Staff staff, Provider provider, OrderDetail orderDetail*/) {
        this.id = id;
        /*this.date = date;*/
        this.stringDate = stringDate;
        /*this.staff = staff;
        this.provider = provider;*/
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /*public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }*/

    public String getStringDate() {
        return stringDate;
    }

    public void setStringDate(String stringDate) {
        this.stringDate = stringDate;
    }

    /*public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }*/


}
