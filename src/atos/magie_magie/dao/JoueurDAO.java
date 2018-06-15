/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magie_magie.dao;

import atos.magie_magie.entity.Joueur;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Administrateur
 */
public class JoueurDAO {
    
    public void modifier(Joueur joueur) {
       
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        
        em.getTransaction().begin();
        em.merge(joueur);
        em.getTransaction().commit();
    }

    public void ajouter(Joueur joueur) {
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        
        em.getTransaction().begin();
        em.persist(joueur);
        em.getTransaction().commit();
               
    }
    
    public long rechercheOrdreNouveauJoueurPourPartieId( long partieId) {

    EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();

    Query query = em.createQuery("SELECT MAX (j.ordre)+1 FROM Joueur j JOIN j.partieActuelle p WHERE p.id=:idPartie");
    query.setParameter("idPartie", partieId);

    Object res = query.getSingleResult();
    
    if (res == null)
        return 1;
    
    return (long) query.getSingleResult();
    }
    
    /**
     * Renvoie le Joueur dont le pseudo existe en DB, ou renvoie null si pas trouvé
     * @param pseudo
     * @return 
     */
    
    public Joueur rechercherParPseudo(String pseudo) {
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        
        Query query = em.createQuery("SELECT j FROM Joueur j WHERE j.pseudo=:lePseudo");
        query.setParameter("lePseudo", pseudo);
        
        List<Joueur> joueurTrouves = query.getResultList();
        
        if(joueurTrouves.isEmpty())
            return null;
        
        return joueurTrouves.get(0);
              
    }


    

    
}
