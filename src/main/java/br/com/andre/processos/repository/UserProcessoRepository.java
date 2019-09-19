package br.com.andre.processos.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.andre.processos.models.UserProcesso;

@Repository
public interface UserProcessoRepository extends CrudRepository<UserProcesso, UUID> {

	@EntityGraph(value = "UserProcesso.getAuthorities", type = EntityGraph.EntityGraphType.FETCH)
	UserProcesso findByEmail(String email);
	
}
