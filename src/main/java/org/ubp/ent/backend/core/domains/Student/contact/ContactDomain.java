package org.ubp.ent.backend.core.domains.teacher.contact;

import com.google.common.base.Objects;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.ubp.ent.backend.core.domains.ModelTransformable;
import org.ubp.ent.backend.core.domains.teacher.contact.address.AddressDomain;
import org.ubp.ent.backend.core.domains.teacher.contact.email.EmailDomain;
import org.ubp.ent.backend.core.domains.teacher.contact.phone.PhoneDomain;
import org.ubp.ent.backend.core.model.teacher.contact.Contact;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Anthony on 14/01/2016.
 */
@Embeddable
public class ContactDomain implements ModelTransformable<Contact> {

    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "teacher_id")
    @Cascade({CascadeType.PERSIST, CascadeType.DELETE})
    private Set<AddressDomain> addresses = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "teacher_id")
    @Cascade({CascadeType.PERSIST, CascadeType.DELETE})
    private Set<PhoneDomain> phones = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "teacher_id")
    @Cascade({CascadeType.PERSIST, CascadeType.DELETE})
    private Set<EmailDomain> emails = new HashSet<>();

    protected ContactDomain() {
    }

    public ContactDomain(Contact model) {
        if (model == null) {
            throw new IllegalArgumentException("Cannot build a " + getClass().getName() + " with a null " + Contact.class.getName());
        }
        model.getAddresses().forEach(e -> addresses.add(new AddressDomain(e)));
        model.getPhones().forEach(e -> phones.add(new PhoneDomain(e)));
        model.getEmails().forEach(e -> emails.add(new EmailDomain(e)));
    }

    public Set<AddressDomain> getAddresses() {
        return Collections.unmodifiableSet(addresses);
    }

    public Set<PhoneDomain> getPhones() {
        return Collections.unmodifiableSet(phones);
    }

    public Set<EmailDomain> getEmails() {
        return Collections.unmodifiableSet(emails);
    }

    @Override
    public Contact toModel() {
        Contact model = new Contact();
        addresses.forEach(e -> model.addAddress(e.toModel()));
        phones.forEach(e -> model.addPhone(e.toModel()));
        emails.forEach(e -> model.addEmail(e.toModel()));
        return model;
    }

    public void addAddress(AddressDomain address) {
        if (address == null) {
            throw new IllegalArgumentException("Cannot add a null" + AddressDomain.class.getName());
        }
        this.addresses.add(address);
    }

    public void addPhone(PhoneDomain phone) {
        if (phone == null) {
            throw new IllegalArgumentException("Cannot add a null" + PhoneDomain.class.getName());
        }
        this.phones.add(phone);
    }

    public void addEmail(EmailDomain email) {
        if (email == null) {
            throw new IllegalArgumentException("Cannot add a null" + EmailDomain.class.getName());
        }
        this.emails.add(email);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactDomain that = (ContactDomain) o;
        return Objects.equal(addresses, that.addresses) &&
                Objects.equal(phones, that.phones) &&
                Objects.equal(emails, that.emails);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(addresses, phones, emails);
    }

}
