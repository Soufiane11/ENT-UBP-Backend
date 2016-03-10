package org.ubp.ent.backend.core.model.teacher.name;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

/**
 * Created by Anthony on 09/01/2016.
 */
public class Name {

    private String firstName;
    private String lastName;

    @JsonCreator
    public Name(@JsonProperty("firstName") String firstName, @JsonProperty("lastName") String lastName) {
        if (StringUtils.isBlank(firstName)) {
            throw new IllegalArgumentException("Cannot create a " + getClass().getName() + " without a firstName : '" + firstName + "' given");
        }
        if (StringUtils.isBlank(lastName)) {
            throw new IllegalArgumentException("Cannot create a " + getClass().getName() + " without a lastName : '" + lastName + "' given");
        }

        this.firstName = formatFirstName(firstName);
        this.lastName = formatLastName(lastName);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    private String formatFirstName(String firstName) {
        firstName = firstName.trim();
        firstName = firstName.toLowerCase();
        return WordUtils.capitalize(firstName, ' ', '_', '-');
    }

    private String formatLastName(String lastName) {
        lastName = lastName.trim();
        return lastName.toUpperCase();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Name name = (Name) o;
        return Objects.equal(firstName, name.firstName) &&
                Objects.equal(lastName, name.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(firstName, lastName);
    }

}
