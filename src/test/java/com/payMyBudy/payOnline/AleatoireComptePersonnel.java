package com.payMyBudy.payOnline;

import com.payMyBudy.payOnline.model.ComptePersonnelModel;
import com.payMyBudy.payOnline.model.OperationBancaireModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class AleatoireComptePersonnel {

    @Autowired
    CommandeNaviguationBaseDonnées commandeNaviguationBaseDonnées;

    private Random random = new Random(System.currentTimeMillis());

    private static String getRandomStr(int n) {
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        StringBuilder s = new StringBuilder(n);

        for (int i = 0; i < n; i++) {
            int index = (int) (str.length() * Math.random());
            s.append(str.charAt(index));
        }
        return s.toString();
    }

    @Transactional
    public List<ComptePersonnelModel> creerComptePersonnelAleatoire(int nombreDeComptePersonnel){

        List<ComptePersonnelModel> comptePersonnelModelList = new ArrayList<>();
        String chaineTemporaire = "";
        int entierTemporaire = 0;

        for (int y =0; y < nombreDeComptePersonnel ; y++) {
            ComptePersonnelModel comptePersonnelModel = new ComptePersonnelModel();
            OperationBancaireModel operationBancaireModel = new OperationBancaireModel();

            do {
                chaineTemporaire = "";
                for (int i = 0; i < random.nextInt(7) + 5; i++) {
                    chaineTemporaire = chaineTemporaire + getRandomStr(1);
                }
                if (commandeNaviguationBaseDonnées.chercherComptePersonnelParPseudoPourTest(chaineTemporaire) == false) {
                    comptePersonnelModel.setPseudo(chaineTemporaire);
                }
            } while (comptePersonnelModel.getPseudo() == null);

            do {
                chaineTemporaire = "";
                for (int i = 0; i < random.nextInt(7) + 5; i++) {
                    chaineTemporaire = chaineTemporaire + getRandomStr(1);
                }
                if (commandeNaviguationBaseDonnées.chercherComptePersonnelParMailPourTest(chaineTemporaire) == false){
                    comptePersonnelModel.setAdresseMail(chaineTemporaire);
                }
            } while (comptePersonnelModel.getAdresseMail() == null);

            do {
                chaineTemporaire = "";
                for (int i = 0; i < random.nextInt(7) + 5; i++) {
                    chaineTemporaire = chaineTemporaire + getRandomStr(1);
                }
                if (commandeNaviguationBaseDonnées.chercherComptePersonnelParPasswordPourTest(chaineTemporaire) == false){
                    comptePersonnelModel.setPassword(chaineTemporaire);
                }
            } while (comptePersonnelModel.getPassword() == null);

            do {
                entierTemporaire = 0;
                entierTemporaire = entierTemporaire + random.nextInt(11111) + 1000;
                if (commandeNaviguationBaseDonnées.chercherComptePersonnelParNumeroDeComptePourTest(entierTemporaire) == false) {
                    comptePersonnelModel.setNumeroDeCompte(entierTemporaire);
                }
            } while (entierTemporaire != comptePersonnelModel.getNumeroDeCompte());

            do {
                entierTemporaire = 0;
                entierTemporaire = entierTemporaire + random.nextInt(11111) + 1000;
                if (commandeNaviguationBaseDonnées.chercherComptePersonnelParSoldePourTest(entierTemporaire) == false) {
                    comptePersonnelModel.setSolde(entierTemporaire);
                }
            } while (entierTemporaire != comptePersonnelModel.getSolde());

            entierTemporaire = 0;
            entierTemporaire = entierTemporaire + random.nextInt(1111111) + 1000;
            operationBancaireModel.setMontantCompteBancaire(entierTemporaire);
            comptePersonnelModel.getOperationBancaireModelList().add(operationBancaireModel);


            comptePersonnelModelList.add(comptePersonnelModel);
            commandeNaviguationBaseDonnées.ajouterComptePersonnelDansBddPourTest(comptePersonnelModel);
        }
        return comptePersonnelModelList;
    }
}
