package com.rmit.demo.service;


import com.rmit.demo.model.DeliveryNote;
import com.rmit.demo.repository.DeliveryNoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class DeliveryNoteService {

    @Autowired
    private DeliveryNoteRepository deliveryNoteRepository;

    // READ All DeliveryNotes
    public List<DeliveryNote> getAllDeliveryNotes() {
        // Find all delivery notes via it repository
        var it = deliveryNoteRepository.findAll();
        return new ArrayList<DeliveryNote>(it);
    }

    // READ All DeliveryNotes by Pagination
    public List<DeliveryNote> getAllDeliveryNotes(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<DeliveryNote> allDeliveryNotes = deliveryNoteRepository.findAll(pageable);

        // Check if it has content in the current page and appropriate or not
        if (allDeliveryNotes.hasContent()) {
            return allDeliveryNotes.getContent();
        }
        return new ArrayList<>();
    }

    // READ One DeliveryNote by ID
    public DeliveryNote getDeliveryNoteById(int id) {
        return deliveryNoteRepository.findById(id).orElse(null);
    }

    // CREATE One DeliveryNote
    public DeliveryNote saveDeliveryNote(DeliveryNote deliveryNote) {
        return deliveryNoteRepository.saveAndReset(deliveryNote);
    }

    // UPDATE One DeliveryNote
    public DeliveryNote updateDeliveryNote(int id, DeliveryNote deliveryNote) {
        DeliveryNote foundDeliveryNote = deliveryNoteRepository.findById(id).orElse(null);
        if (foundDeliveryNote != null) {
            foundDeliveryNote.setDate(deliveryNote.getDate());
            foundDeliveryNote.setStaff(deliveryNote.getStaff());
            return deliveryNoteRepository.saveAndReset(foundDeliveryNote);
        }
        return null;
    }

    // DELETE One DeliveryNote
    public int deleteDeliveryNote(int id) {
        DeliveryNote foundDeliveryNote = deliveryNoteRepository.findById(id).orElseThrow(NullPointerException::new);
            deliveryNoteRepository.delete(foundDeliveryNote);
            return foundDeliveryNote.getId();
    }


}
