package org.ubp.ent.backend.core.model.teacher.contact.address;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Anthony on 09/01/2016.
 */
public class AddressDetails {

    private String streetNumber;
    private String street;
    private String zip;
    private String city;

    @JsonCreator
    public AddressDetails(@JsonProperty("streetNumber") String streetNumber, @JsonProperty("street") String street, @JsonProperty("zip") String zip, @JsonProperty("city") String city) {
        if (StringUtils.isBlank(streetNumber)) {
            throw new IllegalArgumentException("Cannot create a " + getClass().getName() + " without a streetNumber : '" + streetNumber + "' given");
        }
        if (StringUtils.isBlank(street)) {
            throw new IllegalArgumentException("Cannot create a " + getClass().getName() + " without a street : '" + street + "' given");
        }
        if (StringUtils.isBlank(zip)) {
            throw new IllegalArgumentException("Cannot create a " + getClass().getName() + " without a zip : '" + zip + "' given");
        }
        if (StringUtils.isBlank(city)) {
            throw new IllegalArgumentException("Cannot create a " + getClass().getName() + " without a city : '" + city + "' given");
        }

        this.streetNumber = formatStreetNumber(streetNumber);
        this.street = formatStreet(street);
        this.zip = formatZipCode(zip);
        this.city = formatCity(city);
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public String getStreet() {
        return street;
    }

    public String getZip() {
        return zip;
    }

    public String getCity() {
        return city;
    }


    private String formatZipCode(String zip) {
        zip = zip.trim();

        Pattern p = Pattern.compile("([0-9]{5})");
        Matcher m = p.matcher(zip);
        if (!m.find()) {
            throw new IllegalArgumentException("A Zip code must be composed of five digits : '" + zip + "' given");
        }

        return zip;
    }

    private String formatCity(String city) {
        city = city.trim();
        city = city.toLowerCase();
        return WordUtils.capitalize(city, ' ', '_', '-');
    }

    private String formatStreetNumber(String sn) {
        sn = sn.trim();
        sn = sn.toLowerCase();

        Pattern p = Pattern.compile("([0-9]+)[\\s]*(.*)?");
        Matcher m = p.matcher(sn);
        if (!m.find()) {
            throw new IllegalArgumentException("A street number must be composed with at least a number (and optional 'bis', 'ter', ...) : '" + sn + "' given");
        }
        StringBuilder sb = new StringBuilder();
        sb.append(m.group(1));

        if (!StringUtils.isBlank(m.group(2))) {
            sb.append(" ").append(m.group(2));
        }

        return WordUtils.capitalize(sb.toString());
    }

    private String formatStreet(String street) {
        street = street.trim();
        street = street.toLowerCase();
        return WordUtils.capitalize(street, ' ', '_', '-');
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressDetails address = (AddressDetails) o;
        return Objects.equal(streetNumber, address.streetNumber) &&
                Objects.equal(street, address.street) &&
                Objects.equal(zip, address.zip) &&
                Objects.equal(city, address.city);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(streetNumber, street, zip, city);
    }

}
