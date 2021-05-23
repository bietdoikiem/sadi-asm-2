package com.rmit.demo.repository;

import com.rmit.demo.model.Product;
import com.rmit.demo.model.ReceiveDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import com.rmit.demo.model.ReceivingNote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ReceiveDetailRepository extends JpaRepository<ReceiveDetail, Integer> {
    List<ReceiveDetail> findReceiveDetailsByReceivingNote(ReceivingNote receivingNote);
}
