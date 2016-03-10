package org.ubp.ent.backend.core.domains.teacher.name;

import org.ubp.ent.backend.core.domains.ModelTransformable;
import org.ubp.ent.backend.core.model.teacher.name.Name;

import javax.persistence.Embeddable;

/**
 * Created by Anthony on 11/01/2016.
 */
@Embeddable
public class NameDomain implements ModelTransformable<Name> {

    private String firstName;
    private String lastName;

    protected NameDomain() {

    }

    public NameDomain(Name name) {
        if (name == null) {
            throw new IllegalArgumentException("Cannot build a " + getClass().getName() + " with a null " + Name.class.getName());
        }

        this.firstName = name.getFirstName();
        this.lastName = name.getLastName();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public Name toModel() {
        return new Name(firstName, lastName);
    }

}
