package com.payMyBudy.payOnline.controller.dto;

import com.payMyBudy.payOnline.model.ComptePersonnelModel;
import lombok.Data;

@Data
public class VirementDto {

    private String mailDestinataire;
    private double montant;
    private int idCompteEmetteur;

}
