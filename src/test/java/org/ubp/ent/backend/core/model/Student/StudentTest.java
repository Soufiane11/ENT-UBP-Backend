package org.ubp.ent.backend.core.model.student;

import org.junit.Test;
import org.ubp.ent.backend.core.model.student.contact.Contact;
import org.ubp.ent.backend.core.model.student.contact.ContactTest;
import org.ubp.ent.backend.core.model.student.name.Name;
import org.ubp.ent.backend.core.model.student.name.NameTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Soufiane on 27/02/2016.
 */
public class StudentTest {

    public static Student createOne() {
        return createOne(NameTest.createOne(), ContactTest.createOne());
    }

    private static Student createOne(Name name, Contact contact) {
        return createOne(name, contact);
    }

    private static Student createOne(Name name, Contact contact) {
        return new Student(name, contact);
    }

    public static Student createOneEmpty() {
        return createOne(NameTest.createOne(), ContactTest.createOneEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotInstantiateWithNullName() {
        new Student(null, ContactTest.createOne());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotInstantiateWithNullContact() {
        new Student(NameTest.createOne(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotInstantiateWithNullType() {
        new Student(NameTest.createOne(), ContactTest.createOne(), null);
    }

    @Test
    public void shouldInstantiate() {
        Name name = NameTest.createOne();
        Contact contact = ContactTest.createOne();
        Student student = new Student(name, contact);

        assertThat(student.getId()).isNull();
        assertThat(student.getName()).isEqualTo(name);
        assertThat(student.getContact()).isEqualTo(contact);

    }

    @Test
    public void shouldBeEqualById() {
        Student first = StudentTest.createOne();
        first.setId(1L);
        Student second = StudentTest.createOne();
        second.setId(1L);

        assertThat(first).isEqualTo(second);
    }

    @Test
    public void shouldNotBeEqualWithDifferentIds() {
        Student first = StudentTest.createOne();
        first.setId(1L);
        Student second = StudentTest.createOne();
        second.setId(2L);

        assertThat(first).isNotEqualTo(second);
    }

    @Test
    public void shouldNotBeEqualWithNullIds() {
        Student first = StudentTest.createOne();
        Student second = StudentTest.createOne();

        assertThat(first).isNotEqualTo(second);
    }

}
