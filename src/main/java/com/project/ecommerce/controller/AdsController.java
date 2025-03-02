package com.project.ecommerce.controller;

import com.project.ecommerce.model.Ads;
import com.project.ecommerce.service.AdsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdsController {

    private final AdsService adsService;

    @Autowired
    public AdsController(AdsService adsService) {
        this.adsService = adsService;
    }

    @GetMapping("/ads")
    public String showAds(Model model) {
        List<Ads> adsList = adsService.getAllAds();
        model.addAttribute("ads", adsList);
        return "adsPage";
    }

    @GetMapping("/ads")
    public String getAdsPage(Model model) {
        List<Ads> adsList = adsService.getAllAds();

        if (adsList == null || adsList.isEmpty()) {
            System.out.println("❌ No ads found! Check the database.");
        }

        model.addAttribute("adsList", adsList);
        return "adsPage";
    }
}

