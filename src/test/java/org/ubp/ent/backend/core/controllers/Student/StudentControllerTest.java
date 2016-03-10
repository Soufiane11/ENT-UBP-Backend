package org.ubp.ent.backend.core.controllers.student;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.ubp.ent.backend.core.model.student.Student;
import org.ubp.ent.backend.core.model.student.StudentTest;
import org.ubp.ent.backend.utils.WebIntegrationTest;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Soufiane on 27/02/2016.
 */
public class StudentControllerTest extends WebIntegrationTest {

    private final String STUDENT_BASE_URL = StudentController.BASE_URL;

    @Inject
    private ObjectMapper mapper;

    private Student createStudent() throws Exception {
        return createStudents(1).get(0);
    }

    private List<Student> createStudents(int count) throws Exception {
        List<Student> created = new ArrayList<>(count);
        for (int i = 0; i < count; ++i) {
            Student model = StudentTest.createOneEmpty();
            String json = mapper.writeValueAsString(model);
            perform(post(STUDENT_BASE_URL).content(json).contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isCreated())
                    .andDo(
                            result -> {
                                String response = result.getResponse().getContentAsString();
                                Student createdStudent = mapper.readValue(response, Student.class);
                                created.add(createdStudent);
                            }
                    );
        }
        return created;
    }

    @Test
    public void shouldFindAll() throws Exception {
        int count = 5;
        createStudents(count);

        perform(get(STUDENT_BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(count)));
    }

    @Test
    public void shouldFindOneById() throws Exception {
        List<Student> models = createStudents(5);

        for (Student model : models) {
            perform(get(STUDENT_BASE_URL + "/" + model.getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(model.getId().intValue())));
        }
    }

    @Test
    public void shouldThrow404FindOneByIdWithNonExistingId() throws Exception {
        perform(get(STUDENT_BASE_URL + "/2564"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldNotCreateWithNull() throws Exception {
        perform(post(STUDENT_BASE_URL).contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldNotCreateIfIdIsAlreadyDefined() throws Exception {
        Student model = StudentTest.createOne();
        model.setId(125L);

        perform(post(STUDENT_BASE_URL).contentType(MediaType.APPLICATION_JSON_UTF8).content(mapper.writeValueAsString(model)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldCreate() throws Exception {
        Student model = StudentTest.createOneEmpty();

        perform(post(STUDENT_BASE_URL).contentType(MediaType.APPLICATION_JSON_UTF8).content(mapper.writeValueAsString(model)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))

        ;
    }

    @Test
    public void shouldCreateAddressEmailAndPhoneOnCascade() throws Exception {
        Student model = StudentTest.createOne();
        //if one of below fails, the test makes no sense.
        assertThat(model.getContact().getAddresses()).isNotEmpty();
        assertThat(model.getContact().getEmails()).isNotEmpty();
        assertThat(model.getContact().getPhones()).isNotEmpty();

        perform(post(STUDENT_BASE_URL).contentType(MediaType.APPLICATION_JSON_UTF8).content(mapper.writeValueAsString(model)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andDo(
                        result -> {
                            String response = result.getResponse().getContentAsString();
                            Student createdModel = mapper.readValue(response, Student.class);
                            model.setId(createdModel.getId());
                        }
                );

        perform(get(STUDENT_BASE_URL + "/" + model.getId()))
                .andExpect(jsonPath("$.contact.addresses", hasSize(model.getContact().getAddresses().size())))
                .andExpect(jsonPath("$.contact.phones", hasSize(model.getContact().getPhones().size())))
                .andExpect(jsonPath("$.contact.emails", hasSize(model.getContact().getEmails().size())));
    }

}
