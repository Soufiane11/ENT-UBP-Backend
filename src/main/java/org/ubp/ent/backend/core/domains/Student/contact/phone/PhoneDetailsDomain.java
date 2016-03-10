package org.ubp.ent.backend.core.domains.teacher.contact.phone;

import com.google.common.base.Objects;
import org.ubp.ent.backend.core.domains.ModelTransformable;
import org.ubp.ent.backend.core.model.teacher.contact.phone.PhoneDetails;

import javax.persistence.Embeddable;

/**
 * Created by Anthony on 14/01/2016.
 */
@Embeddable
public class PhoneDetailsDomain implements ModelTransformable<PhoneDetails> {

    private String number;

    protected PhoneDetailsDomain() {
    }

    public PhoneDetailsDomain(PhoneDetails model) {
        if (model == null) {
            throw new IllegalArgumentException("Cannot build a " + getClass().getName() + " with a null " + PhoneDetails.class.getName());
        }
        number = model.getNumber();
    }

    public String getNumber() {
        return number;
    }

    @Override
    public PhoneDetails toModel() {
        return new PhoneDetails(number);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneDetailsDomain that = (PhoneDetailsDomain) o;
        return Objects.equal(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(number);
    }

}
