package com.rmit.demo.repository;

import com.rmit.demo.model.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.ArrayList;

@Repository
@Transactional
public interface ProviderRepository extends CrudRepository<Provider, Integer>, JpaRepository<Provider, Integer>,ProviderRepositoryCustom {
    ArrayList<Provider> findAllByName(String name);
    ArrayList<Provider> findAllByAddress(String address);
    ArrayList<Provider> findAllByPhone(String phone);
    Provider findByName(String name);
    Provider findByAddress(String address);
    Provider findByPhone(String phone);
}