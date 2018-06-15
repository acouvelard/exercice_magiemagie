/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magie_magie.services;

import atos.magie_magie.dao.JoueurDAO;
import atos.magie_magie.dao.PartieDAO;
import atos.magie_magie.entity.Joueur;
import atos.magie_magie.entity.Partie;
import java.util.List;

/**
 *
 * @author Administrateur
 */
public class JoueurService {
    
    private JoueurDAO dao = new JoueurDAO();
    private PartieDAO partieDAO = new PartieDAO();
    
    public void rejoindrePartie(String pseudo, String avatar, long idPartie) {
        
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
        
    }
    
}
