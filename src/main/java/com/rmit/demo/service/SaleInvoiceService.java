package com.rmit.demo.service;

import com.rmit.demo.model.SaleDetail;
import com.rmit.demo.model.SaleInvoice;
import com.rmit.demo.repository.SaleDetailRepository;
import com.rmit.demo.repository.SaleInvoiceRepository;
import com.rmit.demo.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SaleInvoiceService implements CrudService<SaleInvoice> {

//    @Autowired
//    private EntityManager em;

    @Autowired
    private SaleInvoiceRepository saleInvoiceRepository;

    @Autowired
    private SaleDetailRepository saleDetailRepository;


    @Override
    public List<SaleInvoice> getAll() {
        var it = saleInvoiceRepository.findAll();
        return new ArrayList<>(it);
    }

    @Override
    public List<SaleInvoice> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<SaleInvoice> allSaleInvoices = saleInvoiceRepository.findAll(pageable);
        if (allSaleInvoices.hasContent()) {
            return allSaleInvoices.getContent();
        }
        return new ArrayList<SaleInvoice>();
    }

    @Override
    public SaleInvoice getOne(int id) {
        return saleInvoiceRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public SaleInvoice saveOne(SaleInvoice object) {
        for (SaleDetail saleDetail : object.getSaleDetailList()) {
            saleDetail.setSaleInvoice(object);
        }
        return saleInvoiceRepository.saveAndReset(object);
    }

    @Override
    public SaleInvoice updateOne(int id, SaleInvoice object) {
        SaleInvoice foundSaleInvoice = saleInvoiceRepository.findById(id).orElse(null);
        if (foundSaleInvoice != null) {
            foundSaleInvoice.setDate(object.getDate());
            foundSaleInvoice.setStaff(object.getStaff());
            foundSaleInvoice.setCustomer(object.getCustomer());
            foundSaleInvoice.setTotalValue(object.getTotalValue());
            return saleInvoiceRepository.saveAndReset(foundSaleInvoice);
        }
        return null;
    }

    @Override
    public int deleteOne(int id) {
        SaleInvoice saleInvoice = saleInvoiceRepository.findById(id).orElseThrow(NullPointerException::new);
        saleInvoiceRepository.delete(saleInvoice);
        return saleInvoice.getId();
    }

    // READ ALL SaleDetails of a SaleInvoice By Id
    public List<SaleDetail> getAllSaleDetailsBySaleInvoice(int id) {
        SaleInvoice saleInvoice = saleInvoiceRepository.findById(id).orElseThrow(NullPointerException::new);
        return saleDetailRepository.findSaleDetailsBySaleInvoice(saleInvoice);
    }

    public List<SaleInvoice> filterByPeriod(Date startDate, Date endDate) {
        // Format Date to String
        String startDateStr = DateUtils.dateToString(startDate);
        String endDateStr = DateUtils.dateToString(endDate);
        // Normalized Datetime to the beginning and very end of the date
        Date normStartDate = DateUtils.parseDatetime(startDateStr + " 00:00:00");
        Date normEndDate = DateUtils.parseDatetime(endDateStr + " 23:59:59");
        return saleInvoiceRepository.findAllByDateBetween(normStartDate, normEndDate);
    }
}
