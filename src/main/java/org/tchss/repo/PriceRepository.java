package org.tchss.repo;

import org.springframework.data.repository.CrudRepository;
import org.tchss.model.Price;

public interface PriceRepository extends CrudRepository<Price, Integer> {
}
