package com.rmit.demo.controller;

import com.rmit.demo.model.DeliveryNote;
import com.rmit.demo.service.DeliveryNoteService;
import com.rmit.demo.utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(path = "/delivery-notes")
public class DeliveryNoteController implements CrudController<DeliveryNote> {

    private DeliveryNoteService deliveryNoteService;

    @Autowired
    public DeliveryNoteController(DeliveryNoteService deliveryNoteService) {
        this.deliveryNoteService = deliveryNoteService;
    }

    // READ All DeliveryNote
    public ResponseEntity<Object> getAll() {
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "/delivery-notes", "All DeliveryNotes fetched successfully.", deliveryNoteService.getAllDeliveryNotes());
    }

    // READ All DeliveryNote by page and size
    public ResponseEntity<Object> getAll(@RequestParam int page, @RequestParam int size) {
        List<DeliveryNote> deliveryNoteList = deliveryNoteService.getAllDeliveryNotes(page, size);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, String.format("/delivery-notes?page=%d&size=%d", page, size), String.format("DeliveryNotes (page %d - size %d) fetched successfully.", page, size), deliveryNoteList);
    }

    // READ All DeliveryNote by ID
    public ResponseEntity<Object> getOne(@PathVariable int id) {
        DeliveryNote deliveryNote = deliveryNoteService.getDeliveryNoteById(id);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "/delivery-notes/" + deliveryNote.getId(),
                String.format("DeliveryNote %d fetch successfully.", deliveryNote.getId()), deliveryNote);
    }

    // CREATE One DeliveryNote
    public ResponseEntity<Object> saveOne(@RequestBody DeliveryNote deliveryNote) {
        DeliveryNote savedNote = deliveryNoteService.saveDeliveryNote(deliveryNote);
        return ResponseHandler.generateResponse(HttpStatus.CREATED, true, "/delivery-notes/" + deliveryNote.getId(),
                String.format("DeliveryNote %d created successfully.", savedNote.getId()), savedNote);
    }

    // UPDATE One DeliveryNote
    public ResponseEntity<Object> updateOne(@PathVariable int id, @RequestBody DeliveryNote deliveryNote) {
        DeliveryNote updatedNote = deliveryNoteService.updateDeliveryNote(id, deliveryNote);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "/delivery-notes/" + updatedNote.getId(),
                String.format("DeliveryNote %d updated successfully.", updatedNote.getId()), updatedNote);
    }

    // DELETE One DeliveryNote
    public ResponseEntity<Object> deleteOne(@PathVariable int id) {
        int result = deliveryNoteService.deleteDeliveryNote(id);
        return ResponseHandler.generateResponse(HttpStatus.ACCEPTED, true, String.format("/delivery-notes/%d", result),
                String.format("DeliveryNote %d deleted successfully.", result), null);
    }


}
