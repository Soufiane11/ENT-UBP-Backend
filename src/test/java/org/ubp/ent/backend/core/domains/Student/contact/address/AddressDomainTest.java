package org.ubp.ent.backend.core.domains.teacher.contact.address;

import org.junit.Test;
import org.ubp.ent.backend.core.model.teacher.contact.address.Address;
import org.ubp.ent.backend.core.model.teacher.contact.address.AddressDetails;
import org.ubp.ent.backend.core.model.teacher.contact.address.AddressTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Anthony on 14/01/2016.
 */
public class AddressDomainTest {

    public static AddressDomain createOne() {
        return new AddressDomain(AddressTest.createOne());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotInstantiateWithNullModel() {
        new AddressDomain(null);
    }

    @Test
    public void shouldCreateFromModel() {
        Address model = AddressTest.createOne();
        model.setId(12L);
        AddressDomain domain = new AddressDomain(model);

        assertThat(domain.getId()).isEqualTo(model.getId());
        assertThat(domain.getDetails().toModel()).isEqualTo(model.getDetails());
    }

    @Test
    public void shouldTransformToModel() {
        AddressDomain domain = createOne();
        domain.setId(12L);
        Address model = domain.toModel();

        assertThat(model.getId()).isEqualTo(domain.getId());
        assertThat(model.getDetails()).isEqualTo(domain.getDetails().toModel());
    }

    @Test
    public void shouldBeEqualsBasedOnAddress() {
        AddressDetails firstDetails = new AddressDetails("9", "rue park", "63000", "Clermont-Ferrand");
        AddressDomain first = new AddressDomain(new Address(firstDetails));

        AddressDetails secondDetails = new AddressDetails("9", "rue park", "63000", "Clermont-Ferrand");
        AddressDomain second = new AddressDomain(new Address(secondDetails));

        assertThat(second).isEqualTo(first);
    }

    @Test
    public void shouldNotBeEqualsWithDifferentAddress() {
        AddressDomain first = createOne();
        AddressDomain second = createOne();
        assertThat(second).isNotEqualTo(first);
    }

}
