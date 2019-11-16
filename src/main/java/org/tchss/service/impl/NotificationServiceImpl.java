package org.tchss.service.impl;

import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.tchss.service.NotificationService;

@Log4j2
@Service
public class NotificationServiceImpl implements NotificationService {

    @Override
    public void sendEmail(String subject, String recipient, String message) {
        // TODO Implement email service
        log.info("Sending email...");
        log.info("   Subject: {}", subject);
        log.info("   Recipient: {}", recipient);
        log.info("   Message: {}", message);
    }
}
