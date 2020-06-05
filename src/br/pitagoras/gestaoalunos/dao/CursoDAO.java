package br.pitagoras.gestaoalunos.dao;

import br.pitagoras.gestaoalunos.database.DatabaseFramework;
import br.pitagoras.gestaoalunos.model.Curso;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class CursoDAO implements IDAO<Curso> {

    private EntityManager em;

    public CursoDAO() {
        DatabaseFramework dbf = DatabaseFramework.getInstance();
        em = dbf.geEntityManager();

    }

    @Override
    public Curso pesquisar(int id) {
        try {
            Query query = em.createNamedQuery("Curso.findByIdCurso", Curso.class)
                    .setParameter("idCurso", id);
            return (Curso) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean inserir(Curso model) {
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
    public boolean alterar(Curso model) {
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
    public boolean deletar(Curso model) {
        try {
            em.getTransaction().begin();
            em.remove(model);
            em.getTransaction().commit();

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Curso> pesquisar() {
        try {
            Query query = em.createNamedQuery("Curso.findAll", Curso.class);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
