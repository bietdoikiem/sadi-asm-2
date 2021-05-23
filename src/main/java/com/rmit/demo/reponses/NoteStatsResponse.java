package com.rmit.demo.reponses;


import com.rmit.demo.model.Product;

public interface NoteStatsResponse {
    int getProduct_id();
    String getProduct_name();
    int getReceived();
    int getDelivery();
    int getBalance();
}
