package org.ubp.ent.backend.core.domains.classroom.equipement;

import org.ubp.ent.backend.core.domains.ModelTransformable;
import org.ubp.ent.backend.core.domains.classroom.ClassroomDomain;
import org.ubp.ent.backend.core.model.classroom.equipement.Quantity;
import org.ubp.ent.backend.core.model.classroom.equipement.RoomEquipment;

import javax.persistence.*;

/**
 * Created by Anthony on 22/11/2015.
 */
@Entity
@Table(name = "room_equipment_association")
@AssociationOverrides({
                              @AssociationOverride(name = "primaryKey.classroom", joinColumns = @JoinColumn(name = "CLASSROOM_ID")),
                              @AssociationOverride(name = "primaryKey.equipmentType", joinColumns = @JoinColumn(name = "EQUIPMENT_TYPE_ID"))
                      })
public class RoomEquipmentDomain implements ModelTransformable<RoomEquipment> {

    @EmbeddedId
    private RoomEquipmentDomainId primaryKey = new RoomEquipmentDomainId();

    private int quantity;

    @SuppressWarnings("unused")
    protected RoomEquipmentDomain() {
    }

    public RoomEquipmentDomain(RoomEquipment roomEquipment, ClassroomDomain classroomDomain) {
        if (roomEquipment == null) {
            throw new IllegalArgumentException("Cannot build a " + getClass().getName() + " with a null " + RoomEquipment.class.getName());
        }
        if (classroomDomain == null) {
            throw new IllegalArgumentException("Cannot build a " + getClass().getName() + " with a null " + ClassroomDomain.class.getName());
        }

        EquipmentTypeDomain equipmentTypeDomain = new EquipmentTypeDomain(roomEquipment.getEquipmentType());
        this.primaryKey = new RoomEquipmentDomainId(classroomDomain, equipmentTypeDomain);
        this.quantity = roomEquipment.getQuantity().getMaxQuantity();
    }


    public RoomEquipmentDomainId getId() {
        return primaryKey;
    }

    @Transient
    public ClassroomDomain getClassroom() {
        return primaryKey.getClassroom();
    }

    @Transient
    public EquipmentTypeDomain getEquipmentType() {
        return primaryKey.getEquipmentType();
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public RoomEquipment toModel() {
        return new RoomEquipment(getEquipmentType().toModel(), new Quantity(quantity));
    }

}
