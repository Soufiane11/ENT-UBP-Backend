package org.ubp.ent.backend.core.domains.student;

import org.junit.Test;
import org.ubp.ent.backend.core.model.student.Student;
import org.ubp.ent.backend.core.model.student.StudentTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Soufiane on 27/02/2016.
 */
public class StudentDTest {

    public static StudentDomain createOne() {
        return createOne(StudentTest.createOne());
    }

    public static StudentDomain createOne(Student model) {
        return new StudentDomain(model);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotInstantiateWithNullModel() {
        new StudentDomain(null);
    }

    @Test
    public void shouldCreateFromModel() {
        Student model = StudentTest.createOne();
        model.setId(12L);
        StudentDomain domain = new StudentDomain(model);

        assertThat(domain.getId()).isEqualTo(model.getId());
        assertThat(domain.getName().toModel()).isEqualTo(model.getName());
        assertThat(domain.getContact().getAddresses()).hasSameSizeAs(model.getContact().getAddresses());
        assertThat(domain.getContact().getEmails()).hasSameSizeAs(model.getContact().getEmails());
        assertThat(domain.getContact().getPhones()).hasSameSizeAs(model.getContact().getPhones());

    }

    @Test
    public void shouldTransformToModel() {
        StudentDomain domain = StudentDTest.createOne();
        domain.setId(12L);
        Student model = domain.toModel();

        assertThat(model.getId()).isEqualTo(domain.getId());
        assertThat(model.getName()).isEqualTo(domain.getName().toModel());
        assertThat(model.getContact().getAddresses()).hasSameSizeAs(domain.getContact().getAddresses());
        assertThat(model.getContact().getEmails()).hasSameSizeAs(domain.getContact().getEmails());
        assertThat(model.getContact().getPhones()).hasSameSizeAs(domain.getContact().getPhones());

    }

    @Test
    public void shouldBeEqualById() {
        StudentDomain first = StudentDTest.createOne();
        first.setId(1L);
        StudentDomain second = StudentDTest.createOne();
        second.setId(1L);

        assertThat(first).isEqualTo(second);
    }

    @Test
    public void shouldNotBeEqualWithDifferentIds() {
        StudentDomain first = StudentDTest.createOne();
        first.setId(1L);
        StudentDomain second = StudentDTest.createOne();
        second.setId(2L);

        assertThat(first).isNotEqualTo(second);
    }

    @Test
    public void shouldNotBeEqualWithNullIds() {
        StudentDomain first = StudentDTest.createOne();
        StudentDomain second = StudentDTest.createOne();

        assertThat(first).isNotEqualTo(second);
    }

}
