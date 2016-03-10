package org.ubp.ent.backend.core.domains.student;

import com.google.common.base.Objects;
import org.ubp.ent.backend.core.domains.ModelTransformable;
import org.ubp.ent.backend.core.domains.Student.contact.ContactDomain;
import org.ubp.ent.backend.core.domains.Student.name.NameDomain;
import org.ubp.ent.backend.core.model.Student.Student;


import javax.persistence.*;

/**
 * Created by Soufiane on 25/02/2016..
 */
@Entity
@Table(name = "student")
public class StudentDomain implements ModelTransformable<Student> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private NameDomain name;

    @Embedded
    private ContactDomain contact;


    protected StudentDomain() {
    }

    public StudentDomain(Student model) {
        if (model == null) {
            throw new IllegalArgumentException("Cannot build a " + getClass().getName() + " with a null " + Student.class.getName());
        }
        id = model.getId();
        name = new NameDomain(model.getName());
        contact = new ContactDomain(model.getContact());

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NameDomain getName() {
        return name;
    }

    public ContactDomain getContact() {
        return contact;
    }


    @Override
    public Student toModel() {
        Student model = new Student(getName().toModel(), getContact().toModel());
        model.setId(this.getId());
        return model;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentDomain other = (StudentDomain) o;
        if (this.getId() == null || other.getId() == null) return false;
        return Objects.equal(this.getId(), other.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getId());
    }

}
