package org.ubp.ent.backend.core.domains.teacher.contact.email;

import com.google.common.base.Objects;
import org.ubp.ent.backend.core.domains.ModelTransformable;
import org.ubp.ent.backend.core.model.teacher.contact.email.EmailDetails;

import javax.persistence.Embeddable;

/**
 * Created by Anthony on 14/01/2016.
 */
@Embeddable
public class EmailDetailsDomain implements ModelTransformable<EmailDetails> {

    private String address;

    protected EmailDetailsDomain() {
    }

    public EmailDetailsDomain(EmailDetails model) {
        if (model == null) {
            throw new IllegalArgumentException("Cannot build a " + getClass().getName() + " with a null " + EmailDetails.class.getName());
        }
        this.address = model.getAddress();
    }

    public String getAddress() {
        return address;
    }

    @Override
    public EmailDetails toModel() {
        return new EmailDetails(address);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailDetailsDomain email = (EmailDetailsDomain) o;
        return Objects.equal(address, email.address);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(address);
    }

}
