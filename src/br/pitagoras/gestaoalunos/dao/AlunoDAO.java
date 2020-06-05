package br.pitagoras.gestaoalunos.dao;

import br.pitagoras.gestaoalunos.database.DatabaseFramework;
import br.pitagoras.gestaoalunos.model.Aluno;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class AlunoDAO implements IDAO<Aluno> {

    private EntityManager em;

    public AlunoDAO() {
        DatabaseFramework dbf = DatabaseFramework.getInstance();
        em = dbf.geEntityManager();

    }

    @Override
    public Aluno pesquisar(int id) {
        try {
            Query query = em.createNamedQuery("Aluno.findByIdAluno", Aluno.class)
                    .setParameter("idAluno", id);
            return (Aluno) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Aluno> pesquisar() {
        try {
            Query query = em.createNamedQuery("Aluno.findAll", Aluno.class);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }

    }

    //RETORNA LISTA DE NOMES DOS ALUNOS.
    public List<Aluno> query(String nome) {
        Query query = em.createNamedQuery("Aluno.findByNomeAluno", Aluno.class)
                .setParameter("nome", nome);
        return query.getResultList();

    }

    @Override
    public boolean inserir(Aluno model) {

        try {
            em.getTransaction().begin();
            em.persist(model);
            em.getTransaction().commit();

            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public boolean alterar(Aluno model) {

        try {
            em.getTransaction().begin();
            em.persist(model);
            em.getTransaction().commit();

            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public boolean deletar(Aluno model) {

        try {
            em.getTransaction().begin();
            em.remove(model);
            em.getTransaction().commit();

            return true;
        } catch (Exception e) {
            return false;
        }

    }
}
