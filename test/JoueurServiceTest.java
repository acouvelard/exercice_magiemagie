/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import atos.magie_magie.services.JoueurService;
import atos.magie_magie.services.PartieService;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Administrateur
 */
public class JoueurServiceTest {

    private JoueurService service = new JoueurService();
    private PartieService partieService = new PartieService();
    
    @Test
    public void rejoindrePartieOk() {
        
        partieService.creerNouvelleParite("partieSecrète");
        
        long partieId = partieService.creerNouvelleParite("partieSecrète").getId();
        
        service.rejoindrePartie("Rita", "sorcièreAVerue",partieId);
        
    }
    
    
}
