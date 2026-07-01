package com.likelion.pbl;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class AssignmentApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void assignment_crud_when_member_exists() throws Exception {
        // Given: a persisted lion member
        String lionResponse = mockMvc.perform(post("/members/lions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "이영희",
                                  "major": "컴퓨터공학과",
                                  "part": "백엔드",
                                  "generation": 13,
                                  "studentId": "20230002"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("이영희"))
                .andReturn()
                .getResponse()
                .getContentAsString();
        Long memberId = extractId(lionResponse);

        // When: creating an assignment for that member
        String assignmentResponse = mockMvc.perform(post("/members/{memberId}/assignments", memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "title": "1주차 과제",
                                  "description": "Java 기초 문법 정리"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("1주차 과제"))
                .andExpect(jsonPath("$.description").value("Java 기초 문법 정리"))
                .andExpect(jsonPath("$.memberId").value(memberId))
                .andExpect(jsonPath("$.memberName").value("이영희"))
                .andReturn()
                .getResponse()
                .getContentAsString();
        Long assignmentId = extractId(assignmentResponse);

        // Then: member assignment list and single lookup expose the same assignment
        mockMvc.perform(get("/members/{memberId}/assignments", memberId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(assignmentId));

        mockMvc.perform(get("/assignments/{id}", assignmentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(assignmentId))
                .andExpect(jsonPath("$.memberName").value("이영희"));

        mockMvc.perform(put("/assignments/{id}", assignmentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "title": "1주차 과제 (수정)",
                                  "description": "Java 기초 문법 정리 + 예외 처리"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("1주차 과제 (수정)"))
                .andExpect(jsonPath("$.description").value("Java 기초 문법 정리 + 예외 처리"));

        mockMvc.perform(delete("/assignments/{id}", assignmentId))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/assignments/{id}", assignmentId))
                .andExpect(status().isNotFound());
    }

    @Test
    void create_assignment_returns_not_found_when_member_is_missing() throws Exception {
        // Given: no member with id 999
        // When: creating an assignment for a missing member
        // Then: the controller follows the null-return plus 404 pattern
        mockMvc.perform(post("/members/999/assignments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "title": "1주차 과제",
                                  "description": "Java 기초 문법 정리"
                                }
                                """))
                .andExpect(status().isNotFound());
    }

    private static Long extractId(String json) {
        int keyIndex = json.indexOf("\"id\":");
        int valueStart = keyIndex + "\"id\":".length();
        int valueEnd = json.indexOf(",", valueStart);
        if (valueEnd == -1) {
            valueEnd = json.indexOf("}", valueStart);
        }
        return Long.valueOf(json.substring(valueStart, valueEnd).trim());
    }
}
