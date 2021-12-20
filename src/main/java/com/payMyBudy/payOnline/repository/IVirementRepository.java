package com.payMyBudy.payOnline.repository;

import com.payMyBudy.payOnline.model.ComptePersonnelModel;
import com.payMyBudy.payOnline.model.VirementModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IVirementRepository extends JpaRepository<VirementModel, Integer>, JpaSpecificationExecutor<VirementModel> {

    public Optional<VirementModel> findByIdComptePersonnel(int id);
    public Optional<VirementModel> findByidVirement(int idVirement);

    public Optional<VirementModel> findByidComptePersonnelEmetteur(ComptePersonnelModel idComptePersonnelEmetteur);
    public List<VirementModel> findAllByidComptePersonnelEmetteur(ComptePersonnelModel idComptePersonnelEmetteur);


}
