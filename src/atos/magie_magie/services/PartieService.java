/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magie_magie.services;

import atos.magie_magie.dao.PartieDAO;
import atos.magie_magie.entity.Partie;
import java.util.List;

/**
 *
 * @author Administrateur
 */
public class PartieService {
    
    private PartieDAO dao = new PartieDAO();
    
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
