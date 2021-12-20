package com.payMyBudy.payOnline.service;

import com.payMyBudy.payOnline.model.ComptePersonnelModel;
import com.payMyBudy.payOnline.repository.IComptePersonnelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ComptePersonnelService {

    @Autowired
    private IComptePersonnelRepository iComptePersonnelRepository;

    private ComptePersonnelModel comptePersonnelModel = new ComptePersonnelModel();

    //TODO : à supprimer quand fini de jouer avec
    public List<ComptePersonnelModel> obtenirToutLesComptePersonnel(){
        return iComptePersonnelRepository.findAll();
    }
    @Transactional
    public double obtenirLeSolde(ComptePersonnelModel comptePersonnelModel){
        return iComptePersonnelRepository.findByPseudo(comptePersonnelModel.getPseudo()).get().getSolde();
    }

    @Transactional
    public ComptePersonnelModel ajouterComptePersonnelModelDansBdd(ComptePersonnelModel comptePersonnelModel){
        return iComptePersonnelRepository.save(comptePersonnelModel);
    }

    public Boolean chercherUnAmiDansLaListeParMail(ComptePersonnelModel comptePersonnelModelEmetteur, String adresseMail) {
        return iComptePersonnelRepository.findByIdComptePersonnelAndListAmi_AdresseMail(comptePersonnelModelEmetteur.getIdComptePersonnel(), adresseMail).isPresent();
    }

    public Boolean chercherComptePersonnelParPseudo(String pseudo) {
        ComptePersonnelModel comptePersonnelModel = this.iComptePersonnelRepository.findByPseudo(pseudo).orElse(null);
        if (comptePersonnelModel == null){
            return false;
        }
        return true;
    }

    public Boolean chercherComptePersonnelParMail(String adresseMail) {
        ComptePersonnelModel comptePersonnelModel = this.iComptePersonnelRepository.findByAdresseMail(adresseMail).orElse(null);
        if (comptePersonnelModel == null){
            return false;
        }
        return true;
    }

    public ComptePersonnelModel recupererComptePersonnelParAdresseMail(String adresseMail){
        ComptePersonnelModel comptePersonnelModel = this.iComptePersonnelRepository.findByAdresseMail(adresseMail).orElse(null);
        return comptePersonnelModel;
    }

    public ComptePersonnelModel recupererComptePersonnelParPseudo(String pseudo){
        ComptePersonnelModel comptePersonnelModel = this.iComptePersonnelRepository.findByPseudo(pseudo).orElse(null);
        return comptePersonnelModel;
    }

    @Transactional
    public void creerComptePersonnel(ComptePersonnelModel comptePersonnelModel) throws Exception {

        if (chercherComptePersonnelParPseudo(comptePersonnelModel.getPseudo()) == true){
            throw new Exception("Pseudo existe déjà");
        }
        if (chercherComptePersonnelParMail(comptePersonnelModel.getAdresseMail()) == true){
            throw new Exception("AdresseMail existe déjà");
        }
        ajouterComptePersonnelModelDansBdd(comptePersonnelModel);
    }

    @Transactional
    public void ajouterUnAmi(ComptePersonnelModel comptePersonnelModel, String adresseMail) throws Exception {
        if (!chercherComptePersonnelParMail(adresseMail)){
            throw new Exception("Aucun compte associé à cette adresse mail");
        }
        if (chercherUnAmiDansLaListeParMail(comptePersonnelModel, adresseMail) == true){
            throw new Exception("Cet ami est déjà dans ta liste d'ami");
        }

        if (!chercherUnAmiDansLaListeParMail(comptePersonnelModel, adresseMail)) {
            comptePersonnelModel = iComptePersonnelRepository.findByPseudo(comptePersonnelModel.getPseudo()).get();
            comptePersonnelModel.getListAmi().add(recupererComptePersonnelParAdresseMail(adresseMail));
            this.ajouterComptePersonnelModelDansBdd(comptePersonnelModel);
        }
    }

    public ComptePersonnelModel seConnecter(String pseudo, String password) throws Exception {
        ComptePersonnelModel comptePersonnelModel = recupererComptePersonnelParPseudo(pseudo);
        if (comptePersonnelModel.getPassword().equalsIgnoreCase(password)){
            return comptePersonnelModel;
        }
        throw new Exception("Le mot de passe ne correspond pas au pseudo");
    }

    public List<ComptePersonnelModel> recupererListAmi(ComptePersonnelModel comptePersonnelModel){
        if (comptePersonnelModel.getListAmi() != null) {
            comptePersonnelModel = iComptePersonnelRepository.findByPseudo(comptePersonnelModel.getPseudo()).get();
            List<ComptePersonnelModel> comptePersonnelModelList = comptePersonnelModel.getListAmi();
            return comptePersonnelModelList;
        }
        return null;
    }

}
