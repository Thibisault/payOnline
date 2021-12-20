package com.payMyBudy.payOnline.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@Entity
@Table(name = "virement")
public class VirementModel {

    @Id
    @Column(name = "idVirement")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idVirement;


    @ManyToOne
    @JoinColumn(name = "idComptePersonnel", insertable = false, updatable = false)
    private ComptePersonnelModel idComptePersonnel;

    @ManyToOne
    @JoinColumn(name = "idComptePersonnelEmetteur")
    private ComptePersonnelModel idComptePersonnelEmetteur;

    @ManyToOne
    @JoinColumn(name = "idComptePersonnelRecepteur")
    private ComptePersonnelModel idComptePersonnelRecepteur;

    @Basic
    @Column(name = "pseudoEmetteur")
    private String pseudoEmetteur;

    @Basic
    @Column(name = "montantTransaction")
    private double montantTransaction;

    @Basic
    @Column(name = "montantCommission")
    private double montantCommission;

    @Basic
    @DateTimeFormat //Ajouter le format de la date :(pattern : "yy/mm/aa")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "datePrecise")
    private Date datePrecise;

    @Basic
    @Column(name = "description")
    private String description;

    public VirementModel() { super();
    }
}
