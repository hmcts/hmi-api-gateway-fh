package uk.gov.hmcts.futurehearings.hmi.integration.controllers;

import uk.gov.hmcts.futurehearings.hmi.Application;

import org.junit.Ignore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("integration")
@SpringBootTest(classes = {Application.class})
public class GetWelcomeTest {

    @DisplayName("Should welcome upon root request with 200 response code")
    @Test
    @Ignore
    public void welcomeRootEndpoint() throws Exception {
/*        MvcResult response = mockMvc.perform(get("/")).andExpect(status().isOk()).andReturn();

        assertThat(response.getResponse().getContentAsString()).startsWith("Welcome");*/
    }
}
