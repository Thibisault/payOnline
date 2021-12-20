package com.payMyBudy.payOnline.repository;

import com.payMyBudy.payOnline.AleatoireComptePersonnel;
import com.payMyBudy.payOnline.CommandeNaviguationBaseDonnées;
import com.payMyBudy.payOnline.model.ComptePersonnelModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OperationBancaireRepositoryTest {

    @Autowired
    private CommandeNaviguationBaseDonnées commandeNaviguationBaseDonnées;

    @Autowired
    private AleatoireComptePersonnel aleatoireComptePersonnel;

    private ComptePersonnelModel comptePersonnelModel = new ComptePersonnelModel();




    private List<ComptePersonnelModel> comptePersonnelModelList = new ArrayList<>();
    private int nombreDeCompteCreeAleatoirement = 4;

    @BeforeEach
    private void initialiserBaseDeDonnees() {
        comptePersonnelModelList.addAll(aleatoireComptePersonnel.creerComptePersonnelAleatoire(nombreDeCompteCreeAleatoirement));
    }

    @AfterEach
    private void supprimerElementAjouterPourTest() {
        for (ComptePersonnelModel compte : comptePersonnelModelList) {
            //commandeNaviguationBaseDonnées.supprimerComptePersonnelParId(compte.getIdComptePersonnel());
        }
    }

    public void accepterVirementViaBanqueTest(int id) {
    }
}