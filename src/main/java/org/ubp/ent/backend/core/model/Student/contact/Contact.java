package org.ubp.ent.backend.core.model.teacher.contact;

import com.google.common.base.Objects;
import org.ubp.ent.backend.core.model.teacher.contact.address.Address;
import org.ubp.ent.backend.core.model.teacher.contact.email.Email;
import org.ubp.ent.backend.core.model.teacher.contact.phone.Phone;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Anthony on 13/01/2016.
 */
public class Contact {

    private Set<Address> addresses;
    private Set<Phone> phones;
    private Set<Email> emails;

    public Contact() {
        this.addresses = new HashSet<>();
        this.phones = new HashSet<>();
        this.emails = new HashSet<>();
    }


    public Set<Address> getAddresses() {
        return Collections.unmodifiableSet(addresses);
    }

    public Set<Phone> getPhones() {
        return Collections.unmodifiableSet(phones);
    }

    public Set<Email> getEmails() {
        return Collections.unmodifiableSet(emails);
    }

    public void addAddress(Address address) {
        if (address == null) {
            throw new IllegalArgumentException("Cannot add a null" + Address.class.getName());
        }
        this.addresses.add(address);
    }

    public void addPhone(Phone phone) {
        if (phone == null) {
            throw new IllegalArgumentException("Cannot add a null" + Phone.class.getName());
        }
        this.phones.add(phone);
    }

    public void addEmail(Email email) {
        if (email == null) {
            throw new IllegalArgumentException("Cannot add a null" + Email.class.getName());
        }
        this.emails.add(email);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equal(addresses, contact.addresses) &&
                Objects.equal(phones, contact.phones) &&
                Objects.equal(emails, contact.emails);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(addresses, phones, emails);
    }

}
