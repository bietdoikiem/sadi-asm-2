package com.rmit.demo.controller;

import com.rmit.demo.model.Provider;
import com.rmit.demo.service.ProviderService;
import com.rmit.demo.utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping(path="/providers")
public class ProviderController implements CrudController<Provider>{
    private final ProviderService providerService;

    @Autowired
    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    // Get all providers
    @Override
    public ResponseEntity<Object> getAll() {
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "/providers", "All Providers fetched successfully.", providerService.getAllProviders());
    }

    // Get one provider by id
    @Override
    public ResponseEntity<Object> getOne(@PathVariable("id") int id){
        try {
            Provider provider = providerService.getOne(id);
            return ResponseHandler.generateResponse(HttpStatus.OK, true, "/providers/" + provider.getId(), String.format("Provider %d fetched successfully.", provider.getId()), provider);
        } catch (NullPointerException e) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "/providers/" + id, String.format("Provider %d not found.", id), new HashMap());
        }
    }

    // Update one provider by id
    @Override
    public ResponseEntity<Object> updateOne(@PathVariable int id, @RequestBody Provider provider) {
        Provider updatedProvider = providerService.updateProvider(id, provider);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, "/providers/" + updatedProvider.getId(), String.format("Provider %d updated successfully.", updatedProvider.getId()), updatedProvider);
    }

    // Delete one customer by id
    @Override
    public ResponseEntity<Object> deleteOne(@PathVariable int id) {
        providerService.deleteProvider(id);
        return ResponseHandler.generateResponse(HttpStatus.ACCEPTED, true, "/providers/" + id, String.format("Provider %d deleted successfully.", id), null);
    }

    // Add one provider
    @Override
    public ResponseEntity<Object> saveOne(@RequestBody Provider provider) {
        Provider savedProvider = providerService.saveProvider(provider);
        return ResponseHandler.generateResponse(HttpStatus.CREATED, true, "/providers/" + savedProvider.getId(), String.format("Provider %d created successfully.", savedProvider.getId()), savedProvider);
    }

    // Get all customers by pagination
    @Override
    public ResponseEntity<Object> getAll(int page, int size) {
        ArrayList<Provider> allProviders = providerService.getAllProviders(page, size);
        return ResponseHandler.generateResponse(HttpStatus.OK, true, String.format("/providers?page=%d&size=%d", page, size),
                String.format("All Providers (page %d - size %d) fetched successfully", page, size), allProviders);
    }

    // Get All by Name
    @RequestMapping(path="", params = "name")
    public @ResponseBody ResponseEntity<Object> getAllProvidersByName(@RequestParam("name") String name) {
        ArrayList<Provider> providers = providerService.getAllProvidersByName(name);
        return providers != null
                ? ResponseHandler.generateResponse(HttpStatus.OK, true, "/providers?name=" + name, "All Providers are " + name + " fetched successfully.", providers)
                : ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "/providers?name=" + name, String.format("Providers are %s not found.", name), new HashMap());
    }

    // Get All by Address
    @RequestMapping(path="", params = "address")
    public @ResponseBody ResponseEntity<Object> getAllProvidersByAddress(@RequestParam("address") String address) {
        ArrayList<Provider> providers = providerService.getAllProvidersByAddress(address);
        return providers != null
                ? ResponseHandler.generateResponse(HttpStatus.OK, true, "/providers?address=" + address, "All Providers are in " + address + " fetched successfully.", providers)
                : ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "/providers?address=" + address, String.format("Providers are in %s not found.", address), new HashMap());
    }

    // Get All by Phone number
    @RequestMapping(path="", params = "phone")
    public @ResponseBody ResponseEntity<Object> getAllCustomersByPhone(@RequestParam("phone") String phone) {
        ArrayList<Provider> providers = providerService.getAllProvidersByPhone(phone);
        return providers != null
                ? ResponseHandler.generateResponse(HttpStatus.OK, true, "/providers?phone=" + phone, "All Providers having the phone number of " + phone + " fetched successfully.", providers)
                : ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "/providers?phone=" + phone, String.format("Providers having the phone number of %s not found.", phone), new HashMap());
    }
}