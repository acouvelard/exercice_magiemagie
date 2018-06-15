/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magie_magie.dao;

import atos.magie_magie.entity.Joueur;
import atos.magie_magie.entity.Partie;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Administrateur
 */
public class PartieDAO {

    public Partie rechercherParId(long idPartie) {
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        
        return em.find(Partie.class, idPartie);
        
    }
    
    
    public List<Partie> ListerPartieNonDemarrees() {
    
    EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
    
    Query query = em.createQuery("SELECT p FROM Partie p"
            + "EXCEPT SELECT p FROM Partie p JOIN p.joueurs j WHERE j.etat IN (:etat_gagne, :etat_alamain)");
    query.setParameter("etat_gagne", Joueur.EtatJoueur.GAGNE);
    query.setParameter("alamain", Joueur.EtatJoueur.A_LA_MAIN);
    
    return query.getResultList();
    
    }
    
    public void ajouter(Partie p){
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
    }

    
      
}
