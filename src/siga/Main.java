package siga;

import siga.dao.AlunoDAO;
import siga.dao.AlunoDAOMemoria;
import siga.model.Aluno;
import siga.service.ServicoAluno;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== SIGA - CRUD de Alunos ===\n");

        AlunoDAO dao = new AlunoDAOMemoria();
        ServicoAluno servico = new ServicoAluno(dao);

        // --- CREATE ---
        cadastrar(servico, new Aluno("Maria Silva", "2026001", 8.5));
        cadastrar(servico, new Aluno("João Souza", "2026002", 6.0));
        System.out.println();

        // --- READ ---
        System.out.println("Alunos cadastrados:");
        for (Aluno aluno : servico.listar()) {
            System.out.println("  " + aluno);
        }
        System.out.println();

        // --- UPDATE ---
        alterar(servico, new Aluno("Maria Silva", "2026001", 9.0));
        System.out.println();

        // --- DELETE ---
        excluir(servico, "2026002");
        System.out.println();

        // --- READ final ---
        System.out.println("Lista final:");
        for (Aluno aluno : servico.listar()) {
            System.out.println("  " + aluno);
        }
    }
        /** Apresentação: traduz as exceções do serviço em mensagens ao usuário. */
    private static void cadastrar(ServicoAluno servico, Aluno aluno) {
        try {
            servico.cadastrar(aluno);
            System.out.println("Cadastrado: " + aluno);
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Não foi possível cadastrar: " + e.getMessage());
        }
    }

    private static void alterar(ServicoAluno servico, Aluno aluno) {
        try {
            servico.alterar(aluno);
            System.out.println("Alterado: " + aluno);
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Não foi possível alterar: " + e.getMessage());
        }
    }

    private static void excluir(ServicoAluno servico, String matricula) {
        try {
            servico.excluir(matricula);
            System.out.println("Excluído: " + matricula);
        } catch (Exception e) {
            System.out.println("Não foi possível excluir: " + e.getMessage());
        }
    }
}
