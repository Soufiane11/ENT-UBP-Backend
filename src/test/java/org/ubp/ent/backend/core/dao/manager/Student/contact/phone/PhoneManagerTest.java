package org.ubp.ent.backend.core.dao.manager.teacher.contact.phone;

import org.junit.Test;
import org.ubp.ent.backend.core.dao.repository.teacher.contact.phone.PhoneRepository;
import org.ubp.ent.backend.core.exceptions.database.AlreadyDefinedInOnNonPersistedEntity;
import org.ubp.ent.backend.core.exceptions.database.notfound.impl.PhoneResourceNotFoundException;
import org.ubp.ent.backend.core.model.teacher.contact.phone.Phone;
import org.ubp.ent.backend.core.model.teacher.contact.phone.PhoneTest;
import org.ubp.ent.backend.utils.WebApplicationTest;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Anthony on 15/01/2016.
 */
public class PhoneManagerTest extends WebApplicationTest {

    @Inject
    private PhoneManager phoneManager;
    @Inject
    private PhoneRepository repository;

    public Phone createPersistedPhone() {
        Phone model = PhoneTest.createOne();
        phoneManager.create(model);
        return model;
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailCreateWithNull() {
        phoneManager.create(null);
    }

    @Test(expected = AlreadyDefinedInOnNonPersistedEntity.class)
    public void shouldFailCreateIfIdIsAlreadyDefined() {
        Phone model = PhoneTest.createOne();
        model.setId(12L);

        phoneManager.create(model);
    }

    @Test
    public void shouldCreate() {
        Phone persisted = createPersistedPhone();

        assertThat(persisted.getId()).isNotNull();
        assertThat(repository.findAll()).hasSize(1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailDeleteWithNull() {
        phoneManager.delete(null);
    }

    @Test(expected = PhoneResourceNotFoundException.class)
    public void shouldFailDeleteNonExisting() {
        phoneManager.delete(15L);
    }

    @Test
    public void shouldDelete() {
        Phone persisted = createPersistedPhone();

        phoneManager.delete(persisted.getId());
        assertThat(repository.findAll()).hasSize(0);
    }

}
