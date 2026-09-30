package siga.service;

import siga.model.Aluno;
import siga.dao.AlunoDAO;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Código INICIAL da atividade — camada de serviço incompleta.
 *
 * DESLIZE 3 — validação duplicada e divergente (etapa 3):
 * a regra da média aparece aqui E na camada de apresentação (Main), com
 * LIMITES DIFERENTES: aqui aceita até 10, lá aceita até 100. Quando a mesma
 * regra mora em dois lugares, elas divergem com o tempo — e ninguém sabe qual
 * é a verdadeira. A correção é centralizar a regra do domínio no serviço.
 *
 * PENDENTE (etapa 2): as operações de consulta, alteração e exclusão ainda
 * não foram implementadas, e a validação não está extraída em um método
 * privado reutilizável.
 *
 * Tarefa:
 *   - Etapa 2: implementar as operações do serviço e extrair validar(...);
 *   - Etapa 3: eliminar a duplicação da regra entre serviço e apresentação.
 */
public class ServicoAluno {

    private final AlunoDAO dao;

    public ServicoAluno(AlunoDAO dao) {   // injeção de dependência (DIP)
        this.dao = dao;
    }

    public void cadastrar(Aluno aluno) {
        // Validação escrita diretamente aqui (e repetida no Main, com outro limite).
       validar(aluno);

        try{
            dao.inserir(aluno); //inserir lançará exceção caso matricula esteja duplicada
        }catch(Exception ex){
            throw new IllegalStateException("Não foi possivel cadastrar o aluno: " + ex.getMessage()); // Aumenta o contexto da exceção (tratamento da mensagem de erro)
        }
    }

    public List<Aluno> listar() {
        return dao.listarTodos();
    }

    // TODO (etapa 2): implementar consultar(String matricula), lançando
    // exceção quando o aluno não for encontrado.
    public Aluno consultar(Aluno aluno) {
        try{
            return dao.buscarPorMatricula(aluno.getMatricula());
        }catch(Exception ex){
            throw new IllegalArgumentException("Aluno não encontrado: " + ex.getMessage());
        }
    }

    // TODO (etapa 2): implementar alterar(Aluno aluno), validando e
    // garantindo que o aluno exista antes de atualizar.
    public void alterar(Aluno aluno) {
        validar(aluno);

        try{
            dao.atualizar(aluno);
        } catch(Exception ex){
            throw new IllegalStateException("Os dados não foram alterados: " +  ex.getMessage());
        }
    }

    // TODO (etapa 2): implementar excluir(String matricula), garantindo
    // que o aluno exista antes de remover.
    public void excluir(String matricula) {
        try{
            dao.remover(matricula);
        } catch(Exception ex){
            throw new NoSuchElementException("Aluno não removido: " + ex.getMessage());
        }
    }

    private void validar(Aluno aluno) {
        if (aluno.getNome() == null || aluno.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório."); // Se não inserir nome para aqui
        }
        if (aluno.getMedia() < 0 || aluno.getMedia() > 10) {
            throw new IllegalArgumentException("Média deve estar entre 0 e 10."); // Se não tiver média entre 0 e 10 para aqui
        }
    }

}
