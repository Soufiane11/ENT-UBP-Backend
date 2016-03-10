package org.ubp.ent.backend.core.domains.teacher.contact.email;

import org.junit.Test;
import org.ubp.ent.backend.core.model.teacher.contact.email.EmailDetails;
import org.ubp.ent.backend.core.model.teacher.contact.email.EmailDetailsTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Anthony on 14/01/2016.
 */
public class EmailDetailsDomainTest {

    public static EmailDetailsDomain createOne() {
        return new EmailDetailsDomain(EmailDetailsTest.createOne());
    }

    public static EmailDetailsDomain createOne(String address) {
        return new EmailDetailsDomain(EmailDetailsTest.createOne(address));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotInstantiateWithNullModel() {
        new EmailDetailsDomain(null);
    }

    @Test
    public void shouldCreateFromModel() {
        EmailDetails model = EmailDetailsTest.createOne();
        EmailDetailsDomain domain = new EmailDetailsDomain(model);

        assertThat(domain.getAddress()).isEqualTo(model.getAddress());
    }

    @Test
    public void shouldTransformToModel() {
        EmailDetailsDomain domain = createOne();
        EmailDetails model = domain.toModel();

        assertThat(model.getAddress()).isEqualTo(domain.getAddress());
    }

}
