package com.rmit.demo.repository;

import com.rmit.demo.model.ReceiveDetail;
import com.rmit.demo.model.ReceivingNote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ReceivingNoteRepository extends CrudRepository<ReceivingNote, Integer> {
}
