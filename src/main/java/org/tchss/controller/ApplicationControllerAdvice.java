package org.tchss.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tchss.exception.AuthenticationException;

import javax.validation.ConstraintViolationException;

@Log4j2
@ControllerAdvice
public class ApplicationControllerAdvice {

    @RequestMapping("/error")
    @ExceptionHandler(AuthenticationException.class)
    public String handleAuthenticationException(
            AuthenticationException e,
            Model model) {
        model.addAttribute("message", e.getMessage());
        log.info("AuthenticationException: {}", e.getMessage());
        return "error";
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public String handleConstraintViolationException(
            ConstraintViolationException e,
            Model model) {
        model.addAttribute("message", e.getMessage());
        return "error";
    }
}
