package org.ubp.ent.backend.core.model.teacher.contact.phone;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import org.apache.commons.lang3.StringUtils;
import org.ubp.ent.backend.core.exceptions.model.BadFormattedPhoneNumber;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Anthony on 12/01/2016.
 */
public class PhoneDetails {

    private String number;

    @JsonCreator
    public PhoneDetails(@JsonProperty("number") String number) {
        if (StringUtils.isBlank(number)) {
            throw new IllegalArgumentException("Cannot build a " + getClass().getName() + " without a number");
        }
        this.number = formatPhoneNumber(number);
    }

    public String getNumber() {
        return number;
    }

    private String formatPhoneNumber(String number) {
        number = StringUtils.deleteWhitespace(number);
        number = StringUtils.remove(number, '.');

        String regex = "(\\+[1-9]{2}|00[1-9]{2}|0)([1-9])([0-9]{8})";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(number);

        if (!m.matches()) {
            throw new BadFormattedPhoneNumber("Given phone number does not match pattern, given :" + number);
        }

        StringBuilder sb = new StringBuilder();

        String prefix = m.group(1);
        prefix = formatPrefix(prefix);
        sb.append(prefix);

        sb.append(m.group(2)).append(" ");

        String heightDigits = formatHeightDigits(m.group(3));
        sb.append(heightDigits);

        return sb.toString();
    }

    private String formatPrefix(String prefix) {
        if (prefix.startsWith("+")) {
            return prefix;
        } else if (prefix.startsWith("00")) {
            prefix = prefix.replace("0", "");
            return "+" + prefix;
        }
        return "+33";
    }

    private String formatHeightDigits(String heightLastDigit) {
        return heightLastDigit.replaceAll("(.{2})(?!$)", "$1 ");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneDetails that = (PhoneDetails) o;
        return Objects.equal(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(number);
    }

}
