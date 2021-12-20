package com.payMyBudy.payOnline.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Entity
@Table(name = "compte_personnel")
public class ComptePersonnelModel {

    @Id
    @Column(name = "idComptePersonnel")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idComptePersonnel;


    @ToString.Exclude
    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }
    )
    @JoinColumn(name = "idComptePersonnel")
    private List<ComptePersonnelModel> listAmi = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(
            mappedBy = "idComptePersonnel",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<VirementModel> virementModelList = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(
            mappedBy = "idComptePersonnel",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Fetch(value = FetchMode.SUBSELECT)
    private List<OperationBancaireModel> operationBancaireModelList = new ArrayList<>();

    @Basic
    @Column(name = "pseudo", length=16, nullable=false, unique = true)
    private String pseudo;

    @Basic
    @Column(name = "adresseMail",nullable = false, unique = true)
    private String adresseMail;

    @Basic
    @Column(name = "password")
    private String password;

    @Basic
    @Column(name = "numeroDeCompte",length=30)
    private int numeroDeCompte;

    @Basic
    @Column(name = "solde")
    private double solde;

    public ComptePersonnelModel(){
        super();
    }
}
