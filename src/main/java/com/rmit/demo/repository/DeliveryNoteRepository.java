package com.rmit.demo.repository;

import com.rmit.demo.model.DeliveryNote;
import com.rmit.demo.model.InventoryNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface DeliveryNoteRepository extends CrudRepository<DeliveryNote, Integer>, JpaRepository<DeliveryNote, Integer>, DeliveryNoteRepositoryCustom {
    List<DeliveryNote> findAllByDateBetween(Date startDate, Date endDate);
}
