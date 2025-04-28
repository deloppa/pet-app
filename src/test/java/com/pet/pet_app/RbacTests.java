package com.pet.pet_app;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pet.pet_app.model.Pet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(classes = PetApplication.class)
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class RbacTests {

    @Autowired private WebApplicationContext webApplicationContext;

    @Autowired private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void beforeAll() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                           .apply(springSecurity())
                           .build();
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void getPets_WhenAdmin_ThenStatusOk() throws Exception {
        mockMvc.perform(get("/api/v1/pets")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"USER"})
    public void getPets_WhenUser_ThenStatusOk() throws Exception {
        mockMvc.perform(get("/api/v1/pets")).andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void getPets_WhenAnonymous_ThenStatusUnauthorized()
        throws Exception {
        mockMvc.perform(get("/api/v1/pets"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void postPet_WhenAdmin_ThenStatusOk() throws Exception {
        Pet pet = new Pet();
        pet.setId("jack");
        pet.setName("Jack");
        pet.setOwnerName("Melina");

        mockMvc
            .perform(post("/api/v1/pets")
                         .contentType(MediaType.APPLICATION_JSON)
                         .content(objectMapper.writeValueAsString(pet)))
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"USER"})
    public void postPet_WhenUser_ThenStatusForbidden() throws Exception {
        Pet pet = new Pet();
        pet.setId("jack");
        pet.setName("Jack");
        pet.setOwnerName("Melina");

        mockMvc
            .perform(post("/api/v1/pets")
                         .contentType(MediaType.APPLICATION_JSON)
                         .content(objectMapper.writeValueAsString(pet)))
            .andExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    public void postPet_WhenAnonymous_ThenStatusUnauthorized()
        throws Exception {
        Pet pet = new Pet();
        pet.setId("jack");
        pet.setName("Jack");
        pet.setOwnerName("Melina");

        mockMvc
            .perform(post("/api/v1/pets")
                         .contentType(MediaType.APPLICATION_JSON)
                         .content(objectMapper.writeValueAsString(pet)))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void putPet_WhenAdmin_ThenStatusOk() throws Exception {
        Pet pet = new Pet();
        pet.setId("jack");
        pet.setName("Jack");
        pet.setOwnerName("Melina");

        mockMvc
            .perform(put("/api/v1/pets/jack")
                         .contentType(MediaType.APPLICATION_JSON)
                         .content(objectMapper.writeValueAsString(pet)))
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"USER"})
    public void putPet_WhenUser_ThenStatusForbidden() throws Exception {
        Pet pet = new Pet();
        pet.setId("jack");
        pet.setName("Jack");
        pet.setOwnerName("Melina");

        mockMvc
            .perform(put("/api/v1/pets/jack")
                         .contentType(MediaType.APPLICATION_JSON)
                         .content(objectMapper.writeValueAsString(pet)))
            .andExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    public void putPet_WhenAnonymous_ThenStatusUnathorized() throws Exception {
        Pet pet = new Pet();
        pet.setId("jack");
        pet.setName("Jack");
        pet.setOwnerName("Melina");

        mockMvc
            .perform(put("/api/v1/pets/jack")
                         .contentType(MediaType.APPLICATION_JSON)
                         .content(objectMapper.writeValueAsString(pet)))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void deletePet_WhenAdmin_ThenStatusOk() throws Exception {
        mockMvc.perform(delete("/api/v1/pets/jack")).andExpect(status().isOk());
    }
}
