package com.rmit.demo.repository;

import com.rmit.demo.model.ReceivingNote;

public interface ReceivingNoteRepositoryCustom {
    ReceivingNote saveAndReset(ReceivingNote receivingNote);
}
