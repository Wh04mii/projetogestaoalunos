package br.pitagoras.gestaoalunos.database;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DatabaseFramework {

    private static EntityManager em;
    private static DatabaseFramework databaseFramework;

    public EntityManager geEntityManager() {
        return em;
    }

    //DESIGN PATTENRS PADRÃO SINGLETON
    public static DatabaseFramework getInstance() {

        if (databaseFramework == null) {
            databaseFramework = new DatabaseFramework();

        }
        return databaseFramework;
    }

    private DatabaseFramework() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("GestaoAlunosOrm");
        em = emf.createEntityManager();

    }

//    public void conectar(String persistenceName) {
//
//       EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceName);
//        em = emf.createEntityManager();
//
//    }
}
