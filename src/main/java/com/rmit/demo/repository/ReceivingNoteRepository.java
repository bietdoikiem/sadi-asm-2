package com.rmit.demo.repository;

import com.rmit.demo.model.ReceivingNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceivingNoteRepository extends JpaRepository<ReceivingNote, Integer>, ReceivingNoteRepositoryCustom {
}
