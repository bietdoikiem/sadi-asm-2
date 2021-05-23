package com.rmit.demo.service;

import com.rmit.demo.model.Customer;
import com.rmit.demo.model.SaleDetail;
import com.rmit.demo.model.SaleInvoice;
import com.rmit.demo.model.Staff;
import com.rmit.demo.repository.CustomerRepository;
import com.rmit.demo.repository.SaleDetailRepository;
import com.rmit.demo.repository.SaleInvoiceRepository;
import com.rmit.demo.repository.StaffRepository;
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

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private StaffRepository staffRepository;


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
        SaleInvoice foundSaleInvoice = saleInvoiceRepository.findById(id).orElseThrow(NullPointerException::new);
        foundSaleInvoice.setDate(object.getDate());
        foundSaleInvoice.setStaff(object.getStaff());
        foundSaleInvoice.setCustomer(object.getCustomer());
        foundSaleInvoice.setTotalValue(object.getTotalValue());
        return saleInvoiceRepository.saveAndReset(foundSaleInvoice);
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

    // FILTER All SaleInvoice in a period of time
    public List<SaleInvoice> filterByPeriod(Date startDate, Date endDate) {
        // Normalized Datetime to the beginning and very end of the date
        Date normStartDate = DateUtils.normalizeDateAtStart(startDate);
        Date normEndDate = DateUtils.normalizeDateAtEnd(endDate);
        return saleInvoiceRepository.findAllByDateBetween(normStartDate, normEndDate);
    }

    // READ ALL SaleInvoice By a Customer in a period
    public List<SaleInvoice> getAllSaleInvoicesByCustomerAndPeriod(int customerId, Date startDate, Date endDate) {
        // Find customer
        Customer customer = customerRepository.findById(customerId).orElseThrow(NullPointerException::new);
        // Normalized Datetime to the beginning and very end of the date
        Date normStartDate = DateUtils.normalizeDateAtStart(startDate);
        Date normEndDate = DateUtils.normalizeDateAtEnd(endDate);
        return saleInvoiceRepository.findSaleInvoicesByCustomerAndDateBetween(customer, normStartDate, normEndDate);
    }

    public List<SaleInvoice> getAllSaleInvoicesByStaffAndPeriod(int staffId, Date startDate, Date endDate) {
        // Find customer
        Staff staff = staffRepository.findById(staffId).orElseThrow(NullPointerException::new);
        // Normalized Datetime to the beginning and very end of the date
        Date normStartDate = DateUtils.normalizeDateAtStart(startDate);
        Date normEndDate = DateUtils.normalizeDateAtEnd(endDate);
        return saleInvoiceRepository.findSaleInvoicesByStaffAndDateBetween(staff, normStartDate, normEndDate);
    }


}
