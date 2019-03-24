package br.com.andre.processos.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.andre.processos.models.Processo;

@Repository
@Transactional
public interface ProcessoRepository extends CrudRepository<Processo, UUID>{
	
	public Processo findByNumeroAndDeletedFalse(String numero);
	
	public List<Processo> findBySetenciadoTrueAndDeletedFalse();
	
	public List<Processo> findBySetenciadoFalseAndDeletedFalse();
	
	public boolean existsByNumeroAndAnoMetaAndDeletedFalse(String numero, String anoMeta);
	
	public boolean existsByNumeroAndDeletedFalse(String numero);
	
	@Modifying
	@Query("update Processo p set p.deleted = true where id = ?1")
	public void deleteProcesso(UUID id);

	
}
