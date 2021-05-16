package com.rmit.demo.service;

import com.rmit.demo.model.Order;
import com.rmit.demo.model.ReceivingNote;
import com.rmit.demo.repository.OrderRepository;
import com.rmit.demo.repository.ReceivingNoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class ReceivingNoteService {

    @Autowired
    private ReceivingNoteRepository receivingNoteRepository;

    public List<ReceivingNote> getAllReceivingNotes() {
        var it = receivingNoteRepository.findAll();
        var receivingNotes = new ArrayList<ReceivingNote>();
        it.forEach(receivingNotes::add);

        return receivingNotes;
    }

    public int saveReceivingNote(ReceivingNote receivingNote) {
        receivingNoteRepository.save(receivingNote);
        return receivingNote.getId();
    }

    public String deleteReceivingNote(int id) {
        receivingNoteRepository.deleteById(id);
        return"Receiving note " + id + " removed!!";
    }

    public ReceivingNote updateReceivingNote(ReceivingNote receivingNote) {
        ReceivingNote existingReceivingNote = receivingNoteRepository.findById(receivingNote.getId()).orElse(null);
        existingReceivingNote.setId(receivingNote.getId());
        existingReceivingNote.setDate(receivingNote.getDate());

        return receivingNoteRepository.save(existingReceivingNote);
    }
}
