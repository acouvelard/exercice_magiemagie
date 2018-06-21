/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magie_magie.main;

import atos.magie_magie.entity.Carte;
import atos.magie_magie.entity.Joueur;
import atos.magie_magie.entity.Partie;
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
    
    public static void main(String[] args) {
       
        PointEntree m = new PointEntree();
        
        m.menuPrincipal();
        
//        m.jeu(0);
    }
    
    public void jeu(long idPartie) {
        
        Scanner scann = new Scanner(System.in);
        String action;

        do {
            List<Joueur> joueurs = joueurService.afficherAutresJoueurs(idPartie);
            //Affichage des cartes
            System.out.println("--- Voici vos cartes ---");
            Joueur joueurQuiALaMain = joueurService.recupJoueurQuiALaMain(idPartie);
            List<Carte> cartes = joueurService.afficherCartes(joueurQuiALaMain.getId());
            for (Carte carte : cartes) {
                System.out.println(carte.getId() + " " + carte.getIngre());
            }
            System.out.println("-------------------");
            System.out.println("Effectuer une action");
            System.out.println("1. Lancer un sort");
            System.out.println("2. Passer son tour");

            action = scann.nextLine();
            if (action == "1") { // LANCER UN SORT
                
                //affichage des cartes
                for (Joueur joueur : joueurs) {
                    System.out.println(joueur.getId() + " " + joueur.getPseudo());
                }
                
                //choisir la victime
                System.out.println("Entrer le numeros de la victime : ");
                Scanner numVictime = new Scanner(System.in);
                int joueurVictimeId = numVictime.nextInt();
                Joueur joueurVictime = joueurs.get(joueurVictimeId);
                
                //Choix des deux cartes
                
                System.out.println("Entrer le num de la première carte : ");
                Scanner premiereCarteChoisie = new Scanner(System.in);
                int premiereCarteId = premiereCarteChoisie.nextInt();
                Carte premiereCarte = cartes.get(premiereCarteId);

                
                System.out.println("Entrer le num de la première carte : ");
                Scanner secondeCarteChoisie = new Scanner(System.in);
                int secondeCarteId = secondeCarteChoisie.nextInt();
                Carte secondeCarte = cartes.get(secondeCarteId);
                
                joueurService.jeterUnSort(idPartie, joueurVictime, premiereCarte.getId(), secondeCarte.getId(), 0);
            
            } else if (action == "2") { //PASSER SON TOUR
                joueurService.passeSonTour(idPartie);
            } 
        } while (joueurService.recupJoueurQuiALaMain(idPartie).getEtat() == Joueur.EtatJoueur.GAGNE);
        
        System.out.println("Partie Fini !");
        System.out.println("Le gagnant est : " + joueurService.recupJoueurQuiALaMain(idPartie).getPseudo());

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
    
}
