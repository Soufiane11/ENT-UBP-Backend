package org.ubp.ent.backend.core.dao.manager.teacher.contact.address;

import org.springframework.stereotype.Service;
import org.ubp.ent.backend.core.dao.repository.teacher.contact.address.AddressRepository;
import org.ubp.ent.backend.core.domains.teacher.contact.address.AddressDomain;
import org.ubp.ent.backend.core.exceptions.database.AlreadyDefinedInOnNonPersistedEntity;
import org.ubp.ent.backend.core.exceptions.database.notfound.impl.AddressResourceNotFoundException;
import org.ubp.ent.backend.core.model.teacher.contact.address.Address;

import javax.inject.Inject;

/**
 * Created by Anthony on 15/01/2016.
 */
@Service
public class AddressManager {

    @Inject
    private AddressRepository repository;

    public Address create(Address model) {
        if (model == null) {
            throw new IllegalArgumentException("Cannot persist a null " + Address.class.getName());
        }
        if (model.getId() != null) {
            throw new AlreadyDefinedInOnNonPersistedEntity("Cannot persist a " + Address.class.getName() + " which already has an ID.");
        }
        AddressDomain domain = new AddressDomain(model);
        domain = repository.saveAndFlush(domain);
        model.setId(domain.getId());

        return domain.toModel();
    }

    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Cannot delete an " + Address.class.getName() + " with a null ID");
        }

        AddressDomain domain = repository.findOne(id);
        if (domain == null) {
            throw new AddressResourceNotFoundException("No " + Address.class.getName() + " found for id :" + id);
        }
        repository.delete(domain);
    }

}
