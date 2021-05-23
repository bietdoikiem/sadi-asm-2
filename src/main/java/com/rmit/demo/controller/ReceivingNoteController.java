package com.rmit.demo.controller;

import com.rmit.demo.model.Order;
import com.rmit.demo.model.ReceiveDetail;
import com.rmit.demo.model.ReceivingNote;
import com.rmit.demo.service.OrderService;
import com.rmit.demo.service.ReceivingNoteService;
import com.rmit.demo.utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(path="/receiving-notes")
public class ReceivingNoteController {

    private ReceivingNoteService receivingNoteService;

    @Autowired
    public ReceivingNoteController(ReceivingNoteService receivingNoteService) {
        this.receivingNoteService = receivingNoteService;
    }

    //GET BY ID
    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getReceivingNoteById(@PathVariable int id) {
        try {
            ReceivingNote receivingNote = receivingNoteService.getReceivingNoteById(id);
            return ResponseHandler.generateResponse(HttpStatus.OK, true, "/receiving-notes/" + receivingNote.getId(), String.format("Receiving note %d fetched successfully.", receivingNote.getId()), receivingNote);
        } catch (NullPointerException e) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "/receiving-notes/" + id, String.format("Receiving note %d not found.", id), new HashMap());
        }
    }

    // Get ALL
    @RequestMapping(path="", method= RequestMethod.GET)
    public ResponseEntity<Object> getAllReceivingNotes() {
        //return receivingNoteService.getAllReceivingNotes();
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "/receiving-notes", "All receiving notes fetched successfully.", receivingNoteService.getAllReceivingNotes());
    }

    // Add
    @RequestMapping(path="", method= RequestMethod.POST)
    public ResponseEntity<Object> addReceivingNote(@RequestBody ReceivingNote receivingNote) {
        //return receivingNoteService.saveReceivingNote(receivingNote);
        ReceivingNote savedReceivingNote = receivingNoteService.saveReceivingNote(receivingNote);
        return ResponseHandler.generateResponse(HttpStatus.CREATED, true, "/receiving-notes/" + savedReceivingNote.getId(), String.format("Receiving note %d created successfully.", savedReceivingNote.getId())
                , savedReceivingNote);
    }

    // Update
    @RequestMapping(path= "", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateReceivingNote(@RequestBody ReceivingNote receivingNote) {
        //return receivingNoteService.updateReceivingNote(receivingNote);
        ReceivingNote updateReceivingNote = receivingNoteService.updateReceivingNote(receivingNote);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "/receiving-notes/" + receivingNote.getId(), String.format("Receiving note %d updated successfully.", updateReceivingNote.getId()), updateReceivingNote);
    }

    // Delete
    @RequestMapping(path= "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteReceivingNote(@PathVariable int id) {
        receivingNoteService.deleteReceivingNote(id);
        return ResponseHandler.generateResponse(HttpStatus.ACCEPTED, true, "/receiving-notes/" + id, String.format("Receiving note %d deleted successfully.", id), null);
    }

    // Filter by Date
    @RequestMapping(value="/filter", method = RequestMethod.GET)
    public ArrayList<ReceivingNote> filterByStartAndEndDate(@RequestParam String startDate, @RequestParam String endDate) throws ParseException {
        return receivingNoteService.getReceivingNotesByStartDateAndEndDate(startDate, endDate);
    }

    // READ All ReceiveDetail of a ReceiveNote by its ID
    @RequestMapping(value = "/{id}/receive-details", method = RequestMethod.GET)
    public ResponseEntity<Object> getReceiveDetailListByReceivingNote(@PathVariable int id) {
        List<ReceiveDetail> receiveDetailList = receivingNoteService.getReceiveDetailListByReceivingNote(id);
        return ResponseHandler.generateResponse(HttpStatus.OK, true,
                String.format("/receiving-notes/%d/receive-details", id),
                String.format("All ReceiveDetail of ReceivingNote %d fetched successfully.", id),
                receiveDetailList);
    }
}
