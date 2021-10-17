package com.sda.weatherlady.controller;

import com.sda.weatherlady.TestHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ApiControllerTest {

    private static ClientAndServer mockServer;

    @Value("${app.service.openweather.apikey}")
    private String openWeatherKey;

    @Autowired
    private MockMvc mockMvc;


    @BeforeAll
    static void setUp() {
        mockServer = ClientAndServer.startClientAndServer(8070);
    }

    @Test
    @WithMockUser("user2")
    void testGetWeatherFromOpenweather() throws Exception {
        // mock Openweather
        new MockServerClient("127.0.0.1", 8070)
                .when(
                        HttpRequest.request()
                                .withMethod("GET")
                                .withPath("/data/2.5/weather")
                                .withQueryStringParameter("appid", openWeatherKey)
                                .withQueryStringParameter("q", "Prague")
                                .withQueryStringParameter("units", "metric")
                                .withHeader("Accept", "application/json, application/*+json")
                )
                .respond(
                        HttpResponse.response()
                                .withStatusCode(200)
                                .withHeaders(
                                        new Header("Content-Type", "application/json; charset=utf-8")
                                )
                                .withBody(TestHelper.getTestDataFromFile("openweather/currentConditionForPrague.json", ApiControllerTest.class))
                );

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/weather")
                                .queryParam("type", "openweather")
                                .queryParam("city", "Prague")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.header().string("Content-Type", "application/json"))
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();

        JSONAssert.assertEquals(
                TestHelper.getTestDataFromFile("expected/currentConditionForPrague.json", ApiControllerTest.class),
                contentAsString,
                true
        );
    }

    @AfterAll
    static void tearDown() {
        mockServer.stop();
    }

}
