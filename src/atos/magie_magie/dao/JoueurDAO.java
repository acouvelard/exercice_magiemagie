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
    
    public List rechercheTousLesJoueursPourUnePartie (long partieId) {
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        
        Query q = em.createQuery("SELECT j FROM Joueur j JOIN j.partieActuelle pa WHERE pa.id = :idPartie");
        q.setParameter("idPartie", partieId);
        
        return q.getResultList();
    }
    
    public Joueur recupereJoueurParOrdre (long idPartie, long ordre) {
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        
        Query query = em.createQuery("SELECT j FROM Joueur j JOIN j.partieActuelle pa WHERE pa.id = :idPartie AND j.ordre = :ordre");
        query.setParameter("idPartie", idPartie);
        query.setParameter("ordre", ordre);
        
        return (Joueur) query.getSingleResult();
    }
    
    public long recupererOrdreMax (long idPartie) {
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        
        Query query = em.createQuery("SELECT MAX(j.ordre) FROM Joueur j JOIN j.partieActuelle pa WHERE pa.id = :idPartie");
        query.setParameter("idPartie", idPartie);
        
        return (long) query.getSingleResult();
        
    }
    
    public long recupererNbJoueurPerdu (long idPartie) {
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        
        Query query = em.createQuery("SELECT COUNT(j.id) FROM Joueur j JOIN j.partieActuelle pa WHERE pa.id = :idPartie AND j.etat = :jEtat ");
        query.setParameter("jEtat", Joueur.EtatJoueur.PERDU );
        query.setParameter("idPartie", idPartie);
        
        return (long) query.getSingleResult();
        
    }
    
    public Joueur recupererJoueurQuiALaMain (long idPartie) {
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        
        Query query = em.createQuery("SELECT j FROM Joueur j JOIN j.partieActuelle pa WHERE pa.id = :idPartie AND j.etat = :jEtat ");
        query.setParameter("jEtat", Joueur.EtatJoueur.A_LA_MAIN );
        query.setParameter("idPartie", idPartie);
        
        return (Joueur) query.getSingleResult();
    }
    
    
    /**
     * 
     * @param idPartie
     * @return recupère id, pseudo, avatar, etat et nombre de carte par joueur : une list dans une liste 
     */
    public List recupererAutresJoueures(long idPartie) {
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        
        Query query = em.createQuery("SELECT j.id, j.pseudo, j.avatar, j.etat , COUNT(c.id)  "
                + "FROM Joueur j JOIN j.cartes c JOIN j.partieActuelle pa WHERE pa.id = :idPartie "
                + "GROUP BY j ");
        query.setParameter("idPartie", idPartie);
        
        return query.getResultList();
    }
    
    public Joueur recupererJoueurViaId (long joueurId) {
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        
        Query query = em.createQuery("SELECT j FROM Joueur j WHERE j.id = :idJoueur");
        query.setParameter("idJoueur", joueurId);
        
        return (Joueur) query.getSingleResult();
        
    }
    
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

    public long rechercheOrdreNouveauJoueurPourPartieId(long partieId) {

        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();

        Query query = em.createQuery("SELECT MAX (j.ordre)+1 FROM Joueur j JOIN j.partieActuelle p WHERE p.id=:idPartie");
        query.setParameter("idPartie", partieId);

        Object res = query.getSingleResult();

        if (res == null) {
            return 1;
        }

        return (long) query.getSingleResult();
    }

    /**
     * Renvoie le Joueur dont le pseudo existe en DB, ou renvoie null si pas
     * trouvé
     *
     * @param pseudo
     * @return
     */
    public Joueur rechercherParPseudo(String pseudo) {

        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();

        Query query = em.createQuery("SELECT j FROM Joueur j WHERE j.pseudo=:lePseudo");
        query.setParameter("lePseudo", pseudo);

        List<Joueur> joueurTrouves = query.getResultList();

        if (joueurTrouves.isEmpty()) {
            return null;
        }

        return joueurTrouves.get(0);

    }

}
