package br.com.claudemirojr.ticket.controller;

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
import br.com.claudemirojr.ticket.model.entity.TipoTarefa;
import br.com.claudemirojr.ticket.model.entity.dto.DozerConverter;
import br.com.claudemirojr.ticket.model.entity.dto.TipoTarefaDTO;
import br.com.claudemirojr.ticket.model.service.ITipoTarefaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@Api(tags = { "TipoTarefaEndpoint" })
@SwaggerDefinition(tags = { @Tag(name = "TipoTarefaEndpoint", description = "descrição do endpoint") })
@RestController
@RequestMapping("/tipotarefa")
public class TipoTarefaController {

	@Autowired
	private ITipoTarefaService _service;

	// -----Converter
	private TipoTarefaDTO conerterToTipoTarefaDTO(TipoTarefa tipoTarefa) {
		return DozerConverter.parseObject(tipoTarefa, TipoTarefaDTO.class);
	}

	@ApiOperation(value = "Localizar um TipoTarefa específico")
	@GetMapping("/{id}")
	public TipoTarefaDTO FindById(@PathVariable Long id) {
		TipoTarefa tipoTarefa = _service.FindById(id);

		return this.conerterToTipoTarefaDTO(tipoTarefa);
	}

	@ApiOperation(value = "Listar todos os TipoTarefa")
	@GetMapping
	public ResponseEntity<?> findAll(@RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<TipoTarefa> tipoTarefaPage = _service.findAll(prm);

		Page<TipoTarefaDTO> tipoTarefaDTO = tipoTarefaPage.map(this::conerterToTipoTarefaDTO);

		return new ResponseEntity<>(tipoTarefaDTO, HttpStatus.OK);
	}

	@ApiOperation(value = "buscar TipoTarefa pelo nome")
	@GetMapping("/findByNome/{nome}")
	public ResponseEntity<?> findByNomeContaining(@PathVariable String nome, @RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<TipoTarefa> tipoTarefaPage = _service.findByNomeContaining(nome, prm);

		Page<TipoTarefaDTO> tipoTarefaDTO = tipoTarefaPage.map(this::conerterToTipoTarefaDTO);

		return new ResponseEntity<>(tipoTarefaDTO, HttpStatus.OK);
	}
	
	
	@ApiOperation(value = "buscar TipoTarefa pelo id")
	@GetMapping("/findByIdGreaterThanEqual/{id}")
	public ResponseEntity<?> findByIdGreaterThanEqual(@PathVariable Long id, @RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<TipoTarefa> tipoTarefaPage = _service.findByIdGreaterThanEqual(id, prm);

		Page<TipoTarefaDTO> tipoTarefaDTO = tipoTarefaPage.map(this::conerterToTipoTarefaDTO);

		return new ResponseEntity<>(tipoTarefaDTO, HttpStatus.OK);
	}
	

	@ApiOperation(value = "buscar TipoTarefa Ativo/Inativo")
	@GetMapping("/findByAtivo/{ativo}")
	public ResponseEntity<?> findByAtivo(@PathVariable Boolean ativo, @RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<TipoTarefa> tipoTarefaPage = _service.findByAtivo(ativo, prm);

		Page<TipoTarefaDTO> tipoTarefaDTO = tipoTarefaPage.map(this::conerterToTipoTarefaDTO);

		return new ResponseEntity<>(tipoTarefaDTO, HttpStatus.OK);
	}

	@ApiOperation(value = "Salvar um TipoTarefa")
	@PostMapping
	public TipoTarefaDTO save(@RequestBody @Valid TipoTarefaDTO tipoTarefaDTO) {
		var entity = DozerConverter.parseObject(tipoTarefaDTO, TipoTarefa.class);

		TipoTarefa tipoTarefa = _service.save(entity);

		tipoTarefaDTO = this.conerterToTipoTarefaDTO(tipoTarefa);

		return tipoTarefaDTO;
	}

	@ApiOperation(value = "Atualizar um TipoTarefa")
	@PutMapping("/{id}")
	public TipoTarefaDTO update(@PathVariable Long id, @RequestBody @Valid TipoTarefaDTO tipoTarefaDTO) {
		TipoTarefa tipoTarefaExiste = _service.FindById(id);

		tipoTarefaExiste.Atualizar(tipoTarefaDTO.getNome(), tipoTarefaDTO.getAtivo());

		TipoTarefa tipoTarefa = _service.save(tipoTarefaExiste);

		tipoTarefaDTO = this.conerterToTipoTarefaDTO(tipoTarefa);

		return tipoTarefaDTO;
	}

	@ApiOperation(value = "Remover um TipoTarefa")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {
		_service.FindById(id);

		_service.deleteById(id);

		return ResponseEntity.ok().build();
	}

}
