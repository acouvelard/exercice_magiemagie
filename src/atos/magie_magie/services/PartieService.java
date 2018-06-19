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
import java.util.List;
import java.util.Random;

/**
 *
 * @author Administrateur
 */
public class PartieService {

    private PartieDAO dao = new PartieDAO();
    private JoueurDAO joueurDAO = new JoueurDAO();
    private CarteDAO carteDAO = new CarteDAO();

    public Partie demarrerPartie(long partieId) {

        Partie partieQuiDemarre = dao.rechercherParId(partieId);

        List partieJoueur = partieQuiDemarre.getJoueurs();
        
        long nbJoueur = dao.compterNbJoueur(partieId);
        if (nbJoueur < 1) {
            throw new RuntimeException("Il n'y a pas assez de joueur pour commencer la partie !");
        }

        for (Joueur joueur : partieQuiDemarre.getJoueurs()) {

            if (joueur.getOrdre() == 1) {
                joueur.setEtat(Joueur.EtatJoueur.A_LA_MAIN);
                joueurDAO.modifier(joueur);
            }

            for (int i = 0; i < 7; i++) {
                Carte nouvelleCarte = new Carte();
                Carte.Ingredient[] ingredients = Carte.Ingredient.values();

                int carteRand = new Random().nextInt(ingredients.length);
                nouvelleCarte.setIngre(ingredients[carteRand]);
                nouvelleCarte.setJoueurProprietaire(joueur);
                joueur.getCartes().add(nouvelleCarte);
                carteDAO.ajouterCarte(nouvelleCarte);
            }
        }

        return partieQuiDemarre;
    }

    public List<Partie> listerPartiesNonDemarrees() {

        return dao.ListerPartieNonDemarrees();
    }

    public Partie creerNouvelleParite(String nom) {

        Partie p = new Partie();
        p.setNom(nom);
        dao.ajouter(p);

        return p;
    }

}
