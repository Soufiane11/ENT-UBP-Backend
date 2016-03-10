package org.ubp.ent.backend.core.dao.manager.teacher.contact.phone;

import org.springframework.stereotype.Service;
import org.ubp.ent.backend.core.dao.repository.teacher.contact.phone.PhoneRepository;
import org.ubp.ent.backend.core.domains.teacher.contact.phone.PhoneDomain;
import org.ubp.ent.backend.core.exceptions.database.AlreadyDefinedInOnNonPersistedEntity;
import org.ubp.ent.backend.core.exceptions.database.notfound.impl.PhoneResourceNotFoundException;
import org.ubp.ent.backend.core.model.teacher.contact.phone.Phone;

import javax.inject.Inject;

/**
 * Created by Anthony on 15/01/2016.
 */
@Service
public class PhoneManager {

    @Inject
    private PhoneRepository repository;

    public Phone create(Phone model) {
        if (model == null) {
            throw new IllegalArgumentException("Cannot persist a null " + Phone.class.getName());
        }
        if (model.getId() != null) {
            throw new AlreadyDefinedInOnNonPersistedEntity("Cannot persist a " + Phone.class.getName() + " which already has an ID.");
        }
        PhoneDomain domain = new PhoneDomain(model);
        domain = repository.saveAndFlush(domain);
        model.setId(domain.getId());

        return domain.toModel();
    }

    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Cannot delete a " + Phone.class.getName() + " with a null ID");
        }

        PhoneDomain domain = repository.findOne(id);
        if (domain == null) {
            throw new PhoneResourceNotFoundException("No " + Phone.class.getName() + " found for id :" + id);
        }
        repository.delete(domain);
    }

}
