package com.rmit.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping(path = "/default")
public interface CrudController<T> {

    @RequestMapping(path = "", method = RequestMethod.GET)
    ResponseEntity<Object> getAll();

    @RequestMapping(path = "", method = RequestMethod.GET, params = {"page", "size"})
    ResponseEntity<Object> getAll(int page, int size);

    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    ResponseEntity<Object> getOne(@PathVariable int id);

    @RequestMapping(path = "", method = RequestMethod.POST)
    ResponseEntity<Object> saveOne(@RequestBody T object);

    @RequestMapping(path = "{id}", method = RequestMethod.PUT)
    ResponseEntity<Object> updateOne(@PathVariable int id, @RequestBody T object);

    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    ResponseEntity<Object> deleteOne(@PathVariable int id);
}
