package org.ubp.ent.backend.core.model.classroom;


import org.junit.Test;
import org.ubp.ent.backend.core.exceptions.database.ModelConstraintViolationException;

import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Anthony on 20/11/2015.
 */
public class RoomCapacityTest {

    public static RoomCapacity createOne() {
        return createOne(ThreadLocalRandom.current().nextInt(12, 35));
    }

    public static RoomCapacity createOne(int maxCapacity) {
        return new RoomCapacity(maxCapacity);
    }

    @Test(expected = ModelConstraintViolationException.class)
    public void shouldNotInstantiateWithZeroCapacity() {
        new RoomCapacity(0);
    }

    @Test(expected = ModelConstraintViolationException.class)
    public void shouldNotInstantiateWithNegativeCapacity() {
        new RoomCapacity(-10);
    }

    @Test
    public void shouldInstantiate() {
        int maxCapacity = 22;
        RoomCapacity roomCapacity = new RoomCapacity(maxCapacity);

        assertThat(roomCapacity.getMaxCapacity().intValue()).isEqualTo(maxCapacity);
    }

}
