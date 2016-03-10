package org.ubp.ent.backend.core.dao.repository.teacher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ubp.ent.backend.core.domains.teacher.TeacherDomain;

/**
 * Created by Soufiane on 25/02/2016.
 */
public interface StudentRepository extends JpaRepository<StudentDomain, Long> {

}
