package br.com.andre.processos.services;

import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.andre.processos.models.Arquivo;

public interface ArquivoService {
	
	void save(MultipartFile file) throws IOException;
	
	Arquivo findById(UUID id);

}
