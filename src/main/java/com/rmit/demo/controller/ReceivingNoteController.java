package com.rmit.demo.controller;

import com.rmit.demo.model.Order;
import com.rmit.demo.model.ReceivingNote;
import com.rmit.demo.service.OrderService;
import com.rmit.demo.service.ReceivingNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/receivingNotes")
public class ReceivingNoteController {

    private ReceivingNoteService receivingNoteService;

    @Autowired
    public ReceivingNoteController(ReceivingNoteService receivingNoteService) {
        this.receivingNoteService = receivingNoteService;
    }

    @RequestMapping(path="", method= RequestMethod.GET)
    public List<ReceivingNote> getAllReceivingNotes() {
        return receivingNoteService.getAllReceivingNotes();
    }

    @RequestMapping(path="", method= RequestMethod.POST)
    public int addReceivingNote(@RequestBody ReceivingNote receivingNote) {
        return receivingNoteService.saveReceivingNote(receivingNote);
    }

    @RequestMapping(path= "", method = RequestMethod.PUT)
    public ReceivingNote updateReceivingNote(@RequestBody ReceivingNote receivingNote) {
        return receivingNoteService.updateReceivingNote(receivingNote);
    }

    @RequestMapping(path= "/{id}", method = RequestMethod.DELETE)
    public String deleteReceivingNote(@PathVariable int id) {
        return receivingNoteService.deleteReceivingNote(id);
    }
}
