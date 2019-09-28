package br.com.andre.processos.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import br.com.andre.processos.exceptions.FailedSaveProcesso;
import br.com.andre.processos.exceptions.NoProcessFound;
import br.com.andre.processos.exceptions.ProcessoNotFoundException;
import br.com.andre.processos.models.Processo;
import br.com.andre.processos.models.ProcessoDataChart;

@Service
public interface ProcessoService {
	
	Processo findProcesso(UUID id) throws ProcessoNotFoundException;
	
	void save(String payload) throws JsonParseException, JsonMappingException, IOException;
	
	byte[] gerarRelatorio() throws FileNotFoundException, IOException;
	
	void saveAll(MultipartFile file) throws FailedSaveProcesso;
	
	List<Processo> findAllProcessos() throws NoProcessFound;
	
	Processo sentenciarProcesso(String numero) throws ProcessoNotFoundException;

	Processo suspenderProcesso(String numero) throws ProcessoNotFoundException;

	List<Processo> findProcessosSentenciados() throws NoProcessFound;
	
	List<Processo> findProcessosASentenciar() throws NoProcessFound;

	List<Processo> findProcessosSuspensos() throws NoProcessFound;

	List<ProcessoDataChart> getDataChart();
	
	void delete(UUID id) throws ProcessoNotFoundException;

}
