package com.payMyBudy.payOnline.controller;

import com.payMyBudy.payOnline.controller.dto.VirementDto;
import com.payMyBudy.payOnline.model.ComptePersonnelModel;
import com.payMyBudy.payOnline.model.VirementModel;
import com.payMyBudy.payOnline.service.ComptePersonnelService;
import com.payMyBudy.payOnline.service.VirementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class VirementController {

    @Autowired
    VirementService virementService;

    @Autowired
    ComptePersonnelService comptePersonnelService;


    @GetMapping("/transfer")
    public String transferSubmit(Model model, HttpSession httpSession){
        model.addAttribute("listeAmiDeLaSession", comptePersonnelService.recupererListAmi((ComptePersonnelModel) httpSession.getAttribute("compte1")));
        model.addAttribute("virementDto", new VirementDto());
        model.addAttribute("historiqueTransfer", virementService.obtenirListeVirementPourUnComptePersonnel((ComptePersonnelModel) httpSession.getAttribute("compte1")));
        model.addAttribute("soldeCompte", comptePersonnelService.obtenirLeSolde((ComptePersonnelModel) httpSession.getAttribute("compte1")));
        return "transfer";
    }

    @PostMapping("/transfer")
    public String transferForm(@ModelAttribute VirementDto virementDto, Model model, HttpSession httpSession) throws Exception {

        ComptePersonnelModel comptePersonnelModelRecepteur = comptePersonnelService.recupererComptePersonnelParAdresseMail(virementDto.getMailDestinataire());
        virementService.faireUneTransaction("commentaire", (ComptePersonnelModel) httpSession.getAttribute("compte1"),
                 comptePersonnelModelRecepteur,  virementDto.getMontant());
        model.addAttribute("historiqueTransfer", virementService.obtenirListeVirementPourUnComptePersonnel((ComptePersonnelModel) httpSession.getAttribute("compte1")));
        model.addAttribute("soldeCompte", comptePersonnelService.obtenirLeSolde((ComptePersonnelModel) httpSession.getAttribute("compte1")));
        return "home";
    }
}
