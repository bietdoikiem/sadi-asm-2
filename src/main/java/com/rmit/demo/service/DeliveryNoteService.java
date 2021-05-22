package com.rmit.demo.service;


import com.rmit.demo.model.DeliveryDetail;
import com.rmit.demo.model.DeliveryNote;
import com.rmit.demo.repository.DeliveryDetailRepository;
import com.rmit.demo.repository.DeliveryNoteRepository;
import com.rmit.demo.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DeliveryNoteService implements CrudService<DeliveryNote> {

    @Autowired
    private DeliveryNoteRepository deliveryNoteRepository;

    @Autowired
    private DeliveryDetailRepository deliveryDetailRepository;

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

    // READ ALL DeliveryDetails of a DeliveryNote
    public List<DeliveryDetail> getAllDeliveryDetailsByDeliveryNoteId(int id) {
        DeliveryNote deliveryNote = deliveryNoteRepository.findById(id).orElseThrow(NullPointerException::new);
        return deliveryDetailRepository.findDeliveryDetailsByDeliveryNote(deliveryNote);
    }

    // FILTER DeliveryNote Between startDate and endDate
    public List<DeliveryNote> filterByPeriod(Date startDate, Date endDate) {
        // Normalize date to the beginning of the date (00:00:00)
        Date normStartDate = DateUtils.normalizeDateAtStart(startDate);
        // Normalize date to the end of teh date (23:59:59)
        Date normEndDate = DateUtils.normalizeDateAtEnd(endDate);
        return deliveryNoteRepository.findAllByDateBetween(normStartDate, normEndDate);
    }

}
