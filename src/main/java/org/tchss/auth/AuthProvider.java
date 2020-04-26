package org.tchss.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.tchss.model.User;
import org.tchss.repo.UserRepository;
import org.tchss.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuthProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getPrincipal().toString();
        String errorMessage = "Either this is an invalid email address, or an incorrect password";
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new org.tchss.exception.AuthenticationException(errorMessage));
        String suppliedPassword = authentication.getCredentials().toString();
        boolean authenticated = BCrypt.checkpw(suppliedPassword, user.getPassword());
        System.out.println("Authenticated: " + authenticated);
        if (!authenticated) {
            throw new org.tchss.exception.AuthenticationException(errorMessage);
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        String role = user.isAdmin() ? "ROLE_ADMIN" : "ROLE_USER";
        grantedAuthorities.add(new SimpleGrantedAuthority(role));
        Authentication auth = new UsernamePasswordAuthenticationToken(email, suppliedPassword, grantedAuthorities);
        return auth;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        if (aClass.equals(UsernamePasswordAuthenticationToken.class)) {
            return true;
        }
        return false;
    }
}
