package com.rmit.demo.service;

import java.util.List;

public interface CrudService<T> {

    // Get All Objects
    List<T> getAll();

    // Get All Objects by Pagination
    List<T> getAll(int page, int size);

    T getOne(int id);

    T saveOne(T object);

    T updateOne(int id, T object);

    int deleteOne(int id);

}
