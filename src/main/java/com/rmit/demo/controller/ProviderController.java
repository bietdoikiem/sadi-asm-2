package com.rmit.demo.controller;
import com.rmit.demo.model.Provider;
import com.rmit.demo.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path="/providers")
public class ProviderController {
    private final ProviderService providerService;
    @Autowired
    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

//    @RequestMapping(path="/", method=RequestMethod.GET)
//    public List<Customer> getAllProducts() {
//        return customerService.getAllProducts();
//    }
//
//    @RequestMapping(path="converts/find", method=RequestMethod.GET)
//    public List<Customer> getConverts() {
//        return customerService.getConverts();
//    }

    @RequestMapping(path="", method=RequestMethod.POST)
    public int addProvider(@RequestBody Provider provider) {
        return providerService.saveProvider(provider);
    }
}
