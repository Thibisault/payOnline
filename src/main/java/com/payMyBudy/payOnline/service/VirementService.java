package com.payMyBudy.payOnline.service;

import com.payMyBudy.payOnline.model.ComptePersonnelModel;
import com.payMyBudy.payOnline.model.VirementModel;
import com.payMyBudy.payOnline.repository.IComptePersonnelRepository;
import com.payMyBudy.payOnline.repository.IVirementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class VirementService {

    @Autowired
    IComptePersonnelRepository iComptePersonnelRepository;

    @Autowired
    IVirementRepository iVirementRepository;

    public Date recupererLaDate(VirementModel virementModel){
        Date date = new Date();
        virementModel.setDatePrecise(date);
        return date;
    }

    @Transactional
    public List<VirementModel> obtenirListeVirementPourUnComptePersonnel(ComptePersonnelModel comptePersonnelModel){
        List<VirementModel> list = iComptePersonnelRepository.findById(comptePersonnelModel.getIdComptePersonnel()).get().getVirementModelList();
        return comptePersonnelModel.getVirementModelList();
    }

    //Vérifier que les deux users sont bien amis grâce à l'adresse mail
    public Boolean verifierSiAmiDansListe(ComptePersonnelModel comptePersonnelModel, String adresseMail) {
        comptePersonnelModel = iComptePersonnelRepository.findByPseudo(comptePersonnelModel.getPseudo()).get();
        for (ComptePersonnelModel compte : comptePersonnelModel.getListAmi()){
            if (compte.getAdresseMail().equalsIgnoreCase(adresseMail)){
                return true;
            }
        }
        return false;
    }

    //Vérifier le solde de l'emetteur et si il a assez pour finaliser sa transaction
    public Boolean verifierSoldeDeEmetteur(ComptePersonnelModel comptePersonnelModelEmetteur, double montantTransaction) {
        if (comptePersonnelModelEmetteur.getSolde() >= montantTransaction) {
            return true;
        }
        return false;
    }

    //Persister la transaction
    @Transactional
    public void persisterTransaction(VirementModel virementModel) {
        iVirementRepository.save(virementModel);
    }

    //Obtenir un VirementModel grâce à son ID
    public Optional<VirementModel> getVirementModelById(Integer virementModelId){
        return iVirementRepository.findById(virementModelId);
    }

    //Obtenir un VirementModel grâce à son idComptePersonnel
    public VirementModel getVirementModelByIdComptePersonnel(Integer idComptePersonnel){
        VirementModel virementModel = this.iVirementRepository.findByIdComptePersonnel(idComptePersonnel).orElse(null);
        return virementModel;
    }


    //Décrémente et incrémente le solde des deux users
    @Transactional
    public void mettreAJourSoldeUtilisateurapresTransaction(ComptePersonnelModel comptePersonnelModelEmetteur, ComptePersonnelModel comptePersonnelModelRecepteur, double montantTransaction) throws Exception {
        comptePersonnelModelEmetteur.setSolde(comptePersonnelModelEmetteur.getSolde() - montantTransaction);
        iComptePersonnelRepository.save(comptePersonnelModelEmetteur);
        comptePersonnelModelRecepteur.setSolde(comptePersonnelModelRecepteur.getSolde() + montantTransaction);
        iComptePersonnelRepository.save(comptePersonnelModelRecepteur);
    }

    //Faire le calcul de la commission
    public void calculerCommission(VirementModel virementModel, double montantTransaction){
        virementModel.setMontantTransaction(montantTransaction);
        virementModel.setMontantCommission(montantTransaction *0.005);
    }

    //Ajouter un commentaire dans la table VirementModel
    public void ecrireCommentaire(VirementModel virementModel, String commentaire){
        virementModel.setDescription(commentaire);
    }

    //Garder la trace de la transaction : date, montant, commission, commentaire
    @Transactional
    public void persisterLaTraceDeLaTransaction(VirementModel virementModel, double montantTransaction, String commentaire){
        this.calculerCommission(virementModel, montantTransaction);
        this.ecrireCommentaire(virementModel, commentaire);
        this.recupererLaDate(virementModel);
        this.persisterTransaction(virementModel);
    }

    //Combinaison de toutes les méthodes de cette classe
    @Transactional
    public VirementModel faireUneTransaction(String commentaire, ComptePersonnelModel comptePersonnelModelEmetteur, ComptePersonnelModel comptePersonnelModelRecepteur, double montantTransaction) throws Exception {
        if (verifierSiAmiDansListe(comptePersonnelModelEmetteur, comptePersonnelModelRecepteur.getAdresseMail()) == false) {
            throw new Exception("Cet utilisateur n'est pas dans ta liste d'ami");
        }
        if (verifierSoldeDeEmetteur(comptePersonnelModelEmetteur, montantTransaction) == false) {
            throw new Exception("Le montant est de la transaction dépasse ton solde");
        }
        VirementModel virementModel = new VirementModel();
        virementModel.setIdComptePersonnelEmetteur(comptePersonnelModelEmetteur);
        virementModel.setIdComptePersonnelRecepteur(comptePersonnelModelRecepteur);
        virementModel.setPseudoEmetteur(comptePersonnelModelEmetteur.getPseudo());
        this.mettreAJourSoldeUtilisateurapresTransaction(comptePersonnelModelEmetteur, comptePersonnelModelRecepteur, montantTransaction);
        this.persisterLaTraceDeLaTransaction(virementModel, montantTransaction, commentaire);
        comptePersonnelModelEmetteur.getVirementModelList().add(virementModel);
        return virementModel;
    }
}