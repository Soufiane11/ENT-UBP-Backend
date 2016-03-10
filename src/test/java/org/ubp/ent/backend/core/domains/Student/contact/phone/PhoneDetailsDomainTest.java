package org.ubp.ent.backend.core.domains.teacher.contact.phone;

import org.junit.Test;
import org.ubp.ent.backend.core.model.teacher.contact.phone.PhoneDetails;
import org.ubp.ent.backend.core.model.teacher.contact.phone.PhoneDetailsTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Anthony on 14/01/2016.
 */
public class PhoneDetailsDomainTest {

    public static PhoneDetailsDomain createOne() {
        return new PhoneDetailsDomain(PhoneDetailsTest.createOne());
    }

    public static PhoneDetailsDomain createOne(String address) {
        return new PhoneDetailsDomain(PhoneDetailsTest.createOne(address));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotInstantiateWithNullModel() {
        new PhoneDetailsDomain(null);
    }

    @Test
    public void shouldCreateFromModel() {
        PhoneDetails model = PhoneDetailsTest.createOne();
        PhoneDetailsDomain domain = new PhoneDetailsDomain(model);

        assertThat(domain.getNumber()).isEqualTo(model.getNumber());
    }

    @Test
    public void shouldTransformToModel() {
        PhoneDetailsDomain domain = createOne();
        PhoneDetails model = domain.toModel();

        assertThat(model.getNumber()).isEqualTo(domain.getNumber());
    }

}
