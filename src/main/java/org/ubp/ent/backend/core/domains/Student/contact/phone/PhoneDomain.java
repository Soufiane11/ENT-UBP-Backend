package org.ubp.ent.backend.core.domains.teacher.contact.phone;

import com.google.common.base.Objects;
import org.ubp.ent.backend.core.domains.ModelTransformable;
import org.ubp.ent.backend.core.model.teacher.contact.phone.Phone;

import javax.persistence.*;

/**
 * Created by Anthony on 14/01/2016.
 */
@Entity
@Table(name = "phone")
public class PhoneDomain implements ModelTransformable<Phone> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private PhoneDetailsDomain details;

    protected PhoneDomain() {
    }

    public PhoneDomain(Phone model) {
        if (model == null) {
            throw new IllegalArgumentException("Cannot build a " + getClass().getName() + " with a null " + Phone.class.getName());
        }
        id = model.getId();
        details = new PhoneDetailsDomain(model.getDetails());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PhoneDetailsDomain getDetails() {
        return details;
    }

    @Override
    public Phone toModel() {
        Phone model = new Phone(details.toModel());
        model.setId(id);
        return model;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneDomain that = (PhoneDomain) o;
        return Objects.equal(details, that.details);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(details);
    }

}
