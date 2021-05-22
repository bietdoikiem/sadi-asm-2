package com.rmit.demo.repository;

import com.rmit.demo.model.Product;
import com.rmit.demo.model.ReceivingNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ReceivingNoteRepository extends CrudRepository<ReceivingNote, Integer>, JpaRepository<ReceivingNote, Integer> {
}
