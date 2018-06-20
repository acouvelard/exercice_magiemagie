/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magie_magie.main;

import atos.magie_magie.entity.Partie;
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
    
    public static void main(String[] args) {
       
        PointEntree m = new PointEntree();
        
        m.menuPrincipal();
        
        
    }
    
}
