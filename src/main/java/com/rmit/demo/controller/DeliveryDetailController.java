package com.rmit.demo.controller;

import com.rmit.demo.model.DeliveryDetail;
import com.rmit.demo.service.DeliveryDetailService;
import com.rmit.demo.utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(path = "/delivery-details")
public class DeliveryDetailController implements CrudController<DeliveryDetail> {

    private DeliveryDetailService deliveryDetailService;

    @Autowired
    public DeliveryDetailController(DeliveryDetailService deliveryDetailService) {
        this.deliveryDetailService = deliveryDetailService;
    }

    @Override
    public ResponseEntity<Object> getAll() {
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "/delivery-details", "All DeliveryDetail fetched successfully.", deliveryDetailService.getAll());
    }

    @Override
    public ResponseEntity<Object> getAll(int page, int size) {
        List<DeliveryDetail> allDeliveryDetails = deliveryDetailService.getAll(page, size);
        return ResponseHandler.generateResponse(HttpStatus.OK, true,
                String.format("/delivery-details?page=%d&size=%d", page, size), String.format("All DeliveryDetail (page %d - size %d) fetched successfully.", page, size), allDeliveryDetails);
    }

    @Override
    public ResponseEntity<Object> getOne(int id) {
        try {
            DeliveryDetail foundDeliveryDetail = deliveryDetailService.getOne(id);
            return ResponseHandler.generateResponse(HttpStatus.OK, true, String.format("/delivery-details/%d", foundDeliveryDetail.getId()),
                    String.format("DeliveryDetail %d fetched successfully.", foundDeliveryDetail.getId()), foundDeliveryDetail);
        } catch (NullPointerException e) {
            return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, false, String.format("/delivery-details/%d", id),
                    String.format("DeliveryDetail %d not found.", id), new HashMap<>());
        }
    }

    @Override
    public ResponseEntity<Object> saveOne(DeliveryDetail deliveryDetail) {
        DeliveryDetail saved = deliveryDetailService.saveOne(deliveryDetail);
        return ResponseHandler.generateResponse(HttpStatus.CREATED, true, String.format("/delivery-details/%d", saved.getId()),
                String.format("DeliveryDetail %d created successfully.", saved.getId()), saved);
    }

    @Override
    public ResponseEntity<Object> updateOne(int id, DeliveryDetail deliveryDetail) {
        DeliveryDetail updated = deliveryDetailService.updateOne(id, deliveryDetail);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, String.format("/delivery-details/%d", updated.getId()),
                String.format("DeliveryDetail %d updated successfully.", updated.getId()), updated);
    }

    @Override
    public ResponseEntity<Object> deleteOne(int id) {
        int result = deliveryDetailService.deleteOne(id);
        if (result >= 0) {
            return ResponseHandler.generateResponse(HttpStatus.ACCEPTED, true, String.format("/delivery-details/%d", result),
                    String.format("DeliveryDetail %d deleted successfully.", result), null);
        }
        return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, false, String.format("/delivery-details/%d", id),
                String.format("DeliveryDetail %d not found to be deleted.", id), null);
    }
}
