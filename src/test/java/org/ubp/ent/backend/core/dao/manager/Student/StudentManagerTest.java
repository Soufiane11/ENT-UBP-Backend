package org.ubp.ent.backend.core.dao.manager.student;

import org.junit.Test;
import org.ubp.ent.backend.core.dao.repository.student.StudentRepository;
import org.ubp.ent.backend.core.dao.repository.student.contact.address.AddressRepository;
import org.ubp.ent.backend.core.dao.repository.student.contact.email.EmailRepository;
import org.ubp.ent.backend.core.dao.repository.student.contact.phone.PhoneRepository;
import org.ubp.ent.backend.core.domains.student.StudentDomain;
import org.ubp.ent.backend.core.exceptions.database.AlreadyDefinedInOnNonPersistedEntity;
import org.ubp.ent.backend.core.exceptions.database.notfound.impl.AddressResourceNotFoundException;
import org.ubp.ent.backend.core.exceptions.database.notfound.impl.EmailResourceNotFoundException;
import org.ubp.ent.backend.core.exceptions.database.notfound.impl.PhoneResourceNotFoundException;
import org.ubp.ent.backend.core.exceptions.database.notfound.impl.StudentResourceNotFoundException;
import org.ubp.ent.backend.core.model.student.Student;
import org.ubp.ent.backend.core.model.student.StudentTest;
import org.ubp.ent.backend.core.model.student.contact.address.Address;
import org.ubp.ent.backend.core.model.student.contact.address.AddressTest;
import org.ubp.ent.backend.core.model.student.contact.email.Email;
import org.ubp.ent.backend.core.model.student.contact.email.EmailTest;
import org.ubp.ent.backend.core.model.student.contact.phone.Phone;
import org.ubp.ent.backend.core.model.student.contact.phone.PhoneTest;
import org.ubp.ent.backend.utils.TestScenarioHelper;
import org.ubp.ent.backend.utils.WebApplicationTest;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Soufiane on 27/01/2016.
 */
public class StudentManagerTest extends WebApplicationTest {

    @Inject
    private StudentManager StudentManager;
    @Inject
    private StudentRepository studentRepository;

    @Inject
    private AddressRepository addressRepository;
    @Inject
    private EmailRepository emailRepository;
    @Inject
    private PhoneRepository phoneRepository;

    @Inject
    private TestScenarioHelper scenarioHelper;


    @Test(expected = IllegalArgumentException.class)
    public void shouldFailCreateWithNull() {
        studentManager.create(null);
    }

    @Test(expected = AlreadyDefinedInOnNonPersistedEntity.class)
    public void shouldFailCreateIfIdIsAlreadyDefined() {
        Student model = StudentTest.createOne();
        model.setId(12L);
        studentManager.create(model);
    }

    @Test
    public void shouldCreate() {
        Student model = StudentTest.createOneEmpty();
        Student saved = studentManager.create(model);
        Student fetched = studentManager.findOneById(model.getId());

        assertThat(model.getId()).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(fetched.getName()).isEqualTo(model.getName());
        assertThat(fetched.getContact()).isEqualTo(model.getContact());
        assertThat(fetched.getType()).isEqualTo(model.getType());
    }

    @Test
    public void shouldCreateAddressAndEmailAndPhoneOnCascade() {
        Student model = StudentTest.createOne();
        scenarioHelper.createStudent(model);
        Student fetched = studentManager.findOneById(model.getId());

        assertThat(addressRepository.findAll()).hasSameSizeAs(model.getContact().getAddresses());
        assertThat(emailRepository.findAll()).hasSameSizeAs(model.getContact().getEmails());
        assertThat(phoneRepository.findAll()).hasSameSizeAs(model.getContact().getPhones());
        assertThat(fetched.getContact().getAddresses()).hasSameSizeAs(model.getContact().getAddresses());
        assertThat(fetched.getContact().getEmails()).hasSameSizeAs(model.getContact().getEmails());
        assertThat(fetched.getContact().getPhones()).hasSameSizeAs(model.getContact().getPhones());
        assertThat(fetched.getType()).isEqualTo(model.getType());
    }

    @Test
    public void shouldDeleteAddressAndEmailAndPhoneOnCascade() {
        Student model = StudentTest.createOne();
        scenarioHelper.createStudent(model);
        studentRepository.delete(new StudentDomain(model));

        assertThat(addressRepository.findAll()).isEmpty();
        assertThat(emailRepository.findAll()).isEmpty();
        assertThat(phoneRepository.findAll()).isEmpty();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailFindOneByIdWithNull() {
        studentManager.findOneById(null);
    }

    @Test(expected = StudentResourceNotFoundException.class)
    public void shouldFailFindOneByIdWithNonExisting() {
        studentManager.findOneById(156L);
    }

    @Test
    public void shouldFindOneById() {
        Student model = StudentTest.createOne();
        scenarioHelper.createStudent(model);

        Student fetched = studentManager.findOneById(model.getId());
        assertThat(fetched).isNotNull();
    }

    @Test
    public void shouldFindAll() {
        scenarioHelper.createStudent();
        scenarioHelper.createStudent();
        scenarioHelper.createStudent();

        assertThat(studentManager.findAll()).hasSize(3);
    }

    /*
     * addAddress()
     */
    @Test(expected = IllegalArgumentException.class)
    public void shouldFailAddAddressWithNullStudentId() {
        studentManager.addAddress(null, AddressTest.createOne());
    }

    @Test(expected = StudentResourceNotFoundException.class)
    public void shouldFailAddAddressWithNonExistingStudent() {
        studentManager.addAddress(12L, AddressTest.createOne());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailAddAddressWithNullAddress() {
        studentManager.addAddress(12L, null);
    }

    @Test(expected = AlreadyDefinedInOnNonPersistedEntity.class)
    public void shouldFailAddAddressWithAddressHavingIdAlreadyDefined() {
        Address model = AddressTest.createOne();
        model.setId(12L);
        studentManager.addAddress(12L, model);
    }

    @Test
    public void shouldAddAddress() {
        Student student = scenarioHelper.createEmptyStudent();
        Address address = AddressTest.createOne();

        assertThat(studentManager.findOneById(student.getId()).getContact().getAddresses()).isEmpty();
        Address saved = studentManager.addAddress(student.getId(), address);

        assertThat(addressRepository.findAll()).hasSize(1);
        assertThat(studentManager.findOneById(student.getId()).getContact().getAddresses()).hasSize(1);
        assertThat(saved.getId()).isNotNull();
    }

    /*
     * removeAddress()
     */
    @Test(expected = IllegalArgumentException.class)
    public void shouldFailRemoveAddressWithNullAddressId() {
        studentManager.removeAddress(null);
    }

    @Test(expected = AddressResourceNotFoundException.class)
    public void shouldFailRemoveAddressWithNonExistingStudent() {
        studentManager.removeAddress(12L);
    }

    @Test
    public void shouldRemoveAddress() {
        Student student = scenarioHelper.createStudent();

        assertThat(student.getContact().getAddresses()).isNotEmpty();

        for (Address address : student.getContact().getAddresses()) {
            studentManager.removeAddress(address.getId());
        }
        assertThat(studentManager.findOneById(student.getId()).getContact().getAddresses()).isEmpty();
        assertThat(addressRepository.findAll()).isEmpty();
    }


    /*
     * addEmail()
     */
    @Test(expected = IllegalArgumentException.class)
    public void shouldFailAddEmailWithNullStudentId() {
        studentManager.addEmail(null, EmailTest.createOne());
    }

    @Test(expected = StudentResourceNotFoundException.class)
    public void shouldFailAddEmailWithNonExistingStudent() {
        studentManager.addEmail(12L, EmailTest.createOne());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailAddEmailWithNullEmail() {
        studentManager.addEmail(12L, null);
    }

    @Test(expected = AlreadyDefinedInOnNonPersistedEntity.class)
    public void shouldFailAddEmailWithEmailHavingIdAlreadyDefined() {
        Email model = EmailTest.createOne();
        model.setId(12L);
        studentManager.addEmail(12L, model);
    }

    @Test
    public void shouldAddEmail() {
        Student student = scenarioHelper.createEmptyStudent();
        Email email = EmailTest.createOne();

        assertThat(studentManager.findOneById(student.getId()).getContact().getEmails()).isEmpty();
        Email saved = studentManager.addEmail(student.getId(), email);

        assertThat(emailRepository.findAll()).hasSize(1);
        assertThat(studentManager.findOneById(student.getId()).getContact().getEmails()).hasSize(1);
        assertThat(saved.getId()).isNotNull();
    }

    /*
     * removeEmail()
     */
    @Test(expected = IllegalArgumentException.class)
    public void shouldFailRemoveEmailWithNullEmailId() {
        studentManager.removeEmail(null);
    }

    @Test(expected = EmailResourceNotFoundException.class)
    public void shouldFailRemoveEmailWithNonExistingStudent() {
        studentManager.removeEmail(12L);
    }

    @Test
    public void shouldRemoveEmail() {
        Student student = scenarioHelper.createStudent();

        assertThat(student.getContact().getEmails()).isNotEmpty();

        for (Email email : student.getContact().getEmails()) {
            studentManager.removeEmail(email.getId());
        }
        assertThat(studentManager.findOneById(student.getId()).getContact().getEmails()).isEmpty();
        assertThat(emailRepository.findAll()).isEmpty();
    }


    /*
     * addPhone()
     */
    @Test(expected = IllegalArgumentException.class)
    public void shouldFailAddPhoneWithNullStudentId() {
        studentManager.addPhone(null, PhoneTest.createOne());
    }

    @Test(expected = StudentResourceNotFoundException.class)
    public void shouldFailAddPhoneWithNonExistingStudent() {
        studentManager.addPhone(12L, PhoneTest.createOne());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailAddPhoneWithNullPhone() {
        studentManager.addPhone(12L, null);
    }

    @Test(expected = AlreadyDefinedInOnNonPersistedEntity.class)
    public void shouldFailAddPhoneWithPhoneHavingIdAlreadyDefined() {
        Phone model = PhoneTest.createOne();
        model.setId(12L);
        studentManager.addPhone(12L, model);
    }

    @Test
    public void shouldAddPhone() {
        Student student = scenarioHelper.createEmptyStudent();
        Phone phone = PhoneTest.createOne();

        assertThat(studentManager.findOneById(student.getId()).getContact().getPhones()).isEmpty();
        Phone saved = studentManager.addPhone(student.getId(), phone);

        assertThat(phoneRepository.findAll()).hasSize(1);
        assertThat(studentManager.findOneById(student.getId()).getContact().getPhones()).hasSize(1);
        assertThat(saved.getId()).isNotNull();
    }

    /*
     * removePhone()
     */
    @Test(expected = IllegalArgumentException.class)
    public void shouldFailRemovePhoneWithNullPhoneId() {
        studentManager.removePhone(null);
    }

    @Test(expected = PhoneResourceNotFoundException.class)
    public void shouldFailRemovePhoneWithNonExistingStudent() {
        studentManager.removePhone(12L);
    }

    @Test
    public void shouldRemovePhone() {
        Student student = scenarioHelper.createStudent();

        assertThat(student.getContact().getPhones()).isNotEmpty();

        for (Phone phone : student.getContact().getPhones()) {
            studentManager.removePhone(phone.getId());
        }
        assertThat(studentManager.findOneById(student.getId()).getContact().getPhones()).isEmpty();
        assertThat(phoneRepository.findAll()).isEmpty();
    }

}
