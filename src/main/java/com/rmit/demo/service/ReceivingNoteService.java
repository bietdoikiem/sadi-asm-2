package com.rmit.demo.service;

import com.rmit.demo.model.Order;
import com.rmit.demo.model.Product;
import com.rmit.demo.model.ReceiveDetail;
import com.rmit.demo.model.ReceivingNote;
import com.rmit.demo.repository.OrderRepository;
import com.rmit.demo.repository.ReceivingNoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class ReceivingNoteService {

    @Autowired
    private ReceivingNoteRepository receivingNoteRepository;

    //get a receive note by id
    public ReceivingNote getReceivingNoteById(int id) {
        return receivingNoteRepository.findById(id).orElse(null);
    }

    // get all receiving note
    public List<ReceivingNote> getAllReceivingNotes() {
        var it = receivingNoteRepository.findAll();
        var receivingNotes = new ArrayList<ReceivingNote>();
        it.forEach(receivingNotes::add);

        return receivingNotes;
    }

    // READ ALL receiving note Pagination
    public List<ReceivingNote> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ReceivingNote> allReceivingNotes = receivingNoteRepository.findAll(pageable);

        if (allReceivingNotes.hasContent()) {
            return allReceivingNotes.getContent();
        }
        return new ArrayList<>();

    }

    // Create receiving note
    public ReceivingNote saveReceivingNote(ReceivingNote receivingNote) {
        return receivingNoteRepository.save(receivingNote);
    }

    // Delete receiving note
    public String deleteReceivingNote(int id) {
        receivingNoteRepository.deleteById(id);
        return"Receiving note " + id + " removed!!";
    }

    // Update receiving note
    public ReceivingNote updateReceivingNote(ReceivingNote receivingNote) {
        ReceivingNote existingReceivingNote = receivingNoteRepository.findById(receivingNote.getId()).orElse(null);

        if (existingReceivingNote != null) {
            existingReceivingNote.setAll(receivingNote);
            return receivingNoteRepository.save(existingReceivingNote);
        }
        return null;
    }

    public ArrayList<ReceivingNote> getReceivingNotesByStartDateAndEndDate(String startDate, String endDate) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false);

        Date parsedStart = sdf.parse(startDate);
        Date parsedEnd = sdf.parse(endDate);

        ArrayList<ReceivingNote> receivingNotes = new ArrayList<>();
        receivingNoteRepository.findAll().forEach(receivingNotes::add);

        ArrayList<ReceivingNote> filteredReceivingNote = new ArrayList<>();
        for (ReceivingNote receivingNote : receivingNotes) {
            if ( parsedStart.getTime() <= receivingNote.getDate().getTime() && receivingNote.getDate().getTime() <= parsedEnd.getTime()) {
                filteredReceivingNote.add(receivingNote);
            }
        }
        return filteredReceivingNote;
    }
}
