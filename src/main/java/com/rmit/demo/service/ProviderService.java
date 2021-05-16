package com.rmit.demo.service;
import com.rmit.demo.model.Provider;
import com.rmit.demo.repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;

@Transactional
@Service
public class ProviderService {

    @Autowired
    private ProviderRepository providerRepository;

    public int saveProvider(Provider provider) {
        providerRepository.save(provider);
        return provider.getId();
    }

    public String updateProvider(Provider provider) {
        providerRepository.save(provider);
        return "Updated successfully!";
    }

    public String deleteProvider(Provider provider) {
        providerRepository.delete(provider);
        return "Delete successfully!";
    }

    public ArrayList<Provider> getAllProviders() {
        ArrayList<Provider> providers = new ArrayList<>();
        providerRepository.findAll().forEach(providers::add);;
        return providers;
    }

    public Provider getOne(int id) {
        return providerRepository.findById(id);
    }
}
