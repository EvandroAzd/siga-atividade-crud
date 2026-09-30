package siga.dao;

import siga.Aluno;

import java.util.*;

/**
 * Código INICIAL da atividade — CRUD INCOMPLETO e com deslizes PROPOSITAIS.
 *
 * DESLIZE 1 — coleção interna exposta (etapa 3):
 * listarTodos() devolve a PRÓPRIA lista interna. Quem chamar o método pode
 * adicionar ou remover alunos diretamente, por fora do DAO, quebrando o
 * encapsulamento estudado na Aula 1. A correção é a cópia defensiva:
 * devolver uma nova lista com os mesmos elementos.
 *
 * DESLIZE 2 — exclusão silenciosa (etapa 3):
 * remover() não verifica se o aluno existia. Quando a matrícula não é
 * encontrada, nada acontece — mas o sistema informa sucesso ao usuário.
 * Falhar em silêncio é pior que falhar com erro.
 *
 * PENDENTE (etapa 1): o método atualizar() ainda não foi implementado, e
 * inserir() não impede matrícula duplicada.
 *
 * Tarefa:
 *   - Etapa 1: completar as quatro operações do CRUD;
 *   - Etapa 3: corrigir a coleção exposta e a exclusão silenciosa.
 */
public class AlunoDAOMemoria implements AlunoDAO {

    //private final List<Aluno> armazem = new ArrayList<>();
    //optei por utilizar Map para otimizar o tempo de busca em O(1) de vez O(n) do List
    private final Map<String, Aluno> armazem = new HashMap<>();

    @Override
    public void inserir(Aluno aluno) {
        // TODO (etapa 1): impedir matrícula duplicada, lançando IllegalStateException.
        if(armazem.containsKey(aluno.getMatricula())) {
            throw new IllegalStateException("Essa matricula já foi utilizada");
        }else{
            armazem.put(aluno.getMatricula(), aluno);
        }

    }

    @Override
    public Aluno buscarPorMatricula(String matricula) {
        if(armazem.get(matricula) != null) {
            return armazem.get(matricula);
        }else{
            throw new IllegalArgumentException("A matricula informada não existe!");
        }
    }

    @Override
    public List<Aluno> listarTodos() {
        // DESLIZE 1: devolve a própria coleção interna, sem cópia defensiva.
        //Deslize 1 resolvido, estamos recebendo uma cópia agora
        // estou populando a classe ArrayList a partir do Map
        return new ArrayList<>(armazem.values());
    }

    @Override
    public void atualizar(Aluno aluno) {
        // TODO (etapa 1): localizar o aluno pela matrícula e substituí-lo,
        // lançando exceção caso não exista.
        if(armazem.containsKey(aluno.getMatricula())) {
            armazem.put(aluno.getMatricula(), aluno);
        }else{
            throw new IllegalStateException("Aluno não encontrado!");
        }
    }

    @Override
    public void remover(String matricula) {
        // DESLIZE 2: não verifica se existia; falha em silêncio.
        if(armazem.containsKey(matricula)) {
            armazem.remove(matricula);
        }else{
            throw new NoSuchElementException("Matricula não encontrada.");
        }
        //Aluno encontrado = buscarPorMatricula(matricula);
        //armazem.remove(encontrado);   // remove(null) simplesmente não faz nada
    }
}
