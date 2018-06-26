package com.anton.kursach.component;

import com.anton.kursach.service.SendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTask {

    private final SendService sendService;

    @Autowired
    public ScheduledTask(SendService sendService) {
        this.sendService = sendService;
    }

    @Scheduled(fixedRate = 30000) //cron = "0 0 8 * * *"
    public void sendEmail() {
        sendService.sendNotifications();
    }

}
