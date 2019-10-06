package br.com.andre.processos.controlers;

import br.com.andre.processos.exceptions.FailedSaveProcesso;
import br.com.andre.processos.exceptions.NoProcessFound;
import br.com.andre.processos.exceptions.ProcessoAlredyExistsException;
import br.com.andre.processos.exceptions.ProcessoNotFoundException;
import br.com.andre.processos.models.Processo;
import br.com.andre.processos.services.ArquivoService;
import br.com.andre.processos.services.ProcessoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/processo")
public class ProcessoController extends AbstractController {
	
	@Autowired
	private ProcessoService pService;
	@Autowired
	private ArquivoService aService;

	@GetMapping("/token")
	public Map<String, String> token(HttpSession session){
		return Collections.singletonMap("token", session.getId());
	}

	@GetMapping("/validarNumero")
	public ResponseEntity<Processo> validarNumero(@RequestParam String numeroProcesso) throws ProcessoAlredyExistsException {

		return ResponseEntity.ok(pService.verificarSeExiste(numeroProcesso));
	}

	@GetMapping("/getProcesso")
	public @ResponseBody ResponseEntity<?> getProcesso(@RequestParam UUID id){
		
		try {

			return ResponseEntity.ok(pService.findProcesso(id));
		} catch (ProcessoNotFoundException e) {

			return ResponseEntity.badRequest().body("Processo não encontrado");
		}
	}

	@GetMapping("chartData")
	public ResponseEntity<?> getChartData() {
		
		return ResponseEntity.ok(pService.getDataChart());
	}
	
	@GetMapping("getAll")
	public ResponseEntity<?> getAllProcessos(){
		
		try {
			return ResponseEntity.ok(pService.findAllProcessos());
		} catch (NoProcessFound e) {
			return ResponseEntity.badRequest().body("Nenhum processo encontrado");
		}
	}
	
	@GetMapping("sentenciados")
	public ResponseEntity<?> getProcessosSentenciados(){
		
		try {
			return ResponseEntity.ok(pService.findProcessosSentenciados());
		} catch (NoProcessFound e) {
			return ResponseEntity.badRequest().body("Nenhum processo encontrado");
		}
	}
	
	@GetMapping("naoSentenciados")
	public ResponseEntity<?> getProcessosNaoSentenciados(){
		
		try {
			return ResponseEntity.ok(pService.findProcessosASentenciar());
		} catch (NoProcessFound e) {
			return ResponseEntity.badRequest().body("Nenhum processo encontrado");
		}
	}
		@GetMapping("suspensos")
	public ResponseEntity<?> getProcessosSuspensos(){

		try {
			return ResponseEntity.ok(pService.findProcessosSuspensos());
		} catch (NoProcessFound e) {
			return ResponseEntity.badRequest().body("Nenhum processo encontrado");
		}
	}
	
	@PostMapping("{numero}")
	public ResponseEntity<?> sentenciarProcesso(@PathVariable("numero") String numero){
		
		try {
			
			return ResponseEntity.ok(pService.sentenciarProcesso(numero));
		} catch (ProcessoNotFoundException e) {
			
			return ResponseEntity.badRequest().body("Processo não pode ser senticado");
		}
	}

	@GetMapping("/sentenciar/{numero}")
	public ResponseEntity<?> sentenciarProcessoQrCode(@PathVariable("numero") String numero){

		try {
			pService.sentenciarProcesso(numero);
			return ResponseEntity.ok(new HashMap<String, String>().put("success", "Processo sentenciado"));
		} catch (ProcessoNotFoundException e) {

			return ResponseEntity.badRequest().body("Processo não pode ser senticado");
		}
	}

	@PostMapping("/suspender/{numero}")
	public ResponseEntity<?> suspenderProcessoQrCode(@PathVariable("numero") String numero){

		try {
			return ResponseEntity.ok(pService.suspenderProcesso(numero));
		} catch (ProcessoNotFoundException e) {

			return ResponseEntity.badRequest().body("Processo não pode ser senticado");
		}
	}

	@PostMapping("/saveProcesso")
	public ResponseEntity<Processo> saveProcesso(@RequestBody @Valid Processo processo) throws IOException {
		
			return ResponseEntity.ok(pService.save(processo));
	}
	
	@GetMapping("/gerarRelatorio/{id}")
	public  HttpEntity<byte[]> gerarRelatorio(@PathVariable("id") UUID id) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Disposition", "attachment;filename=\"processo.pdf\"");
		return new HttpEntity<byte[]>(aService.findById(id).getFile(), headers);
	}

	@GetMapping("/get-relatorio")
	public  HttpEntity<byte[]> getRelatorio() throws IOException {

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Disposition", "attachment;filename=\"processo.pdf\"");
		return new HttpEntity<byte[]>(pService.gerarRelatorio());
	}
	
	@PostMapping("/uploadFile")
	public void uploadFile(@RequestParam("file") MultipartFile file) throws IllegalStateException, IOException, FailedSaveProcesso {
		
			pService.saveAll(file);
	}
	
	@PostMapping("/delete/{id}")
	public void deleteProcesso(@PathVariable("id") String id) throws ProcessoNotFoundException {
		
			pService.delete(UUID.fromString(id));
	}

}
