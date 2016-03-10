package org.ubp.ent.backend.core.dao.manager.teacher.contact.email;

import org.junit.Test;
import org.ubp.ent.backend.core.dao.repository.teacher.contact.email.EmailRepository;
import org.ubp.ent.backend.core.exceptions.database.AlreadyDefinedInOnNonPersistedEntity;
import org.ubp.ent.backend.core.exceptions.database.notfound.impl.EmailResourceNotFoundException;
import org.ubp.ent.backend.core.model.teacher.contact.email.Email;
import org.ubp.ent.backend.core.model.teacher.contact.email.EmailTest;
import org.ubp.ent.backend.utils.WebApplicationTest;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Anthony on 15/01/2016.
 */
public class EmailManagerTest extends WebApplicationTest {

    @Inject
    private EmailManager emailManager;
    @Inject
    private EmailRepository repository;

    public Email createPersistedEmail() {
        Email model = EmailTest.createOne();
        emailManager.create(model);
        return model;
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailCreateWithNull() {
        emailManager.create(null);
    }

    @Test(expected = AlreadyDefinedInOnNonPersistedEntity.class)
    public void shouldFailCreateIfIdIsAlreadyDefined() {
        Email model = EmailTest.createOne();
        model.setId(12L);

        emailManager.create(model);
    }

    @Test
    public void shouldCreate() {
        Email persisted = createPersistedEmail();

        assertThat(persisted.getId()).isNotNull();
        assertThat(repository.findAll()).hasSize(1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailDeleteWithNull() {
        emailManager.delete(null);
    }

    @Test(expected = EmailResourceNotFoundException.class)
    public void shouldFailDeleteNonExisting() {
        emailManager.delete(15L);
    }

    @Test
    public void shouldDelete() {
        Email persisted = createPersistedEmail();

        emailManager.delete(persisted.getId());
        assertThat(repository.findAll()).hasSize(0);
    }

}
