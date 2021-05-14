package com.dmc30.livreservice.service.contract;

import com.dmc30.livreservice.data.entity.livre.Auteur;

import java.util.List;

public interface AuteurService {

    List<Auteur> findAll();
    List<Auteur> findAuteurByNomOrPrenom(String nom, String prenom);
    Auteur findAuteurById(int id);
    List<Auteur> findAuteurByLivres(int id);
    void save(Auteur auteur);
}