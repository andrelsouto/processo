package br.com.andre.processos.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.andre.processos.models.ProcessoDataChart;

@Repository
@Transactional(readOnly = true)
public interface ProcessoChartRepository extends CrudRepository<ProcessoDataChart, Integer>{

}
