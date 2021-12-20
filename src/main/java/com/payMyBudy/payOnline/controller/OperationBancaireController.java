package com.payMyBudy.payOnline.controller;

import com.payMyBudy.payOnline.model.ComptePersonnelModel;
import com.payMyBudy.payOnline.model.OperationBancaireModel;
import com.payMyBudy.payOnline.model.VirementModel;
import com.payMyBudy.payOnline.service.ComptePersonnelService;
import com.payMyBudy.payOnline.service.OperationBancaireService;
import com.payMyBudy.payOnline.service.VirementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class OperationBancaireController {

    @Autowired
    OperationBancaireService operationBancaireService;

    @Autowired
    ComptePersonnelService comptePersonnelService;

    @Autowired
    VirementService virementService;

    @GetMapping("/operationBancaire")
    public String afficherLaListeDesOperationsBancairesSubmit(Model model){
        return "operationBancaire";
    }

    @GetMapping("/afficherProfile")
    public String afficherProfileSubmit(Model model, HttpSession httpSession){
        model.addAttribute("soldeCompte", comptePersonnelService.obtenirLeSolde((ComptePersonnelModel) httpSession.getAttribute("compte1")));
        model.addAttribute("listeOperationBancaires", operationBancaireService.obtenirOperationBancairePourUnComptePersonnel((ComptePersonnelModel) httpSession.getAttribute("compte1")));
        model.addAttribute("operationBancaireModel", new OperationBancaireModel());
        return "profile";
    }

    @PostMapping("/faireUnVirementBancaire")
    public String faireUnVirementBancaireForm(@ModelAttribute OperationBancaireModel operationBancaireModel, Model model, HttpSession httpSession) throws Exception {
        operationBancaireService.faireUnVirementBancaire((ComptePersonnelModel) httpSession.getAttribute("compte1"),
                operationBancaireModel,operationBancaireModel.getMontantVersement() , "description");
        model.addAttribute("historiqueTransfer", virementService.obtenirListeVirementPourUnComptePersonnel((ComptePersonnelModel) httpSession.getAttribute("compte1")));
        model.addAttribute("soldeCompte", comptePersonnelService.obtenirLeSolde((ComptePersonnelModel) httpSession.getAttribute("compte1")));
        return "home";
    }

    @PostMapping("/faireUnRetraitBancaire")
    public String faireUnRetraitBancaireForm(@ModelAttribute OperationBancaireModel operationBancaireModel, Model model, HttpSession httpSession) throws Exception {
        operationBancaireService.faireUnRetraitBancaire((ComptePersonnelModel) httpSession.getAttribute("compte1"),
                operationBancaireModel,operationBancaireModel.getMontantRetrait(), "description");
        model.addAttribute("historiqueTransfer", virementService.obtenirListeVirementPourUnComptePersonnel((ComptePersonnelModel) httpSession.getAttribute("compte1")));
        model.addAttribute("soldeCompte", comptePersonnelService.obtenirLeSolde((ComptePersonnelModel) httpSession.getAttribute("compte1")));
        return "home";
    }

}
