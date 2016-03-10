package org.ubp.ent.backend.core.dao.manager.teacher.contact.address;

import org.junit.Test;
import org.ubp.ent.backend.core.dao.repository.teacher.contact.address.AddressRepository;
import org.ubp.ent.backend.core.exceptions.database.AlreadyDefinedInOnNonPersistedEntity;
import org.ubp.ent.backend.core.exceptions.database.notfound.impl.AddressResourceNotFoundException;
import org.ubp.ent.backend.core.model.teacher.contact.address.Address;
import org.ubp.ent.backend.core.model.teacher.contact.address.AddressTest;
import org.ubp.ent.backend.utils.WebApplicationTest;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Anthony on 15/01/2016.
 */
public class AddressManagerTest extends WebApplicationTest {

    @Inject
    private AddressManager addressManager;
    @Inject
    private AddressRepository repository;

    public Address createPersistedAddress() {
        Address model = AddressTest.createOne();
        addressManager.create(model);
        return model;
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailCreateWithNull() {
        addressManager.create(null);
    }

    @Test(expected = AlreadyDefinedInOnNonPersistedEntity.class)
    public void shouldFailCreateIfIdIsAlreadyDefined() {
        Address model = AddressTest.createOne();
        model.setId(12L);

        addressManager.create(model);
    }

    @Test
    public void shouldCreate() {
        Address persisted = createPersistedAddress();

        assertThat(persisted.getId()).isNotNull();
        assertThat(repository.findAll()).hasSize(1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailDeleteWithNull() {
        addressManager.delete(null);
    }

    @Test(expected = AddressResourceNotFoundException.class)
    public void shouldFailDeleteNonExisting() {
        addressManager.delete(15L);
    }

    @Test
    public void shouldDelete() {
        Address persisted = createPersistedAddress();

        addressManager.delete(persisted.getId());
        assertThat(repository.findAll()).hasSize(0);
    }

}
