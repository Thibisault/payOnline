package com.payMyBudy.payOnline;

import com.payMyBudy.payOnline.model.ComptePersonnelModel;
import com.payMyBudy.payOnline.repository.IComptePersonnelRepository;
import com.payMyBudy.payOnline.repository.IOperationBancaireRepository;
import com.payMyBudy.payOnline.repository.IVirementRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.transaction.Transactional;
import java.util.Optional;

@Data
@AllArgsConstructor
@Component
public class CommandeNaviguationBaseDonnées {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    IVirementRepository iVirementRepository;

    @Autowired
    IOperationBancaireRepository iOperationBancaireRepository;

    @Autowired
    IComptePersonnelRepository iComptePersonnelRepository;

    public CommandeNaviguationBaseDonnées() {
        super();
    }


    public Optional<ComptePersonnelModel> chercherComptePersonnelParIdPourTes(Integer comptePersonnelId) {
        return iComptePersonnelRepository.findById(comptePersonnelId);
    }

    public Boolean chercherComptePersonnelParPseudoPourTest(String pseudo) {
        ComptePersonnelModel comptePersonnelModel = this.iComptePersonnelRepository.findByPseudo(pseudo).orElse(null);
        if (comptePersonnelModel == null) {
            return false;
        }
        return true;
    }

    public Boolean chercherComptePersonnelParMailPourTest(String adresseMail) {
        ComptePersonnelModel comptePersonnelModel = this.iComptePersonnelRepository.findByAdresseMail(adresseMail).orElse(null);
        if (comptePersonnelModel == null) {
            return false;
        }
        return true;
    }

    public Boolean chercherComptePersonnelParPasswordPourTest(String password) {
        ComptePersonnelModel comptePersonnelModel = this.iComptePersonnelRepository.findByPassword(password).orElse(null);
        if (comptePersonnelModel == null) {
            return false;
        }
        return true;
    }

    public Boolean chercherComptePersonnelParNumeroDeComptePourTest(int numeroDeCompte) {
        ComptePersonnelModel comptePersonnelModel = this.iComptePersonnelRepository.findByNumeroDeCompte(numeroDeCompte).orElse(null);
        if (comptePersonnelModel == null) {
            return false;
        }
        return true;
    }

    public Boolean chercherComptePersonnelParSoldePourTest(double solde) {
        ComptePersonnelModel comptePersonnelModel = this.iComptePersonnelRepository.findBySolde(solde).orElse(null);
        if (comptePersonnelModel == null) {
            return false;
        }
        return true;
    }

    @Transactional
    public ComptePersonnelModel ajouterComptePersonnelDansBddPourTest(ComptePersonnelModel comptePersonnelModel) {
        return iComptePersonnelRepository.save(comptePersonnelModel);
    }

    @Transactional
    public void supprimerTouteLesComptesPersonnelDeLaBddPourTest() {
        iComptePersonnelRepository.deleteAll();
    }

    @Transactional
    public void supprimerTouteLesVirementsDeLaBddPourTest(){ iVirementRepository.deleteAll();}

    @Transactional
    public void supprimerTouteLesOperationBancairesDeLaBddPourTest(){ iOperationBancaireRepository.deleteAll();}

    @Transactional
    public void supprimerToutLeContenuDeLaBaseDeDonnees(){
        this.supprimerTouteLesVirementsDeLaBddPourTest();
        this.supprimerTouteLesOperationBancairesDeLaBddPourTest();
        this.supprimerTouteLesComptesPersonnelDeLaBddPourTest();
    }
}
