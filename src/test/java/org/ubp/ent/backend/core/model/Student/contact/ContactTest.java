package org.ubp.ent.backend.core.model.teacher.contact;

import org.junit.Test;
import org.ubp.ent.backend.core.model.teacher.contact.address.Address;
import org.ubp.ent.backend.core.model.teacher.contact.address.AddressDetails;
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
    public void shouldNotAddTwoAddressWithSameAddress() {
        Contact contact = createOneEmpty();

        AddressDetails firstDetails = new AddressDetails("9", "rue park", "63000", "Clermont-Ferrand");
        Address address1 = new Address(firstDetails);
        contact.addAddress(address1);

        AddressDetails secondDetails = new AddressDetails("9", "rue park", "63000", "Clermont-Ferrand");
        Address address2 = new Address(secondDetails);
        contact.addAddress(address2);

        assertThat(contact.getAddresses()).containsOnly(address2);
    }

    @Test
    public void shouldAddTwoAddressWithDifferentAddress() {
        Contact contact = createOneEmpty();

        Address address1 = AddressTest.createOne();
        contact.addAddress(address1);

        Address address2 = AddressTest.createOne();
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
    public void shouldNotAddTwoPhoneWithSameNumber() {
        Contact contact = createOneEmpty();

        Phone phone1 = PhoneTest.createOne("04 00 00 00 00");
        contact.addPhone(phone1);

        Phone phone2 = PhoneTest.createOne("04 00 00 00 00");
        contact.addPhone(phone2);

        assertThat(contact.getPhones()).containsOnly(phone2);
    }

    @Test
    public void shouldAddTwoPhoneWithDifferentNumber() {
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
    public void shouldNotAddTwoEmailWithSameAddress() {
        Contact contact = createOneEmpty();

        Email email1 = EmailTest.createOne("aa@a.fr");
        contact.addEmail(email1);

        Email email2 = EmailTest.createOne("aa@a.fr");
        contact.addEmail(email2);

        assertThat(contact.getEmails()).containsOnly(email2);
    }

    @Test
    public void shouldAddTwoEmailWithDifferentAddress() {
        Contact contact = createOneEmpty();

        Email email1 = EmailTest.createOne();
        contact.addEmail(email1);

        Email email2 = EmailTest.createOne();
        contact.addEmail(email2);

        assertThat(contact.getEmails()).containsOnly(email1, email2);
    }

}
