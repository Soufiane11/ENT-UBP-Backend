package org.ubp.ent.backend.config.conditional;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;
import org.ubp.ent.backend.config.conditional.condition.TestProfileCondition;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Anthony on 06/01/2016.
 */
@Component
@Conditional(value = {TestProfileCondition.class})
public class DatabaseTableList {

    @Inject
    private EntityManagerFactory emf;

    private Set<String> names = new HashSet<>();

    public DatabaseTableList() {
    }

    @PostConstruct
    public void populateTableList() {
        SessionFactory sessionFactory = emf.unwrap(SessionFactory.class);

        Map<String, ClassMetadata> classMetadataMap = sessionFactory.getAllClassMetadata();
        for (ClassMetadata classMetadata : classMetadataMap.values()) {
            AbstractEntityPersister aep = (AbstractEntityPersister) classMetadata;
            String tableName = aep.getTableName();
            if (StringUtils.isBlank(tableName) || StringUtils.containsWhitespace(tableName)) {
                continue;
            }
            names.add(tableName);
        }
    }

    public Collection<String> getNames() {
        return names;
    }

}
