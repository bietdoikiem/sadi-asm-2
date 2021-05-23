package com.rmit.demo.service;

import com.rmit.demo.model.OrderDetail;
import com.rmit.demo.model.Product;
import com.rmit.demo.model.ReceiveDetail;
import com.rmit.demo.repository.OrderDetailRepository;
import com.rmit.demo.repository.ReceiveDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public class ReceiveDetailService {
    @Autowired
    private ReceiveDetailRepository receiveDetailRepository;


    // READ All receive detail
    public List<ReceiveDetail> getAllReceiveDetails() {
        var it = receiveDetailRepository.findAll();
        var receiveDetails = new ArrayList<ReceiveDetail>();
        it.forEach(receiveDetails::add);
        return receiveDetails;
    }

    // READ ALL receive details Pagination
    public List<ReceiveDetail> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ReceiveDetail> allReceiveDetails = receiveDetailRepository.findAll(pageable);

        if (allReceiveDetails.hasContent()) {
            return allReceiveDetails.getContent();
        }
        return new ArrayList<>();

    }

    // READ One receive detail by ID
    public ReceiveDetail getReceiveDetailById(int id) {
        return receiveDetailRepository.findById(id).orElse(null);
    }

    // POST New receive detail
    public ReceiveDetail saveReceiveDetail(ReceiveDetail receiveDetail) {
        return receiveDetailRepository.save(receiveDetail);
    }

    // UPDATE Here
    public ReceiveDetail updateReceiveDetail(int receiveDetailId, ReceiveDetail receiveDetail) {
        ReceiveDetail foundReceiveDetail = receiveDetailRepository.findById(receiveDetailId).orElse(null);
        if (foundReceiveDetail != null) {
            foundReceiveDetail.setAll(receiveDetail);

            return receiveDetailRepository.save(foundReceiveDetail);
        }
        return null;
    }

    // Do DELETE Here
    public String deleteReceiveDetail(int receiveDetailId) {
        ReceiveDetail receiveDetail = receiveDetailRepository.findById(receiveDetailId).orElse(null);
        if (receiveDetail != null) {
            receiveDetailRepository.delete(receiveDetail);
            return "order detail " + receiveDetailId + " removed!!";
        }
        return null;
    }
}
