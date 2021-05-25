package com.rmit.demo.model;

import com.fasterxml.jackson.annotation.*;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;
    @Column
    private String model;
    @Column
    private String brand;
    @Column
    private String company;
    @Column
    private String description;
    @Column
    private double price;

    // One-to-Many Relationships

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<ReceiveDetail> receiveDetailList;


    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<DeliveryDetail> deliveryDetailList;


    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<SaleDetail> saleDetailList;

    // Many-to-One Relationships
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = true)
    private Category category;


    public List<DeliveryDetail> getDeliveryDetailList() {
        return deliveryDetailList;
    }

    private void setDeliveryDetailList(List<DeliveryDetail> deliveryDetailList) {
        this.deliveryDetailList = deliveryDetailList;
    }

    public List<SaleDetail> getSaleDetailList() {
        return saleDetailList;
    }

    public void setSaleDetailList(List<SaleDetail> saleDetailList) {
        this.saleDetailList = saleDetailList;
    }

    public List<ReceiveDetail> getReceiveDetailList() {
        return receiveDetailList;
    }

    public void setReceiveDetailList(List<ReceiveDetail> receiveDetailList) {
        this.receiveDetailList = receiveDetailList;
    }

    @PreRemove
    public void preRemove() {
        for (OrderDetail orderDetail : orderDetails) {
            orderDetail.setProduct(null);
        }
    }

    public Product() {
    }

    public Product(int id, String name, String model, String brand, String company, String description, double price, Category category) {
        super();
        this.id = id;
        this.name = name;
        this.model = model;
        this.brand = brand;
        this.company = company;
        this.description = description;
        this.price = price;
        this.category = category;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
