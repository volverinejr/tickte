package br.com.claudemirojr.ticket.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import br.com.claudemirojr.ticket.model.entity.Solicitacao;
import br.com.claudemirojr.ticket.model.entity.SolicitacaoMovimentacao;
import br.com.claudemirojr.ticket.model.entity.dto.DozerConverter;
import br.com.claudemirojr.ticket.model.entity.dto.SolicitacaoDTO;
import br.com.claudemirojr.ticket.model.entity.dto.SolicitacaoMovimentacaoDTO;
import br.com.claudemirojr.ticket.model.service.IPessoaService;
import br.com.claudemirojr.ticket.model.service.ISistemaService;
import br.com.claudemirojr.ticket.model.service.ISolicitacaoMovimentacaoService;
import br.com.claudemirojr.ticket.model.service.ISolicitacaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@Api(tags = { "SolicitacaoEndpoint" })
@SwaggerDefinition(tags = { @Tag(name = "SolicitacaoEndpoint", description = "descrição do endpoint") })
@RestController
@RequestMapping("/solicitacoes")
public class SolicitacaoController {

	@Autowired
	private ISolicitacaoService _service;

	@Autowired
	private ISistemaService _serviceSistema;

	@Autowired
	private IPessoaService _servicePessoa;

	@Autowired
	private ISolicitacaoMovimentacaoService _serviceSolicitacaoMovimentacao;

	// @Autowired
	// private IBacklogService _serviceSolicitacaoBacklog;

	// -----Converter
	private SolicitacaoDTO conerterToSolicitacaoDTO(Solicitacao solicitacao) {
		return DozerConverter.parseObject(solicitacao, SolicitacaoDTO.class);
	}

	private SolicitacaoMovimentacaoDTO conerterToSolicitacaoMovimentacaoDTO(
			SolicitacaoMovimentacao solicitacaoMovimentacao) {
		return DozerConverter.parseObject(solicitacaoMovimentacao, SolicitacaoMovimentacaoDTO.class);
	}

	// private BacklogDTO conerterToSolicitacaoBacklogDTO(Backlog
	// solicitacaoBacklog) {
	// return DozerConverter.parseObject(solicitacaoBacklog, BacklogDTO.class);
	// }

	@ApiOperation(value = "Localizar uma Solicitacao específica")
	@GetMapping("/{id}")
	public SolicitacaoDTO FindById(@PathVariable Long id) {
		Solicitacao solicitacao = _service.FindById(id);

		return this.conerterToSolicitacaoDTO(solicitacao);
	}

	@ApiOperation(value = "Listar todas as Solicitacao")
	@GetMapping
	public ResponseEntity<?> findAll(@RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<Solicitacao> solicitacaoPage = _service.findAll(prm);

		Page<SolicitacaoDTO> solicitacaoDTO = solicitacaoPage.map(this::conerterToSolicitacaoDTO);

		return new ResponseEntity<>(solicitacaoDTO, HttpStatus.OK);
	}

	@ApiOperation(value = "buscar Solicitacao pela id")
	@GetMapping("/findByIdGreaterThanEqual/{id}")
	public ResponseEntity<?> findByIdGreaterThanEqual(@PathVariable Long id, @RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<Solicitacao> solicitacaoPage = _service.findByIdGreaterThanEqual(id, prm);

		Page<SolicitacaoDTO> solicitacaoDTO = solicitacaoPage.map(this::conerterToSolicitacaoDTO);

		return new ResponseEntity<>(solicitacaoDTO, HttpStatus.OK);
	}

	@ApiOperation(value = "buscar Solicitacao pela descricao")
	@GetMapping("/findByDescricao/{descricao}")
	public ResponseEntity<?> findByDescricaoContaining(@PathVariable String descricao,
			@RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<Solicitacao> solicitacaoPage = _service.findByDescricaoContaining(descricao, prm);

		Page<SolicitacaoDTO> solicitacaoDTO = solicitacaoPage.map(this::conerterToSolicitacaoDTO);

		return new ResponseEntity<>(solicitacaoDTO, HttpStatus.OK);
	}

	@ApiOperation(value = "Salvar uma Solicitacao")
	@PostMapping
	public SolicitacaoDTO nova(@RequestBody @Valid SolicitacaoDTO solicitacaoDTO) {
		_serviceSistema.FindById(solicitacaoDTO.getSistema().getId());

		_servicePessoa.FindById(solicitacaoDTO.getPessoa().getId());

		var entity = DozerConverter.parseObject(solicitacaoDTO, Solicitacao.class);

		Solicitacao solicitacao = _service.nova(entity);

		solicitacaoDTO = this.conerterToSolicitacaoDTO(solicitacao);

		return solicitacaoDTO;
	}

	@ApiOperation(value = "Atualizar uma Solicitacao")
	@PutMapping("/{id}")
	public SolicitacaoDTO update(@PathVariable Long id, @RequestBody @Valid SolicitacaoDTO solicitacaoDTO) {
		Solicitacao solicitacaoExiste = _service.FindById(id);

		Sistema sistemaExiste = _serviceSistema.FindById(solicitacaoDTO.getSistema().getId());

		solicitacaoExiste.Atualizar(solicitacaoDTO.getDescricao(), sistemaExiste);

		Solicitacao solicitacao = _service.update(solicitacaoExiste);

		solicitacaoDTO = this.conerterToSolicitacaoDTO(solicitacao);

		return solicitacaoDTO;
	}

	/*
	 * // -- Analise
	 * 
	 * @ApiOperation(value = "Analisar uma Solicitacao")
	 * 
	 * @PutMapping("/{id}/analise") public SolicitacaoDTO analise(@PathVariable Long
	 * id,
	 * 
	 * @RequestBody @Valid SolicitacaoAnaliseDTO solicitacaoAnaliseDTO) {
	 * Solicitacao solicitacaoExiste = _service.FindById(id);
	 * 
	 * solicitacaoExiste.Analise(solicitacaoAnaliseDTO.getPrazoExpectativa(),
	 * solicitacaoAnaliseDTO.getAnaliseRisco(),
	 * solicitacaoAnaliseDTO.getPrioridade());
	 * 
	 * Solicitacao solicitacao = _service.analise(solicitacaoExiste);
	 * 
	 * SolicitacaoDTO solicitacaoDTO = this.conerterToSolicitacaoDTO(solicitacao);
	 * 
	 * return solicitacaoDTO; }
	 */
	// -- Movimentações
	@ApiOperation(value = "Dado uma Solicitação, listar suas movimentações")
	@GetMapping("/{id}/movimentacoes")
	public List<SolicitacaoMovimentacaoDTO> findAllMovimentacoes(@PathVariable Long id) {
		Solicitacao solicitacaoExiste = _service.FindById(id);

		List<SolicitacaoMovimentacao> solicitacaoMovimentacaos = _serviceSolicitacaoMovimentacao
				.findBySolicitacao(solicitacaoExiste);

		List<SolicitacaoMovimentacaoDTO> solicitacaoMovimentacaoDTO = new ArrayList<SolicitacaoMovimentacaoDTO>();
		for (SolicitacaoMovimentacao solicitacaoMovimentacao : solicitacaoMovimentacaos) {
			solicitacaoMovimentacaoDTO.add(this.conerterToSolicitacaoMovimentacaoDTO(solicitacaoMovimentacao));
		}

		return solicitacaoMovimentacaoDTO;
	}

	/*
	 * // -- Backlog
	 * 
	 * @ApiOperation(value = "Adicionar um Backlog a Solicitação")
	 * 
	 * @PostMapping("/{id}/backlog") public BacklogDTO addBacklog(@PathVariable Long
	 * id, @RequestBody @Valid BacklogDTO solicitacaoBacklogDTO) { Solicitacao
	 * solicitacaoExiste = _service.FindById(id);
	 * 
	 * var solicitacaoBacklog = DozerConverter.parseObject(solicitacaoBacklogDTO,
	 * Backlog.class); solicitacaoBacklog.setSolicitacao(solicitacaoExiste);
	 * 
	 * _serviceSolicitacaoBacklog.save(solicitacaoBacklog);
	 * 
	 * return DozerConverter.parseObject(solicitacaoBacklog, BacklogDTO.class); }
	 * 
	 * @ApiOperation(value = "Dado uma Solicitação, listar seus backlogs")
	 * 
	 * @GetMapping("/{id}/backlog") public List<BacklogDTO>
	 * findAllBacklogs(@PathVariable Long id) { Solicitacao solicitacaoExiste =
	 * _service.FindById(id);
	 * 
	 * List<Backlog> listSolicitacaoBacklog =
	 * _serviceSolicitacaoBacklog.findBySolicitacao(solicitacaoExiste);
	 * 
	 * List<BacklogDTO> solicitacaoBacklogDTO = new ArrayList<BacklogDTO>(); for
	 * (Backlog solicitacaoBacklog : listSolicitacaoBacklog) {
	 * solicitacaoBacklogDTO.add(this.conerterToSolicitacaoBacklogDTO(
	 * solicitacaoBacklog)); }
	 * 
	 * return solicitacaoBacklogDTO; }
	 * 
	 * @ApiOperation(value = "Remover Backlog da Solicitacao")
	 * 
	 * @DeleteMapping("/{id}/backlog/{idBacklog}") public ResponseEntity<?>
	 * deleteSistemaById(@PathVariable Long id, @PathVariable Long idBacklog) {
	 * Solicitacao solicitacaoExiste = _service.FindById(id);
	 * 
	 * _serviceSolicitacaoBacklog.findByIdAndSolicitacao(idBacklog,
	 * solicitacaoExiste);
	 * 
	 * _serviceSolicitacaoBacklog.deleteById(idBacklog);
	 * 
	 * return ResponseEntity.ok().build(); }
	 */

	@ApiOperation(value = "Informando a prioridade, listar todos as Solicitações que estam em Analise")
	@GetMapping("/analise/prioridade/{prioridade}")
	public ResponseEntity<?> findByAnalisePrioridade(@PathVariable Integer prioridade,
			@RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<Solicitacao> solicitacaoPage = _service.findByAnalisePrioridade(prioridade, prm);

		Page<SolicitacaoDTO> solicitacaoDTO = solicitacaoPage.map(this::conerterToSolicitacaoDTO);

		return new ResponseEntity<>(solicitacaoDTO, HttpStatus.OK);
	}

	@GetMapping("/sprint/{idSprint}/prioridade/{prioridade}")
	public ResponseEntity<?> FindByTeste(@PathVariable Long idSprint, @PathVariable Integer prioridade, @RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		return new ResponseEntity<>(_service.FindByTeste(idSprint, prioridade, prm), HttpStatus.OK);
	}

}
