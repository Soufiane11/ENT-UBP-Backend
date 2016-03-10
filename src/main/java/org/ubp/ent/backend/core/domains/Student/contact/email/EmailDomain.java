package org.ubp.ent.backend.core.domains.teacher.contact.email;

import com.google.common.base.Objects;
import org.ubp.ent.backend.core.domains.ModelTransformable;
import org.ubp.ent.backend.core.model.teacher.contact.email.Email;

import javax.persistence.*;

/**
 * Created by Anthony on 14/01/2016.
 */
@Entity
@Table(name = "email")
public class EmailDomain implements ModelTransformable<Email> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private EmailDetailsDomain details;

    protected EmailDomain() {
    }

    public EmailDomain(Email model) {
        if (model == null) {
            throw new IllegalArgumentException("Cannot build a " + getClass().getName() + " with a null " + Email.class.getName());
        }
        id = model.getId();
        details = new EmailDetailsDomain(model.getDetails());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EmailDetailsDomain getDetails() {
        return details;
    }

    @Override
    public Email toModel() {
        Email model = new Email(details.toModel());
        model.setId(id);
        return model;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailDomain that = (EmailDomain) o;
        return Objects.equal(details, that.details);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(details);
    }

}
