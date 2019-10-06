package br.com.andre.processos.repository;

import java.util.List;
import java.util.UUID;

import br.com.andre.processos.models.enumerations.SituacaoProcessoEnum;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.andre.processos.models.Processo;

@Repository
@Transactional
public interface ProcessoRepository extends CrudRepository<Processo, UUID>{
	
	Processo findByNumero(String numero);
	
	List<Processo> findBySituacao(SituacaoProcessoEnum situacao);

	List<Processo> findBySituacaoNot(SituacaoProcessoEnum situacao);
	
	boolean existsByNumeroAndAnoMeta(String numero, String anoMeta);
	
	boolean existsByNumero(String numero);
	
	@Modifying
	@Query("update Processo p set p.deleted = true where id = ?1")
	void deleteProcesso(UUID id);

	
}
