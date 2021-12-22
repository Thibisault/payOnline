package com.payMyBudy.payOnline.service;

import com.payMyBudy.payOnline.AleatoireComptePersonnel;
import com.payMyBudy.payOnline.CommandeNaviguationBaseDonnées;
import com.payMyBudy.payOnline.model.ComptePersonnelModel;
import com.payMyBudy.payOnline.model.VirementModel;
import com.payMyBudy.payOnline.repository.IComptePersonnelRepository;
import com.payMyBudy.payOnline.repository.IVirementRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VirementServiceTest {

    @Autowired
    VirementService virementService;

    @Autowired
    IComptePersonnelRepository iComptePersonnelRepository;

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

    @BeforeEach
    private void creerComptePersonnel(){
        comptePersonnelModelList.addAll(aleatoireComptePersonnel.creerComptePersonnelAleatoire(nombreDeCompteCreeAleatoirement));
        comptePersonnelModelEmetteur = comptePersonnelModelList.get(premierComptePersonnelDeListe);
        comptePersonnelModelRecepteur = comptePersonnelModelList.get(premierComptePersonnelDeListe+1);
        comptePersonnelModelEmetteur.getListAmi().add(comptePersonnelModelRecepteur);
        iComptePersonnelRepository.save(comptePersonnelModelEmetteur);
        montantTransaction = ((comptePersonnelModelEmetteur.getSolde()/2) * tauxCommission) + (comptePersonnelModelEmetteur.getSolde()/2);
        montantCommission = (comptePersonnelModelEmetteur.getSolde()/2) * tauxCommission;
    }

    @AfterEach
    private void supprimerToutLesComptePersonnel(){
        commandeNaviguationBaseDonnées.supprimerToutLeContenuDeLaBaseDeDonnees();
    }


    @Test
    void recupererLaDateTest() throws Exception {
        VirementModel virementModel = virementService.faireUneTransaction("Commentaire", comptePersonnelModelEmetteur, comptePersonnelModelRecepteur, montantTransaction);
        Date date = virementService.recupererLaDate(virementModel);
        assertEquals(virementModel.getDatePrecise(), date);
    }

    @Test
    void verifierSiAmiDansListeTest() {
        assertTrue(virementService.verifierSiAmiDansListe(comptePersonnelModelEmetteur, comptePersonnelModelRecepteur.getAdresseMail()));
    }

    @Test
    void verifierSoldeDeEmetteurTest() {
        //Test si le solde est effectivement superieur au montant de la transaction
        assertTrue(virementService.verifierSoldeDeEmetteur(comptePersonnelModelEmetteur, montantTransaction));

        montantTransaction = comptePersonnelModelEmetteur.getSolde()+1.0;
        //Test si le solde est inférieur au montant de la transaction
        assertTrue(virementService.verifierSoldeDeEmetteur(comptePersonnelModelEmetteur, montantTransaction) == false);
    }

    @Test
    void persisterTransactionTest() {
        VirementModel virementModel = new VirementModel();
        virementService.persisterTransaction(virementModel);
        assertTrue(iVirementRepository.findByidVirement(virementModel.getIdVirement()).isPresent());
        assertTrue(iVirementRepository.findByidVirement(virementModel.getIdVirement()+1).isPresent() == false);
    }

    @Test
    void getVirementModelByIdTest() {
        VirementModel virementModel = new VirementModel();
        iVirementRepository.save(virementModel);
        assertTrue(virementService.getVirementModelById(virementModel.getIdVirement()).isPresent());
        assertTrue(virementService.getVirementModelById(virementModel.getIdVirement()+1).isPresent() == false);
    }

    /*
    @Test
    void getVirementModelByIdComptePersonnelTest() {
        VirementModel virementModel = new VirementModel();
        VirementModel virementModel1 = virementService.getVirementModelByIdComptePersonnel(virementModel.getIdComptePersonnel().getIdComptePersonnel());
        iVirementRepository.save(virementModel);
        iVirementRepository.save(virementModel1);
        assertEquals(virementModel, virementModel1);
    }
     */

    @Test
    void mettreAJourSoldeUtilisateurapresTransactionTest() throws Exception {
        double soldeEmetteurAvantTransaction = comptePersonnelModelEmetteur.getSolde();
        double soldeRecepteurAvantTransaction = comptePersonnelModelRecepteur.getSolde();
        virementService.mettreAJourSoldeUtilisateurapresTransaction(comptePersonnelModelEmetteur, comptePersonnelModelRecepteur,
                montantTransaction);
        double soldeEmetteurApresTransaction = comptePersonnelModelEmetteur.getSolde();
        double soldeRecepteurApresTransaction = comptePersonnelModelRecepteur.getSolde();

        assertEquals(soldeEmetteurAvantTransaction - montantTransaction, soldeEmetteurApresTransaction);
        assertEquals(soldeRecepteurAvantTransaction + montantTransaction , soldeRecepteurApresTransaction);
    }

    @Test
    void calculerCommissionTest() {
        VirementModel virementModel = new VirementModel();
        virementService.calculerCommission(virementModel, montantTransaction);
        assertTrue(virementModel.getMontantTransaction() == montantTransaction);
        assertTrue(virementModel.getMontantCommission() == 0.005* montantTransaction);
    }

    @Test
    void ecrireCommentaireTest() {
        VirementModel virementModel = new VirementModel();
        virementService.ecrireCommentaire(virementModel,"Commentaire");
        assertTrue(virementModel.getDescription().equalsIgnoreCase("Commentaire"));
    }

    @Test
    void persisterLaTraceDeLaTransactionTest() {
        VirementModel virementModel = new VirementModel();
        virementService.persisterLaTraceDeLaTransaction(virementModel, montantTransaction,"Commentaire");
        assertTrue(virementModel.getDescription().equalsIgnoreCase("Commentaire"));
        assertEquals(virementModel.getMontantTransaction(), montantTransaction);
    }

    @Test
    void faireUneTransactionTest() throws Exception {

        double soldeEmetteurAvantTransaction = comptePersonnelModelEmetteur.getSolde();
        double soldeRecepteurAvantTransaction = comptePersonnelModelRecepteur.getSolde();

        virementService.faireUneTransaction("Commentaire", comptePersonnelModelEmetteur,
                comptePersonnelModelRecepteur, montantTransaction);

        double soldeEmetteurApresTransaction = comptePersonnelModelEmetteur.getSolde();
        double soldeRecepteurApresTransaction = comptePersonnelModelRecepteur.getSolde();

        assertEquals(soldeRecepteurApresTransaction, soldeRecepteurAvantTransaction + montantTransaction);
        assertEquals(soldeEmetteurApresTransaction, soldeEmetteurAvantTransaction - montantTransaction);

        //Tester l'exception si jamais au cours du virement, l'utilisateur n'est pas dans ta liste d'ami ou so le montant de la trasaction dépasse le solde
        try {
            virementService.faireUneTransaction("Commentaire", comptePersonnelModelEmetteur,
                    comptePersonnelModelRecepteur, montantTransaction);
            fail("Ma méthode aurait dû renvoyer l'exception : 'Cet utilisateur n'est pas dans ta liste d'ami' ou 'Le montant est de la transaction dépasse ton solde'");
        } catch (Exception exception){
            assert (exception.getMessage().contains("Cet utilisateur n'est pas dans ta liste d'ami") ||
                    exception.getMessage().contains("Le montant est de la transaction dépasse ton solde"));
        }


    }
    @Test
    void obtenirListeVirementPourUnComptePersonnelTest() throws Exception {
        virementService.faireUneTransaction("commentaire", comptePersonnelModelEmetteur, comptePersonnelModelRecepteur, 10);
        virementService.faireUneTransaction("commentaire", comptePersonnelModelEmetteur, comptePersonnelModelRecepteur, 10);
        virementService.faireUneTransaction("commentaire", comptePersonnelModelEmetteur, comptePersonnelModelRecepteur, 10);
        virementService.faireUneTransaction("commentaire", comptePersonnelModelEmetteur, comptePersonnelModelRecepteur, 10);
        virementService.faireUneTransaction("commentaire", comptePersonnelModelEmetteur, comptePersonnelModelRecepteur, 10);
        //List<ComptePersonnelModel> virementModelList = virementService.obtenirListeVirementPourUnComptePersonnel(comptePersonnelModelEmetteur);
        //System.out.println(virementModelList.size());
    }
}