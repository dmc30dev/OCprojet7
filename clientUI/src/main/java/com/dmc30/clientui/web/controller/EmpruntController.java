package com.dmc30.clientui.web.controller;

import com.dmc30.clientui.shared.UtilsMethodService;
import com.dmc30.clientui.shared.bean.bibliotheque.*;
import com.dmc30.clientui.service.contract.BibliothequeService;
import com.dmc30.clientui.service.contract.EmpruntService;
import com.dmc30.clientui.service.contract.OuvrageService;
import com.dmc30.clientui.service.contract.UserService;
import com.dmc30.clientui.shared.bean.utilisateur.UtilisateurBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class EmpruntController {

    Logger logger = LoggerFactory.getLogger(EmpruntController.class);
    UtilsMethodService utilsMethodService;
    BibliothequeService bibliothequeService;
    UserService userService;
    OuvrageService ouvrageService;
    EmpruntService empruntService;

    @Autowired
    public EmpruntController(UtilsMethodService utilsMethodService,
                             BibliothequeService bibliothequeService,
                             UserService userService,
                             OuvrageService ouvrageService,
                             EmpruntService empruntService) {
        this.utilsMethodService = utilsMethodService;
        this.bibliothequeService = bibliothequeService;
        this.userService = userService;
        this.ouvrageService = ouvrageService;
        this.empruntService = empruntService;
    }

    @GetMapping("/showEmpruntPage")
    public ModelAndView showEmpruntPage(@RequestParam("bibliothequeId") Long bibliothequeId,
                                        @ModelAttribute CreateEmpruntBean createEmpruntBean,
                                        @RequestParam(value = "numAbonne", required = false) String numAbonne,
                                        @RequestParam(value = "idInterne", required = false) String idInterne) {
        ModelAndView theModel = new ModelAndView("emprunt-page");
        utilsMethodService.setBibliothequeForTheVue(theModel, bibliothequeId);
        List<UtilisateurBean> abonnes = new ArrayList<>();
        List<OuvrageResponseModelBean> ouvrages = new ArrayList<>();
        theModel.addObject("abonnes", abonnes);
        theModel.addObject("ouvrages", ouvrages);
        String message = "";
        createEmpruntBean = new CreateEmpruntBean();
        List<OuvrageResponseModelBean> ouvragesByBibliotheque = new ArrayList<>();
        if (numAbonne == null) numAbonne = "";
        if (idInterne == null) idInterne = "";
        if ((!numAbonne.equals("")) && (!idInterne.equals(""))) {
            abonnes = userService.getUtilisateursByNumAbonne(numAbonne);
            if (abonnes.size() == 1) {
                utilsMethodService.setAbonneForEmpruntBean(createEmpruntBean, abonnes.get(0));
                theModel.addObject("createEmpruntBean", createEmpruntBean);
            } else {
                message = "Il y a plusieurs abonnés correspondants à votre choix";
                theModel.addObject("abonnes", abonnes);
            }
            ouvrages = ouvrageService.getOuvragesByIdInterne(idInterne);
            if (ouvrages.size() == 1) {
                utilsMethodService.setOuvrageForEmpruntBean(createEmpruntBean, ouvrages.get(0));
                theModel.addObject("createEmpruntBean", createEmpruntBean);
            } else {
                for (OuvrageResponseModelBean ouvrage : ouvrages) {
                    if (ouvrage.getBibliothequeId().equals(bibliothequeId)) {
                        ouvragesByBibliotheque.add(ouvrage);
                    }
                }
                message = "Il y a plusieurs ouvrages correspondants à votre choix";
                theModel.addObject("ouvrages", ouvragesByBibliotheque);
            }
        } else if (!numAbonne.equals("")) {
            UtilisateurBean abonneSelectionne = userService.getUtilisateurByNumAbonne(numAbonne);
            utilsMethodService.setAbonneForEmpruntBean(createEmpruntBean, abonneSelectionne);
            theModel.addObject("createEmpruntBean", createEmpruntBean);
        } else if (!idInterne.equals("")) {
            OuvrageResponseModelBean ouvrageSelectionne = ouvrageService.getOuvrageByIdInterne(idInterne);
            utilsMethodService.setOuvrageForEmpruntBean(createEmpruntBean, ouvrageSelectionne);
            theModel.addObject("createEmpruntBean", createEmpruntBean);
        }
        return theModel;
    }


    @PostMapping("/createEmpruntSearchForm")
    public ModelAndView populateTheCreateEmpruntForm(@RequestParam("bibliothequeId") Long bibliothequeId,
                                                     @ModelAttribute CreateEmpruntBean createEmpruntBean,
                                                     @RequestParam(value = "numAbonne", required = false) String numAbonne,
                                                     @RequestParam(value = "idInterne", required = false) String idInterne) {
        ModelAndView theModel = new ModelAndView("emprunt-page");
        utilsMethodService.setBibliothequeForTheVue(theModel, bibliothequeId);
        List<UtilisateurBean> abonnes = new ArrayList<>();
        List<OuvrageResponseModelBean> ouvrages = new ArrayList<>();
        theModel.addObject("abonnes", abonnes);
        theModel.addObject("ouvrages", ouvrages);
        String message = "";
        List<OuvrageResponseModelBean> ouvragesByBibliotheque = new ArrayList<>();
        if ((numAbonne.equals("")) && (idInterne.equals(""))) {
            message = "Aucuns critères renseignés pour la recherche.";
        }
        if ((numAbonne != null) && (!numAbonne.equals(""))) {
            abonnes = userService.getUtilisateursByNumAbonne(createEmpruntBean.getNumAbonne());
            if (abonnes.size() == 1) {
                utilsMethodService.setAbonneForEmpruntBean(createEmpruntBean, abonnes.get(0));
            } else {
                message = "Il y a plusieurs abonnés correspondants à votre choix";
                theModel.addObject("abonnes", abonnes);
            }

        }
        if ((idInterne != null) && (!idInterne.equals(""))) {
            ouvrages = ouvrageService.getOuvragesByIdInterne(createEmpruntBean.getIdInterne());
            if (ouvrages.size() == 1) {
                utilsMethodService.setOuvrageForEmpruntBean(createEmpruntBean, ouvrages.get(0));
            } else {
                for (OuvrageResponseModelBean ouvrage : ouvrages) {
                    if (ouvrage.getBibliothequeId().equals(bibliothequeId)) {
                        ouvragesByBibliotheque.add(ouvrage);
                    }
                }
                message = "Il y a plusieurs ouvrages correspondants à votre choix";
                theModel.addObject("ouvrages", ouvragesByBibliotheque);
            }
            theModel.addObject("createEmpruntBean", createEmpruntBean);
        }
        theModel.addObject("message", message);
        return theModel;
    }

    @PostMapping("/createEmprunt")
    public ModelAndView createEmprunt(@RequestParam("bibliothequeId") Long bibliothequeId,
                                      @ModelAttribute CreateEmpruntBean createEmpruntBean) {
        ModelAndView theModel = new ModelAndView("emprunt-page");
        utilsMethodService.setBibliothequeForTheVue(theModel, bibliothequeId);
        String message;
        List<UtilisateurBean> abonnes = new ArrayList<>();
        List<OuvrageResponseModelBean> ouvrages = new ArrayList<>();
        theModel.addObject("abonnes", abonnes);
        theModel.addObject("ouvrages", ouvrages);
        PretBean pretBean = empruntService.createEmprunt(createEmpruntBean);
        message = "L'emprunt du livre a bien été enregistré.";
        theModel.addObject("messageCreateEmprunt", message);
        return theModel;
    }

    @GetMapping("/searchEmpruntsEnCours")
    public ModelAndView getEmpruntEnCours(@RequestParam("bibliothequeId") Long bibliothequeId) {
        ModelAndView theModel = new ModelAndView("emprunt-en-cours-page");
        utilsMethodService.setBibliothequeForTheVue(theModel, bibliothequeId);
        List<EmpruntModelBean> empruntModelBeans = new ArrayList<>();
        Date dateRetourPrevu;
        String message = "";
        List<PretBean> empruntsEnCours = empruntService.getEmpruntsEnCours(bibliothequeId);
        if (empruntsEnCours.isEmpty()) {
            message = "Aucun emprunt en cours pour " + (bibliothequeService.getBibliothequeById(bibliothequeId)).getNom();
            theModel.addObject("message", message);
        } else {
            for (PretBean pret : empruntsEnCours) {
                EmpruntModelBean empruntModelBean = new EmpruntModelBean();
                UtilisateurBean abonne = userService.getUtilisateurById(pret.getUtilisateurId());
                utilsMethodService.setEmpruntModelBean(empruntModelBeans, pret, empruntModelBean, abonne, ouvrageService);
            }
            theModel.addObject("empruntEnCours", empruntModelBeans);
        }
        return theModel;
    }


}


