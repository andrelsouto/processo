package br.com.andre.processos.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import br.com.andre.processos.models.enumerations.SituacaoProcessoEnum;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.bean.CsvToBeanBuilder;

import br.com.andre.processos.exceptions.FailedSaveProcesso;
import br.com.andre.processos.exceptions.NoProcessFound;
import br.com.andre.processos.exceptions.ProcessoNotFoundException;
import br.com.andre.processos.models.Processo;
import br.com.andre.processos.models.ProcessoDataChart;
import br.com.andre.processos.models.ProcessoRelatorio;
import br.com.andre.processos.models.report.FontKey;
import br.com.andre.processos.repository.ProcessoChartRepository;
import br.com.andre.processos.repository.ProcessoRepository;
import br.com.andre.processos.utils.PDFReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import static br.com.andre.processos.utils.QRCode.gerarQRCode;

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
		
		List<Processo> processos = (List<Processo>) pRepository.findBySituacaoNot(SituacaoProcessoEnum.PROCESSO_SUSPENSO);
		List<ProcessoRelatorio> pr = new ArrayList<>();
		processos.stream().forEach(processo -> {
			try {
				pr.add(new ProcessoRelatorio(processo, gerarQRCode("http://processo.herokuapp.com/sentenciar/" + processo.getNumero())));
			} catch (WriterException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		Map<String, Object> p = new HashMap<>();
		p.put("NOME", "André");
		p.put("SansSerif", new FontKey("SansSerif", true, false));
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(pr);
		String fileName = UUID.randomUUID()+"processo.pdf";
		return report.reportGenarator(fileName, p, dataSource);
		
//		return Files.readAllBytes(Paths.get(fileName));
	}

	@Override
	@CacheEvict(value = { "processos", "processosNSent" }, allEntries = true)
	public void saveAll(MultipartFile file) throws FailedSaveProcesso {
		
		try {
			
			List<Processo> processos = new CsvToBeanBuilder(new InputStreamReader(new ByteArrayInputStream(file.getBytes())))
					.withType(Processo.class).withSeparator(';').build().parse();
			processos.forEach(p -> p.setAnoMeta(LocalDate.now().getYear() + ""));
			processos.removeIf(p -> (p.getNumero().isEmpty() ||
					pRepository.existsByNumeroAndAnoMetaAndDeletedFalse(p.getNumero(), p.getAnoMeta()) ||
					p.getNumero().trim().contains("+") || p.getNumero().length() == 1));
			if(processos.isEmpty()) return;
			processos.forEach(p -> {
				if(pRepository.existsByNumeroAndDeletedFalse(p.getNumero())) {
					p.setId(pRepository.findByNumeroAndDeletedFalse(p.getNumero()).getId());
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
	@Cacheable("processos")
	public List<Processo> findAllProcessos() throws NoProcessFound {
		
		List<Processo> p = (List<Processo>) pRepository.findAll();
		if(p.isEmpty()) throw new NoProcessFound();
		
		return p.stream().filter(processo -> !processo.isDeleted()).collect(Collectors.toList());
	}

	@Override
	@CacheEvict(value = {"processos", "processosSent", "processosNSent"}, allEntries = true)
	public Processo sentenciarProcesso(String numero) throws ProcessoNotFoundException {
		
		Processo p = pRepository.findByNumeroAndDeletedFalse(numero);
		if (p == null || p.getSituacao().equals(SituacaoProcessoEnum.PROCESSO_SUSPENSO)) {
			throw new ProcessoNotFoundException();
		} else if (p.getSituacao().equals(SituacaoProcessoEnum.PROCESSO_NAO_SENTENCIADO)) {
			p.setSituacao(SituacaoProcessoEnum.PROCESSO_SENTENCIADO);
		} else {
			p.setSituacao(SituacaoProcessoEnum.PROCESSO_NAO_SENTENCIADO);
		}
 		p = pRepository.save(p);
		return p;
	}

	@Override
	@CacheEvict(value = {"processos", "processosSent", "processosNSent"}, allEntries = true)
	public Processo suspenderProcesso(String numero) throws ProcessoNotFoundException {

		Processo p = pRepository.findByNumeroAndDeletedFalse(numero);
		if (p == null) {
			throw new ProcessoNotFoundException();
		} else if (p.getSituacao().equals(SituacaoProcessoEnum.PROCESSO_SUSPENSO)) {
			p.setSituacao(SituacaoProcessoEnum.PROCESSO_NAO_SENTENCIADO);
		} else {
			p.setSituacao(SituacaoProcessoEnum.PROCESSO_SUSPENSO);
		}
		pRepository.save(p);
		return p;
	}
	
	@Override
	@Cacheable("processosSent")
	public List<Processo> findProcessosSentenciados() throws NoProcessFound {
		
		List<Processo> p = pRepository.findBySituacao(SituacaoProcessoEnum.PROCESSO_SENTENCIADO);
		if(p.isEmpty()) throw new NoProcessFound();
		return p;
	}

	@Override
//	@Cacheable("processosSent")
	public List<Processo> findProcessosSuspensos() throws NoProcessFound {

		List<Processo> p = pRepository.findBySituacao(SituacaoProcessoEnum.PROCESSO_SUSPENSO);
		if(p.isEmpty()) throw new NoProcessFound();
		return p;
	}
	
	@Override
	@Cacheable("processosNSent")
	public List<Processo> findProcessosASentenciar() throws NoProcessFound {

		List<Processo> p = pRepository.findBySituacao(SituacaoProcessoEnum.PROCESSO_NAO_SENTENCIADO);
		if(p.isEmpty()) throw new NoProcessFound();
		return p;
	}

	@Override
	public List<ProcessoDataChart> getDataChart() {
		
		List<ProcessoDataChart> p = (List<ProcessoDataChart>) pChartRepository.findAll();
		
		return p;
	}

	@Override
	@CacheEvict(value = {"processos", "processosSent", "processosNSent"}, allEntries = true)
	public void delete(UUID id) throws ProcessoNotFoundException {
		// TODO Auto-generated method stub
		
		Optional<Processo> p = pRepository.findById(id);
		
		if (!p.isPresent()) throw new ProcessoNotFoundException();
		
		pRepository.deleteProcesso(p.get().getId());
	}

}
