package br.com.andre.processos.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

import org.exolab.castor.types.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.bean.CsvToBeanBuilder;

import br.com.andre.processos.exceptions.FailedSaveProcesso;
import br.com.andre.processos.exceptions.ProcessoNotFoundException;
import br.com.andre.processos.excpetions.NoProcessFound;
import br.com.andre.processos.models.Processo;
import br.com.andre.processos.models.ProcessoDataChart;
import br.com.andre.processos.models.ProcessoRelatorio;
import br.com.andre.processos.models.report.FontKey;
import br.com.andre.processos.repository.ProcessoChartRepository;
import br.com.andre.processos.repository.ProcessoRepository;
import br.com.andre.processos.utils.PDFReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class ProcessoServiceImp implements ProcessoService{

	@Autowired
	private ProcessoRepository pRepository;
	
	@Autowired
	private ProcessoChartRepository pChartRepository;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Autowired
	private PDFReport report;
	
	@Override
	public Processo findProcesso(UUID id) throws ProcessoNotFoundException {
		
		Optional<Processo> p = pRepository.findById(id);
		if (!p.isPresent()) {
			throw new ProcessoNotFoundException();
		}
		
		return p.get();
	}

	@Override
	public void save(String payload) throws JsonParseException, JsonMappingException, IOException {
		
		Processo p = mapper.readValue(payload, Processo.class);
		pRepository.save(p);
	}

	@Override
	public byte[] gerarRelatorio() throws IOException {
		
		List<Processo> processos = (List<Processo>) pRepository.findAll();
		List<ProcessoRelatorio> pr = new ArrayList<>();
		for (Processo processo : processos) {
			pr.add(new ProcessoRelatorio(processo));
		}
		Map<String, Object> p = new HashMap<>();
		p.put("NOME", "André");
		p.put("SansSerif", new FontKey("SansSerif", true, false));
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(processos);
		String fileName = UUID.randomUUID()+"processo.pdf";
		return report.reportGenarator(fileName, p, dataSource);
		
//		return Files.readAllBytes(Paths.get(fileName));
	}

	@Override
	public void saveAll(MultipartFile file) throws FailedSaveProcesso {
		
		try {
			
			List<Processo> processos = new CsvToBeanBuilder(new InputStreamReader(new ByteArrayInputStream(file.getBytes())))
					.withType(Processo.class).withSeparator(';').build().parse();
			processos.forEach(p -> p.setAnoMeta(LocalDate.now().getYear() + ""));
			processos.removeIf(p -> (p.getNumero().isEmpty() ||
					pRepository.existsByNumeroAndAnoMeta(p.getNumero(), p.getAnoMeta()) ||
					p.getNumero().trim().contains("+") || p.getNumero().length() == 1));
			if(processos.isEmpty()) return;
			processos.forEach(p -> {
				if(pRepository.existsByNumero(p.getNumero())) {
					p.setId(pRepository.findByNumero(p.getNumero()).getId());
					p.setAnoMeta(LocalDate.now().getYear() + "");
				}
			});
			
			pRepository.saveAll(processos);
		} catch (IllegalStateException | IOException e) {
			
			e.printStackTrace();
			throw new FailedSaveProcesso();
		} 
	}
	
	@Override
	public List<Processo> findAllProcessos() throws NoProcessFound {
		
		List<Processo> p = (List<Processo>) pRepository.findAll();
		if(p.isEmpty()) throw new NoProcessFound();
		
		return p;
	}

	@Override
	public Processo sentenciarProcesso(String numero) throws ProcessoNotFoundException {
		
		Processo p = pRepository.findByNumero(numero);
		if(p == null) throw new ProcessoNotFoundException();
		if(p.isSetenciado()) {
			p.setSetenciado(false);
		}
		else p.setSetenciado(true);
		pRepository.save(p);
		return p;
	}
	
	@Override
	public List<Processo> findProcessosSentenciados() throws NoProcessFound {
		
		List<Processo> p = pRepository.findBySetenciadoTrue();
		if(p.isEmpty()) throw new NoProcessFound();
		return p;
	}
	
	@Override
	public List<Processo> findProcessosASentenciar() throws NoProcessFound {
		
		List<Processo> p = pRepository.findBySetenciadoFalse();
		if(p.isEmpty()) throw new NoProcessFound();
		return p;
	}

	@Override
	public ProcessoDataChart getDataChart() {
		
		List<ProcessoDataChart> p = (List<ProcessoDataChart>) pChartRepository.findAll();
		
		return p.get(0);
	}

}
