package org.ubp.ent.backend.core.dao.manager.teacher.contact.email;

import org.springframework.stereotype.Service;
import org.ubp.ent.backend.core.dao.repository.teacher.contact.email.EmailRepository;
import org.ubp.ent.backend.core.domains.teacher.contact.email.EmailDomain;
import org.ubp.ent.backend.core.exceptions.database.AlreadyDefinedInOnNonPersistedEntity;
import org.ubp.ent.backend.core.exceptions.database.notfound.impl.EmailResourceNotFoundException;
import org.ubp.ent.backend.core.model.teacher.contact.email.Email;

import javax.inject.Inject;

/**
 * Created by Anthony on 15/01/2016.
 */
@Service
public class EmailManager {

    @Inject
    private EmailRepository repository;

    public Email create(Email model) {
        if (model == null) {
            throw new IllegalArgumentException("Cannot persist a null " + Email.class.getName());
        }
        if (model.getId() != null) {
            throw new AlreadyDefinedInOnNonPersistedEntity("Cannot persist a " + Email.class.getName() + " which already has an ID.");
        }
        EmailDomain domain = new EmailDomain(model);
        domain = repository.saveAndFlush(domain);
        model.setId(domain.getId());

        return domain.toModel();
    }

    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Cannot delete a " + Email.class.getName() + " with a null ID");
        }

        EmailDomain domain = repository.findOne(id);
        if (domain == null) {
            throw new EmailResourceNotFoundException("No " + Email.class.getName() + " found for id :" + id);
        }
        repository.delete(domain);
    }

}
