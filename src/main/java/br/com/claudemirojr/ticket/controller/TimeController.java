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
import br.com.claudemirojr.ticket.model.entity.Time;
import br.com.claudemirojr.ticket.model.entity.dto.DozerConverter;
import br.com.claudemirojr.ticket.model.entity.dto.TimeDTO;
import br.com.claudemirojr.ticket.model.service.ITimeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@Api(tags = { "TimeEndpoint" })
@SwaggerDefinition(tags = { @Tag(name = "TimeEndpoint", description = "descrição do endpoint") })
@RestController
@RequestMapping("/times")
public class TimeController {

	@Autowired
	private ITimeService _service;

	// -----Converter
	private TimeDTO conerterToTimeDTO(Time time) {
		return DozerConverter.parseObject(time, TimeDTO.class);
	}

	@ApiOperation(value = "Listar todos os Times")
	@GetMapping
	public ResponseEntity<?> findAll(@RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<Time> timePage = _service.findAll(prm);

		Page<TimeDTO> timeDTO = timePage.map(this::conerterToTimeDTO);

		return new ResponseEntity<>(timeDTO, HttpStatus.OK);
	}

	@ApiOperation(value = "Localizar um Time específico")
	@GetMapping("/{id}")
	public TimeDTO FindById(@PathVariable Long id) {
		Time time = _service.FindById(id);

		return this.conerterToTimeDTO(time);
	}


	@ApiOperation(value = "buscar Time pelo nome")
	@GetMapping("/findByNome/{nome}")
	public ResponseEntity<?> findByNomeContaining(@PathVariable String nome, @RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<Time> timePage = _service.findByNomeContaining(nome, prm);

		Page<TimeDTO> timeDTO = timePage.map(this::conerterToTimeDTO);

		return new ResponseEntity<>(timeDTO, HttpStatus.OK);
	}

	@ApiOperation(value = "buscar Time pelo id")
	@GetMapping("/findByIdGreaterThanEqual/{id}")
	public ResponseEntity<?> findByIdGreaterThanEqual(@PathVariable Long id, @RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<Time> timePage = _service.findByIdGreaterThanEqual(id, prm);

		Page<TimeDTO> timeDTO = timePage.map(this::conerterToTimeDTO);

		return new ResponseEntity<>(timeDTO, HttpStatus.OK);
	}

	
	@ApiOperation(value = "buscar Time Ativo/Inativo")
	@GetMapping("/findByAtivo/{ativo}")
	public ResponseEntity<?> findByAtivo(@PathVariable Boolean ativo, @RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<Time> timePage = _service.findByAtivo(ativo, prm);

		Page<TimeDTO> timeDTO = timePage.map(this::conerterToTimeDTO);

		return new ResponseEntity<>(timeDTO, HttpStatus.OK);
	}

	@ApiOperation(value = "Salvar um Time")
	@PostMapping
	public TimeDTO save(@RequestBody @Valid TimeDTO timeDTO) {
		var entity = DozerConverter.parseObject(timeDTO, Time.class);

		Time time = _service.save(entity);

		timeDTO = this.conerterToTimeDTO(time);

		return timeDTO;
	}

	@ApiOperation(value = "Atualizar um Time")
	@PutMapping("/{id}")
	public TimeDTO update(@PathVariable Long id, @RequestBody @Valid TimeDTO timeDTO) {
		Time timeExiste = _service.FindById(id);

		timeExiste.Atualizar(timeDTO.getNome(), timeDTO.getAtivo());

		Time time = _service.save(timeExiste);

		timeDTO = this.conerterToTimeDTO(time);

		return timeDTO;
	}

	@ApiOperation(value = "Remover um Time")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {
		_service.FindById(id);

		_service.deleteById(id);

		return ResponseEntity.ok().build();
	}

}
