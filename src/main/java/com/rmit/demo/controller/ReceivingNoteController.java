package com.rmit.demo.controller;

import com.rmit.demo.model.Order;
import com.rmit.demo.model.Product;
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
public class ReceivingNoteController implements CrudController<ReceivingNote> {

    private ReceivingNoteService receivingNoteService;

    @Autowired
    public ReceivingNoteController(ReceivingNoteService receivingNoteService) {
        this.receivingNoteService = receivingNoteService;
    }


    @Override
    public ResponseEntity<Object> getAll() {
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "/receivingNotes", "All receiving notes fetched successfully.", receivingNoteService.getAllReceivingNotes());
    }

    // Read all receiving note by pagination
    @Override
    public ResponseEntity<Object> getAll(int page, int size) {
        List<ReceivingNote> listOfReceivingNotes = receivingNoteService.getAll(page, size);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "/receivingNotes", String.format("Receiving notes (page %d - size %d) fetched successfully.", page, size), listOfReceivingNotes);
    }

    @Override
    public ResponseEntity<Object> getOne(int id) {
        try {
            ReceivingNote receivingNote = receivingNoteService.getReceivingNoteById(id);
            return ResponseHandler.generateResponse(HttpStatus.OK, true, "/receiving-notes/" + receivingNote.getId(), String.format("Receiving note %d fetched successfully.", receivingNote.getId()), receivingNote);
        } catch (NullPointerException e) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "/receiving-notes/" + id, String.format("Receiving note %d not found.", id), new HashMap());
        }
    }

    @Override
    public ResponseEntity<Object> saveOne(ReceivingNote receivingNote) {
        ReceivingNote savedReceivingNote = receivingNoteService.saveReceivingNote(receivingNote);
        return ResponseHandler.generateResponse(HttpStatus.CREATED, true, "/receiving-notes/" + savedReceivingNote.getId(), String.format("Receiving note %d created successfully.", savedReceivingNote.getId())
                , savedReceivingNote);
    }

    @Override
    public ResponseEntity<Object> updateOne(int id, ReceivingNote receivingNote) {
        ReceivingNote updateReceivingNote = receivingNoteService.updateReceivingNote(receivingNote);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "/receiving-notes/" + receivingNote.getId(), String.format("Receiving note %d updated successfully.", updateReceivingNote.getId()), updateReceivingNote);
    }

    @Override
    public ResponseEntity<Object> deleteOne(int id) {
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
