package com.deliverytech.delivery_api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deliverytech.delivery_api.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{
    Optional<Client> findByEmail(String email);

    boolean existsByEmail(String email);

    List<Client> findByActiveTrue();

    List<Client> findByNameContainingIgnoreCase(String name);
}
