package br.com.claudemirojr.ticket.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.claudemirojr.ticket.model.entity.Solicitacao;
import br.com.claudemirojr.ticket.model.entity.dto.IAnaliseBacklog;
import br.com.claudemirojr.ticket.model.entity.enums.SolicitacaoStatus;

public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Long> {

	Page<Solicitacao> findByStatusAtualIn(List<SolicitacaoStatus> solicitacaoStatus, Pageable pageable);

	Page<Solicitacao> findByStatusAtualInAndIdGreaterThanEqual(List<SolicitacaoStatus> solicitacaoStatus, Long id, Pageable pageable);
	
	Page<Solicitacao> findByStatusAtualInAndDescricaoIgnoreCaseContaining(List<SolicitacaoStatus> solicitacaoStatus, String descricao, Pageable pageable);

	Optional<Solicitacao> findByIdAndStatusAtualIn(Long id, List<SolicitacaoStatus> solicitacaoStatus);
	
	
	Page<Solicitacao> findByIdGreaterThanEqual(Long id, Pageable pageable);

	Page<Solicitacao> findByDescricaoIgnoreCaseContaining(String descricao, Pageable pageable);
	
	
	Page<Solicitacao> findByStatusAtualInAndPrioridade(List<SolicitacaoStatus> solicitacaoStatus, Integer prioridade, Pageable pageable);
	
	

	
	@Query(	value = "SELECT a.id AS solicitacaoId,\n"
			+ "		 a.descricao AS solicitacaoDescricao,\n"
			+ "		 d.nome AS pessoaNome,\n"
			+ "		 c.nome AS sistemaNome,\n"
			+ "		 b.id AS backlogId,\n"
			+ "		 b.descricao AS backlogDescricao,\n"
			+ "		 case\n"
			+ "		 	WHEN e.backlog_id ISNULL THEN false\n"
			+ "		 	ELSE true\n"
			+ "		END AS cadastrado,"
			+ "		e.sprint_id AS sprintCadastrado,\n"
			+ "		e.id as sprintBacklogId"
			+ "		 \n"
			+ "FROM solicitacao AS a\n"
			+ "		JOIN backlog AS b ON ( a.id = b.solicitacao_id )\n"
			+ "		JOIN sistema AS c ON ( a.sistema_id = c.id )\n"
			+ "		JOIN pessoa AS d ON ( a.pessoa_id = d.id )\n"
			+ "		LEFT JOIN sprint_backlog AS e ON ( b.id = e.backlog_id ) \n"
			+ "		\n"
			+ "WHERE a.status_atual IN ('ANALISADA')\n"
			+ "	   AND a.prioridade= ?1 ",

			countQuery = "SELECT COUNT(1)\n"
					+ "		 \n"
					+ "FROM solicitacao AS a\n"
					+ "		JOIN backlog AS b ON ( a.id = b.solicitacao_id )\n"
					+ "		\n"
					+ "WHERE a.status_atual IN ('ANALISADA')\n"
					+ "	   AND a.prioridade = ?1 \n"
					+ "\n"
					+ "",
					
			nativeQuery = true

			)
	Page<IAnaliseBacklog> findByTeste(Integer prioridade, Pageable pageable);
	

}
