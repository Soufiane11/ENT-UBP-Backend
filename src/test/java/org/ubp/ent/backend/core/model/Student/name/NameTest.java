package org.ubp.ent.backend.core.model.teacher.name;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Anthony on 09/01/2016.
 */
public class NameTest {

    public static Name createOne() {
        String firstName = RandomStringUtils.randomAlphabetic(ThreadLocalRandom.current().nextInt(3, 10));
        String lastName = RandomStringUtils.randomAlphabetic(ThreadLocalRandom.current().nextInt(3, 10));
        return new Name(firstName, lastName);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotInstantiateWithNullFirstName() {
        new Name(null, "RAYMOND");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotInstantiateWithEmptyFirstName() {
        new Name(null, "RAYMOND");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotInstantiateWithNullLastName() {
        new Name("Anthony", " ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotInstantiateWithEmptyLastName() {
        new Name("Anthony", " ");
    }

    @Test
    public void shouldCapitalizeFirstName() {
        Name name = new Name("pierre henry", "Doe");
        assertThat(name.getFirstName()).isEqualTo("Pierre Henry");

        name = new Name("pierre-henry", "Doe");
        assertThat(name.getFirstName()).isEqualTo("Pierre-Henry");
    }

    @Test
    public void shouldCapitalizeAndTrim() {
        Name name = new Name(" bernard henri ", " levy ");
        assertThat(name.getFirstName()).isEqualTo("Bernard Henri");
        assertThat(name.getLastName()).isEqualTo("LEVY");
    }

    @Test
    public void shouldInstantiate() {
        Name name = new Name("Anthony", "RAYMOND");
        assertThat(name.getFirstName()).isEqualTo("Anthony");
        assertThat(name.getLastName()).isEqualTo("RAYMOND");
    }

}
