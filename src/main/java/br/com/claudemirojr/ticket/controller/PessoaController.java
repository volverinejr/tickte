package br.com.claudemirojr.ticket.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.claudemirojr.ticket.model.ParamsRequestModel;
import br.com.claudemirojr.ticket.model.entity.Cliente;
import br.com.claudemirojr.ticket.model.entity.Pessoa;
import br.com.claudemirojr.ticket.model.entity.PessoaTime;
import br.com.claudemirojr.ticket.model.entity.Time;
import br.com.claudemirojr.ticket.model.entity.dto.CodigoEntidadeDTO;
import br.com.claudemirojr.ticket.model.entity.dto.DozerConverter;
import br.com.claudemirojr.ticket.model.entity.dto.PessoaDTO;
import br.com.claudemirojr.ticket.model.entity.dto.PessoaTimeDTO;
import br.com.claudemirojr.ticket.model.entity.dto.TimeDTO;
import br.com.claudemirojr.ticket.model.service.IClienteService;
import br.com.claudemirojr.ticket.model.service.IPessoaService;
import br.com.claudemirojr.ticket.model.service.IPessoaTimeService;
import br.com.claudemirojr.ticket.model.service.ITimeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@Api(tags = { "PessoaEndpoint" })
@SwaggerDefinition(tags = { @Tag(name = "PessoaEndpoint", description = "descrição do endpoint") })
@RestController
@RequestMapping("/pessoas")
public class PessoaController {

	@Autowired
	private IPessoaService _service;

	@Autowired
	private IClienteService _serviceCliente;

	@Autowired
	private ITimeService _serviceTime;

	@Autowired
	private IPessoaTimeService _servicePessoaTime;

	// -----Converter
	private PessoaDTO conerterToPessoaDTO(Pessoa pessoa) {
		return DozerConverter.parseObject(pessoa, PessoaDTO.class);
	}

	private TimeDTO conerterToTimeDTO(Time time) {
		return DozerConverter.parseObject(time, TimeDTO.class);
	}

	@ApiOperation(value = "Localizar uma Pessoa específico")
	@GetMapping("/{id}")
	public PessoaDTO FindById(@PathVariable Long id) {
		Pessoa pessoa = _service.FindById(id);

		return this.conerterToPessoaDTO(pessoa);
	}

	@ApiOperation(value = "Listar todas as Pessoas")
	@GetMapping
	public ResponseEntity<?> findAll(@RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<Pessoa> pessoaPage = _service.findAll(prm);

		Page<PessoaDTO> pessoaDTO = pessoaPage.map(this::conerterToPessoaDTO);

		return new ResponseEntity<>(pessoaDTO, HttpStatus.OK);
	}

	@ApiOperation(value = "buscar Pessoa pelo id")
	@GetMapping("/findByIdGreaterThanEqual/{id}")
	public ResponseEntity<?> findByIdGreaterThanEqual(@PathVariable Long id, @RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<Pessoa> pessoaPage = _service.findByIdGreaterThanEqual(id, prm);

		Page<PessoaDTO> pessoaDTO = pessoaPage.map(this::conerterToPessoaDTO);

		return new ResponseEntity<>(pessoaDTO, HttpStatus.OK);
	}

	@ApiOperation(value = "buscar Pessoa pelo nome")
	@GetMapping("/findByNome/{nome}")
	public ResponseEntity<?> findByNomeContaining(@PathVariable String nome, @RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<Pessoa> pessoaPage = _service.findByNomeContaining(nome, prm);

		Page<PessoaDTO> pessoaDTO = pessoaPage.map(this::conerterToPessoaDTO);

		return new ResponseEntity<>(pessoaDTO, HttpStatus.OK);
	}

	@ApiOperation(value = "Salvar uma Pessoa")
	@PostMapping
	public PessoaDTO save(@RequestBody @Valid PessoaDTO pessoaDTO) {
		var entity = DozerConverter.parseObject(pessoaDTO, Pessoa.class);

		if (entity.getCliente() != null) {
			Cliente cliente = _serviceCliente.FindById(entity.getCliente().getId());
			entity.setCliente(cliente);
		}

		Pessoa pessoa = _service.save(entity);

		pessoaDTO = this.conerterToPessoaDTO(pessoa);

		return pessoaDTO;
	}

	@ApiOperation(value = "Atualizar uma Pessoa")
	@PutMapping("/{id}")
	public PessoaDTO update(@PathVariable Long id, @RequestBody @Valid PessoaDTO pessoaDTO) {
		Cliente clienteExiste = null;

		if (pessoaDTO.getCliente() != null) {
			clienteExiste = _serviceCliente.FindById(pessoaDTO.getCliente().getId());
		}

		Pessoa pessoaExiste = _service.FindById(id);

		pessoaExiste.Atualizar(pessoaDTO.getNome(), pessoaDTO.getAtivo(), clienteExiste);

		Pessoa pessoa = _service.save(pessoaExiste);

		pessoaDTO = this.conerterToPessoaDTO(pessoa);

		return pessoaDTO;
	}

	@ApiOperation(value = "Remover uma Pessoa")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {
		_service.FindById(id);

		_service.deleteById(id);

		return ResponseEntity.ok().build();
	}

	// ----- Time
	@ApiOperation(value = "Adicionar Time a Pessoa")
	@PostMapping("/{id}/times")
	public PessoaTimeDTO addSistema(@PathVariable Long id, @RequestBody @Valid CodigoEntidadeDTO codigoEntidadeDTO) {
		Pessoa pessoaExiste = _service.FindById(id);

		Time timeExiste = _serviceTime.FindById(codigoEntidadeDTO.getId());

		PessoaTime pessoaTime = new PessoaTime(null, pessoaExiste, timeExiste, null);

		pessoaTime = _servicePessoaTime.save(pessoaTime);

		return DozerConverter.parseObject(pessoaTime, PessoaTimeDTO.class);
	}

	@ApiOperation(value = "Dado uma Pessoa, listar seus Times")
	@GetMapping("/{id}/times")
	public List<TimeDTO> findAllPessoa(@PathVariable Long id) {
		Pessoa pessoa = _service.FindById(id);

		List<PessoaTime> pessoaTimes = _servicePessoaTime.findByPessoa(pessoa);

		List<TimeDTO> timeDTO = new ArrayList<TimeDTO>();
		for (PessoaTime pessoaTime : pessoaTimes) {
			timeDTO.add(this.conerterToTimeDTO(pessoaTime.getTime()));
		}

		return timeDTO;
	}

	@ApiOperation(value = "Remover Time da Pessoa")
	@DeleteMapping("/{id}/times/{idTime}")
	public ResponseEntity<?> deleteSistemaById(@PathVariable Long id, @PathVariable Long idTime) {
		Pessoa pessoa = _service.FindById(id);

		Time time = _serviceTime.FindById(idTime);

		PessoaTime pessoaTime = _servicePessoaTime.findByPessoaAndTime(pessoa, time);

		_servicePessoaTime.deleteById(pessoaTime.getId());

		return ResponseEntity.ok().build();
	}

	/*
	 * // ----- Time
	 * 
	 * @ApiOperation(value = "Vincular Time com a Pessoa")
	 * 
	 * @PostMapping("/{id}/times") public PessoaDTO addPessoa(@PathVariable Long
	 * id, @RequestBody @Valid TimeDTO timeDTO) { Pessoa pessoaExiste =
	 * _service.FindById(id);
	 * 
	 * Time timeExiste = _serviceTime.FindById(timeDTO.getId());
	 * 
	 * pessoaExiste.addTime(timeExiste);
	 * 
	 * _service.save(pessoaExiste);
	 * 
	 * return DozerConverter.parseObject(pessoaExiste, PessoaDTO.class); }
	 * 
	 * 
	 * @ApiOperation(value = "Listar os Times que a Pessoa faz parte")
	 * 
	 * @GetMapping("/{id}/times") public List<TimeDTO> findAllP(@PathVariable Long
	 * id) { Pessoa pessoa = _service.FindById(id);
	 * 
	 * List<TimeDTO> timeDTO = new ArrayList<TimeDTO>(); for (Time time :
	 * pessoa.getTimes()) { timeDTO.add(this.conerterToTimeDTO(time)); }
	 * 
	 * return timeDTO; }
	 * 
	 * @ApiOperation(value = "Remover Time de uma Pessoa")
	 * 
	 * @DeleteMapping("/{id}/times/{idTime}") public ResponseEntity<?>
	 * deleteTimeById(@PathVariable Long id, @PathVariable Long idTime) { Pessoa
	 * pessoaExiste = _service.FindById(id);
	 * 
	 * Time timeExiste = _serviceTime.FindById(idTime);
	 * 
	 * pessoaExiste.removeTime(timeExiste);
	 * 
	 * _service.save(pessoaExiste);
	 * 
	 * return ResponseEntity.ok().build(); }
	 */

}
