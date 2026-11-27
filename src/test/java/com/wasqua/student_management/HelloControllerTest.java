package com.wasqua.student_management;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.mockito.Mockito.mock;
import com.wasqua.student_management.dto.StudentRequestDTO;
import com.wasqua.student_management.dto.StudentResponseDTO;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.http.MediaType;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;

class HelloControllerTest {

    @Test
    void helloEndpointTest() throws Exception {

        StudentService studentService = mock(StudentService.class);

        HelloController controller = new HelloController(studentService);

        MockMvc mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();

        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        "Hello! Welcome to Student Management System."
                ));
    }

    @Test
    void addStudentTest() throws Exception {

        StudentService studentService = mock(StudentService.class);

        HelloController controller = new HelloController(studentService);

        MockMvc mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();

        StudentResponseDTO response =
                new StudentResponseDTO(101, "Wasqua", "CSE");

        when(studentService.saveStudent(any(StudentRequestDTO.class)))
                .thenReturn(response);

        mockMvc.perform(post("/student")
                        .contentType("application/json")
                        .content("""
                            {
                                "roll": 101,
                                "name": "Wasqua",
                                "departmentId": 1
                            }
                            """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.roll").value(101))
                .andExpect(jsonPath("$.name").value("Wasqua"))
                .andExpect(jsonPath("$.department").value("CSE"));
    }

    @Test
    void addStudentWithInvalidDataTest() throws Exception {

        StudentService studentService = mock(StudentService.class);

        HelloController controller = new HelloController(studentService);

        MockMvc mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();

        mockMvc.perform(post("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "roll": 0,
                                "name": "",
                                "departmentId": 1
                            }
                            """))
                .andExpect(status().isBadRequest());

        verify(studentService, never())
                .saveStudent(any(StudentRequestDTO.class));
    }

}