package org.ubp.ent.backend.core.model.student;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import org.ubp.ent.backend.core.model.student.contact.Contact;
import org.ubp.ent.backend.core.model.student.name.Name;

/**
 * Created by Soufiane on 24/02/2016.
 */
public class Student {

    private Long id;
    private Name name;
    private Contact contact;
    //private int age;
    //private float note;

    @JsonCreator
    public Student(@JsonProperty("name") Name name, @JsonProperty("contact") Contact contact,@JsonProperty("age") int age, @JsonProperty("note") float note {
        if (name == null) {
            throw new IllegalArgumentException("Cannot build a " + getClass().getName() + " with a null " + Name.class.getName());
        }
        if (contact == null) {
            throw new IllegalArgumentException("Cannot build a " + getClass().getName() + " with a null " + Contact.class.getName());
        }

        this.name = name;
        this.contact = contact;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Name getName() {
        return name;
    }

    public Contact getContact() {
        return contact;
    }

   /* public StudentAge getStudentAge() {
        return age;
    }

    public StudentNote getStudentNot() {
        return note;

        static void addToCount()
    {
        studentCount++;
    }

    static int readStudentCount()
    {
        return studentCount;
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student other = (Student) o;
        if (this.getId() == null || other.getId() == null) return false;
        return Objects.equal(this.getId(), other.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getId());
    }

}
