package com.payMyBudy.payOnline.repository;

import com.payMyBudy.payOnline.model.ComptePersonnelModel;
import com.payMyBudy.payOnline.model.VirementModel;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IComptePersonnelRepository extends JpaRepository<ComptePersonnelModel, Integer>, JpaSpecificationExecutor<ComptePersonnelModel>     {

    public Optional<ComptePersonnelModel> findByPseudo(String pseudo);
    public Optional<ComptePersonnelModel> findByAdresseMail(String adresseMail);
    public Optional<ComptePersonnelModel> findByPassword(String password);


    public Optional<ComptePersonnelModel> findByNumeroDeCompte(int numeroDeCompte);
    public Optional<ComptePersonnelModel> findBySolde(double solde);

    public List<ComptePersonnelModel> findAll(Specification<ComptePersonnelModel> comptePersonnelModelSpecification);

    public Optional<ComptePersonnelModel> findByIdComptePersonnelAndListAmi_AdresseMail(int idComptePersonnelModel ,String adresseMail);

    //public Optional<ComptePersonnelModel> findByIdComptePersonnelAndVirementModelList_IdComptePersonnelEmetteurIn(int idComptePersonnelModel);

    public Optional<ComptePersonnelModel> findByAdresseMailAndPseudo(String adresseMail, String pseudo);
}
