package org.ubp.ent.backend.core.dao.manager.student;

import org.springframework.stereotype.Service;
import org.ubp.ent.backend.core.dao.manager.student.contact.address.AddressManager;
import org.ubp.ent.backend.core.dao.manager.student.contact.email.EmailManager;
import org.ubp.ent.backend.core.dao.manager.student.contact.phone.PhoneManager;
import org.ubp.ent.backend.core.dao.repository.student.StudentRepository;
import org.ubp.ent.backend.core.domains.student.StudentDomain;
import org.ubp.ent.backend.core.exceptions.database.AlreadyDefinedInOnNonPersistedEntity;
import org.ubp.ent.backend.core.exceptions.database.notfound.impl.StudentResourceNotFoundException;
import org.ubp.ent.backend.core.model.student.Student;
import org.ubp.ent.backend.core.model.student.contact.address.Address;
import org.ubp.ent.backend.core.model.student.contact.email.Email;
import org.ubp.ent.backend.core.model.student.contact.phone.Phone;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Soufiane on 25/02/2016..
 */
@Service
public class StudentManager {

    @Inject
    private StudentRepository studentRepository;

    @Inject
    private AddressManager addressManager;

    @Inject
    private EmailManager emailManager;

    @Inject
    private PhoneManager phoneManager;

    public Student create(Student model) {
        if (model == null) {
            throw new IllegalArgumentException("Cannot persist a null " + Student.class.getName());
        }
        if (model.getId() != null) {
            throw new AlreadyDefinedInOnNonPersistedEntity("Cannot persist a " + Student.class.getName() + " which already has an ID.");
        }
        StudentDomain domain = new StudentDomain(model);
        domain = studentRepository.saveAndFlush(domain);
        model.setId(domain.getId());

        return domain.toModel();
    }

    public Student findOneById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Cannot find a " + Student.class.getName() + " with a null id.");
        }
        StudentDomain domain = studentRepository.findOne(id);

        if (domain == null) {
            throw new StudentResourceNotFoundException("No " + Student.class.getName() + " found for id :" + id);
        }

        return domain.toModel();
    }

    public List<Student> findAll() {
        return studentRepository.findAll().parallelStream()
                .map(StudentDomain::toModel)
                .collect(Collectors.toList());
    }

    public Address addAddress(Long studentId, Address model) {
        if (studentId == null) {
            throw new IllegalArgumentException("Cannot find a " + Student.class.getName() + " with a null id.");
        }
        if (model == null) {
            throw new IllegalArgumentException("Cannot persist a null " + Address.class.getName());
        }
        if (model.getId() != null) {
            throw new AlreadyDefinedInOnNonPersistedEntity("Cannot persist a " + Address.class.getName() + " which already has an ID.");
        }

        model = addressManager.create(model);

        Student fetched = findOneById(studentId);
        fetched.getContact().addAddress(model);
        studentRepository.saveAndFlush(new StudentDomain(fetched));
        return model;
    }

    public void removeAddress(Long addressId) {
        addressManager.delete(addressId);
    }

    public Email addEmail(Long studentId, Email model) {
        if (studentId == null) {
            throw new IllegalArgumentException("Cannot find a " + Student.class.getName() + " with a null id.");
        }
        if (model == null) {
            throw new IllegalArgumentException("Cannot persist a null " + Email.class.getName());
        }
        if (model.getId() != null) {
            throw new AlreadyDefinedInOnNonPersistedEntity("Cannot persist a " + Email.class.getName() + " which already has an ID.");
        }

        model = emailManager.create(model);

        Student fetched = findOneById(studentId);
        fetched.getContact().addEmail(model);
        studentRepository.saveAndFlush(new StudentDomain(fetched));
        return model;
    }

    public void removeEmail(Long emailId) {
        emailManager.delete(emailId);
    }

    public Phone addPhone(Long studentId, Phone model) {
        if (studentId == null) {
            throw new IllegalArgumentException("Cannot find a " + Student.class.getName() + " with a null id.");
        }
        if (model == null) {
            throw new IllegalArgumentException("Cannot persist a null " + Phone.class.getName());
        }
        if (model.getId() != null) {
            throw new AlreadyDefinedInOnNonPersistedEntity("Cannot persist a " + Phone.class.getName() + " which already has an ID.");
        }

        model = phoneManager.create(model);

        Student fetched = findOneById(studentId);
        fetched.getContact().addPhone(model);
        studentRepository.saveAndFlush(new StudentDomain(fetched));
        return model;
    }

    public void removePhone(Long phoneId) {
        phoneManager.delete(phoneId);
    }

}
