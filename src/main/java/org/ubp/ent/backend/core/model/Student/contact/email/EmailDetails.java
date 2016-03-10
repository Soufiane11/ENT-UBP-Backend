package org.ubp.ent.backend.core.model.teacher.contact.email;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import org.apache.commons.lang3.StringUtils;
import org.ubp.ent.backend.core.exceptions.model.BadFormattedEmailAddress;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Anthony on 13/01/2016.
 */
public class EmailDetails {

    private String address;

    @JsonCreator
    public EmailDetails(@JsonProperty("address") String address) {
        if (StringUtils.isBlank(address)) {
            throw new IllegalArgumentException("Cannot build a " + getClass().getName() + " without an email address");
        }
        validateAddress(address);
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    private void validateAddress(String mail) {
        Pattern p = Pattern.compile("\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}\\b", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(mail);
        if (!m.matches()) {
            throw new BadFormattedEmailAddress("Given email address does not match pattern, given :" + mail);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailDetails email = (EmailDetails) o;
        return Objects.equal(address, email.address);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(address);
    }

}
