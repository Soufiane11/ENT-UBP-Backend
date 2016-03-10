package org.ubp.ent.backend.core.model.teacher.contact.phone;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Anthony on 13/01/2016.
 */
public class PhoneTest {

    public static Phone createOne() {
        return new Phone(PhoneDetailsTest.createOne());
    }

    public static Phone createOne(String number) {
        return new Phone(PhoneDetailsTest.createOne(number));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotInstantiateWithNullDetail() {
        new Phone(null);
    }

    @Test
    public void shouldInstantiate() {
        PhoneDetails data = PhoneDetailsTest.createOne();
        Phone model = new Phone(data);

        assertThat(model.getId()).isNull();
        assertThat(model.getDetails()).isEqualTo(data);
    }

    @Test
    public void shouldBeEqualsBasedOnNumber() {
        Phone first = createOne("04 00 00 00 00");
        Phone second = createOne("04 00 00 00 00");
        assertThat(second).isEqualTo(first);
    }

    @Test
    public void shouldNotBeEqualsWithDifferentNumber() {
        Phone first = createOne();
        Phone second = createOne();
        assertThat(second).isNotEqualTo(first);
    }

}
