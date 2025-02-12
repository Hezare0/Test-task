package com.effectiveMobile.testTask;

import com.effectiveMobile.testTask.DTO.taskDTO.crudAction.GetListOfTasksResponse;
import com.effectiveMobile.testTask.services.servicesAssociatedTask.CrudTaskService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class JwtTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CrudTaskService taskService;

    @Test
    @WithMockUser(username = "TestUser@gmail.com", roles = {"PERFORMER"})
    public void testAccessWithValidJwt() throws Exception {
        String token = generateJwtToken("TestUser@gmail.com");

        when(taskService.getAllTask(eq("TestUser@gmail.com"),eq(0),eq(20))).thenReturn(new GetListOfTasksResponse());

        mockMvc.perform(MockMvcRequestBuilders.get("/get-task")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    public void testAccessWithoutJwt() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/get-task"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testAccessWithInvalidJwt() throws Exception {
        String invalidToken = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUEVSRk9STUVSIiwic3ViIjoidGVzdFVzZXJAZ21haWwuY29tIiwiaWF0IjoxNzM5MTAxODMxLCJleHAiOjE3MzkyNDU4MzF9.OEY8gbtwxkv1-veNi1SRn1DlRgRBNZnBClCvUxDxl_g";
        mockMvc.perform(MockMvcRequestBuilders.get("/get-task")
                        .header("Authorization", "Bearer " + invalidToken))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testAccessWithExpiredJwt() throws Exception {
        String expiredToken = generateExpiredJwtToken("user");

        mockMvc.perform(MockMvcRequestBuilders.get("/api/protected")
                        .header("Authorization", "Bearer " + expiredToken))
                .andExpect(status().isUnauthorized());
    }

    private String generateJwtToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(SignatureAlgorithm.HS256, "9a4f2c8d3b7a1e6f45c8a0b3f267d8b1d4e6f3c8a9d2b5f8e3a9c8b5f6v8a3d9")
                .compact();
    }

    private String generateExpiredJwtToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 10)) // 10 hours ago
                .setExpiration(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 5)) // 5 hours ago
                .signWith(SignatureAlgorithm.HS256, "9a4f2c8d3b7a1e6f45c8a0b3f267d8b1d4e6f3c8a9d2b5f8e3a9c8b5f6v8a3d9")
                .compact();
    }
}
