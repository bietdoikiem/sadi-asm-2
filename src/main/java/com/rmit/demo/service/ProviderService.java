package com.rmit.demo.service;

import com.rmit.demo.model.Provider;
import com.rmit.demo.repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;

@Transactional
@Service
public class ProviderService {

    @Autowired
    private ProviderRepository providerRepository;

    // Save one provider
    public Provider saveProvider(Provider provider) {
        return providerRepository.save(provider);
    }

    // Update one provider
    public Provider updateProvider(int id, Provider provider) {
        Provider existingProvider = providerRepository.findById(id).orElse(null);
        if(existingProvider != null) {
            existingProvider.setId(id);
            existingProvider.setContactPerson(provider.getContactPerson());
            existingProvider.setPhone(provider.getPhone());
            existingProvider.setAddress(provider.getAddress());
            existingProvider.setEmail(provider.getEmail());
            existingProvider.setFax(provider.getFax());
            existingProvider.setName(provider.getName());
            return providerRepository.save(existingProvider);
        }
        return null;
    }

    // Update one provider
    public void deleteProvider(int id) {
        providerRepository.deleteById(id);
    }

    // Get all provider
    public ArrayList<Provider> getAllProviders() {
        return new ArrayList<>(providerRepository.findAll());
    }

    // Get all providers by name
    public ArrayList<Provider> getAllProvidersByName(String name) {
        ArrayList<Provider> providers = new ArrayList<>(providerRepository.findAllByName(name));
        return providers.size() == 0 ? null : providers;
    }

    // Get all providers by address
    public ArrayList<Provider> getAllProvidersByAddress(String name) {
        ArrayList<Provider> providers = new ArrayList<>(providerRepository.findAllByName(name));
        return providers.size() == 0 ? null : providers;
    }

    // Get all providers by phone number
    public ArrayList<Provider> getAllProvidersByPhone(String name) {
        ArrayList<Provider> providers = new ArrayList<>(providerRepository.findAllByName(name));
        return providers.size() == 0 ? null : providers;
    }

    // Get one customer by id
    public Provider getOne(int id) {
        return providerRepository.findById(id).orElse(null);
    }

    // Get all providers by pagination
    public ArrayList<Provider> getAllProviders(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Provider> allProvider = providerRepository.findAll(pageable);
        if(allProvider.hasContent()) {
            return new ArrayList<>(allProvider.getContent());
        }
        return new ArrayList<>();
    }
}