/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magie_magie.services;

import atos.magie_magie.dao.CarteDAO;
import atos.magie_magie.dao.JoueurDAO;
import atos.magie_magie.dao.PartieDAO;
import atos.magie_magie.entity.Carte;
import atos.magie_magie.entity.Joueur;
import atos.magie_magie.entity.Partie;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Administrateur
 */
public class JoueurService {
    
    private JoueurDAO dao = new JoueurDAO();
    private PartieDAO partieDAO = new PartieDAO();
    private CarteDAO carteDAO = new CarteDAO();
    
    
    public void passerSonTour () {
        
    }
    
//    public void passeSonTour(long joueurId, long partieId) {
//        
//        Joueur joueur = dao.recupererJoueurViaId(joueurId);
//        
//        Carte nouvelleCarte = new Carte();
//        Carte.Ingredient[] ingredients = Carte.Ingredient.values();
//        int carteRand = new Random().nextInt(ingredients.length);
//        nouvelleCarte.setIngre(ingredients[carteRand]);
//        nouvelleCarte.setJoueurProprietaire(joueur);
//        joueur.getCartes().add(nouvelleCarte);
//        carteDAO.ajouterCarte(nouvelleCarte);
//     
//        joueurSuivant(partieId);
//        
//    }
    
    public void joueurSuivant (long partieId) {
        Partie partie = partieDAO.rechercherParId(partieId);
        List listJoueur = partie.getJoueurs();
        Joueur joueur = recupJoueurQuiALaMain(partieId);

        long nbJoueurPerdu = dao.recupererNbJoueurPerdu(partieId);
        if (nbJoueurPerdu == listJoueur.size()-1) {
            joueur.setEtat(Joueur.EtatJoueur.GAGNE); //partie finie !
        } else {
            joueur.setEtat(Joueur.EtatJoueur.N_A_PAS_LA_MAIN);
        }
//        Joueur joueurSuivant = joueurSuivant.setEtat(Joueur.EtatJoueur.A_LA_MAIN);

        
        
        
//        Joueur joueurSuivant = joueur where joueur.getOrder+1;
//        
//        while(joueurSuivant.getEtat() !== Joueur.EtatJoueur.N_A_PAS_LA_MAIN) {
//            
//            joueurSuivant = joueurSuiviant where joueurSuivant.getOrder+1;
//        }
//        
//        joueurSuivant.setEtat(Joueur.EtatJoueur.A_LA_MAINs);
//                     
    }
    
    
    public Joueur recupJoueurQuiALaMain (long partieId) {
        
        Joueur joueur = dao.recupererJoueurQuiALaMain(partieId);
                
        return joueur;
    }
    
    public List afficherAutresJoueurs (long partieId) {
        
        List joueurs = dao.recupererAutresJoueures(partieId);

        return joueurs ;
    }
    
    public List afficherCartes (long joueurId) {
        
        Joueur joueur = dao.recupererJoueurViaId(joueurId);
        return joueur.getCartes();
    }
    
    public Joueur rejoindrePartie(String pseudo, String avatar, long idPartie) {
        
        // Recherche si joueur existe déjà
        Joueur joueur = dao.rechercherParPseudo(pseudo);
        
        if (joueur==null) {
            //Le joueur n'existe pas encore
            joueur = new Joueur();
            joueur.setPseudo(pseudo);
        }
        
        joueur.setAvatar(avatar);
        joueur.setEtat(Joueur.EtatJoueur.N_A_PAS_LA_MAIN);
        joueur.setOrdre(dao.rechercheOrdreNouveauJoueurPourPartieId(idPartie));
        
        //Associe le joueur à la partie et vise-versa
        Partie partie = partieDAO.rechercherParId(idPartie);
        joueur.setPartieActuelle(partie);
        List<Joueur> listJoueurs = partie.getJoueurs();
        listJoueurs.add(joueur);
        
        if (joueur.getId()== null) { //nouveau joueur
            dao.ajouter(joueur);
        } else { // joueur existe déjà
            dao.modifier(joueur);
        }
        
        return joueur;
        
    }
    
}
