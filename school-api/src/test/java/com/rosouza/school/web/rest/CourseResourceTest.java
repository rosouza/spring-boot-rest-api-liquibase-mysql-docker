package com.rosouza.school.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rosouza.school.exception.CourseNotExistException;
import com.rosouza.school.service.CourseService;
import com.rosouza.school.service.dto.CourseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.ZonedDateTime;

import static com.rosouza.school.CourseTestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CourseResource.class)
public class CourseResourceTest {


    @MockBean
    CourseService courseService;

    @Autowired
    MockMvc mockMvc;

    CourseDTO courseDTO;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void before() {
        courseDTO = courseDTO(false);
        given(courseService.getCourse(eq(ENTITY_ID))).willReturn(courseDTO);
    }

    @Test
    void courseCanBeReadIfCourseIdCanBeFound() throws Exception {
        MvcResult result = mockMvc.perform(get(GET_BY_ID, ENTITY_ID)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

        String actualResponseBody = result.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper.writeValueAsString(courseDTO);
        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(expectedResponseBody);
    }

    @Test
    void courseCanNotBeReadIfCourseIdCanNotBeFound() throws Exception {
        given(courseService.getCourse(anyLong())).willThrow(CourseNotExistException.class);

        mockMvc.perform(get(GET_BY_ID, ENTITY_NOT_FOUND_ID)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    void courseCanNotBeCreatedIfCourseIdAlreadyExists() throws Exception {
        //Request
        CourseDTO courseRequestDTO = courseDTO(false);

        mockMvc.perform(post(CREATE_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(courseRequestDTO))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());

    }

    @Test
    void courseCanBeCreatedWithCompleteRequest() throws Exception {
        //Request
        CourseDTO courseRequestDTO = courseDTO(true);

        //Expected Response
        CourseDTO courseResponseDTO = courseDTO(false);
        given(courseService.createCourse(any())).willReturn(courseResponseDTO);

        MvcResult result = mockMvc.perform(post(CREATE_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(courseRequestDTO))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andReturn();

        String actualResponseBody = result.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper.writeValueAsString(courseResponseDTO);
        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(expectedResponseBody);
    }

    @Test
    void courseCanNotBeUpdatedWithBadRequest() throws Exception {
        //Request
        CourseDTO courseRequestDTO = courseDTO(false);

        //Expected Response
        given(courseService.updateCourse(any(), any())).willThrow(new CourseNotExistException());

        mockMvc.perform(put(UPDATE_URI, ENTITY_ID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(courseRequestDTO))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect((status().isNotFound()));
    }

    @Test
    void courseCanNotBeUpdatedWithIfCourseIdCanNotBeFound() throws Exception {
        //Request
        CourseDTO courseRequestDTO = courseDTO(false);

        //Expected Response
        given(courseService.updateCourse(any(), any())).willThrow(new CourseNotExistException());

        mockMvc.perform(put(UPDATE_URI, ENTITY_ID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(courseRequestDTO))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    void courseCanBeUpdatedIfCourseIdCanBeFound() throws Exception {
        //Request
        CourseDTO courseRequestDTO = courseDTO(false);

        //Expected Response
        CourseDTO courseResponseDTO = courseDTO(false);
        courseRequestDTO.setId(ENTITY_ID);
        given(courseService.updateCourse(any(), any())).willReturn(courseResponseDTO);

        MvcResult result = mockMvc.perform(put(UPDATE_URI, ENTITY_ID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(courseRequestDTO))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

        String actualResponseBody = result.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper.writeValueAsString(courseResponseDTO);
        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(expectedResponseBody);
    }

    @Test
    void courseCanBeUpdatedWithCourseUpdatePermission() throws Exception {
        //Request
        CourseDTO courseRequestDTO = courseDTO(false);

        //Expected Response
        CourseDTO courseResponseDTO = courseDTO(false);
        courseRequestDTO.setId(ENTITY_ID);
        given(courseService.updateCourse(eq(ENTITY_ID), any())).willReturn(courseResponseDTO);

        MvcResult result = mockMvc.perform(put(UPDATE_URI, ENTITY_ID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(courseRequestDTO))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

        String actualResponseBody = result.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper.writeValueAsString(courseResponseDTO);
        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(expectedResponseBody);
    }

    @Test
    void courseCanBeCreatedWithCourseCreatePermission() throws Exception {
        //Request
        CourseDTO courseRequestDTO = courseDTO(true);

        //Expected Response
        CourseDTO courseResponseDTO = courseDTO(false);
        given(courseService.createCourse(any())).willReturn(courseResponseDTO);

        MvcResult result = mockMvc.perform(post(CREATE_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(courseRequestDTO))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andReturn();

        String actualResponseBody = result.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper.writeValueAsString(courseResponseDTO);
        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(expectedResponseBody);
    }

    private CourseDTO courseDTO(Boolean isCreate) {
        var courseDTO = new CourseDTO();

        if (!isCreate){
            courseDTO.setId(ENTITY_ID);
        }

        courseDTO.setName(NAME);
        return courseDTO;
    }
}
