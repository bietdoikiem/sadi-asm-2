package com.rmit.demo.service;

import com.rmit.demo.model.DeliveryDetail;
import com.rmit.demo.repository.DeliveryDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class DeliveryDetailService implements CrudService<DeliveryDetail> {

    @Autowired
    private DeliveryDetailRepository deliveryDetailRepository;

    // READ All DeliveryDetail
    public List<DeliveryDetail> getAll() {
        var it = deliveryDetailRepository.findAll();
        return new ArrayList<DeliveryDetail>(it);
    }

    // READ ALL DeliveryDetailsBy Pagination
    public List<DeliveryDetail> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<DeliveryDetail> allDeliveryDetails = deliveryDetailRepository.findAll(pageable);

        // Check if there has been content in the current page & size
        if (allDeliveryDetails.hasContent()) {
            return allDeliveryDetails.getContent();
        }
        return new ArrayList<DeliveryDetail>();
    }

    // READ One By ID
    public DeliveryDetail getOne(int id) {
        return deliveryDetailRepository.findById(id).orElse(null);

    }

    // CREATE DeliveryDetail
    public DeliveryDetail saveOne(DeliveryDetail deliveryDetail) {
        return deliveryDetailRepository.saveAndReset(deliveryDetail);
    }

    // UPDATE One DeliveryDetail
    public DeliveryDetail updateOne(int id, DeliveryDetail deliveryDetail) {
        DeliveryDetail foundDeliveryDetail = deliveryDetailRepository.findById(id).orElse(null);
        if (foundDeliveryDetail != null) {
            foundDeliveryDetail.setDeliveryNote(deliveryDetail.getDeliveryNote());
            foundDeliveryDetail.setProduct(deliveryDetail.getProduct());
            foundDeliveryDetail.setQuantity(deliveryDetail.getQuantity());
            return deliveryDetailRepository.saveAndReset(foundDeliveryDetail);
        }
        return null;
    }

    // DELETE One Delivery Detail
    public int deleteOne(int id) {
        DeliveryDetail foundDeliveryDetail = deliveryDetailRepository.findById(id).orElse(null);
        if (foundDeliveryDetail != null) {
            deliveryDetailRepository.delete(foundDeliveryDetail);
            return id;
        }
        return -1;
    }


}
