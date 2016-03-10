package org.ubp.ent.backend.core.model.teacher.contact.address;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Anthony on 13/01/2016.
 */
public class AddressTest {

    public static Address createOne() {
        return new Address(AddressDetailsTest.createOne());
    }


    @Test(expected = IllegalArgumentException.class)
    public void shouldNotInstantiateWithNullDetail() {
        new Address(null);
    }

    @Test
    public void shouldInstantiate() {
        AddressDetails data = AddressDetailsTest.createOne();
        Address model = new Address(data);

        assertThat(model.getId()).isNull();
        assertThat(model.getDetails()).isEqualTo(data);
    }

    @Test
    public void shouldBeEqualsBasedOnAddress() {
        AddressDetails firstDetails = new AddressDetails("9", "rue park", "63000", "Clermont-Ferrand");
        Address first = new Address(firstDetails);

        AddressDetails secondDetails = new AddressDetails("9", "rue park", "63000", "Clermont-Ferrand");
        Address second = new Address(secondDetails);
        assertThat(second).isEqualTo(first);
    }

    @Test
    public void shouldNotBeEqualsWithDifferentAddress() {
        Address first = new Address(AddressDetailsTest.createOne());
        Address second = new Address(AddressDetailsTest.createOne());
        assertThat(second).isNotEqualTo(first);
    }

}
