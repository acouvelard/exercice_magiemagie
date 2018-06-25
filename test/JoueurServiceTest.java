/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import atos.magie_magie.entity.Carte;
import atos.magie_magie.entity.Joueur;
import atos.magie_magie.entity.Partie;
import atos.magie_magie.services.JoueurService;
import atos.magie_magie.services.PartieService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Administrateur
 */
public class JoueurServiceTest {

    private JoueurService joueurService = new JoueurService();
    private PartieService partieService = new PartieService();
    
//    @Test
    public void sortsOk() {
        
        Joueur joueurVictime = joueurService.choisirJoueurVictime(25);

        joueurService.jeterUnSort(43, joueurVictime, 132, 134, 0);
    }
    
//    @Test
    public void passerSonTourOk() {
        
        joueurService.passeSonTour(41);
    }
    
    
//    @Test
    public void passerAuJoueurSuivantOk() {
        
        joueurService.joueurSuivant(23);
    }
    
//    @Test
    public void joueurQuiALaMainOk() {
        
        long joueurId = joueurService.recupJoueurQuiALaMain(23).getId();
        
        assertEquals(2L, joueurId);
    }
    
//    @Test
    public void afficherJoueursEtCartesOK() {
        
        List joueurs = joueurService.afficherAutresJoueurs(23);
        
        System.out.println(joueurs);
    }
    
//    @Test
    public void afficherLesCartesOK() {
        
        List cartes = joueurService.afficherCartes(3);
        
        System.out.println(cartes);
        
    }
    
//    @Test
    public void ordreJoueursOk() {
        
        Partie nouvellePartie = partieService.creerNouvelleParite("ordreJoueurOk2");
        
        joueurService.rejoindrePartie("Lili", "sorcièreLili",nouvellePartie.getId());
        joueurService.rejoindrePartie("Lila", "sorcièreLila",nouvellePartie.getId());
        Joueur j = joueurService.rejoindrePartie("Lilou", "sorcièreLilou",nouvellePartie.getId());
        
        assertEquals(3L, (long) j.getOrdre());
        
        
    }
    
//    @Test
    public void rejoindrePartieOk() {
        
        partieService.creerNouvelleParite("partieSecrète");
        
        long partieId = partieService.creerNouvelleParite("partieSecrète").getId();
        
        joueurService.rejoindrePartie("Rita", "sorcièreAVerue",partieId);
        
    }
    
    
}
