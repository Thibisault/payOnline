package com.payMyBudy.payOnline.service;

import com.payMyBudy.payOnline.AleatoireComptePersonnel;
import com.payMyBudy.payOnline.CommandeNaviguationBaseDonnées;
import com.payMyBudy.payOnline.model.ComptePersonnelModel;
import com.payMyBudy.payOnline.model.OperationBancaireModel;
import com.payMyBudy.payOnline.repository.IVirementRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OperationBancaireServiceTest {

    @Autowired
    VirementService virementService;

    @Autowired
    IVirementRepository iVirementRepository;

    @Autowired
    AleatoireComptePersonnel aleatoireComptePersonnel;

    @Autowired
    CommandeNaviguationBaseDonnées commandeNaviguationBaseDonnées;

    private List<ComptePersonnelModel> comptePersonnelModelList = new ArrayList<>();
    private int nombreDeCompteCreeAleatoirement = 4;
    private int premierComptePersonnelDeListe = nombreDeCompteCreeAleatoirement - nombreDeCompteCreeAleatoirement;
    private ComptePersonnelModel comptePersonnelModelEmetteur;
    private ComptePersonnelModel comptePersonnelModelRecepteur;
    private double tauxCommission = 0.005;
    private double montantTransaction;
    private double montantCommission;
    private double montantRetraitBancaire;
    private double montantVirementBancaire;

    @Autowired
    OperationBancaireService operationBancaireService;

    @BeforeEach
    private void creerComptePersonnel(){
        comptePersonnelModelList.addAll(aleatoireComptePersonnel.creerComptePersonnelAleatoire(nombreDeCompteCreeAleatoirement));
        comptePersonnelModelEmetteur = comptePersonnelModelList.get(premierComptePersonnelDeListe);
        comptePersonnelModelRecepteur = comptePersonnelModelList.get(premierComptePersonnelDeListe+1);
        comptePersonnelModelEmetteur.getListAmi().add(comptePersonnelModelRecepteur);
        montantTransaction = ((comptePersonnelModelEmetteur.getSolde()/2) * tauxCommission) + (comptePersonnelModelEmetteur.getSolde()/2);
        montantCommission = (comptePersonnelModelEmetteur.getSolde()/2) * tauxCommission;
        montantVirementBancaire = (comptePersonnelModelEmetteur.getSolde()*2);
        montantRetraitBancaire = (comptePersonnelModelEmetteur.getSolde()/2);
    }

    @AfterEach
    private void supprimerToutLesComptePersonnel(){
        commandeNaviguationBaseDonnées.supprimerToutLeContenuDeLaBaseDeDonnees();
    }



    @Test
    void accepterVirementBancaire() {
    }

    @Test
    void verifierSoldePourRetrait() {
    }

    @Test
    void verifierMontantCompteBancaire() {
    }

    @Test
    void persisterOperationBancaire() {
    }

    @Test
    void mettreAJourSolde() {
    }

    @Test
    void recupererLaDate() {
    }

    @Test
    void recupererMontantRetrait() {
    }

    @Test
    void recupererMontantVersement() {
    }

    @Test
    void recupererDescription() {
    }

    @Test
    void calculerNovueauMontantCompteBancaire() {
    }

    @Test
    void persisterLaTraceDeOperationBancaire() {
    }

    @Test
    void faireUnVirementBancaire() throws Exception {
        OperationBancaireModel operationBancaireModel = new OperationBancaireModel();
        double soldeAvantVirement = comptePersonnelModelEmetteur.getSolde();
        operationBancaireService.faireUnVirementBancaire(comptePersonnelModelEmetteur, operationBancaireModel, montantVirementBancaire, "Description");
        double soldeApresvirement = comptePersonnelModelEmetteur.getSolde();
        assertEquals(soldeAvantVirement *2, soldeApresvirement);
    }

    @Test
    void faireUnRetraitBancaire() throws Exception {
        OperationBancaireModel operationBancaireModel = new OperationBancaireModel();
        double soldeAvantRetrait = comptePersonnelModelEmetteur.getSolde();
        operationBancaireService.faireUnRetraitBancaire(comptePersonnelModelEmetteur, operationBancaireModel, montantRetraitBancaire, "Description");
        double soldeApresRetrait = comptePersonnelModelEmetteur.getSolde();
        assertEquals(soldeAvantRetrait /2, soldeApresRetrait);
    }
}