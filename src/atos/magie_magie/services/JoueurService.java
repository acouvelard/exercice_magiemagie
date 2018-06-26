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
public class JoueurService {

    private JoueurDAO dao = new JoueurDAO();
    private PartieDAO partieDAO = new PartieDAO();
    private CarteDAO carteDAO = new CarteDAO();
    
    public List rechercherJoueurEtatPasLaMainEtSommeil(long partieId) {
        
        List<Joueur> joueurs = dao.rechercherJoueursPasLaMainEtSommeil(partieId);
        
        return joueurs;
    }
    
    public List tousLesJoueursDeLaPartie (long partieId) {
        
        List<Joueur> joueurs = dao.rechercheTousLesJoueursPourUnePartie(partieId);
        
        return joueurs;
    }
    
    public Joueur choisirJoueurVictime (long joueurId) {
        
        Joueur joueurVictime = dao.recupererJoueurViaId(joueurId);
        
        return joueurVictime;
    }
    
    public void sortInvisibilite (long partieId, Joueur joueurQuiALaMain) {
        
        List<Joueur> joueurs = dao.rechercherJoueursPasLaMainEtSommeil(partieId);
        
        for (Joueur joueur : joueurs) {
            int r = new Random().nextInt(joueur.getCartes().size());
            Carte cartePrise = joueur.getCartes().get(r);
            joueur.getCartes().remove(cartePrise);
            dao.modifier(joueur);
            cartePrise.setJoueurProprietaire(joueurQuiALaMain);
            carteDAO.modifierCarte(cartePrise);
            joueurQuiALaMain.getCartes().add(cartePrise);
            dao.modifier(joueurQuiALaMain);


            if (joueur.getCartes().size() == 0) {
            joueur.setEtat(Joueur.EtatJoueur.PERDU);
            dao.modifier(joueur);
        }
        }
        
        
    }
    
    public void sortPhiltreDAmour (Joueur joueurVictime, Joueur joueurQuiALaMain ) {
        
        List<Carte> cartesDeLaVictime = joueurVictime.getCartes();
        if (cartesDeLaVictime.size() == 1) {
            joueurQuiALaMain.getCartes().add((Carte) cartesDeLaVictime);
            cartesDeLaVictime.remove(cartesDeLaVictime);
            return;
        } 
        
        int moitiéDesCartesquiReste;
        if (cartesDeLaVictime.size()%2 == 0) {
            moitiéDesCartesquiReste = cartesDeLaVictime.size()/2;
        } else {
            moitiéDesCartesquiReste = cartesDeLaVictime.size()/2 + (1/2);
        }
        
        while (cartesDeLaVictime.size() != moitiéDesCartesquiReste) {            
            int r = new Random().nextInt(cartesDeLaVictime.size());
            Carte cartePrise = cartesDeLaVictime.get(r);
            cartesDeLaVictime.remove(cartePrise);
            dao.modifier(joueurVictime);
            cartePrise.setJoueurProprietaire(joueurQuiALaMain);
            carteDAO.modifierCarte(cartePrise);
            joueurQuiALaMain.getCartes().add(cartePrise);
            dao.modifier(joueurQuiALaMain);

        }
    }
    
    public void sortHypnose (Joueur joueurVictime, Joueur joueurQuiALaMain, long carteEchangeeId) {
        
        if (joueurVictime.getCartes().size() < 3) {
            List<Carte> cartes = joueurVictime.getCartes();
            for (Carte carte : cartes) {
                joueurVictime.getCartes().remove(carte);
                dao.modifier(joueurVictime);
                carte.setJoueurProprietaire(joueurQuiALaMain);
                carteDAO.modifierCarte(carte);
            }
            return;
        }
     
        
        for (int i = 0; i < 3; i++) {
            int r = new Random().nextInt(joueurVictime.getCartes().size());
            Carte cartePrise = joueurVictime.getCartes().get(r);
            joueurVictime.getCartes().remove(cartePrise);
            dao.modifier(joueurVictime);
            cartePrise.setJoueurProprietaire(joueurQuiALaMain);
            carteDAO.modifierCarte(cartePrise);
            joueurQuiALaMain.getCartes().add(cartePrise);
            dao.modifier(joueurQuiALaMain);
        }           


        
        Carte carteEchangee = carteDAO.recupererCarteViaId(carteEchangeeId);
        
        joueurQuiALaMain.getCartes().remove(carteEchangee);
        dao.modifier(joueurQuiALaMain);
        carteEchangee.setJoueurProprietaire(joueurVictime);
        carteDAO.modifierCarte(carteEchangee);
        joueurVictime.getCartes().add(carteEchangee);
        dao.modifier(joueurVictime);


    }
    
    public List<Joueur> sortDivination (long partieId) {
        
        List<Joueur> joueurs = dao.rechercherJoueursPasLaMainEtSommeil(partieId);
        
        return joueurs;
    }
    
    public void sortSommeilProfond (Joueur joueurVictime) {
        
        joueurVictime.setEtat(Joueur.EtatJoueur.SOMMEIL_PROFOND);
        dao.modifier(joueurVictime);
        
    }

    public List<Joueur> jeterUnSort (long partieId, Joueur joueurVictime, long idCarte1, long idCarte2, long carteEchangeeId ) {
        
        Joueur joueurQuiALaMain = dao.recupererJoueurQuiALaMain(partieId);
       
        Carte carte1 = carteDAO.recupererCarteViaId(idCarte1);
        Carte carte2 = carteDAO.recupererCarteViaId(idCarte2);

        List<Joueur> listJoueurs = null;
        
        //cartes correspondent à celle d'un sort
        if ((carte1.getIngre() == Carte.Ingredient.CORNE_DE_LICORNE && carte2.getIngre() == Carte.Ingredient.BAVE_DE_CRAPAUX)
            ||(carte2.getIngre() == Carte.Ingredient.CORNE_DE_LICORNE && carte1.getIngre() == Carte.Ingredient.BAVE_DE_CRAPAUX)) {
            sortInvisibilite(partieId, joueurQuiALaMain);
        } else if ((carte1.getIngre() == Carte.Ingredient.CORNE_DE_LICORNE && carte2.getIngre() == Carte.Ingredient.MANDRAGORE)
            ||(carte2.getIngre() == Carte.Ingredient.CORNE_DE_LICORNE && carte1.getIngre() == Carte.Ingredient.MANDRAGORE)) {
            sortPhiltreDAmour(joueurVictime, joueurQuiALaMain);
        } else if ((carte1.getIngre() == Carte.Ingredient.BAVE_DE_CRAPAUX && carte2.getIngre() == Carte.Ingredient.LAPIS_LAZULI)
            ||(carte2.getIngre() == Carte.Ingredient.BAVE_DE_CRAPAUX && carte1.getIngre() == Carte.Ingredient.LAPIS_LAZULI)) {
            sortHypnose(joueurVictime, joueurQuiALaMain, carteEchangeeId);
        } else if ((carte1.getIngre() == Carte.Ingredient.LAPIS_LAZULI && carte2.getIngre() == Carte.Ingredient.AILE_DE_CHAUVE_SOURIE )
            || (carte2.getIngre() == Carte.Ingredient.LAPIS_LAZULI && carte1.getIngre() == Carte.Ingredient.AILE_DE_CHAUVE_SOURIE )) {
            listJoueurs = sortDivination(partieId);
        } else if ((carte1.getIngre() == Carte.Ingredient.MANDRAGORE && carte2.getIngre() == Carte.Ingredient.AILE_DE_CHAUVE_SOURIE)
            ||(carte2.getIngre() == Carte.Ingredient.MANDRAGORE && carte1.getIngre() == Carte.Ingredient.AILE_DE_CHAUVE_SOURIE)) {
            sortSommeilProfond(joueurVictime);
        } else {
            System.out.println("Vous n'avez pas selectionné les bonnes cartes. Vous les avez perdues.");;
        }
        
        //test si joueurVictime à encore des cartes
        if (joueurVictime.getCartes().size() == 0) {
            joueurVictime.setEtat(Joueur.EtatJoueur.PERDU);
            dao.modifier(joueurVictime);
        }
        
        //On retire les deux cartes utilisées
        joueurQuiALaMain.getCartes().remove(carte1);
        dao.modifier(joueurQuiALaMain);
        carteDAO.supprimerCarte(carte1.getId());
        joueurQuiALaMain.getCartes().remove(carte2);
        dao.modifier(joueurQuiALaMain);
        carteDAO.supprimerCarte(carte2.getId());
        

        joueurSuivant(partieId);
        
        return listJoueurs;

    }

    public void passeSonTour(long partieId) {
        
        Joueur joueur = dao.recupererJoueurQuiALaMain(partieId);
 
        Carte nouvelleCarte = new Carte();
        Carte.Ingredient[] ingredients = Carte.Ingredient.values();
        int carteRand = new Random().nextInt(ingredients.length);
        nouvelleCarte.setIngre(ingredients[carteRand]);
        nouvelleCarte.setJoueurProprietaire(joueur);
        joueur.getCartes().add(nouvelleCarte);
        carteDAO.ajouterCarte(nouvelleCarte);
     
        joueurSuivant(partieId);
        
    }
    
    public void joueurSuivant(long partieId) {
       
        Partie partie = partieDAO.rechercherParId(partieId);
        Joueur joueurQuiALaMain = recupJoueurQuiALaMain(partieId);

        if (dao.recupererNbJoueurPerdu(partieId) == partieDAO.compterNbJoueur(partieId) - 1) {
            joueurQuiALaMain.setEtat(Joueur.EtatJoueur.GAGNE); 
            dao.modifier(joueurQuiALaMain);//partie finie !
            return;
        }

        long ordreMax = dao.recupererOrdreMax(partieId);

        Joueur joueurEvalue = joueurQuiALaMain;
        
        //boucle
        while (true) {

            if (joueurEvalue.getOrdre() >= ordreMax) {
                joueurEvalue = dao.recupereJoueurParOrdre(partieId, 1);
            } else {
                joueurEvalue = dao.recupereJoueurParOrdre(partieId, joueurEvalue.getOrdre()+1);
            }
            
            if (joueurEvalue.getId() == joueurQuiALaMain.getId()) {
                return;
            }

            if (joueurEvalue.getEtat() == Joueur.EtatJoueur.SOMMEIL_PROFOND) {
                joueurEvalue.setEtat(Joueur.EtatJoueur.N_A_PAS_LA_MAIN);
                dao.modifier(joueurEvalue);
            } else if (joueurEvalue.getEtat() == Joueur.EtatJoueur.N_A_PAS_LA_MAIN) {
                joueurQuiALaMain.setEtat(Joueur.EtatJoueur.N_A_PAS_LA_MAIN);
                dao.modifier(joueurQuiALaMain);
                joueurEvalue.setEtat(Joueur.EtatJoueur.A_LA_MAIN);
                dao.modifier(joueurEvalue);
                return;
            }
        }
    }

    public Joueur recupJoueurQuiALaMain(long partieId) {

        Joueur joueur = dao.recupererJoueurQuiALaMain(partieId);

        return joueur;
    }

    public List afficherInfosSurLesJoueursPourUnePartie(long partieId) {

        List joueurs = dao.recupererAutresJoueures(partieId);

        return joueurs;
    }

    public List afficherCartes(long joueurId) {

        Joueur joueur = dao.recupererJoueurViaId(joueurId);
        return joueur.getCartes();
    }

    public Joueur rejoindrePartie(String pseudo, String avatar, long idPartie) {

        // Recherche si joueur existe déjà
        Joueur joueur = dao.rechercherParPseudo(pseudo);

        if (joueur == null) {
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

        if (joueur.getId() == null) { //nouveau joueur
            dao.ajouter(joueur);
        } else { // joueur existe déjà
            dao.modifier(joueur);
        }

        return joueur;

    }

}
