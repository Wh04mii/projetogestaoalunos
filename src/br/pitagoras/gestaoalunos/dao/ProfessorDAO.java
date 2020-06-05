package br.pitagoras.gestaoalunos.dao;

import br.pitagoras.gestaoalunos.database.DatabaseFramework;
import br.pitagoras.gestaoalunos.model.Professor;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class ProfessorDAO implements IDAO<Professor> {

    private EntityManager em;

    public ProfessorDAO() {
        DatabaseFramework dbf = DatabaseFramework.getInstance();
        em = dbf.geEntityManager();
    }

    // metodo utilizado na tela de login para validar usuario.
    public Professor pesquisarUsuario(String usuario, String senha) {
        try {

            Query query = (Query) em.createQuery("SELECT p FROM Professor p "
                    + "WHERE p.loginProfessor = :loginProfessor "
                    + "and p.senhaProfessor = :senhaProfessor", Professor.class)
                    .setParameter("loginProfessor", usuario)
                    .setParameter("senhaProfessor", senha);

            Professor professorBanco = (Professor) query.getSingleResult();

            return professorBanco;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Professor pesquisar(int id) {

        try {
            Query query = em.createNamedQuery("Professor.findByIdProfessor", Professor.class)
                    .setParameter("idProfessor", id);

            return (Professor) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Professor> pesquisar() {
        try {
            Query query = em.createNamedQuery("Professor.findAll", Professor.class)
                    .setParameter("loginProfessor", "suporte");
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }

    }
    //RETORNA LISTA DE NOMES DOS ALUNOS.

    public List<Professor> query(String nome) {
        Query query = em.createNamedQuery("Professor.findByNomeProfessor", Professor.class)
                .setParameter("nome", nome);
        return query.getResultList();

    }

    @Override
    public boolean inserir(Professor model) {

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
    public boolean alterar(Professor model) {

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
    public boolean deletar(Professor model) {

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
