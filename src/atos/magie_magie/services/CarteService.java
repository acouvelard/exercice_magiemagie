/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magie_magie.services;

import atos.magie_magie.dao.CarteDAO;
import atos.magie_magie.entity.Carte;

/**
 *
 * @author Administrateur
 */
public class CarteService {
    
    private CarteDAO dao = new CarteDAO();
    
    public Carte recupererCarteViaId (long carteId) {
        
        Carte carte = dao.recupererCarteViaId(carteId);
        
        return carte;
    }
    
}
