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
import br.com.claudemirojr.ticket.model.entity.ClienteSistema;
import br.com.claudemirojr.ticket.model.entity.Pessoa;
import br.com.claudemirojr.ticket.model.entity.Sistema;
import br.com.claudemirojr.ticket.model.entity.dto.ClienteDTO;
import br.com.claudemirojr.ticket.model.entity.dto.CodigoEntidadeDTO;
import br.com.claudemirojr.ticket.model.entity.dto.DozerConverter;
import br.com.claudemirojr.ticket.model.entity.dto.PessoaDTO;
import br.com.claudemirojr.ticket.model.entity.dto.SistemaDTO;
import br.com.claudemirojr.ticket.model.service.IClienteService;
import br.com.claudemirojr.ticket.model.service.IClienteSistemaService;
import br.com.claudemirojr.ticket.model.service.IPessoaService;
import br.com.claudemirojr.ticket.model.service.ISistemaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@Api(tags = { "ClienteEndpoint" })
@SwaggerDefinition(tags = { @Tag(name = "ClienteEndpoint", description = "descrição do endpoint") })
@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private IClienteService _service;

	@Autowired
	private ISistemaService _serviceSistema;

	@Autowired
	private IClienteSistemaService _serviceClienteSistema;

	@Autowired
	private IPessoaService _servicePessoa;

	// -----Converter
	private ClienteDTO conerterToClienteDTO(Cliente cliente) {
		return DozerConverter.parseObject(cliente, ClienteDTO.class);
	}

	private SistemaDTO conerterToSistemaDTO(Sistema sistema) {
		return DozerConverter.parseObject(sistema, SistemaDTO.class);
	}

	private PessoaDTO conerterToPessoaDTO(Pessoa pessoa) {
		return DozerConverter.parseObject(pessoa, PessoaDTO.class);
	}

	@ApiOperation(value = "Localizar um Cliente específico")
	@GetMapping("/{id}")
	public ClienteDTO FindById(@PathVariable Long id) {
		Cliente cliente = _service.FindById(id);

		return this.conerterToClienteDTO(cliente);
	}

	@ApiOperation(value = "Listar todos os Clientes")
	@GetMapping
	public ResponseEntity<?> findAll(@RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<Cliente> clientePage = _service.findAll(prm);

		Page<ClienteDTO> clienteDTO = clientePage.map(this::conerterToClienteDTO);

		return new ResponseEntity<>(clienteDTO, HttpStatus.OK);
	}

	@ApiOperation(value = "buscar Cliente pelo id")
	@GetMapping("/findByIdGreaterThanEqual/{id}")
	public ResponseEntity<?> findByIdGreaterThanEqual(@PathVariable Long id, @RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<Cliente> clientePage = _service.findByIdGreaterThanEqual(id, prm);

		Page<ClienteDTO> clienteDTO = clientePage.map(this::conerterToClienteDTO);

		return new ResponseEntity<>(clienteDTO, HttpStatus.OK);
	}

	@ApiOperation(value = "buscar Cliente pelo nome")
	@GetMapping("/findByNome/{nome}")
	public ResponseEntity<?> findByNomeContaining(@PathVariable String nome, @RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<Cliente> clientePage = _service.findByNomeContaining(nome, prm);

		Page<ClienteDTO> clienteDTO = clientePage.map(this::conerterToClienteDTO);

		return new ResponseEntity<>(clienteDTO, HttpStatus.OK);
	}

	@ApiOperation(value = "buscar Cliente Ativo/Inativo")
	@GetMapping("/findByAtivo/{ativo}")
	public ResponseEntity<?> findByAtivo(@PathVariable Boolean ativo, @RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<Cliente> clientePage = _service.findByAtivo(ativo, prm);

		Page<ClienteDTO> clienteDTO = clientePage.map(this::conerterToClienteDTO);

		return new ResponseEntity<>(clienteDTO, HttpStatus.OK);
	}

	@ApiOperation(value = "Salvar um Cliente")
	@PostMapping
	public ClienteDTO save(@RequestBody @Valid ClienteDTO clienteDTO) {
		var entity = DozerConverter.parseObject(clienteDTO, Cliente.class);

		Cliente cliente = _service.save(entity);

		clienteDTO = this.conerterToClienteDTO(cliente);

		return clienteDTO;
	}

	@ApiOperation(value = "Atualizar um Cliente")
	@PutMapping("/{id}")
	public ClienteDTO update(@PathVariable Long id, @RequestBody @Valid ClienteDTO clienteDTO) {
		Cliente clienteExiste = _service.FindById(id);

		clienteExiste.Atualizar(clienteDTO.getNome(), clienteDTO.getAtivo());

		Cliente cliente = _service.save(clienteExiste);

		clienteDTO = this.conerterToClienteDTO(cliente);

		return clienteDTO;
	}

	@ApiOperation(value = "Remover um Cliente")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {
		_service.FindById(id);

		_service.deleteById(id);

		return ResponseEntity.ok().build();
	}

	// ----- Sistema
	@ApiOperation(value = "Adicionar Sistema ao Cliente")
	@PostMapping("/{id}/sistemas")
	public ResponseEntity<?> addSistema(@PathVariable Long id,
			@RequestBody @Valid CodigoEntidadeDTO codigoEntidadeDTO) {
		Cliente clienteExiste = _service.FindById(id);

		Sistema sistemaExiste = _serviceSistema.FindById(codigoEntidadeDTO.getId());

		ClienteSistema clienteSistema = new ClienteSistema(null, clienteExiste, sistemaExiste, null);

		clienteSistema = _serviceClienteSistema.save(clienteSistema);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@ApiOperation(value = "Dado um Cliente, listar seus Sistemas")
	@GetMapping("/{id}/sistemas")
	public List<SistemaDTO> findAllSistema(@PathVariable Long id) {
		Cliente cliente = _service.FindById(id);

		List<ClienteSistema> clienteSistemas = _serviceClienteSistema.findByCliente(cliente);

		List<SistemaDTO> sistemaDTO = new ArrayList<SistemaDTO>();
		for (ClienteSistema clienteSistema : clienteSistemas) {
			sistemaDTO.add(this.conerterToSistemaDTO(clienteSistema.getSistema()));
		}

		return sistemaDTO;
	}

	@ApiOperation(value = "Remover Sistema do Cliente")
	@DeleteMapping("/{id}/sistemas/{idSistema}")
	public ResponseEntity<?> deleteSistemaById(@PathVariable Long id, @PathVariable Long idSistema) {
		Cliente cliente = _service.FindById(id);

		Sistema sistema = _serviceSistema.FindById(idSistema);

		ClienteSistema clienteSistema = _serviceClienteSistema.findByClienteAndSistema(cliente, sistema);

		_serviceClienteSistema.deleteById(clienteSistema.getId());

		return ResponseEntity.ok().build();
	}

	// ----- Pessoa
	@ApiOperation(value = "Dado um Cliente, listar suas Pessoas")
	@GetMapping("/{id}/pessoas")
	public ResponseEntity<?> findAllPessoas(@PathVariable Long id, @RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Cliente cliente = _service.FindById(id);

		Page<Pessoa> pessoaPage = _servicePessoa.findByCliente(cliente, prm);

		Page<PessoaDTO> pessoaDTO = pessoaPage.map(this::conerterToPessoaDTO);

		return new ResponseEntity<>(pessoaDTO, HttpStatus.OK);
	}

}
