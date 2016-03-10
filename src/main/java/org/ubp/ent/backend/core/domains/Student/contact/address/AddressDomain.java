package org.ubp.ent.backend.core.domains.teacher.contact.address;

import com.google.common.base.Objects;
import org.ubp.ent.backend.core.domains.ModelTransformable;
import org.ubp.ent.backend.core.model.teacher.contact.address.Address;

import javax.persistence.*;

/**
 * Created by Anthony on 13/01/2016.
 */
@Entity
@Table(name = "address")
public class AddressDomain implements ModelTransformable<Address> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private AddressDetailsDomain details;

    protected AddressDomain() {
    }

    public AddressDomain(Address model) {
        if (model == null) {
            throw new IllegalArgumentException("Cannot build a " + getClass().getName() + " with a null " + Address.class.getName());
        }
        id = model.getId();
        details = new AddressDetailsDomain(model.getDetails());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AddressDetailsDomain getDetails() {
        return details;
    }

    @Override
    public Address toModel() {
        Address model = new Address(details.toModel());
        model.setId(id);
        return model;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressDomain that = (AddressDomain) o;
        return Objects.equal(details, that.details);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(details);
    }

}
