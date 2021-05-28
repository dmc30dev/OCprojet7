package com.dmc30.livreservice.data.entity.bibliotheque;

import com.dmc30.livreservice.data.entity.commun.Adresse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "bibliotheque")
@Getter
@Setter
public class Bibliotheque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "numero_siret")
    private String numeroSiret;

    @Column(name = "nom")
    private String nom;

    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_adresse")
    private Adresse adresse;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_bibliotheque")
    private Set<Ouvrage> ouvrages;
}
