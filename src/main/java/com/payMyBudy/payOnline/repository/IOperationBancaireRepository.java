package com.payMyBudy.payOnline.repository;

import com.payMyBudy.payOnline.model.ComptePersonnelModel;
import com.payMyBudy.payOnline.model.OperationBancaireModel;
import com.payMyBudy.payOnline.model.VirementModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface IOperationBancaireRepository extends JpaRepository<OperationBancaireModel, Integer>, JpaSpecificationExecutor<OperationBancaireModel> {

    public Optional<OperationBancaireModel> findByIdComptePersonnel(int idComptePersonnel);

    public Optional<OperationBancaireModel> findByidOperationBancaire(int idOperationBancaire);

    public Optional<OperationBancaireModel> findByMontantCompteBancaire(double montant);


    public List<OperationBancaireModel> findAllByIdDuComptePersonnel(int idDuComptePersonnel);


    public List<OperationBancaireModel> findAllByIdComptePersonnel(ComptePersonnelModel idComptePersonnel);


}
