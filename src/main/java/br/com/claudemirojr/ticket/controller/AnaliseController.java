package br.com.claudemirojr.ticket.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.claudemirojr.ticket.model.ParamsRequestModel;
import br.com.claudemirojr.ticket.model.entity.Solicitacao;
import br.com.claudemirojr.ticket.model.entity.dto.DozerConverter;
import br.com.claudemirojr.ticket.model.entity.dto.SolicitacaoAnaliseDTO;
import br.com.claudemirojr.ticket.model.service.IAnaliseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@Api(tags = { "AnaliseEndpoint" })
@SwaggerDefinition(tags = { @Tag(name = "AnaliseEndpoint", description = "descrição do endpoint") })
@RestController
@RequestMapping("/analise")
public class AnaliseController {

	@Autowired
	private IAnaliseService _serviceAnalise;

	// -----Converter
	private SolicitacaoAnaliseDTO conerterToSolicitacaoAnaliseDTO(Solicitacao solicitacao) {
		return DozerConverter.parseObject(solicitacao, SolicitacaoAnaliseDTO.class);
	}

	@ApiOperation(value = "Localizar uma Solicitacao específica")
	@GetMapping("/{id}")
	public SolicitacaoAnaliseDTO FindById(@PathVariable Long id) {
		Solicitacao solicitacao = _serviceAnalise.findById(id);

		return this.conerterToSolicitacaoAnaliseDTO(solicitacao);
	}

	@ApiOperation(value = "Listar todas as Solicitacao")
	@GetMapping
	public ResponseEntity<?> findAll(@RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<Solicitacao> solicitacaoPage = _serviceAnalise.findAll(prm);

		Page<SolicitacaoAnaliseDTO> solicitacaoAnaliseDTO = solicitacaoPage.map(this::conerterToSolicitacaoAnaliseDTO);

		return new ResponseEntity<>(solicitacaoAnaliseDTO, HttpStatus.OK);
	}

	@ApiOperation(value = "buscar Solicitacao pela id")
	@GetMapping("/findByIdGreaterThanEqual/{id}")
	public ResponseEntity<?> findByIdGreaterThanEqual(@PathVariable Long id, @RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<Solicitacao> solicitacaoPage = _serviceAnalise.findByIdGreaterThanEqual(id, prm);

		Page<SolicitacaoAnaliseDTO> solicitacaoAnaliseDTO = solicitacaoPage.map(this::conerterToSolicitacaoAnaliseDTO);

		return new ResponseEntity<>(solicitacaoAnaliseDTO, HttpStatus.OK);
	}

	@ApiOperation(value = "buscar Solicitacao pela descricao")
	@GetMapping("/findByDescricao/{descricao}")
	public ResponseEntity<?> findByDescricaoContaining(@PathVariable String descricao,
			@RequestParam Map<String, String> params) {
		ParamsRequestModel prm = new ParamsRequestModel(params);

		Page<Solicitacao> solicitacaoPage = _serviceAnalise.findByDescricaoContaining(descricao, prm);

		Page<SolicitacaoAnaliseDTO> solicitacaoAnaliseDTO = solicitacaoPage.map(this::conerterToSolicitacaoAnaliseDTO);

		return new ResponseEntity<>(solicitacaoAnaliseDTO, HttpStatus.OK);
	}

	@ApiOperation(value = "Analisar uma Solicitacao")
	@PutMapping("/{id}")
	public SolicitacaoAnaliseDTO analise(@PathVariable Long id,
			@RequestBody @Valid SolicitacaoAnaliseDTO solicitacaoAnaliseDTO) {
		Solicitacao solicitacaoExiste = _serviceAnalise.findById(id);

		solicitacaoExiste.Analise(solicitacaoAnaliseDTO.getPrazoExpectativa(), solicitacaoAnaliseDTO.getAnaliseRisco(),
				solicitacaoAnaliseDTO.getPrioridade());

		Solicitacao solicitacao = _serviceAnalise.analise(solicitacaoExiste);

		SolicitacaoAnaliseDTO responseDTO = this.conerterToSolicitacaoAnaliseDTO(solicitacao);

		return responseDTO;
	}

}
