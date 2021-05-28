package com.dmc30.livreservice.service.contract;

import com.dmc30.livreservice.shared.livre.LivreDto;

import java.util.List;

public interface LivreService {

    List<LivreDto> findAll();
    List<LivreDto> findLivreByTitreContaining(String motCle);
    List<LivreDto> findLivreByAuteur(Long auteurId);
    List<LivreDto> findLast12();
    LivreDto findLivreById(Long livreId);
}
