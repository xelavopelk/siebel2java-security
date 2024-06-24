package ru.klepov.sibjava;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.klepov.security.SecurityConfiguration;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(SecurityConfiguration.class)
public class ViewControllerTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }
    @Test
    public void happyPathSuccess() throws Exception {
        mockMvc.perform(get("/index")
                .with(user("user")
                        .password("password")))
                .andExpect(status()
                        .isOk())
                .andExpect(view().name("index"));
    }
    @WithMockUser(roles = "USER")
    @Test
    public void whoFail() throws Exception {
        mockMvc.perform(get("/hello/who").with(user("u1").password("p"))).andExpect(status().is4xxClientError());
    }
    @WithMockUser(roles = "ADMIN")
    @Test
    public void whoSuccess() throws Exception {
        mockMvc.perform(get("/who").with(user("u1").password("p"))).andExpect(status().is4xxClientError());
    }
}
