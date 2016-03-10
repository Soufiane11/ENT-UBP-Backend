package org.ubp.ent.backend.core.controllers.teacher;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.ubp.ent.backend.core.dao.manager.teacher.TeacherManager;
import org.ubp.ent.backend.core.model.teacher.Teacher;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Soufiane on 27/02/2016.
 */
@RestController
@RequestMapping(StudentController.BASE_URL)
public class StudentController {

    public static final String BASE_URL = "/api/student";

    @Inject
    private StudentManager studentManager;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Student> findAll() {
        return studentManager.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Student findOneById(@PathVariable("id") Long id) {
        return studentManager.findOneById(id);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Student create(@RequestBody Student model) {
        return studentManager.create(model);
    }

}
