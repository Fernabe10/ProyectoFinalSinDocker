package com.fgr.apirest.repository;

import com.fgr.apirest.entity.Item;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
    Optional<Item> findById(Integer id);
    List<Item> findByPrueba_IdPrueba(Integer idPrueba);
}
