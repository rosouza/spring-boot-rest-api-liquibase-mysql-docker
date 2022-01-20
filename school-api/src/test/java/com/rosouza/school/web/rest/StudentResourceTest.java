package com.rosouza.school.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rosouza.school.exception.StudentNotExistException;
import com.rosouza.school.service.RegistrationService;
import com.rosouza.school.service.StudentService;
import com.rosouza.school.service.dto.StudentDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.ZonedDateTime;

import static com.rosouza.school.StudentTestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentResource.class)
public class StudentResourceTest {


    @MockBean
    StudentService studentService;

    @MockBean
    RegistrationService registrationService;

    @Autowired
    MockMvc mockMvc;

    StudentDTO studentDTO;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void before() {
        studentDTO = studentDTO(false);
        given(studentService.getStudent(eq(ENTITY_ID))).willReturn(studentDTO);
    }

    @Test
    void studentCanBeReadIfStudentIdCanBeFound() throws Exception {
        MvcResult result = mockMvc.perform(get(GET_BY_ID, ENTITY_ID)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

        String actualResponseBody = result.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper.writeValueAsString(studentDTO);
        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(expectedResponseBody);
    }

    @Test
    void studentCanNotBeReadIfStudentIdCanNotBeFound() throws Exception {
        given(studentService.getStudent(anyLong())).willThrow(StudentNotExistException.class);

        mockMvc.perform(get(GET_BY_ID, ENTITY_NOT_FOUND_ID)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    void studentCanNotBeCreatedIfStudentIdAlreadyExists() throws Exception {
        //Request
        StudentDTO studentRequestDTO = studentDTO(false);

        mockMvc.perform(post(CREATE_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(studentRequestDTO))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());

    }

    @Test
    void studentCanBeCreatedWithCompleteRequest() throws Exception {
        //Request
        StudentDTO studentRequestDTO = studentDTO(true);

        //Expected Response
        StudentDTO studentResponseDTO = studentDTO(false);
        given(studentService.createStudent(any())).willReturn(studentResponseDTO);

        MvcResult result = mockMvc.perform(post(CREATE_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(studentRequestDTO))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andReturn();

        String actualResponseBody = result.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper.writeValueAsString(studentResponseDTO);
        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(expectedResponseBody);
    }

    @Test
    void studentCanNotBeUpdatedWithBadRequest() throws Exception {
        //Request
        StudentDTO studentRequestDTO = studentDTO(false);

        //Expected Response
        given(studentService.updateStudent(any(), any())).willThrow(new StudentNotExistException());

        mockMvc.perform(put(UPDATE_URI, ENTITY_ID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(studentRequestDTO))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect((status().isNotFound()));
    }

    @Test
    void studentCanNotBeUpdatedWithIfStudentIdCanNotBeFound() throws Exception {
        //Request
        StudentDTO studentRequestDTO = studentDTO(false);

        //Expected Response
        given(studentService.updateStudent(any(), any())).willThrow(new StudentNotExistException());

        mockMvc.perform(put(UPDATE_URI, ENTITY_ID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(studentRequestDTO))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    void studentCanBeUpdatedIfStudentIdCanBeFound() throws Exception {
        //Request
        StudentDTO studentRequestDTO = studentDTO(false);

        //Expected Response
        StudentDTO studentResponseDTO = studentDTO(false);
        studentRequestDTO.setId(ENTITY_ID);
        given(studentService.updateStudent(any(), any())).willReturn(studentResponseDTO);

        MvcResult result = mockMvc.perform(put(UPDATE_URI, ENTITY_ID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(studentRequestDTO))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

        String actualResponseBody = result.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper.writeValueAsString(studentResponseDTO);
        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(expectedResponseBody);
    }

    @Test
    void studentCanBeUpdatedWithStudentUpdatePermission() throws Exception {
        //Request
        StudentDTO studentRequestDTO = studentDTO(false);

        //Expected Response
        StudentDTO studentResponseDTO = studentDTO(false);
        studentRequestDTO.setId(ENTITY_ID);
        given(studentService.updateStudent(eq(ENTITY_ID), any())).willReturn(studentResponseDTO);

        MvcResult result = mockMvc.perform(put(UPDATE_URI, ENTITY_ID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(studentRequestDTO))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

        String actualResponseBody = result.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper.writeValueAsString(studentResponseDTO);
        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(expectedResponseBody);
    }

    @Test
    void studentCanBeCreatedWithStudentCreatePermission() throws Exception {
        //Request
        StudentDTO studentRequestDTO = studentDTO(true);

        //Expected Response
        StudentDTO studentResponseDTO = studentDTO(false);
        given(studentService.createStudent(any())).willReturn(studentResponseDTO);

        MvcResult result = mockMvc.perform(post(CREATE_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(studentRequestDTO))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andReturn();

        String actualResponseBody = result.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper.writeValueAsString(studentResponseDTO);
        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(expectedResponseBody);
    }

    private StudentDTO studentDTO(Boolean isCreate) {
        var studentDTO = new StudentDTO();

        if (!isCreate){
            studentDTO.setId(ENTITY_ID);
        }

        studentDTO.setName(NAME);
        return studentDTO;
    }
}
