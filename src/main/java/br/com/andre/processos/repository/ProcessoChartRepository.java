package br.com.andre.processos.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.andre.processos.models.ProcessoDataChart;

@Repository
public interface ProcessoChartRepository extends CrudRepository<ProcessoDataChart, Integer>{

}
