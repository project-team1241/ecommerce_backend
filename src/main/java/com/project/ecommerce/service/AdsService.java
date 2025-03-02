package com.project.ecommerce.service;

import com.project.ecommerce.model.Ads;
import com.project.ecommerce.repository.AdsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdsService {

    private final AdsRepository adsRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public AdsService(AdsRepository adsRepository, SimpMessagingTemplate messagingTemplate) {
        this.adsRepository = adsRepository;
        this.messagingTemplate = messagingTemplate;
    }

    public List<Ads> getAllAds() {
        return adsRepository.findAll();
    }

    /**
     * Fetch ads every 10 seconds and broadcast updates to clients.
     */
    @Scheduled(fixedRate = 10000) // Runs every 10 seconds
    public void sendRealTimeAdsUpdates() {
        List<Ads> adsList = adsRepository.findAll();
        messagingTemplate.convertAndSend("/topic/ads", adsList);
    }
}
