/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magie_magie.dao;

import atos.magie_magie.entity.Carte;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Administrateur
 */
public class CarteDAO {

    public void ajouterCarte(Carte carte) {

        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();

        em.getTransaction().begin();
        em.persist(carte);
        em.getTransaction().commit();

    }
    
    public void modifierCarte(Carte carte) {
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        
        em.getTransaction().begin();
        em.merge(carte);
        em.getTransaction().commit();
        
    }
    
    public void supprimerCarte(long carteId) {
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        
        em.getTransaction().begin();
        Query q = em.createQuery("DELETE FROM Carte c WHERE c.id = :id");
        q.setParameter("id", carteId);
        q.executeUpdate();
        em.getTransaction().commit();
        
    }
    
    public Carte recupererCarteViaId (long carteId) {

    EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();

    Query query = em.createQuery("SELECT c FROM Carte c WHERE c.id = :idCarte");
    query.setParameter("idCarte", carteId);

    return (Carte) query.getSingleResult();
    }
    
}
