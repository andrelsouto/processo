package br.com.andre.processos.controlers;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.andre.processos.exceptions.FailedSaveProcesso;
import br.com.andre.processos.exceptions.NoProcessFound;
import br.com.andre.processos.exceptions.ProcessoNotFoundException;
import br.com.andre.processos.services.ArquivoService;
import br.com.andre.processos.services.ProcessoService;

import javax.servlet.http.HttpSession;

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
	
	@PostMapping("/saveProcesso")
	public @ResponseBody ResponseEntity<?> saveProcesso(@RequestBody String payload){
		
		try {
			
			pService.save(payload);
			return ResponseEntity.ok("Processo salvo com sucesso");
		}catch (Exception e) {
			
			return ResponseEntity.badRequest().body("Falha ao salvar processo");
		}
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
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) throws IllegalStateException, IOException{
		
		try {
			pService.saveAll(file);
			return ResponseEntity.ok("Processos salvos com sucesso");
		} catch (FailedSaveProcesso e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body("Erro ao salvar arquivo");
		}
	}
	
	@PostMapping("/delete/{id}")
	public ResponseEntity<?> deleteProcesso(@PathVariable("id") String id){
		
		try {
			pService.delete(UUID.fromString(id));
			return ResponseEntity.ok("Processo deletetado com sucesso!"); 
		} catch (ProcessoNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.badRequest().body("Processo não encontrado!");
		}
	}

}
