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
    
    public static void main(String[] args) throws InterruptedException {
       
        PointEntree m = new PointEntree();
        
        m.menuPrincipal();
        
    }
    
    public Joueur jeu(long idPartie) {
        
        Scanner scann = new Scanner(System.in);
        int action;
        List<Joueur> joueurs = joueurService.tousLesJoueursDeLaPartie(idPartie);
        
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

            //choix d'une potentielle carte à échanger pour le sort Hypnose
            long carteEchangeeId = 0;
            if ((premiereCarte.getIngre() == Carte.Ingredient.BAVE_DE_CRAPAUX && secondeCarte.getIngre() == Carte.Ingredient.LAPIS_LAZULI)
            ||(secondeCarte.getIngre() == Carte.Ingredient.BAVE_DE_CRAPAUX && premiereCarte.getIngre() == Carte.Ingredient.LAPIS_LAZULI)) {
                System.out.print("Entrer le num de la carte à échanger : ");
                Scanner carteEcgangeeChoisie = new Scanner(System.in);
                carteEchangeeId = carteEcgangeeChoisie.nextInt();
            }    

            //Afficher les joueurs pour le sort Divination
            List<Joueur> listJoueursDivination = joueurService.jeterUnSort(idPartie, joueurVictime, premiereCarte.getId(), secondeCarte.getId(), carteEchangeeId);
            if (listJoueursDivination != null) {
                for (Joueur joueurDivination : listJoueursDivination) {
                    System.out.println(joueurDivination.getPseudo() + " a " + joueurDivination.getCartes().size() + " cartes");
                }
            }
                
        } else if (action == 2) { //PASSER SON TOUR
            joueurService.passeSonTour(idPartie);
        } else {
            System.out.println("Je n'ai pas compris !");
        }
        
        Joueur joueurWin = partieService.finDePartie(idPartie);
        return joueurWin;
    }
    
    public void menuPrincipal() throws InterruptedException {
        
        Scanner scan = new Scanner(System.in);
        String choix;
        do {
            System.out.println("Menu principal");
            System.out.println("-------------");
            System.out.println("1. Lister les parties non-démarées");
            System.out.println("2. Créer une partie");
            System.out.println("3. Rejoindre une partie");
            System.out.println("4. Demarrer une partie");
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
                    List<Partie> parties2 = partieService.listerPartiesNonDemarrees();
                    for (Partie party : parties2) {
                        System.out.println(party.getId() + " " + party.getNom());
                    }
                    System.out.print("Entrez le num de votre partie : ");
                    Long partieId = persoScan.nextLong();
                    Joueur nouveauJoueur = joueurService.rejoindrePartie(pseudo, avatar, partieId);
                    // pour un joueur par fenêtre !
                    ecranJeu(partieId, nouveauJoueur.getId());
                    break;
                
                case "4":
                    Scanner s = new Scanner(System.in);
                    System.out.print("Entre le num de la partie à démarrer ");
                    Long choix2 = s.nextLong();
                    partieService.demarrerPartie(choix2);
                    break;
                    
                case "Q":
                    break;

                default:
                    System.out.println("Choix inconnu");
                    break;
            }
        } while ( choix.equals("Q") == false);

    }

    private void ecranJeu(Long partieId, long idJoueur) throws InterruptedException {

        //recupe id de moi-même
        long monId = idJoueur;

        Joueur joueurGagnant = null;
        long joueurQuiALaMainId = 0;
        
        //TODO : Condition pour commencer la partie. A faire ???
        
        System.out.println("La partie va démarrer");
                
        //Tours de jeux tant qu'il n'y a pas de joueur gagants
        while (joueurGagnant == null) {            
            if (joueurQuiALaMainId == monId) {
                joueurGagnant = jeu(partieId);
            } else {
                System.err.println("Ce n'est pas votre tour!");
                Thread.sleep(1000);
            }
        }
   
        System.err.println("****** PARTIE FINIE ******");
        
        System.err.println("La reine des sorcières est " + joueurGagnant.getPseudo() + " !!!!!!");
        
    }
}
