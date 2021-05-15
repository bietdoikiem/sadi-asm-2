package com.rmit.demo.repository;

import com.rmit.demo.model.Provider;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ProviderRepository extends CrudRepository<Provider, Integer>, ProviderRepositoryCustom {
    Provider findByName(String name);
}
