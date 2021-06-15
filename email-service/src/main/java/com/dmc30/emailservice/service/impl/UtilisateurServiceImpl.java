package com.dmc30.emailservice.service.impl;

import com.dmc30.emailservice.data.entity.UtilisateurEntity;
import com.dmc30.emailservice.data.repository.UtilisateurRepository;
import com.dmc30.emailservice.service.contract.UtilisateurService;
import com.dmc30.emailservice.service.dto.UtilisateurDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    UtilisateurRepository utilisateurRepository;

    @Autowired
    public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public List<UtilisateurDto> findAll() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        List<UtilisateurDto> utilisateurDtos = new ArrayList<>();
        List<UtilisateurEntity> utilisateurEntities = utilisateurRepository.findAll();
        for (UtilisateurEntity utilisateur : utilisateurEntities) {
            utilisateurDtos.add(modelMapper.map(utilisateur, UtilisateurDto.class));
        }
        return utilisateurDtos;
    }

    @Override
    public UtilisateurDto findById(Long utilisateurId) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(utilisateurRepository.getById(utilisateurId), UtilisateurDto.class);
    }
}
