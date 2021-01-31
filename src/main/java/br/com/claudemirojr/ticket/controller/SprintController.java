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
import br.com.claudemirojr.ticket.model.entity.Backlog;
import br.com.claudemirojr.ticket.model.entity.Sprint;
import br.com.claudemirojr.ticket.model.entity.SprintBacklog;
import br.com.claudemirojr.ticket.model.entity.Time;
import br.com.claudemirojr.ticket.model.entity.dto.DozerConverter;
import br.com.claudemirojr.ticket.model.entity.dto.SprintBacklogDTO;
import br.com.claudemirojr.ticket.model.entity.dto.SprintDTO;
import br.com.claudemirojr.ticket.model.service.IBacklogService;
import br.com.claudemirojr.ticket.model.service.ISprintBacklogService;
import br.com.claudemirojr.ticket.model.service.ISprintService;
import br.com.claudemirojr.ticket.model.service.ITimeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@Api(tags = { "SprintEndpoint" })
@SwaggerDefinition(tags = { @Tag(name = "SprintEndpoint", description = "descrição do endpoint") })
@RestController
@RequestMapping("/sprints")
public class SprintController {

	@Autowired
	private ISprintService _service;

	@Autowired
	private IBacklogService _serviceSolicitacaoBacklog;

	@Autowired
	private ISprintBacklogService _serviceSprintBacklog;

	@Autowired
	private ITimeService _serviceTime;

	// -----Converter
	private SprintDTO conerterToSprintDTO(Sprint sprint) {
		return DozerConverter.parseObject(sprint, SprintDTO.class);
	}

	private SprintBacklogDTO conerterToSprintSolicitacaoBacklogDTO(SprintBacklog sprintSolicitacaoBacklog) {
		return DozerConverter.parseObject(sprintSolicitacaoBacklog, SprintBacklogDTO.class);
	}
	
	
	//----- GET
	@ApiOperation(value = "Listar todos os Sprint")
	@GetMapping
	public ResponseEntity<?> findAll(@RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<Sprint> sprintPage = _service.findAll(prm);

		Page<SprintDTO> sprintDTO = sprintPage.map(this::conerterToSprintDTO);

		return new ResponseEntity<>(sprintDTO, HttpStatus.OK);
	}

	@ApiOperation(value = "Localizar um Sprint específico")
	@GetMapping("/{id}")
	public SprintDTO FindById(@PathVariable Long id) {
		Sprint sprint = _service.FindById(id);

		return this.conerterToSprintDTO(sprint);
	}

	@ApiOperation(value = "buscar Sprint por parte do nome")
	@GetMapping("/findByNome/{nome}")
	public ResponseEntity<?> findByNomeContaining(@PathVariable String nome, @RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<Sprint> sprintPage = _service.findByNomeContaining(nome, prm);

		Page<SprintDTO> sprintDTO = sprintPage.map(this::conerterToSprintDTO);

		return new ResponseEntity<>(sprintDTO, HttpStatus.OK);
	}

	@ApiOperation(value = "buscar Sprint >= ao id")
	@GetMapping("/findByIdGreaterThanEqual/{id}")
	public ResponseEntity<?> findByNomeContaining(@PathVariable Long id, @RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<Sprint> sprintPage = _service.findByIdGreaterThanEqual(id, prm);

		Page<SprintDTO> sprintDTO = sprintPage.map(this::conerterToSprintDTO);

		return new ResponseEntity<>(sprintDTO, HttpStatus.OK);
	}

	
	//----- POST/PUT
	@ApiOperation(value = "Salvar um Sprint")
	@PostMapping
	public ResponseEntity<SprintDTO> save(@RequestBody @Valid SprintDTO sprintDTO) {
		var entity = DozerConverter.parseObject(sprintDTO, Sprint.class);

		_serviceTime.FindById(sprintDTO.getTime().getId());

		Sprint sprint = _service.save(entity);

		sprintDTO = this.conerterToSprintDTO(sprint);

		return new ResponseEntity<>(sprintDTO, HttpStatus.CREATED);
	}

	@ApiOperation(value = "Atualizar um Sprint")
	@PutMapping("/{id}")
	public ResponseEntity<SprintDTO> update(@PathVariable Long id, @RequestBody @Valid SprintDTO sprintDTO) {
		Time timeEXiste = _serviceTime.FindById(sprintDTO.getTime().getId());

		Sprint sprintExiste = _service.FindById(id);

		sprintExiste.Atualizar(sprintDTO.getNome(), sprintDTO.getDataInicio(), sprintDTO.getDataFim(), timeEXiste);

		Sprint sprint = _service.save(sprintExiste);

		sprintDTO = this.conerterToSprintDTO(sprint);

		return new ResponseEntity<>(sprintDTO, HttpStatus.OK);
		// return sprintDTO;
	}

	//----- DELETE
	@ApiOperation(value = "Remover um Sprint")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {
		_service.FindById(id);

		_service.deleteById(id);

		return ResponseEntity.ok().build();
	}
	
	

	// -- Backlog
	@ApiOperation(value = "Adicionar um Backlog a Sprint")
	@PostMapping("/{id}/backlog/{idBacklog}")
	public SprintBacklogDTO addBacklog(@PathVariable Long id, @PathVariable Long idBacklog) {
		Sprint sprintExiste = _service.FindById(id);

		Backlog backlog = _serviceSolicitacaoBacklog.FindById(idBacklog);

		SprintBacklog sprintBacklog = new SprintBacklog(null, sprintExiste, backlog, false, null);

		sprintBacklog = _serviceSprintBacklog.save(sprintBacklog);

		return DozerConverter.parseObject(sprintBacklog, SprintBacklogDTO.class);
	}

	@ApiOperation(value = "Dado uma Sprint, listar seus backlogs")
	@GetMapping("/{id}/backlog")
	public List<SprintBacklogDTO> findAllBacklogs(@PathVariable Long id) {
		Sprint sprintExiste = _service.FindById(id);

		List<SprintBacklog> listSprintSolicitacaoBacklog = _serviceSprintBacklog.findBySprint(sprintExiste);

		List<SprintBacklogDTO> sprintSolicitacaoBacklogDTO = new ArrayList<SprintBacklogDTO>();
		for (SprintBacklog sprintSolicitacaoBacklog : listSprintSolicitacaoBacklog) {
			sprintSolicitacaoBacklogDTO.add(this.conerterToSprintSolicitacaoBacklogDTO(sprintSolicitacaoBacklog));
		}

		return sprintSolicitacaoBacklogDTO;
	}

	@ApiOperation(value = "Remover Backlog da Sprint")
	@DeleteMapping("/sprintbacklog/{id}")
	public ResponseEntity<?> deleteSistemaById(@PathVariable Long id) {
		_serviceSprintBacklog.FindById(id);

		_serviceSprintBacklog.deleteById(id);

		return ResponseEntity.ok().build();
	}

}
