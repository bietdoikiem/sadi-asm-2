package com.rmit.demo.controller;

import com.rmit.demo.model.DeliveryDetail;
import com.rmit.demo.model.DeliveryNote;
import com.rmit.demo.service.DeliveryNoteService;
import com.rmit.demo.utils.DateUtils;
import com.rmit.demo.utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "/delivery-notes", "All DeliveryNotes fetched successfully.", deliveryNoteService.getAll());
    }

    // READ All DeliveryNote by page and size
    public ResponseEntity<Object> getAll(@RequestParam int page, @RequestParam int size) {
        List<DeliveryNote> deliveryNoteList = deliveryNoteService.getAll(page, size);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, String.format("/delivery-notes?page=%d&size=%d", page, size), String.format("DeliveryNotes (page %d - size %d) fetched successfully.", page, size), deliveryNoteList);
    }

    // READ All DeliveryNote by ID
    public ResponseEntity<Object> getOne(@PathVariable int id) {
        DeliveryNote deliveryNote = deliveryNoteService.getOne(id);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "/delivery-notes/" + deliveryNote.getId(),
                String.format("DeliveryNote %d fetch successfully.", deliveryNote.getId()), deliveryNote);
    }

    // CREATE One DeliveryNote
    public ResponseEntity<Object> saveOne(@RequestBody DeliveryNote deliveryNote) {
        DeliveryNote savedNote = deliveryNoteService.saveOne(deliveryNote);
        return ResponseHandler.generateResponse(HttpStatus.CREATED, true, "/delivery-notes",
                String.format("DeliveryNote %d created successfully.", savedNote.getId()), savedNote);
    }

    // UPDATE One DeliveryNote
    public ResponseEntity<Object> updateOne(@PathVariable int id, @RequestBody DeliveryNote deliveryNote) {
        DeliveryNote updatedNote = deliveryNoteService.updateOne(id, deliveryNote);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "/delivery-notes/" + updatedNote.getId(),
                String.format("DeliveryNote %d updated successfully.", updatedNote.getId()), updatedNote);
    }

    // DELETE One DeliveryNote
    public ResponseEntity<Object> deleteOne(@PathVariable int id) {
        int result = deliveryNoteService.deleteOne(id);
        return ResponseHandler.generateResponse(HttpStatus.ACCEPTED, true, String.format("/delivery-notes/%d", result),
                String.format("DeliveryNote %d deleted successfully.", result), null);
    }

    // READ ALL DeliveryDetails of a DeliveryNote
    @RequestMapping(path = "{id}/delivery-details", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllDeliveryDetailsByDeliveryNote(@PathVariable int id) {
        List<DeliveryDetail> deliveryDetailList = deliveryNoteService.getAllDeliveryDetailsByDeliveryNoteId(id);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, String.format("/delivery-notes/%d/delivery-details", id),
                String.format("All DeliveryDetail of DeliveryNote %d fetched successfully.", id), deliveryDetailList);
    }

    // FILTER ALL DeliveryNote between startDate and endDate
    @RequestMapping(path = "filter", method = RequestMethod.GET, params = {"startDate", "endDate"})
    public ResponseEntity<Object> filterByPeriod(@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date startDate, @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate) {
        List<DeliveryNote> deliveryNoteList = deliveryNoteService.filterByPeriod(startDate, endDate);
        String startDateStr = DateUtils.dateToString(startDate);
        String endDateStr = DateUtils.dateToString(endDate);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, String.format("/delivery-notes/filter?startDate=%s&endDate=%s", startDateStr, endDateStr),
                String.format("All DeliveryNote between %s and %s fetched successfully.", startDateStr, endDateStr), deliveryNoteList);
    }


}
