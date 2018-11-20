package br.com.andre.processos.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.andre.processos.models.Processo;

@Repository
public interface ProcessoRepository extends CrudRepository<Processo, UUID>{
	
	public Processo findByNumero(String numero);
	
	public List<Processo> findBySetenciadoTrue();
	
	public List<Processo> findBySetenciadoFalse();
	
}
