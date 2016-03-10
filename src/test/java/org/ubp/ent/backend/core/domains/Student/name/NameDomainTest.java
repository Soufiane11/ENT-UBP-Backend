package org.ubp.ent.backend.core.domains.teacher.name;

import org.junit.Test;
import org.ubp.ent.backend.core.model.teacher.name.Name;
import org.ubp.ent.backend.core.model.teacher.name.NameTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Anthony on 11/01/2016.
 */
public class NameDomainTest {

    public static NameDomain createOne() {
        return new NameDomain(NameTest.createOne());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotInstantiateWithNullModel() {
        new NameDomain(null);
    }

    @Test
    public void shouldCreateFromModel() {
        Name model = NameTest.createOne();
        NameDomain domain = new NameDomain(model);

        assertThat(domain.getFirstName()).isEqualTo(model.getFirstName());
        assertThat(domain.getLastName()).isEqualTo(model.getLastName());
    }

    @Test
    public void shouldTransformToModel() {
        NameDomain domain = createOne();
        Name model = domain.toModel();

        assertThat(model.getFirstName()).isEqualTo(domain.getFirstName());
        assertThat(model.getLastName()).isEqualTo(domain.getLastName());
    }

}
