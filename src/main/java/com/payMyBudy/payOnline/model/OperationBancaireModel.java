package com.payMyBudy.payOnline.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@Entity
@Table(name = "operation_bancaire")
public class OperationBancaireModel {

    @Id
    @Column(name = "idOperationBancaire")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idOperationBancaire;



    @ManyToOne
    @JoinColumn(name = "idComptePersonnel", insertable = false, updatable = false)
    private ComptePersonnelModel idComptePersonnel;

    @Basic
    @DateTimeFormat //Ajouter le format de la date :(pattern : "yy/mm/aa")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "datePrecise")
    private Date datePrecise;

    @Basic
    @Column(name = "idDuComptePersonnel")
    private int idDuComptePersonnel;

    @Basic
    @Column(name = "description")
    private String description;

    @Basic
    @Column(name = "montantRetrait")
    private double montantRetrait;

    @Basic
    @Column(name = "montantVersement")
    private double montantVersement;

    @Basic
    @Column(name = "montantCompteBancaire")
    private double montantCompteBancaire;

    public OperationBancaireModel(){
        super();
    }
}
