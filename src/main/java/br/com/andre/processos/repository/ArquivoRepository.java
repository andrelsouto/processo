package br.com.andre.processos.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.andre.processos.models.Arquivo;

@Repository
public interface ArquivoRepository extends CrudRepository<Arquivo, UUID>{

}
