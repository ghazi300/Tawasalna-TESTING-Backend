package com.tawasalna.business;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = TawasalnaBusinessApplication.class)
@AutoConfigureMockMvc
@Slf4j
class MainServiceImplTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    /*
    @Test
    void testRequestBusinessVerification() {

        try {
            MockMultipartFile file
                    = new MockMultipartFile(
                    "document",
                    "hello.txt",
                    MediaType.TEXT_PLAIN_VALUE,
                    "Hello, World!".getBytes()
            );

            MockMvc mockMvc
                    = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
            mockMvc.perform(multipart("/main/send-verif-req/123").file(file))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            log.trace("oopsie", e);
        }
    }

     */
}
