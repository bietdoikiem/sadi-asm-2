package com.rmit.demo.repository;

import com.rmit.demo.model.Product;
import com.rmit.demo.model.ReceiveDetail;
import com.rmit.demo.model.ReceivingNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ReceivingNoteRepository extends JpaRepository<ReceivingNote, Integer> {
}
