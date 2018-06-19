/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import atos.magie_magie.entity.Joueur;
import atos.magie_magie.entity.Partie;
import atos.magie_magie.services.JoueurService;
import atos.magie_magie.services.PartieService;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Administrateur
 */
public class PartieServiceTest {
    
    private JoueurService joueurService = new JoueurService();
    private PartieService service = new PartieService();
     
    @Test
    public void demarrerUnePartieOK() {
       
        Partie part = service.creerNouvelleParite("coucou");
        joueurService.rejoindrePartie("A", "A", part.getId());
        joueurService.rejoindrePartie("B", "A", part.getId());
        joueurService.rejoindrePartie("c", "A", part.getId());
        
        
        Partie p = service.demarrerPartie(part.getId());
        
        for (Joueur joueur : p.getJoueurs() ) {
            long nbCarte = joueur.getCartes().size();
            
            assertEquals(7L, nbCarte);
        }
    }
    
//    @Test
    public void creerNouvellePartieOK() {
        
        Partie p = service.creerNouvelleParite("SecondePartie");
        
        assertNotNull(p.getId());
    }
    
}
