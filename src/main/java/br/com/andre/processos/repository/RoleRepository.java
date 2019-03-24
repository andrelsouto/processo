package br.com.andre.processos.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.andre.processos.models.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, UUID> {

}
