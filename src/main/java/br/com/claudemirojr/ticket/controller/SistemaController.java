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
import br.com.claudemirojr.ticket.model.entity.Sistema;
import br.com.claudemirojr.ticket.model.entity.dto.DozerConverter;
import br.com.claudemirojr.ticket.model.entity.dto.SistemaDTO;
import br.com.claudemirojr.ticket.model.service.ISistemaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@Api(tags = { "SistemaEndpoint" })
@SwaggerDefinition(tags = { @Tag(name = "SistemaEndpoint", description = "descrição do endpoint") })
@RestController
@RequestMapping("/sistemas")
public class SistemaController {

	@Autowired
	private ISistemaService _service;

	private SistemaDTO conerterToSistemaDTO(Sistema sistema) {
		return DozerConverter.parseObject(sistema, SistemaDTO.class);
	}

	@ApiOperation(value = "Localizar um Sistema específico")
	@GetMapping("/{id}")
	public SistemaDTO FindById(@PathVariable Long id) {
		Sistema sistema = _service.FindById(id);

		return this.conerterToSistemaDTO(sistema);
	}

	@ApiOperation(value = "Listar todos os Sistemas")
	@GetMapping
	public ResponseEntity<?> findAll(@RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<Sistema> sistemaPage = _service.findAll(prm);

		Page<SistemaDTO> sistemaDTO = sistemaPage.map(this::conerterToSistemaDTO);

		return new ResponseEntity<>(sistemaDTO, HttpStatus.OK);
	}

	@ApiOperation(value = "buscar Sistema pelo id")
	@GetMapping("/findByIdGreaterThanEqual/{id}")
	public ResponseEntity<?> findByIdGreaterThanEqual(@PathVariable Long id, @RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<Sistema> sistemaPage = _service.findByIdGreaterThanEqual(id, prm);

		Page<SistemaDTO> sistemaDTO = sistemaPage.map(this::conerterToSistemaDTO);

		return new ResponseEntity<>(sistemaDTO, HttpStatus.OK);
	}

	@ApiOperation(value = "buscar Sistema pelo nome")
	@GetMapping("/findByNome/{nome}")
	public ResponseEntity<?> findByNomeContaining(@PathVariable String nome, @RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<Sistema> sistemaPage = _service.findByNomeContaining(nome, prm);

		Page<SistemaDTO> sistemaDTO = sistemaPage.map(this::conerterToSistemaDTO);

		return new ResponseEntity<>(sistemaDTO, HttpStatus.OK);
	}

	@ApiOperation(value = "buscar Sistema Ativo/Inativo")
	@GetMapping("/findByAtivo/{ativo}")
	public ResponseEntity<?> findByAtivo(@PathVariable Boolean ativo, @RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<Sistema> sistemaPage = _service.findByAtivo(ativo, prm);

		Page<SistemaDTO> sistemaDTO = sistemaPage.map(this::conerterToSistemaDTO);

		return new ResponseEntity<>(sistemaDTO, HttpStatus.OK);
	}

	@ApiOperation(value = "Salvar um Sistema")
	@PostMapping
	public SistemaDTO save(@RequestBody @Valid SistemaDTO sistemaDTO) {
		var entity = DozerConverter.parseObject(sistemaDTO, Sistema.class);

		Sistema sistema = _service.save(entity);

		sistemaDTO = this.conerterToSistemaDTO(sistema);

		return sistemaDTO;
	}

	@ApiOperation(value = "Atualizar um Sistema")
	@PutMapping("/{id}")
	public SistemaDTO update(@PathVariable Long id, @RequestBody @Valid SistemaDTO sistemaDTO) {
		Sistema sistemaExiste = _service.FindById(id);

		sistemaExiste.Atualizar(sistemaDTO.getNome(), sistemaDTO.getAtivo());

		Sistema sistema = _service.save(sistemaExiste);

		sistemaDTO = this.conerterToSistemaDTO(sistema);

		return sistemaDTO;
	}

	@ApiOperation(value = "Remover um Sistema")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {
		_service.FindById(id);

		_service.deleteById(id);

		return ResponseEntity.ok().build();
	}

}
