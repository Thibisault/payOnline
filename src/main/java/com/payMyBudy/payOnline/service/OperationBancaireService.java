package com.payMyBudy.payOnline.service;

import com.payMyBudy.payOnline.model.ComptePersonnelModel;
import com.payMyBudy.payOnline.model.OperationBancaireModel;
import com.payMyBudy.payOnline.repository.IComptePersonnelRepository;
import com.payMyBudy.payOnline.repository.IOperationBancaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class OperationBancaireService {

    @Autowired
    private IOperationBancaireRepository iOperationBancaireRepository;

    @Autowired
    private IComptePersonnelRepository iComptePersonnelRepository;

    @Autowired
    private ComptePersonnelService comptePersonnelService;

    @Transactional
    public List<OperationBancaireModel> obtenirOperationBancairePourUnComptePersonnel(ComptePersonnelModel comptePersonnelModel){
        return iOperationBancaireRepository.findAllByIdDuComptePersonnel(comptePersonnelModel.getIdComptePersonnel());
    }

    //Savoir si la banque accepte la trandaction
    public Boolean accepterVirementBancaire() {
        return true;
    }

    // Verifier Si solde est suffisant pour retrait bancaire
    public Boolean verifierSoldePourRetrait(ComptePersonnelModel comptePersonnelModel, double montantRetrait) {
        if (comptePersonnelModel.getSolde() >= montantRetrait) {
            return true;
        }
        return false;
    }

    // Verifier si le montant du compte bancaire est suffisafement haut pour faire virement
    public Boolean verifierMontantCompteBancaire(OperationBancaireModel operationBancaireModel, double montantVersement){
        if (operationBancaireModel.getMontantCompteBancaire() < montantVersement){
            return false;
        }
        return true;
    }

    @Transactional
    public void persisterOperationBancaire(OperationBancaireModel operationBancaireModel) {
        iOperationBancaireRepository.save(operationBancaireModel);
    }

    //TODO : a faire
    @Transactional
    public void mettreAJourSolde(ComptePersonnelModel comptePersonnelModel, double montantTransaction){
        comptePersonnelModel.setSolde(comptePersonnelModel.getSolde() + montantTransaction);
    }

    public void recupererLaDate(OperationBancaireModel operationBancaireModel){
        Date date = new Date();
        operationBancaireModel.setDatePrecise(date);
    }

    public void recupererMontantRetrait(OperationBancaireModel operationBancaireModel, double montantRetrait){
        operationBancaireModel.setMontantRetrait(montantRetrait);
    }

    public void recupererMontantVersement(OperationBancaireModel operationBancaireModel, double montantVersement){
        operationBancaireModel.setMontantVersement(montantVersement);
    }

    public void recupererDescription(OperationBancaireModel operationBancaireModel, String description){
        operationBancaireModel.setDescription(description);
    }

    public void calculerNovueauMontantCompteBancaire(OperationBancaireModel operationBancaireModel, double montantTransaction){
            operationBancaireModel.setMontantCompteBancaire(operationBancaireModel.getMontantCompteBancaire() + montantTransaction);
        }

    @Transactional
    public void persisterLaTraceDeOperationBancaire(OperationBancaireModel operationBancaireModel, double montantRetrait, double montantVersement, double montantTransaction, String description){
        this.recupererDescription(operationBancaireModel, description);
        this.recupererMontantRetrait(operationBancaireModel, montantRetrait);
        this.recupererMontantVersement(operationBancaireModel, montantVersement);
        this.recupererLaDate(operationBancaireModel);
        this.calculerNovueauMontantCompteBancaire(operationBancaireModel, montantTransaction);
        this.persisterOperationBancaire(operationBancaireModel);
    }

    //Envoyer de l'argent sur solde
    public OperationBancaireModel faireUnVirementBancaire(ComptePersonnelModel comptePersonnelModel, OperationBancaireModel operationBancaireModel, double montantVirement, String description) throws Exception {
        if (accepterVirementBancaire() == false) {
            throw new Exception("La banque a refusé l'opération");
        }
        if (verifierMontantCompteBancaire(operationBancaireModel, montantVirement)){
            throw new Exception("Le solde de compte bancaire est inférieur au montant du versement");
        }
        operationBancaireModel.setIdDuComptePersonnel(comptePersonnelModel.getIdComptePersonnel());
        comptePersonnelModel.getOperationBancaireModelList().add(operationBancaireModel);
        operationBancaireModel.setIdComptePersonnel(comptePersonnelModel);
        this.mettreAJourSolde(comptePersonnelModel, + montantVirement);
        this.persisterLaTraceDeOperationBancaire(operationBancaireModel, 0, montantVirement, - montantVirement, description);
        iComptePersonnelRepository.save(comptePersonnelModel);
        return operationBancaireModel;
    }

    // faire un retrait bancaire et vérifier si le solde à retirer est suffisant// Envoyer de l'argent sur compte bancaire
    public OperationBancaireModel faireUnRetraitBancaire(ComptePersonnelModel comptePersonnelModel, OperationBancaireModel operationBancaireModel, double montantRetrait, String description) throws Exception {
        if (accepterVirementBancaire() == false) {
            throw new Exception("La banque a refusé l'opération");
        }
        if (verifierSoldePourRetrait(comptePersonnelModel, montantRetrait) == false) {
            throw new Exception("Le solde du compte est inférieur au montant du retrait");
        }
        operationBancaireModel.setIdDuComptePersonnel(comptePersonnelModel.getIdComptePersonnel());
        comptePersonnelModel.getOperationBancaireModelList().add(operationBancaireModel);
        operationBancaireModel.setIdComptePersonnel(comptePersonnelModel);
        this.mettreAJourSolde(comptePersonnelModel, - montantRetrait);
        this.persisterLaTraceDeOperationBancaire(operationBancaireModel, montantRetrait, 0, - montantRetrait, description);
        iComptePersonnelRepository.save(comptePersonnelModel);
        return operationBancaireModel;
    }
}
