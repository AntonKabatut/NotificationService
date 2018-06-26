package com.anton.kursach.service;

import com.anton.kursach.model.Lab;
import com.sendgrid.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import static com.anton.kursach.util.DateMapping.formatDateWithoutTime;
import static com.anton.kursach.util.DateMapping.formatDateToString;

@Service
@Slf4j
public class SendService {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SendService.class);

    private final UserService userService;

    private final LabService labService;

    @Value("${SendgridApiKey}")
    private String SENGRID_API_KEY;

    @Value("${emailAdress}")
    private String email;

    @Autowired
    public SendService(UserService userService, LabService labService) {
        this.userService = userService;
        this.labService = labService;
    }

    public void sendNotifications() {

        List<Lab> labs = labService.getLabsNeededNotify(formatDateWithoutTime(new Date().getTime())); //1516752000000L
        System.out.println(labs.size());

        labs.forEach((lab) -> {
            System.out.println(lab.getName());

            lab.setNotified(true);
            labService.updateLab(lab);

            Email from = new Email(email);
            String subject = "Deadline of " + lab.getName();
            Email to = new Email(userService.getUserByLabId(lab.getId()).getLogin());
            Content content = new Content("text/plain",
                    formatDateToString(lab.getDeadline()) + " Deadline of " + lab.getName());
            Mail mail = new Mail(from, subject, to, content);

            SendGrid sg = new SendGrid(SENGRID_API_KEY);
            Request request = new Request();
            try {
                request.setMethod(Method.POST);
                request.setEndpoint("mail/send");
                request.setBody(mail.build());
                Response response = sg.api(request);
                StringBuilder emailResponse = new StringBuilder();
                emailResponse.append(response.getStatusCode()).append(response.getBody()).append(response.getHeaders());
                log.trace("[SendService] [sendNotifications] []. ", emailResponse);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

}
