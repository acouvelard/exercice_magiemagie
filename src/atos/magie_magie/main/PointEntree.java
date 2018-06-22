/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magie_magie.main;

import atos.magie_magie.entity.Carte;
import atos.magie_magie.entity.Joueur;
import atos.magie_magie.entity.Partie;
import atos.magie_magie.services.CarteService;
import atos.magie_magie.services.JoueurService;
import atos.magie_magie.services.PartieService;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Administrateur
 */
public class PointEntree {

    private PartieService partieService = new PartieService();
    private JoueurService joueurService = new JoueurService();
    private CarteService carteService = new CarteService();
    
    public static void main(String[] args) {
       
        PointEntree m = new PointEntree();
        
        m.menuPrincipal();
        
        System.out.print("Entrer la partie à jouer : ");
        Scanner s = new Scanner(System.in);
        long partie = s.nextLong();
        
        m.jeu(partie);
    }
    
    public void jeu(long idPartie) {
        
        Scanner scann = new Scanner(System.in);
        int action;
        List<Joueur> joueurs = joueurService.tousLesJoueursDeLaPartie(idPartie);
        Joueur joueurGagnant = null;
        
        do {

            //Affichage des cartes
            System.out.println("--- Voici vos cartes ---");
            Joueur joueurQuiALaMain = joueurService.recupJoueurQuiALaMain(idPartie);
            List<Carte> cartes = joueurService.afficherCartes(joueurQuiALaMain.getId());
            for (Carte carte : cartes) {
                System.out.println(carte.getId() + ". " + carte.getIngre());
            }
            System.out.println("-------------------");
            System.out.println("Effectuer une action");
            System.out.println("1. Lancer un sort");
            System.out.println("2. Passer son tour");
            System.out.print("Choix de " + joueurQuiALaMain.getPseudo() + " : ");

            action = scann.nextInt();
            
            if (action == 1) { // LANCER UN SORT
                
                //affichage des cartes
                for (Joueur joueur : joueurs) {
                    if (joueur.getEtat() != Joueur.EtatJoueur.PERDU && (joueur.getEtat() != Joueur.EtatJoueur.A_LA_MAIN)) {
                        System.out.println(joueur.getId() + " " + joueur.getPseudo() + " ( " + joueur.getCartes().size() + " cartes ) ");
                    }
                }
                
                //choisir la victime
                System.out.print("Entrer le numeros de la victime : ");
                Scanner numVictime = new Scanner(System.in);
                int joueurVictimeId = numVictime.nextInt();
                Joueur joueurVictime = joueurService.choisirJoueurVictime(joueurVictimeId);
                
                //Choix des deux cartes
                
                System.out.print("Entrer le num de la première carte : ");
                Scanner premiereCarteChoisie = new Scanner(System.in);
                int premiereCarteId = premiereCarteChoisie.nextInt();
                Carte premiereCarte = carteService.recupererCarteViaId(premiereCarteId);

                
                System.out.print("Entrer le num de la seconde carte : ");
                Scanner secondeCarteChoisie = new Scanner(System.in);
                int secondeCarteId = secondeCarteChoisie.nextInt();
                Carte secondeCarte = carteService.recupererCarteViaId(secondeCarteId);
                
                long carteEchangeeId = 0;
                if ((premiereCarte.getIngre() == Carte.Ingredient.BAVE_DE_CRAPAUX && secondeCarte.getIngre() == Carte.Ingredient.LAPIS_LAZULI)
                ||(secondeCarte.getIngre() == Carte.Ingredient.BAVE_DE_CRAPAUX && premiereCarte.getIngre() == Carte.Ingredient.LAPIS_LAZULI)) {
                    System.out.print("Entrer le num de la carte à échanger : ");
                    Scanner carteEcgangeeChoisie = new Scanner(System.in);
                    carteEchangeeId = carteEcgangeeChoisie.nextInt();
                }    

                joueurService.jeterUnSort(idPartie, joueurVictime, premiereCarte.getId(), secondeCarte.getId(), carteEchangeeId);
                
            } else if (action == 2) { //PASSER SON TOUR
                joueurService.passeSonTour(idPartie);
            } else {
                System.out.println("Je n'ai pas compris !");
            }

            for (Joueur joueur : joueurs) {
                if(joueur.getEtat() == Joueur.EtatJoueur.GAGNE) {
                    joueurGagnant = joueur;
                } 
            }
        } while ( joueurGagnant == null);
        
        for (Joueur joueur : joueurs) {
            joueur.setNbPartiesJouees(joueur.getNbPartiesJouees());
            if (joueur == joueurGagnant) {
                joueur.setNbPartiesGagnees(joueur.getNbPartiesGagnees());
            }
        }
        
        System.err.println(" -*-*- Partie Fini ! -*-*- ");

        System.err.println("La reine des sorcières est : " + joueurGagnant.getPseudo() + " !!!!");
        

    }
    
    public void menuPrincipal() {
        
        Scanner scan = new Scanner(System.in);
        String choix;
        do {
            System.out.println("Menu principal");
            System.out.println("-------------");
            System.out.println("1. Lister les parties non-démarées");
            System.out.println("2. Créer une partie");
            System.out.println("3. Rejouindre une partie");
            System.out.println("4. Démarrer une partie");
            System.out.println("Q. Quitter");
            System.out.print("Votre choix > ");
        

            choix = scan.nextLine();
            switch (choix) {
                case "1":
                    List<Partie> parties = partieService.listerPartiesNonDemarrees();
                    for (Partie party : parties) {
                        System.out.println(party.getId() + " " + party.getNom());
                    }
                    break;

                case "2":
                    System.out.print("Entrez le nom de votre partie : ");
                    Scanner partieNom = new Scanner(System.in);
                    partieService.creerNouvelleParite(partieNom.nextLine());
                    break;

                case "3":
                    Scanner persoScan = new Scanner(System.in);
                    System.out.print("Entrez votre pseudo : ");
                    String pseudo = persoScan.nextLine();
                    System.out.print("Entrez votre avatar : ");
                    String avatar = persoScan.nextLine();
                    System.out.print("Entrez le num de votre partie : ");
                    Long partieId = persoScan.nextLong();
                    joueurService.rejoindrePartie(pseudo, avatar, partieId);
                    // pour un joueur par fenêtre !
                    ecranJeu(partieId, pseudo);
                    break;

                case "4":
                    System.out.println("Entrez le num de la partie à démarrer");
                    Scanner partieADemarrer = new Scanner(System.in);
                    partieService.demarrerPartie(partieADemarrer.nextLong());
                    break;

                case "Q":
                    break;

                default:
                    System.out.println("Choix inconnu");
                    break;
            }
        } while ( choix.equals("Q") == false);

    }

    private void ecranJeu(Long partieId, String pseudo) {
        //recupe id de moi-même
       long monId = 1;
       
        while (true) {            
            
            // recup le joueur qui à la main
            long joueurQuiALaMain = joueurService.recupJoueurQuiALaMain(partieId).getId();
            
            if (joueurQuiALaMain == monId) {
                
                //appeller la fonction qui permet de jouer !
                jeu(partieId);
                
            }
        }
       
    }
    
}
