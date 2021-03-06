package org.ubp.ent.backend.core.model.classroom;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import org.ubp.ent.backend.core.exceptions.database.ModelConstraintViolationException;

/**
 * Created by Anthony on 20/11/2015.
 */
public class RoomCapacity {

    private final Integer maxCapacity;

    @JsonCreator
    public RoomCapacity(@JsonProperty("maxCapacity") Integer maxCapacity) {
        if (maxCapacity < 1) {
            throw new ModelConstraintViolationException("Cannot build a " + getClass().getName() + " with a maxCapacity equal or less than 0.");
        }

        this.maxCapacity = maxCapacity;
    }

    public Integer getMaxCapacity() {
        return maxCapacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomCapacity that = (RoomCapacity) o;
        return Objects.equal(maxCapacity, that.maxCapacity);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(maxCapacity);
    }

}
