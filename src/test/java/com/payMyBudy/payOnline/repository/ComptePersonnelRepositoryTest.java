/*
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
class ComptePersonnelRepositoryTest {

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

    /*
    @AfterEach
    private void supprimerElementAjouterPourTest() {
        for (ComptePersonnelModel compte : comptePersonnelModelList) {
            commandeNaviguationBaseDonnées.supprimerComptePersonnelParId(compte.getIdComptePersonnel());
        }
    }


    @Test
    public void creerComptePersonnelTest() {
        comptePersonnelModel.setPseudo("Pou@llloammax");
        comptePersonnelModel.setNumeroDeCompte(1);
        comptePersonnelModel.setPassword("@oeipzrieoz");
        comptePersonnelModel.setAdresseMail("sdfuiosqdf42@gm");

        comptePersonnelModelList.add(comptePersonnelModel);
        comptePersonnelRepository.creerComptePersonnel(comptePersonnelModel);

        assertTrue(comptePersonnelModelList.get(nombreDeCompteCreeAleatoirement).getPseudo().equals("Pou@llloammax"));
    }

    @Test
    public void verifierSiPseudoExisteDejaTest() {
        boolean verifierSiPseudoExistePas = comptePersonnelRepository.verifierSiPseudoExisteDeja("@@@@@@");
        boolean verifierSiPseudoExiste = comptePersonnelRepository.verifierSiPseudoExisteDeja(comptePersonnelModelList.get(0).getPseudo());
        assertTrue(verifierSiPseudoExistePas == false && verifierSiPseudoExiste == true);
    }

    @Test
    public void verifierSiAdresseMailExisteDejaTest() {
        Boolean verifierSiAdresseMailExistePas = comptePersonnelRepository.verifierSiAdresseExisteDeja("mail@mail]@");
        Boolean verifierSiAdresseMailExiste = comptePersonnelRepository.verifierSiAdresseExisteDeja(comptePersonnelModelList.get(0).getAdresseMail());
        assertTrue(verifierSiAdresseMailExistePas == false && verifierSiAdresseMailExiste == true);
    }
}
     */
