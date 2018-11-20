package br.com.andre.processos.services;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.andre.processos.models.Arquivo;
import br.com.andre.processos.repository.ArquivoRepository;

@Service
public class ArquivoServiceImp implements ArquivoService{

	@Autowired
	private ArquivoRepository aRepository;
	
	@Override
	public void save(MultipartFile file) throws IOException {
		
		System.out.println(file.getSize());
		aRepository.save(new Arquivo(file.getBytes()));
	}

	@Override
	public Arquivo findById(UUID id) {
		
		return aRepository.findById(id).get();
	}

}
