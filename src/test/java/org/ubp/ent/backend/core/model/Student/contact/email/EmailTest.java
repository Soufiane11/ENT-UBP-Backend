package org.ubp.ent.backend.core.model.teacher.contact.email;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Anthony on 13/01/2016.
 */
public class EmailTest {

    public static Email createOne() {
        return new Email(EmailDetailsTest.createOne());
    }

    public static Email createOne(String email) {
        return new Email(EmailDetailsTest.createOne(email));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotInstantiateWithNullDetail() {
        new Email(null);
    }

    @Test
    public void shouldInstantiate() {
        EmailDetails data = EmailDetailsTest.createOne();
        Email model = new Email(data);

        assertThat(model.getId()).isNull();
        assertThat(model.getDetails()).isEqualTo(data);
    }

    @Test
    public void shouldBeEqualsBasedOnNumber() {
        Email first = createOne("abc@dd.fr");
        Email second = createOne("abc@dd.fr");
        assertThat(second).isEqualTo(first);
    }

    @Test
    public void shouldNotBeEqualsWithDifferentNumber() {
        Email first = createOne();
        Email second = createOne();
        assertThat(second).isNotEqualTo(first);
    }

}
