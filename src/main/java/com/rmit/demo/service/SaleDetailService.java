package com.rmit.demo.service;

import com.rmit.demo.model.SaleDetail;
import com.rmit.demo.model.SaleInvoice;
import com.rmit.demo.repository.SaleDetailRepository;
import com.rmit.demo.repository.SaleInvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.TransactionScoped;
import javax.transaction.Transactional;
import java.util.ArrayList;

import java.util.List;

@Service
@Transactional
public class SaleDetailService implements CrudService<SaleDetail> {

    @Autowired
    private SaleDetailRepository saleDetailRepository;


    @Override
    public List<SaleDetail> getAll() {
        var it = saleDetailRepository.findAll();
        return new ArrayList<>(it);
    }

    @Override
    public List<SaleDetail> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<SaleDetail> saleDetails = saleDetailRepository.findAll(pageable);

        if (saleDetails.hasContent()) {
            return saleDetails.getContent();
        }
        return new ArrayList<>();
    }

    @Override
    public SaleDetail getOne(int id) {
        return saleDetailRepository.findById(id).orElse(null);
    }

    @Override
    public SaleDetail saveOne(SaleDetail object) {
        return saleDetailRepository.saveAndReset(object);
    }

    @Override
    @Transactional
    public SaleDetail updateOne(int id, SaleDetail object) {
        SaleDetail saleDetail = saleDetailRepository.findById(id).orElse(null);
        if (saleDetail != null) {
            saleDetail.setPrice(object.getPrice());
            saleDetail.setSaleInvoice(object.getSaleInvoice());
            saleDetail.setProduct(object.getProduct());
            saleDetail.setQuantity(object.getQuantity());
            return saleDetailRepository.saveAndReset(saleDetail);
        }
        return null;
    }

    @Override
    public int deleteOne(int id) {
        SaleDetail saleDetail = saleDetailRepository.findById(id).orElseThrow(NullPointerException::new);
        saleDetailRepository.delete(saleDetail);
        return saleDetail.getId();
    }
}
