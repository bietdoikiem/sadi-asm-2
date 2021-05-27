package com.rmit.demo.service;

import com.rmit.demo.model.Order;
import com.rmit.demo.model.Product;
import com.rmit.demo.model.ReceiveDetail;
import com.rmit.demo.model.ReceivingNote;
import com.rmit.demo.repository.OrderRepository;
import com.rmit.demo.repository.ReceiveDetailRepository;
import com.rmit.demo.repository.ReceivingNoteRepository;
import com.rmit.demo.utils.DateUtils;
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
public class ReceivingNoteService implements CrudService<ReceivingNote>{

    @Autowired
    private ReceivingNoteRepository receivingNoteRepository;

    @Autowired
    private ReceiveDetailRepository receiveDetailRepository;

    //get a receive note by id
    public ReceivingNote getOne(int id) {
        return receivingNoteRepository.findById(id).orElse(null);
    }

    // get all receiving note
    public List<ReceivingNote> getAll() {
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
    // Create receiving note along with receiving details
    public ReceivingNote saveOne(ReceivingNote receivingNote) {
        for (ReceiveDetail receiveDetail: receivingNote.getReceiveDetailList()) {
            receiveDetail.setReceivingNote(receivingNote);
        }
        return receivingNoteRepository.saveAndReset(receivingNote);
    }

    // Delete receiving note
    public int deleteOne(int receivingNoteId) {
        ReceivingNote receivingNote = receivingNoteRepository.findById(receivingNoteId).orElseThrow(NullPointerException::new);
        receivingNoteRepository.delete(receivingNote);
        return receivingNote.getId();
    }

    // Update receiving note
    public ReceivingNote updateOne(int receivingNoteId, ReceivingNote receivingNote) {
        ReceivingNote existingReceivingNote = receivingNoteRepository.findById(receivingNoteId).orElseThrow(NullPointerException::new);
        if (existingReceivingNote != null) {
            existingReceivingNote.setAll(receivingNote);
            return receivingNoteRepository.saveAndReset(existingReceivingNote);
        }
        return null;
    }

    public ArrayList<ReceivingNote> getReceivingNotesByStartDateAndEndDate(String startDate, String endDate) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false);
        Date parsedStart = sdf.parse(startDate + " 00:00:00");
        Date parsedEnd = sdf.parse(endDate + " 23:59:59");

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

    public List<ReceiveDetail> getReceiveDetailListByReceivingNote(int receivingNoteId) {
        ReceivingNote receivingNote = receivingNoteRepository.findById(receivingNoteId).orElseThrow(NullPointerException::new);
        return receiveDetailRepository.findReceiveDetailsByReceivingNote(receivingNote);
    }
}
