package com.student.information.system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.student.information.system.model.Student;
import com.student.information.system.service.StudentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author TOURE110485
 */
@RunWith(SpringRunner.class)
@WebMvcTest
public class StudentRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StudentService studentService;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Student mohamed;
    private Student ernest;

    private final Long mohamedStudentNumber = 23L;
    private final Long ernetsStudentNumber = 91L;

    @Before
    public void setup() {
        mohamed = new Student();
        mohamed.setId("aBc123");
        mohamed.setName("Mohamed");
        mohamed.setEmail("student@test.com");
        mohamed.setStudentNumber(mohamedStudentNumber);
        mohamed.setCourseList(Arrays.asList("Math", "Science"));
        mohamed.setGpa(3.37f);

        ernest = new Student();
        ernest.setId("dEf345");
        ernest.setName("yigit");
        ernest.setEmail("yigit@ygt.com");
        ernest.setStudentNumber(ernetsStudentNumber);
        ernest.setCourseList(Arrays.asList("Turkish", "German"));
        ernest.setGpa(3.58f);
    }

    @Test
    public void givenStudents_whenGetAllStudents_thenReturnJsonArray() throws Exception {
        given(studentService.findAll()).willReturn(Arrays.asList(mohamed));

        mvc.perform(get("/students/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(mohamed.getName())));
    }

    @Test
    public void givenStudent_whenFindByStudentNumber_thenReturnJsonArray() throws Exception {
        given(studentService.findByStudentNumber(mohamedStudentNumber)).willReturn(mohamed);

        mvc.perform(get("/students/byStudentNumber/{studentNumber}", mohamedStudentNumber)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(mohamed.getName())));
    }

    @Test
    public void givenStudent_whenFindAllByOrderByGpaDesc_thenReturnJsonArray() throws Exception {
        given(studentService.findAllByOrderByGpaDesc()).willReturn(Arrays.asList(ernest, mohamed));

        mvc.perform(get("/students/orderByGpa/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(ernest.getName())));
    }

    @Test
    public void saveStudent_itShouldReturnStatusOk() throws Exception {
        given(studentService.saveOrUpdateStudent(any(Student.class))).willReturn(ernest);

        String jsonString = objectMapper.writeValueAsString(ernest);

        mvc.perform(post("/students/save/")
                .contentType(MediaType.APPLICATION_JSON).content(jsonString))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteStudentByStudentNumber_itShouldReturnStatusOk() throws Exception {
        given(studentService.findByStudentNumber(ernetsStudentNumber)).willReturn(ernest);
        Mockito.doNothing().when(studentService).deleteStudentById(any(String.class));

        mvc.perform(delete("/students/delete/{studentNumber}", ernetsStudentNumber)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
