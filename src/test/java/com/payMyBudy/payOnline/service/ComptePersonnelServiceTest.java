package com.payMyBudy.payOnline.service;

import com.payMyBudy.payOnline.AleatoireComptePersonnel;
import com.payMyBudy.payOnline.CommandeNaviguationBaseDonnées;
import com.payMyBudy.payOnline.model.ComptePersonnelModel;


import com.payMyBudy.payOnline.repository.IComptePersonnelRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class ComptePersonnelServiceTest {

    @Autowired
    private IComptePersonnelRepository iComptePersonnelRepository;

    @Autowired
    private CommandeNaviguationBaseDonnées commandeNaviguationBaseDonnées;

    @Autowired
    private AleatoireComptePersonnel aleatoireComptePersonnel;

    private ComptePersonnelModel comptePersonnelModel = new ComptePersonnelModel();

    @Autowired
    private ComptePersonnelService comptePersonnelService;

    private List<ComptePersonnelModel> comptePersonnelModelList = new ArrayList<>();
    private int nombreDeCompteCreeAleatoirement = 4;
    private int premierComptePersonnelDeListe = nombreDeCompteCreeAleatoirement - nombreDeCompteCreeAleatoirement;

    @BeforeEach
    private void initialiserBaseDeDonnees() {
        comptePersonnelModelList.addAll(aleatoireComptePersonnel.creerComptePersonnelAleatoire(nombreDeCompteCreeAleatoirement));
    }

    @AfterEach
    private void supprimerElementAjoutePourTest() {
        commandeNaviguationBaseDonnées.supprimerToutLeContenuDeLaBaseDeDonnees();
    }

    /**
     * Test si un comptePersonnel a bien été ajouté dans la base de données
     */
    @Test
    @Transactional
    public void ajouterComptePersonnelModelDansBddTestSiFonctionne() {
        comptePersonnelService.ajouterComptePersonnelModelDansBdd(comptePersonnelModelList.get(premierComptePersonnelDeListe));
        assertTrue(iComptePersonnelRepository.findById(comptePersonnelModelList.get(premierComptePersonnelDeListe).getIdComptePersonnel()).isPresent());
    }

    /**
     * test si on peut identifier un ami dans dans la table listAmi du modèle comptePersonnel grâce à son mail.
     * Test si une adresse mail qui n'est pas dans la liste d'ami declanche l'erreur
     */
    @Test
    public void chercherUnAmiDansLaListeParMailTest() {
        ComptePersonnelModel comptePersonnelEmetteur = comptePersonnelModelList.get(premierComptePersonnelDeListe);
        comptePersonnelEmetteur.getListAmi().add(comptePersonnelModelList.get(premierComptePersonnelDeListe + 1));

        Boolean testSiAdresseMailDansListe = null;
        Boolean testSiFonctionRepereCetteAdresse;

        //test si l'adresse mail existe dans la liste ami
        for (ComptePersonnelModel compte : comptePersonnelEmetteur.getListAmi()) {
            if (compte.getAdresseMail().equalsIgnoreCase(comptePersonnelModelList.get(premierComptePersonnelDeListe + 1).getAdresseMail())) {
                testSiAdresseMailDansListe = false;
            }
        }
        testSiFonctionRepereCetteAdresse = iComptePersonnelRepository.findByIdComptePersonnelAndListAmi_AdresseMail(comptePersonnelEmetteur.getIdComptePersonnel(),
                comptePersonnelModelList.get(premierComptePersonnelDeListe + 1).getAdresseMail()).isPresent();
        assertEquals(testSiFonctionRepereCetteAdresse, testSiAdresseMailDansListe);

        //Test si l'adresse mail n'existe pas dans la liste ami
        for (ComptePersonnelModel compte : comptePersonnelEmetteur.getListAmi()) {
            if (compte.getAdresseMail().equalsIgnoreCase(comptePersonnelModelList.get(premierComptePersonnelDeListe + 2).getAdresseMail())) {
                testSiAdresseMailDansListe = true;
            }
        }
        testSiFonctionRepereCetteAdresse = iComptePersonnelRepository.findByIdComptePersonnelAndListAmi_AdresseMail(comptePersonnelEmetteur.getIdComptePersonnel(),
                comptePersonnelModelList.get(premierComptePersonnelDeListe + 2).getAdresseMail()).isPresent();
        assertEquals(testSiFonctionRepereCetteAdresse, testSiAdresseMailDansListe);
    }

    /**
     * Test si la fonction repère bien les pseudos dans la base de données
     */
    @Test
    public void chercherComptePersonnelParPseudoTest() {
        Boolean testerSiPseudoExiste = null;
        String pseudoATester = comptePersonnelModelList.get(premierComptePersonnelDeListe).getPseudo();

        //Test si le pseudo existe vraiment dans la base de données
        testerSiPseudoExiste = comptePersonnelService.chercherComptePersonnelParPseudo(pseudoATester);
        assertEquals(testerSiPseudoExiste, true);

        //Test si le pseudo n'existe pas dans la base de données
        pseudoATester = pseudoATester + pseudoATester + pseudoATester + pseudoATester + pseudoATester;
        testerSiPseudoExiste = comptePersonnelService.chercherComptePersonnelParPseudo(pseudoATester);
        assertEquals(testerSiPseudoExiste, false);
    }

    /**
     * Test si la fonction trouve bien un compte personnel grâce à son adresse mail
     */
    @Test
    public void chercherComptePersonnelParMailTest() {
        Boolean testerSiAdresseMailExiste = null;
        String adresseMailATester = comptePersonnelModelList.get(premierComptePersonnelDeListe).getAdresseMail();

        //Test si l'adresse mail existe vraiment dans la base de données
        testerSiAdresseMailExiste = comptePersonnelService.chercherComptePersonnelParMail(adresseMailATester);
        assertEquals(testerSiAdresseMailExiste, true);

        //Test si l'adresse mail n'existe pas dans la base de données
        adresseMailATester = adresseMailATester + adresseMailATester + adresseMailATester + adresseMailATester + adresseMailATester;
        testerSiAdresseMailExiste = comptePersonnelService.chercherComptePersonnelParMail(adresseMailATester);
        assertEquals(testerSiAdresseMailExiste, false);
    }

    /**
     * Test si la fonction renvoie bien un compte personnel via son adresse mail
     */
    @Test
    public void recupererComptePersonnelParAdresseMailTest() {
        ComptePersonnelModel comptePersonnelModel = comptePersonnelService.recupererComptePersonnelParAdresseMail(comptePersonnelModelList.get(premierComptePersonnelDeListe).getAdresseMail());
        assertEquals(comptePersonnelModel.getAdresseMail(), comptePersonnelModelList.get(premierComptePersonnelDeListe).getAdresseMail());
    }

    /**
     * Test si un comptePersonnel a bien été crée en base de données.
     *
     * @throws Exception
     */
    @Transactional
    @Test
    public void creerComptePersonnelTest() throws Exception {

        //Tester si le compte a bien été sauvegarder en base
        assertTrue(iComptePersonnelRepository.findByPseudo(comptePersonnelModelList.get(premierComptePersonnelDeListe).getPseudo()).isPresent());

        //tester si le code ne fonctionne pas si le compte n'est pas ajouté en base de données
        assertEquals(false, iComptePersonnelRepository.findByPseudo(comptePersonnelModelList.get(premierComptePersonnelDeListe).getPseudo() + "azeazea").isPresent());

        //Tester l'exception si j'amais à la création du compte, un compte avec un pseudo ou une adresse mail similaire existe déjà
        try {
            comptePersonnelService.creerComptePersonnel(comptePersonnelModelList.get(premierComptePersonnelDeListe));
            fail("Ma méthode aurait dû renvoyer l'exception : 'Pseudo existe déjà' ou 'AdresseMail existe déjà' mais ne l'a pas fait");
        } catch (Exception exception) {
            assert (exception.getMessage().contains("Pseudo existe déjà") || exception.getMessage().contains("AdresseMail existe déjà"));
        }
    }


    /**
     * Test si un ami a bien été ajouté dans la listAmi de la table ComptePersonnelModel dans la base de données.
     *
     * @throws Exception
     */
    /*
    @Transactional
    @Test
    public void ajouterUnAmiTest() throws Exception {
        ComptePersonnelModel comptePersonnelModel = comptePersonnelModelList.get(premierComptePersonnelDeListe);
        comptePersonnelService.ajouterUnAmi(comptePersonnelModel, comptePersonnelModelList.get(premierComptePersonnelDeListe + 1).getAdresseMail());
        Boolean testerSiVrai = null;
        String messsageErreur = null;

        //Test si l'adresse mail est dans la liste d'ami.
        for (ComptePersonnelModel compte : comptePersonnelModel.getListAmi()) {
            if (compte.getAdresseMail().equalsIgnoreCase(comptePersonnelModelList.get(premierComptePersonnelDeListe + 1).getAdresseMail())) {
                testerSiVrai = true;
            } else {
                testerSiVrai = false;
                messsageErreur = "N'a pas réussi à trouver le compte associé à ce mail dans la liste ami";
            }
        }
        assertTrue(testerSiVrai == true, messsageErreur);

        //test si l'adresse mail n'est pas dans la liste d'ami.
        for (ComptePersonnelModel compte : comptePersonnelModel.getListAmi()) {
            if (compte.getAdresseMail().equalsIgnoreCase(comptePersonnelModelList.get(premierComptePersonnelDeListe + 2).getAdresseMail())) {
                testerSiVrai = true;
                messsageErreur = "";
                messsageErreur = "Il a trouvé ce compte dans la liste ami alors qu'il n'aurait pas dû";
            } else {
                testerSiVrai = false;
            }
        }
        assertTrue(testerSiVrai == false, messsageErreur);

        //Test si on ajoute plusieurs amis avec la même adresse mail
        comptePersonnelService.ajouterUnAmi(comptePersonnelModel, comptePersonnelModelList.get(premierComptePersonnelDeListe + 1).getAdresseMail());
        assertEquals(comptePersonnelModel.getListAmi().size(), 1);

        //Test si l'adresse mail n'est pas dans la base de données
        try {
            comptePersonnelService.ajouterUnAmi(comptePersonnelModel,
                    comptePersonnelModelList.get(premierComptePersonnelDeListe).getAdresseMail() + "azazazazazazaza");
            fail("Ma méthode aurait dû renvoyer l'exception : 'Aucun compte associé à cette adresse mail' mais ne l'a pas fait");
        } catch (Exception exception) {
            assert (exception.getMessage().contains("Aucun compte associé à cette adresse mail"));
        }
    }
     */

    //TODO : à supprimer quand fini de jouer avec
    @Test
    public void obtenirListe() {
        System.out.println(comptePersonnelService.obtenirToutLesComptePersonnel());
    }


    @Test
    public void seConnecterTest() throws Exception {
        assertNotNull(comptePersonnelService.seConnecter(comptePersonnelModelList.get(0).getPseudo(), comptePersonnelModelList.get(0).getPassword()));
        try {
            comptePersonnelService.seConnecter(comptePersonnelModelList.get(0).getPseudo(), comptePersonnelModelList.get(1).getPassword());
            fail("Ma méthode aurait dû renvoyer l'exception : 'Le mot de passe ne correspond pas au pseudo' mais ne l'a pas fait");
        } catch (Exception exception) {
            assert (exception.getMessage().contains("Le mot de passe ne correspond pas au pseudo"));
        }
    }
}

    /*
    @Rollback(value = false)
    @Test
    public void creerComptePersonnelTest(){

        comptePersonnelModel.setNumeroDeCompte(1);
        comptePersonnelModel.setPseudo("@Pseudo@ps");
        comptePersonnelModel.setAdresseMail("@mail@adresse");

        try {
            comptePersonnelService.creerComptePersonnel(comptePersonnelModel);
            comptePersonnelModelList.add(comptePersonnelModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //assertTrue(entityManager.find(ComptePersonnelModel.class, comptePersonnelModel.getPseudo()).equals("@Pseudo@ps"));
    }
     */
