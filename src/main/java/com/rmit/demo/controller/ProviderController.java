package com.rmit.demo.controller;
import com.rmit.demo.model.Provider;
import com.rmit.demo.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

@RestController
@RequestMapping(path="/providers")
public class ProviderController {
    private final ProviderService providerService;
    @Autowired
    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    // Get all provider
    @RequestMapping(path="", method=RequestMethod.GET)
    public ArrayList<Provider> getAllProviders() {
        return providerService.getAllProviders();
    }

    // Get one provider by id
    @RequestMapping(value="/search/id={id}", method = RequestMethod.GET)
    public Provider getOne(@PathVariable("id") int id){
        return providerService.getOne(id);
    }

    // Update one provider by id
    @RequestMapping(value = "/id={id}", method = RequestMethod.PUT)
    public String updateProvider(@PathVariable int id, @RequestBody Provider provider) {
        provider.setId(id);
        return providerService.updateProvider(provider);
    }

    // Delete one customer by id
    @RequestMapping(value = "/id={id}", method = RequestMethod.DELETE)
    public String deleteProvider(@PathVariable int id) {
        return providerService.deleteProvider(getOne(id));
    }


    // Add one provider
    @RequestMapping(path="", method=RequestMethod.POST)
    public int addProvider(@RequestBody Provider provider) {
        return providerService.saveProvider(provider);
    }
}