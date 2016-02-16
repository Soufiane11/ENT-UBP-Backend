package org.ubp.ent.backend.core.model.teacher.contact;

import org.junit.Test;
import org.ubp.ent.backend.core.model.teacher.contact.address.Address;
import org.ubp.ent.backend.core.model.teacher.contact.address.AddressTest;
import org.ubp.ent.backend.core.model.teacher.contact.email.Email;
import org.ubp.ent.backend.core.model.teacher.contact.email.EmailTest;
import org.ubp.ent.backend.core.model.teacher.contact.phone.Phone;
import org.ubp.ent.backend.core.model.teacher.contact.phone.PhoneTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Anthony on 13/01/2016.
 */
public class ContactTest {

    public static Contact createOneEmpty() {
        return new Contact();
    }

    public static Contact createOne() {
        Contact contact = createOneEmpty();
        contact.addAddress(AddressTest.createOne());
        contact.addPhone(PhoneTest.createOne());
        contact.addEmail(EmailTest.createOne());
        return contact;
    }

    @Test
    public void shouldInstantiate() {
        Contact contact = new Contact();

        assertThat(contact.getAddresses()).isEmpty();
        assertThat(contact.getPhones()).isEmpty();
        assertThat(contact.getEmails()).isEmpty();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailAddNullAddress() {
        createOneEmpty().addAddress(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldNotBeAbleToModifyAddressSet() {
        Contact contact = createOneEmpty();

        contact.getAddresses().add(AddressTest.createOne());
    }

    @Test
    public void shouldNotAddTwoAddressWithSameType() {
        Contact contact = createOneEmpty();

        Address address1 = AddressTest.createOne("Home");
        contact.addAddress(address1);

        Address address2 = AddressTest.createOne("Home");
        contact.addAddress(address2);

        assertThat(contact.getAddresses()).containsOnly(address2);
    }

    @Test
    public void shouldAddTwoAddressWithDifferentTypes() {
        Contact contact = createOneEmpty();

        Address address1 = AddressTest.createOne("Home");
        contact.addAddress(address1);

        Address address2 = AddressTest.createOne("Secondary Home");
        contact.addAddress(address2);

        assertThat(contact.getAddresses()).containsOnly(address1, address2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailAddNullPhone() {
        createOneEmpty().addPhone(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldNotBeAbleToModifyPhoneSet() {
        Contact contact = createOneEmpty();

        contact.getPhones().add(PhoneTest.createOne());
    }

    @Test
    public void shouldAddTwoPhoneWithDifferentTypes() {
        Contact contact = createOneEmpty();

        Phone phone1 = PhoneTest.createOne();
        contact.addPhone(phone1);

        Phone phone2 = PhoneTest.createOne();
        contact.addPhone(phone2);

        assertThat(contact.getPhones()).containsOnly(phone1, phone2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailAddNullMail() {
        createOneEmpty().addEmail(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldNotBeAbleToModifyEmailSet() {
        Contact contact = createOneEmpty();

        contact.getEmails().add(EmailTest.createOne());
    }

    @Test
    public void shouldNotAddTwoEmailWithSameType() {
        Contact contact = createOneEmpty();

        Email email1 = EmailTest.createOne("University");
        contact.addEmail(email1);

        Email email2 = EmailTest.createOne("University");
        contact.addEmail(email2);

        assertThat(contact.getEmails()).containsOnly(email2);
    }

    @Test
    public void shouldAddTwoEmailWithDifferentTypes() {
        Contact contact = createOneEmpty();

        Email email1 = EmailTest.createOne("University");
        contact.addEmail(email1);

        Email email2 = EmailTest.createOne("Personal");
        contact.addEmail(email2);

        assertThat(contact.getEmails()).containsOnly(email1, email2);
    }

}
