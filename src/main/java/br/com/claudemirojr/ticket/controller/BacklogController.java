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
import br.com.claudemirojr.ticket.model.entity.Backlog;
import br.com.claudemirojr.ticket.model.entity.Solicitacao;
import br.com.claudemirojr.ticket.model.entity.dto.BacklogDTO;
import br.com.claudemirojr.ticket.model.entity.dto.DozerConverter;
import br.com.claudemirojr.ticket.model.service.IBacklogService;
import br.com.claudemirojr.ticket.model.service.ISolicitacaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@Api(tags = { "BacklogEndpoint" })
@SwaggerDefinition(tags = { @Tag(name = "BacklogEndpoint", description = "descrição do endpoint") })
@RestController
@RequestMapping("/backlogs")
public class BacklogController {

	@Autowired
	private IBacklogService _service;

	@Autowired
	private ISolicitacaoService _serviceSolicitacao;

	@Autowired
	private IBacklogService _serviceBacklog;

	// -----Converter
	private BacklogDTO conerterToBacklogDTO(Backlog backlog) {
		return DozerConverter.parseObject(backlog, BacklogDTO.class);
	}

	// -- Backlog
	@ApiOperation(value = "Adicionar um Backlog a Solicitação")
	@PostMapping
	public ResponseEntity<?> addBacklog(@RequestBody @Valid BacklogDTO solicitacaoBacklogDTO) {
		Solicitacao solicitacaoExiste = _serviceSolicitacao
				.FindBySolicitacaoStatusAtualAnalisada(solicitacaoBacklogDTO.getIdSolicitacao());

		var backlog = DozerConverter.parseObject(solicitacaoBacklogDTO, Backlog.class);
		backlog.setSolicitacao(solicitacaoExiste);

		_serviceBacklog.save(backlog);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@ApiOperation(value = "Atualizar Backlog da Solicitação")
	@PutMapping("/{id}")
	public ResponseEntity<?> updateBacklog(@PathVariable Long id,
			@RequestBody @Valid BacklogDTO solicitacaoBacklogDTO) {
		Backlog backlogExiste = _service.FindById(id);

		_serviceSolicitacao.FindBySolicitacaoStatusAtualAnalisada(backlogExiste.getSolicitacao().getId());

		backlogExiste.Atualizar(solicitacaoBacklogDTO.getDescricao());

		_serviceBacklog.save(backlogExiste);

		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@ApiOperation(value = "Listar todos os Backlogs da Solicitacao")
	@GetMapping("/solicitacao/{id}")
	public ResponseEntity<?> findAll(@PathVariable Long id, @RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Solicitacao solicitacao = _serviceSolicitacao.FindById(id);

		Page<Backlog> backlogPage = _service.findBySolicitacao(solicitacao, prm);

		Page<BacklogDTO> backlogDTO = backlogPage.map(this::conerterToBacklogDTO);

		return new ResponseEntity<>(backlogDTO, HttpStatus.OK);
	}

	@ApiOperation(value = "Remover um Backlog da Solicitação")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {
		Backlog backlogExiste = _service.FindById(id);

		_serviceSolicitacao.FindBySolicitacaoStatusAtualAnalisada(backlogExiste.getSolicitacao().getId());

		_service.deleteById(id);

		return ResponseEntity.ok().build();
	}

	@ApiOperation(value = "Localizar um Backlog específico")
	@GetMapping("/{id}")
	public BacklogDTO FindById(@PathVariable Long id) {
		Backlog backlog = _service.FindById(id);

		return this.conerterToBacklogDTO(backlog);
	}
	
	
	

}
