package br.com.andre.processos.services;

import br.com.andre.processos.exceptions.FailedSaveProcesso;
import br.com.andre.processos.exceptions.NoProcessFound;
import br.com.andre.processos.exceptions.ProcessoAlredyExistsException;
import br.com.andre.processos.exceptions.ProcessoNotFoundException;
import br.com.andre.processos.models.Processo;
import br.com.andre.processos.models.ProcessoDataChart;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface ProcessoService {
	
	Processo findProcesso(UUID id) throws ProcessoNotFoundException;
	
	Processo save(Processo processo) throws JsonParseException, JsonMappingException, IOException, ProcessoAlredyExistsException;

	Processo verificarSeExiste(String numero) throws ProcessoAlredyExistsException;
	
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
