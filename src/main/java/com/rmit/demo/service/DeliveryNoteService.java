package com.rmit.demo.service;


import com.rmit.demo.model.DeliveryDetail;
import com.rmit.demo.model.DeliveryNote;
import com.rmit.demo.repository.DeliveryNoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class DeliveryNoteService implements CrudService<DeliveryNote> {

    @Autowired
    private DeliveryNoteRepository deliveryNoteRepository;

    // READ All DeliveryNotes
    public List<DeliveryNote> getAll() {
        // Find all delivery notes via it repository
        var it = deliveryNoteRepository.findAll();
        return new ArrayList<DeliveryNote>(it);
    }

    // READ All DeliveryNotes by Pagination
    public List<DeliveryNote> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<DeliveryNote> allDeliveryNotes = deliveryNoteRepository.findAll(pageable);

        // Check if it has content in the current page and appropriate or not
        if (allDeliveryNotes.hasContent()) {
            return allDeliveryNotes.getContent();
        }
        return new ArrayList<>();
    }

    // READ One DeliveryNote by ID
    public DeliveryNote getOne(int id) {
        return deliveryNoteRepository.findById(id).orElse(null);
    }

    // CREATE One DeliveryNote (including One-Shot Array of deliveryDetail)
    public DeliveryNote saveOne(DeliveryNote deliveryNote) {
        for (DeliveryDetail deliveryDetail: deliveryNote.getDeliveryDetailList()) {
            deliveryDetail.setDeliveryNote(deliveryNote);
        }
        return deliveryNoteRepository.saveAndReset(deliveryNote);
    }

    // UPDATE One DeliveryNote
    public DeliveryNote updateOne(int id, DeliveryNote deliveryNote) {
        DeliveryNote foundDeliveryNote = deliveryNoteRepository.findById(id).orElse(null);
        if (foundDeliveryNote != null) {
            foundDeliveryNote.setDate(deliveryNote.getDate());
            foundDeliveryNote.setStaff(deliveryNote.getStaff());
            return deliveryNoteRepository.saveAndReset(foundDeliveryNote);
        }
        return null;
    }

    // DELETE One DeliveryNote
    public int deleteOne(int id) {
        DeliveryNote foundDeliveryNote = deliveryNoteRepository.findById(id).orElseThrow(NullPointerException::new);
            deliveryNoteRepository.delete(foundDeliveryNote);
            return foundDeliveryNote.getId();
    }


}
