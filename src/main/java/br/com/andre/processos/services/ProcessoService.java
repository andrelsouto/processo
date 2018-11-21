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
import br.com.andre.processos.exceptions.ProcessoNotFoundException;
import br.com.andre.processos.excpetions.NoProcessFound;
import br.com.andre.processos.models.Processo;
import br.com.andre.processos.models.ProcessoDataChart;

@Service
public interface ProcessoService {
	
	public Processo findProcesso(UUID id) throws ProcessoNotFoundException;
	
	public void save(String payload) throws JsonParseException, JsonMappingException, IOException;
	
	public byte[] gerarRelatorio() throws FileNotFoundException, IOException;
	
	public void saveAll(MultipartFile file) throws FailedSaveProcesso;
	
	public List<Processo> findAllProcessos() throws NoProcessFound;
	
	public Processo sentenciarProcesso(String numero) throws ProcessoNotFoundException;
	
	public List<Processo> findProcessosSentenciados() throws NoProcessFound;
	
	public List<Processo> findProcessosASentenciar() throws NoProcessFound;
	
	public ProcessoDataChart getDataChart();

}
