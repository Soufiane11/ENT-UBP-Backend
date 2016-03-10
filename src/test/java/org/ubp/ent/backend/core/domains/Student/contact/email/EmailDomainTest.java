package org.ubp.ent.backend.core.domains.teacher.contact.email;

import org.junit.Test;
import org.ubp.ent.backend.core.model.teacher.contact.email.Email;
import org.ubp.ent.backend.core.model.teacher.contact.email.EmailTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Anthony on 14/01/2016.
 */
public class EmailDomainTest {

    public static EmailDomain createOne() {
        return new EmailDomain(EmailTest.createOne());
    }

    public static EmailDomain createOne(String email) {
        return new EmailDomain(EmailTest.createOne(email));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotInstantiateWithNullModel() {
        new EmailDomain(null);
    }

    @Test
    public void shouldCreateFromModel() {
        Email model = EmailTest.createOne();
        model.setId(12L);
        EmailDomain domain = new EmailDomain(model);

        assertThat(domain.getId()).isEqualTo(model.getId());
        assertThat(domain.getDetails().toModel()).isEqualTo(model.getDetails());
    }

    @Test
    public void shouldTransformToModel() {
        EmailDomain domain = createOne();
        domain.setId(12L);
        Email model = domain.toModel();

        assertThat(model.getId()).isEqualTo(domain.getId());
        assertThat(model.getDetails()).isEqualTo(domain.getDetails().toModel());
    }

    @Test
    public void shouldBeEqualsBasedOnAddress() {
        EmailDomain first = createOne("aa@a.fr");
        EmailDomain second = createOne("aa@a.fr");
        assertThat(second).isEqualTo(first);
    }

    @Test
    public void shouldNotBeEqualsWithDifferentAddress() {
        EmailDomain first = createOne();
        EmailDomain second = createOne();
        assertThat(second).isNotEqualTo(first);
    }

}
