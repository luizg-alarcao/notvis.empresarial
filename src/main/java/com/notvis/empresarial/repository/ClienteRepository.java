package com.notvis.empresarial.repository;

import com.notvis.empresarial.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

//JpaRepository é uma interface do Spring Data JPA que já vem com
//vários métodos prontos pra uso como (findAll(), findById(UUID id), save(cliente), delete(cliente)

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
    Optional<Cliente> findByCpfCnpj(String cpfCnpj);
    boolean existsByCpfCnpj(String cpfCnpj);
}
