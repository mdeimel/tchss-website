package org.tchss.service;

public interface NotificationService {

    public void sendEmail(String subject, String recipient, String message);
}
