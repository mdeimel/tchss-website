package org.tchss.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

@NoArgsConstructor
@Getter
@Entity
public class PasswordResetToken {

    // 24 hours, in milliseconds
    private static final int EXPIRATION = 24*60*60*1000;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    private Date expiryDate;

    public PasswordResetToken(User user) {
        this.user = user;

        expiryDate = new Date();
        expiryDate.setTime(expiryDate.getTime()+EXPIRATION);

        // Generate random string token
        token = generateRandomString();
    }

    private String generateRandomString() {
        byte[] randomBytes = new byte[12];

        for (int i = 0; i < 12; i++) {
            // Start by generating a number to see if the next character should be
            // upper case, or lower case.

            // Lower case
            if (randomNumber(2) == 0) {
                // Letters A-Z are 65-90
                randomBytes[i] = (byte) (randomNumber(26) + 65);
            }
            // Upper case
            else {
                // Letters a-z are 97-122
                randomBytes[i] = (byte) (randomNumber(26) + 97);
            }
        }

        return new String(randomBytes, Charset.defaultCharset());
    }

    private Integer randomNumber(Integer modulo) {
        return Math.abs(new Random().nextInt() % modulo);
    }
}