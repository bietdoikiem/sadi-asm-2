package com.rmit.demo.reponses;

public class NoteStatsResponseImpl implements  NoteStatsResponse{

    private final int product_id;
    private final String product_name;
    private final int received;
    private final int delivery;
    private final int balance;

    public NoteStatsResponseImpl(int product_id, String product_name, int received, int delivery, int balance) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.received = received;
        this.delivery = delivery;
        this.balance = balance;
    }

    @Override
    public int getProduct_id() {
        return product_id;
    }

    @Override
    public String getProduct_name() {
        return product_name;
    }

    @Override
    public int getReceived() {
        return received;
    }

    @Override
    public int getDelivery() {
        return delivery;
    }

    @Override
    public int getBalance() {
        return balance;
    }
}
