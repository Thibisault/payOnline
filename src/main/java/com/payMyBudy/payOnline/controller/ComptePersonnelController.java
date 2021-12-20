package com.payMyBudy.payOnline.controller;

import com.mysql.cj.jdbc.SuspendableXAConnection;
import com.payMyBudy.payOnline.controller.dto.AjouterUnAmiDto;
import com.payMyBudy.payOnline.model.ComptePersonnelModel;
import com.payMyBudy.payOnline.model.OperationBancaireModel;
import com.payMyBudy.payOnline.service.ComptePersonnelService;
import com.payMyBudy.payOnline.service.VirementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

//@RequestMapping("/*")
//@Controller
//@ResponseBody
@Controller
public class ComptePersonnelController {

    @Autowired
    ComptePersonnelService comptePersonnelService;

    @Autowired
    VirementService virementService;



    @GetMapping("/index")
    public String afficherIndexSubmit(){
        return "index";
    }

    @GetMapping("/home")
    public String afficherHomeSubmit(Model model, HttpSession httpSession){
        model.addAttribute("historiqueTransfer", virementService.obtenirListeVirementPourUnComptePersonnel((ComptePersonnelModel) httpSession.getAttribute("compte1")));
        model.addAttribute("soldeCompte", comptePersonnelService.obtenirLeSolde((ComptePersonnelModel) httpSession.getAttribute("compte1")));
        return "home";
    }


    @GetMapping("/profile")
    public String afficherProfileSubmit(){
        return "profile";
    }

    @GetMapping("/contact")
    public String afficherContactSubmit(){
        return "contact";
    }

    @GetMapping("/creerComptePersonnel")
    public String comptePersonnelForm(Model model) {
        model.addAttribute("compte", new ComptePersonnelModel());
        return "creerComptePersonnel";
    }


    @PostMapping("/creerComptePersonnel")
    public String creerComptePersonnelController(@ModelAttribute ComptePersonnelModel comptePersonnelModel, Model model, HttpSession httpSession) throws Exception {
        comptePersonnelService.creerComptePersonnel(comptePersonnelModel);
        model.addAttribute("compte", comptePersonnelModel);
        httpSession.setAttribute("compte1", comptePersonnelModel);
        return "home";
    }

        //return comptePersonnelService.obtenirToutLesComptePersonnel();
    @GetMapping("/obtenirToutlesComptePersonnel")
    public String getAllComptePersonnelSubmit(Model model) {
        model.addAttribute("comptePersonnel", comptePersonnelService.obtenirToutLesComptePersonnel());
        return "obtenirToutlesComptePersonnel";
    }

    @GetMapping("/seConnecter")
    public String seConnecterSubmit(Model model){
        model.addAttribute("connexion", new ComptePersonnelModel());
        return "seConnecter";
    }

    @PostMapping("/seConnecter")
    public String seConnecterForm(@ModelAttribute ComptePersonnelModel comptePersonnelModel, Model model, HttpSession httpSession) throws Exception {
        model.addAttribute("connexion", comptePersonnelService.seConnecter(comptePersonnelModel.getPseudo(), comptePersonnelModel.getPassword()));
        httpSession.setAttribute("compte1", (ComptePersonnelModel) model.getAttribute("connexion"));
        model.addAttribute("historiqueTransfer", virementService.obtenirListeVirementPourUnComptePersonnel((ComptePersonnelModel) httpSession.getAttribute("compte1")));
        model.addAttribute("soldeCompte", comptePersonnelService.obtenirLeSolde((ComptePersonnelModel) httpSession.getAttribute("compte1")));
        return "home";
    }

    @GetMapping("/seDeconnecter")
    public String seDeconnecterSubmit(@ModelAttribute ComptePersonnelModel comptePersonnelModel, Model model, HttpSession httpSession){
        httpSession.removeAttribute("compte1");
        return "index";
    }


    @GetMapping("/ajouterUnAmi")
    public String ajouterUnAmiSubmit(Model model, HttpSession httpSession){
        model.addAttribute("listeAmiDeLaSession", comptePersonnelService.recupererListAmi((ComptePersonnelModel) httpSession.getAttribute("compte1")));
        model.addAttribute("ajouterUnAmiDto", new AjouterUnAmiDto());
        return "ajouterUnAmi";
    }

    @PostMapping("/ajouterUnAmi")
    public String ajouterUnAmiForm(@ModelAttribute AjouterUnAmiDto ajouterUnAmiDto, HttpSession httpSession, Model model) throws Exception {
        comptePersonnelService.ajouterUnAmi((ComptePersonnelModel) httpSession.getAttribute("compte1"), ajouterUnAmiDto.getAdresseMailAmi());
        model.addAttribute("listeAmiDeLaSession", comptePersonnelService.recupererListAmi((ComptePersonnelModel) httpSession.getAttribute("compte1")));
        return "ajouterUnAmi";
    }

}
