package org.ubp.ent.backend.core.domains.teacher.contact.address;

import org.junit.Test;
import org.ubp.ent.backend.core.model.teacher.contact.address.AddressDetails;
import org.ubp.ent.backend.core.model.teacher.contact.address.AddressDetailsTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Anthony on 13/01/2016.
 */
public class AddressDetailsDomainTest {

    public static AddressDetailsDomain createOne() {
        return new AddressDetailsDomain(AddressDetailsTest.createOne());
    }

    public static AddressDetailsDomain createOne(String streetNumber, String street, String zip, String city) {
        return new AddressDetailsDomain(AddressDetailsTest.createOne(streetNumber, street, zip, city));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotInstantiateWithNullModel() {
        new AddressDetailsDomain(null);
    }

    @Test
    public void shouldCreateFromModel() {
        AddressDetails model = AddressDetailsTest.createOne();
        AddressDetailsDomain domain = new AddressDetailsDomain(model);

        assertThat(domain.getStreetNumber()).isEqualTo(model.getStreetNumber());
        assertThat(domain.getStreet()).isEqualTo(model.getStreet());
        assertThat(domain.getZip()).isEqualTo(model.getZip());
        assertThat(domain.getCity()).isEqualTo(model.getCity());
    }

    @Test
    public void shouldTransformToModel() {
        AddressDetailsDomain domain = createOne();
        AddressDetails model = domain.toModel();

        assertThat(model.getStreetNumber()).isEqualTo(domain.getStreetNumber());
        assertThat(model.getStreet()).isEqualTo(domain.getStreet());
        assertThat(model.getZip()).isEqualTo(domain.getZip());
        assertThat(model.getCity()).isEqualTo(domain.getCity());
    }

}
