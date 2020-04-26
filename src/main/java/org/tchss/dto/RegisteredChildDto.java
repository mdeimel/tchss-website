package org.tchss.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class RegisteredChildDto {

    @NotNull
    @Length(min = 1, max = 50, message = "First name must be between 1 and 50 characters")
    private String firstName;

    @NotNull
    @Length(min = 1, max = 50, message = "Last name must be between 1 and 50 characters")
    private String lastName;

    @NotNull
    @Min(1)
    @Max(12)
    private Integer monthBorn;

    private Integer yearBorn;

    private boolean baseball;

    private boolean soccer;
}
