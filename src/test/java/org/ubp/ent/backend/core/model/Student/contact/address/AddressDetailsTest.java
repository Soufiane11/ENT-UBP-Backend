package org.ubp.ent.backend.core.model.teacher.contact.address;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Anthony on 09/01/2016.
 */
public class AddressDetailsTest {
    private static final String VALID_STREET_NUMBER = "6 Bis";
    private static final String VALID_STREET = "Rue Jacques Essebag";
    private static final String VALID_ZIP = "63000";
    private static final String VALID_CITY = "Clermont-Ferrand";

    public static AddressDetails createOne() {
        String street = RandomStringUtils.randomAlphabetic(ThreadLocalRandom.current().nextInt(6, 10));
        return createOne(VALID_STREET_NUMBER, street, VALID_ZIP, VALID_CITY);
    }

    public static AddressDetails createOne(String streetNumber, String street, String zip, String city) {
        return new AddressDetails(streetNumber, street, zip, city);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotInstantiateWithEmptyZip() {
        createOne(VALID_STREET_NUMBER, VALID_STREET, " ", VALID_CITY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotInstantiateWithNullZip() {
        createOne(VALID_STREET_NUMBER, VALID_STREET, null, VALID_CITY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotInstantiateWithEmptyCity() {
        createOne(VALID_STREET_NUMBER, VALID_STREET, VALID_ZIP, " ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotInstantiateWithNullCity() {
        createOne(VALID_STREET_NUMBER, VALID_STREET, VALID_ZIP, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotInstantiateWithEmptyStreetNumber() {
        createOne(" ", VALID_STREET, VALID_ZIP, VALID_CITY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotInstantiateWithNullStreetNumber() {
        createOne(null, VALID_STREET, VALID_ZIP, VALID_CITY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotInstantiateWithEmptyStreet() {
        createOne(VALID_STREET_NUMBER, " ", VALID_ZIP, VALID_CITY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotInstantiateWithNullStreet() {
        createOne(VALID_STREET_NUMBER, null, VALID_ZIP, VALID_CITY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotInstantiateWithNonNumberZip() {
        createOne(VALID_STREET_NUMBER, VALID_STREET, "abcde", VALID_CITY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotInstantiateWithNonFiveDigitNumberZip() {
        createOne(VALID_STREET_NUMBER, VALID_STREET, "512", VALID_CITY);
    }

    @Test
    public void shouldAcceptSimpleDigitAndDigitPlusComplementaryStringAsStreetNumber() {
        AddressDetails address = createOne("6", VALID_STREET, VALID_ZIP, VALID_CITY);

        assertThat(address.getStreetNumber()).isEqualTo("6");

        address = createOne("63", VALID_STREET, VALID_ZIP, VALID_CITY);
        assertThat(address.getStreetNumber()).isEqualTo("63");

        address = createOne("6 bis", VALID_STREET, VALID_ZIP, VALID_CITY);
        assertThat(address.getStreetNumber()).isEqualTo("6 Bis");

        address = createOne("63 bis", VALID_STREET, VALID_ZIP, VALID_CITY);
        assertThat(address.getStreetNumber()).isEqualTo("63 Bis");

        address = createOne("6bis", VALID_STREET, VALID_ZIP, VALID_CITY);
        assertThat(address.getStreetNumber()).isEqualTo("6 Bis");

        address = createOne("6bis.", VALID_STREET, VALID_ZIP, VALID_CITY);
        assertThat(address.getStreetNumber()).isEqualTo("6 Bis.");
    }

    @Test
    public void shouldCapitalizeStreet() {
        AddressDetails address = createOne(VALID_STREET_NUMBER, "rue bargouin", VALID_ZIP, VALID_CITY);
        assertThat(address.getStreet()).isEqualTo("Rue Bargouin");

        address = createOne(VALID_STREET_NUMBER, "rue-bargouin", VALID_ZIP, VALID_CITY);
        assertThat(address.getStreet()).isEqualTo("Rue-Bargouin");
    }

    @Test
    public void shouldInstantiate() {
        AddressDetails address = createOne(VALID_STREET_NUMBER, VALID_STREET, VALID_ZIP, VALID_CITY);
        assertThat(address.getStreetNumber()).isEqualTo(VALID_STREET_NUMBER);
        assertThat(address.getStreet()).isEqualTo(VALID_STREET);
        assertThat(address.getZip()).isEqualTo(VALID_ZIP);
        assertThat(address.getCity()).isEqualTo(VALID_CITY);
    }

    @Test
    public void shouldCapitalizeAndTrim() {
        String streetNumber = " " + VALID_STREET_NUMBER.toLowerCase().replaceAll("\\s", "") + " ";
        String street = " " + VALID_STREET.toLowerCase() + " ";
        String zip = " " + VALID_ZIP.toLowerCase() + " ";
        String city = " " + VALID_CITY.toLowerCase() + " ";

        AddressDetails address = createOne(streetNumber, street, zip, city);
        assertThat(address.getStreetNumber()).isEqualTo(VALID_STREET_NUMBER);
        assertThat(address.getStreet()).isEqualTo(VALID_STREET);
        assertThat(address.getZip()).isEqualTo(VALID_ZIP);
        assertThat(address.getCity()).isEqualTo(VALID_CITY);
    }

}
