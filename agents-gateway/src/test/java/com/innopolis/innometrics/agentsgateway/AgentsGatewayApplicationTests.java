package com.innopolis.innometrics.agentsgateway;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.innopolis.innometrics.agentsgateway.DTO.AgentResponse;
import com.innopolis.innometrics.agentsgateway.DTO.DataConfigDTO;
import com.innopolis.innometrics.agentsgateway.DTO.DetailsConfigDTO;
import com.innopolis.innometrics.agentsgateway.DTO.MethodConfigDTO;
import com.innopolis.innometrics.agentsgateway.DTO.ResponseConfigDTO;
import com.innopolis.innometrics.agentsgateway.controller.AgentAdminController;
import com.innopolis.innometrics.agentsgateway.service.AgentconfigService;
import com.innopolis.innometrics.agentsgateway.service.AgentconfigdetailsService;
import com.innopolis.innometrics.agentsgateway.service.AgentconfigmethodsService;
import com.innopolis.innometrics.agentsgateway.service.AgentdataconfigService;
import com.innopolis.innometrics.agentsgateway.service.AgentresponseconfigService;

@RunWith(MockitoJUnitRunner.class)
@AutoConfigureMockMvc
@PropertySource("classpath:application-dev.properties")
@SpringBootTest
class AgentsGatewayApplicationTests {

    @Autowired
    private AgentAdminController controller;
    @Autowired
    private AgentconfigService agentconfigService;
    @Autowired
    private AgentdataconfigService agentdataconfigService;
    @Autowired
    private AgentconfigmethodsService agentconfigmethodsService;
    @Autowired
    private AgentconfigdetailsService agentconfigdetailsService;
    @Autowired
    private AgentresponseconfigService agentresponseconfigService;

    @Autowired
    private MockMvc mockMvc;

    /**
     * Utility methods
     */

    private <T> Object convertJSONStringToObject(String json, Class<T> objectClass) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        JavaTimeModule module = new JavaTimeModule();
        mapper.registerModule(module);
        return mapper.readValue(json, objectClass);
    }

    private <T> String getJSON(T object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(object);
    }

    /**
     * Tests
     */

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
        assertThat(agentconfigService).isNotNull();
        assertThat(agentdataconfigService).isNotNull();
        assertThat(agentconfigmethodsService).isNotNull();
        assertThat(agentconfigdetailsService).isNotNull();
        assertThat(agentresponseconfigService).isNotNull();
    }

    /**
     * Table: agentconfig
     */

    private AgentResponse agentResponseResult;

    private AgentResponse createAgentResponse() {
        AgentResponse agentResponse = new AgentResponse();
        agentResponse.setAgentname("test");
        agentResponse.setDescription("test");
        agentResponse.setIsconnected("Y");
        agentResponse.setSourcetype("test");
        agentResponse.setDbschemasource("test");
        return agentResponse;
    }

    @Test
    public void testGetAgentConfiguration() throws Exception {
        mockMvc.perform(get("/AgentAdmin/AgentConfiguration?AgentID=1")
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAgentList() throws Exception {
        mockMvc.perform(get("/AgentAdmin/Agent?ProjectId=1")
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAgentResponse200() throws Exception {
        mockMvc.perform(get("/AgentAdmin/Agent/1")
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.agentid").value("1"))
                .andExpect(jsonPath("$.agentname").value("Trello agent"));
    }

    @Test
    public void testGetAgentResponse404() throws Exception {
        mockMvc.perform(get("/AgentAdmin/Agent/1000000")
                .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testPostAgentResponse201() throws Exception {
        AgentResponse agentResponse = createAgentResponse();
        String requestJson = getJSON(agentResponse);

        mockMvc.perform(post("/AgentAdmin/Agent")
                .contentType(APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.agentname").value(agentResponse.getAgentname()))
                .andDo(mvcResult -> {
                    String json = mvcResult.getResponse().getContentAsString();
                    agentResponseResult = (AgentResponse) this.convertJSONStringToObject(json, AgentResponse.class);
                });

        Assertions.assertEquals(agentResponse.getAgentname(), agentResponseResult.getAgentname());
    }

    @Test
    public void testPostAgentResponse400() throws Exception {
        mockMvc.perform(post("/AgentAdmin/Agent")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPutAgentResponseUpdate() throws Exception {
        AgentResponse agentResponse = createAgentResponse();
        String requestJson = getJSON(agentResponse);

        mockMvc.perform(post("/AgentAdmin/Agent")
                .contentType(APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.agentname").value(agentResponse.getAgentname()))
                .andDo(mvcResult -> {
                    String json = mvcResult.getResponse().getContentAsString();
                    agentResponseResult = (AgentResponse) this.convertJSONStringToObject(json, AgentResponse.class);
                });

        Integer agentId = agentResponseResult.getAgentid();

        agentResponse.setAgentid(agentId);
        agentResponse.setAgentname("test2");
        agentResponse.setDescription("test2");
        agentResponse.setIsconnected("Y");
        agentResponse.setSourcetype("test2");
        agentResponse.setDbschemasource("test2");
        requestJson = getJSON(agentResponse);

        mockMvc.perform(put("/AgentAdmin/Agent/" + agentId)
                .contentType(APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andDo(mvcResult -> {
                    String json = mvcResult.getResponse().getContentAsString();
                    agentResponseResult = (AgentResponse) this.convertJSONStringToObject(json, AgentResponse.class);
                });

        Assertions.assertEquals(agentResponse.getAgentname(), agentResponseResult.getAgentname());
        Assertions.assertEquals(agentResponse.getAgentid(), agentResponseResult.getAgentid());
    }

    @Test
    public void testPutAgentResponseCreate() throws Exception {

        AgentResponse agentResponse = createAgentResponse();
        agentResponse.setAgentid(1000000);

        String requestJson = getJSON(agentResponse);

        mockMvc.perform(put("/AgentAdmin/Agent/1000000")
                .contentType(APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andDo(mvcResult -> {
                    String json = mvcResult.getResponse().getContentAsString();
                    agentResponseResult = (AgentResponse) this.convertJSONStringToObject(json, AgentResponse.class);
                });

        Assertions.assertEquals(agentResponse.getAgentname(), agentResponseResult.getAgentname());
        Assertions.assertNotEquals(agentResponse.getAgentid(), agentResponseResult.getAgentid());
    }

    @Test
    public void testPutAgentResponse400() throws Exception {
        mockMvc.perform(put("/AgentAdmin/Agent/55")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteAgentResponse200() throws Exception {

        AgentResponse agentResponse = createAgentResponse();
        String requestJson = getJSON(agentResponse);

        mockMvc.perform(post("/AgentAdmin/Agent")
                .contentType(APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andDo(mvcResult -> {
                    String json = mvcResult.getResponse().getContentAsString();
                    agentResponseResult = (AgentResponse) this.convertJSONStringToObject(json, AgentResponse.class);
                });

        Integer agentId = agentResponseResult.getAgentid();

        mockMvc.perform(delete("/AgentAdmin/Agent/" + agentId)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(mvcResult -> {
                    String json = mvcResult.getResponse().getContentAsString();
                    agentResponseResult = (AgentResponse) this.convertJSONStringToObject(json, AgentResponse.class);
                });

        Assertions.assertEquals(agentResponse.getAgentname(), agentResponseResult.getAgentname());
        Assertions.assertEquals(agentId, agentResponseResult.getAgentid());

        mockMvc.perform(delete("/AgentAdmin/Agent/" + agentId)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteAgentResponse404() throws Exception {
        mockMvc.perform(delete("/AgentAdmin/Agent/1000000")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCRUDAgentResponse() throws Exception {

        AgentResponse agentResponse = createAgentResponse();
        String requestJson = getJSON(agentResponse);

        mockMvc.perform(post("/AgentAdmin/Agent")
                .contentType(APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.agentname").value(agentResponse.getAgentname()))
                .andDo(mvcResult -> {
                    String json = mvcResult.getResponse().getContentAsString();
                    agentResponseResult = (AgentResponse) this.convertJSONStringToObject(json, AgentResponse.class);
                });

        Assertions.assertEquals(agentResponse.getAgentname(), agentResponseResult.getAgentname());

        Integer agentId = agentResponseResult.getAgentid();

        agentResponse.setAgentid(agentId);
        agentResponse.setAgentname("test2");
        agentResponse.setDescription("test2");
        agentResponse.setIsconnected("Y");
        agentResponse.setSourcetype("test2");
        agentResponse.setDbschemasource("test2");
        requestJson = getJSON(agentResponse);

        mockMvc.perform(put("/AgentAdmin/Agent/" + agentId)
                .contentType(APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andDo(mvcResult -> {
                    String json = mvcResult.getResponse().getContentAsString();
                    agentResponseResult = (AgentResponse) this.convertJSONStringToObject(json, AgentResponse.class);
                });

        Assertions.assertEquals(agentResponse.getAgentname(), agentResponseResult.getAgentname());
        Assertions.assertEquals(agentResponse.getAgentid(), agentResponseResult.getAgentid());

        mockMvc.perform(delete("/AgentAdmin/Agent/" + agentId)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(mvcResult -> {
                    String json = mvcResult.getResponse().getContentAsString();
                    agentResponseResult = (AgentResponse) this.convertJSONStringToObject(json, AgentResponse.class);
                });

        Assertions.assertEquals(agentResponse.getAgentname(), agentResponseResult.getAgentname());
        Assertions.assertEquals(agentId, agentResponseResult.getAgentid());

        mockMvc.perform(delete("/AgentAdmin/Agent/" + agentId)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetCreateSeveralAgentResponses() throws Exception {
        AgentResponse agentResponse = createAgentResponse();
        String requestJson = getJSON(agentResponse);

        String jsonString = mockMvc.perform(get("/AgentAdmin/Agent?ProjectId=1")
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        int initialSize = StringUtils.countMatches(jsonString, "agentid");

        int count = 5;
        for (int i = 0; i < count; i++) {
            mockMvc.perform(post("/AgentAdmin/Agent")
                    .contentType(APPLICATION_JSON)
                    .content(requestJson))
                    .andExpect(status().isCreated());
        }

        jsonString = mockMvc.perform(get("/AgentAdmin/Agent?ProjectId=1")
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        int finalSize = StringUtils.countMatches(jsonString, "agentid");

        Assertions.assertEquals(count, finalSize - initialSize);
    }

    /**
     * Table: agent_data_config
     */

    private DataConfigDTO dataConfigDTOResult;

    private DataConfigDTO createDataConfigDTO() {
        DataConfigDTO dataConfigDTO = new DataConfigDTO();
        dataConfigDTO.setAgentid(1);
        dataConfigDTO.setSchemaname("test");
        dataConfigDTO.setTablename("test");
        dataConfigDTO.setEventdatefield("test");
        dataConfigDTO.setEventauthorfield("test");
        dataConfigDTO.setIsactive("Y");
        return dataConfigDTO;
    }

    @Test
    public void testGetDataList() throws Exception {
        mockMvc.perform(get("/AgentAdmin/AgentData")
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetDataById200() throws Exception {
        mockMvc.perform(get("/AgentAdmin/AgentData/data/7")
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.datacofingid").value("7"))
                .andExpect(jsonPath("$.schemaname").value("test"));
    }

    @Test
    public void testGetDataById404() throws Exception {
        mockMvc.perform(get("/AgentAdmin/AgentData/data/1000000")
                .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetDataByAgentId200() throws Exception {
        mockMvc.perform(get("/AgentAdmin/AgentData/agent/1")
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetDataByAgentId404() throws Exception {
        mockMvc.perform(get("/AgentAdmin/AgentData/agent/1000000")
                .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testPostData201() throws Exception {
        DataConfigDTO dataConfigDTO = createDataConfigDTO();
        String requestJson = getJSON(dataConfigDTO);

        mockMvc.perform(post("/AgentAdmin/AgentData")
                .contentType(APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.schemaname").value(dataConfigDTO.getSchemaname()))
                .andDo(mvcResult -> {
                    String json = mvcResult.getResponse().getContentAsString();
                    dataConfigDTOResult = (DataConfigDTO) this.convertJSONStringToObject(json, DataConfigDTO.class);
                });

        Assertions.assertEquals(dataConfigDTO.getSchemaname(), dataConfigDTOResult.getSchemaname());
    }

    @Test
    public void testPostDataEmptyBody400() throws Exception {
        mockMvc.perform(post("/AgentAdmin/AgentData")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPostDataWrongAgentId400() throws Exception {
        DataConfigDTO dataConfigDTO = createDataConfigDTO();
        dataConfigDTO.setAgentid(100000);
        String requestJson = getJSON(dataConfigDTO);

        mockMvc.perform(post("/AgentAdmin/AgentData")
                .contentType(APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPutDataUpdate() throws Exception {
        DataConfigDTO dataConfigDTO = createDataConfigDTO();
        String requestJson = getJSON(dataConfigDTO);

        mockMvc.perform(post("/AgentAdmin/AgentData")
                .contentType(APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.schemaname").value(dataConfigDTO.getSchemaname()))
                .andDo(mvcResult -> {
                    String json = mvcResult.getResponse().getContentAsString();
                    dataConfigDTOResult = (DataConfigDTO) this.convertJSONStringToObject(json, DataConfigDTO.class);
                });

        Integer dataId = dataConfigDTOResult.getDatacofingid();

        dataConfigDTO.setDatacofingid(dataId);
        dataConfigDTO.setAgentid(1);
        dataConfigDTO.setSchemaname("test2");
        dataConfigDTO.setTablename("test2");
        dataConfigDTO.setEventdatefield("test2");
        dataConfigDTO.setEventauthorfield("test2");
        dataConfigDTO.setIsactive("Y");
        requestJson = getJSON(dataConfigDTO);

        mockMvc.perform(put("/AgentAdmin/AgentData/" + dataId)
                .contentType(APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andDo(mvcResult -> {
                    String json = mvcResult.getResponse().getContentAsString();
                    dataConfigDTOResult = (DataConfigDTO) this.convertJSONStringToObject(json, DataConfigDTO.class);
                });

        Assertions.assertEquals(dataConfigDTO.getSchemaname(), dataConfigDTOResult.getSchemaname());
        Assertions.assertEquals(dataConfigDTO.getDatacofingid(), dataConfigDTOResult.getDatacofingid());
        Assertions.assertEquals(dataConfigDTO.getAgentid(), dataConfigDTOResult.getAgentid());
    }

    @Test
    public void testPutDataCreate() throws Exception {

        DataConfigDTO dataConfigDTO = createDataConfigDTO();
        dataConfigDTO.setDatacofingid(100000);
        String requestJson = getJSON(dataConfigDTO);

        mockMvc.perform(put("/AgentAdmin/AgentData/100000")
                .contentType(APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andDo(mvcResult -> {
                    String json = mvcResult.getResponse().getContentAsString();
                    dataConfigDTOResult = (DataConfigDTO) this.convertJSONStringToObject(json, DataConfigDTO.class);
                });

        Assertions.assertEquals(dataConfigDTO.getSchemaname(), dataConfigDTOResult.getSchemaname());
        Assertions.assertNotEquals(dataConfigDTO.getDatacofingid(), dataConfigDTOResult.getDatacofingid());
    }

    @Test
    public void testPutDataEmptyBody400() throws Exception {
        mockMvc.perform(put("/AgentAdmin/AgentData/7")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPutDataWrongAgentId400() throws Exception {
        DataConfigDTO dataConfigDTO = createDataConfigDTO();
        dataConfigDTO.setAgentid(100000);
        String requestJson = getJSON(dataConfigDTO);

        mockMvc.perform(put("/AgentAdmin/AgentData/7")
                .contentType(APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteData200() throws Exception {

        DataConfigDTO dataConfigDTO = createDataConfigDTO();
        String requestJson = getJSON(dataConfigDTO);

        mockMvc.perform(post("/AgentAdmin/AgentData")
                .contentType(APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andDo(mvcResult -> {
                    String json = mvcResult.getResponse().getContentAsString();
                    dataConfigDTOResult = (DataConfigDTO) this.convertJSONStringToObject(json, DataConfigDTO.class);
                });

        Integer dataId = dataConfigDTOResult.getDatacofingid();

        mockMvc.perform(delete("/AgentAdmin/AgentData/data/" + dataId)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(mvcResult -> {
                    String json = mvcResult.getResponse().getContentAsString();
                    dataConfigDTOResult = (DataConfigDTO) this.convertJSONStringToObject(json, DataConfigDTO.class);
                });

        Assertions.assertEquals(dataConfigDTO.getSchemaname(), dataConfigDTOResult.getSchemaname());
        Assertions.assertEquals(dataId, dataConfigDTOResult.getDatacofingid());

        mockMvc.perform(delete("/AgentAdmin/AgentData/data/" + dataId)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteData404() throws Exception {
        mockMvc.perform(delete("/AgentAdmin/AgentData/data/1000000")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCRUDData() throws Exception {

        DataConfigDTO dataConfigDTO = createDataConfigDTO();
        String requestJson = getJSON(dataConfigDTO);

        mockMvc.perform(post("/AgentAdmin/AgentData")
                .contentType(APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.schemaname").value(dataConfigDTO.getSchemaname()))
                .andDo(mvcResult -> {
                    String json = mvcResult.getResponse().getContentAsString();
                    dataConfigDTOResult = (DataConfigDTO) this.convertJSONStringToObject(json, DataConfigDTO.class);
                });

        Assertions.assertEquals(dataConfigDTO.getSchemaname(), dataConfigDTOResult.getSchemaname());

        Integer dataId = dataConfigDTOResult.getDatacofingid();

        dataConfigDTO.setDatacofingid(dataId);
        dataConfigDTO.setAgentid(1);
        dataConfigDTO.setSchemaname("test2");
        dataConfigDTO.setTablename("test2");
        dataConfigDTO.setEventdatefield("test2");
        dataConfigDTO.setEventauthorfield("test2");
        dataConfigDTO.setIsactive("Y");
        requestJson = getJSON(dataConfigDTO);

        mockMvc.perform(put("/AgentAdmin/AgentData/" + dataId)
                .contentType(APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andDo(mvcResult -> {
                    String json = mvcResult.getResponse().getContentAsString();
                    dataConfigDTOResult = (DataConfigDTO) this.convertJSONStringToObject(json, DataConfigDTO.class);
                });

        Assertions.assertEquals(dataConfigDTO.getSchemaname(), dataConfigDTOResult.getSchemaname());
        Assertions.assertEquals(dataConfigDTO.getDatacofingid(), dataConfigDTOResult.getDatacofingid());
        Assertions.assertEquals(dataConfigDTO.getAgentid(), dataConfigDTOResult.getAgentid());

        mockMvc.perform(delete("/AgentAdmin/AgentData/data/" + dataId)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(mvcResult -> {
                    String json = mvcResult.getResponse().getContentAsString();
                    dataConfigDTOResult = (DataConfigDTO) this.convertJSONStringToObject(json, DataConfigDTO.class);
                });

        Assertions.assertEquals(dataConfigDTO.getSchemaname(), dataConfigDTOResult.getSchemaname());
        Assertions.assertEquals(dataId, dataConfigDTOResult.getDatacofingid());

        mockMvc.perform(delete("/AgentAdmin/AgentData/data/" + dataId)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    // @Test
    // public void testGetCreateSeveralData() throws Exception {
    // DataConfigDTO dataConfigDTO = createDataConfigDTO();
    // String requestJson = getJSON(dataConfigDTO);

    // String jsonString = mockMvc.perform(get("/AgentAdmin/AgentData")
    // .accept(APPLICATION_JSON))
    // .andExpect(status().isOk())
    // .andReturn()
    // .getResponse()
    // .getContentAsString();

    // int initialSize = StringUtils.countMatches(jsonString, "datacofingid");

    // int count = 5;
    // for (int i = 0; i < count; i++) {
    // mockMvc.perform(post("/AgentAdmin/AgentData")
    // .contentType(APPLICATION_JSON)
    // .content(requestJson))
    // .andExpect(status().isCreated());
    // }

    // jsonString = mockMvc.perform(get("/AgentAdmin/AgentData")
    // .accept(APPLICATION_JSON))
    // .andExpect(status().isOk())
    // .andReturn()
    // .getResponse()
    // .getContentAsString();

    // int finalSize = StringUtils.countMatches(jsonString, "datacofingid");

    // Assertions.assertEquals(count, finalSize - initialSize);
    // }

    /**
     * Table: agentconfigmethods
     */

    private MethodConfigDTO methodConfigDTOResult;

    private MethodConfigDTO createMethodConfigDTO() {
        MethodConfigDTO methodConfigDTO = new MethodConfigDTO();
        methodConfigDTO.setAgentid(34);
        methodConfigDTO.setDescription("test");
        methodConfigDTO.setEndpoint("test");
        methodConfigDTO.setIsactive("Y");
        methodConfigDTO.setOperation("test");
        methodConfigDTO.setRequesttype("POST");
        return methodConfigDTO;
    }

    @Test
    public void testGetMethodsList() throws Exception {
        mockMvc.perform(get("/AgentAdmin/AgentConfigMethods")
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetMethodsById200() throws Exception {
        mockMvc.perform(get("/AgentAdmin/AgentConfigMethods/method/1")
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.methodid").value("1"))
                .andExpect(jsonPath("$.endpoint").value("http://InnoAgents:9098/api/keytoken"));
    }

    @Test
    public void testGetMethodsById404() throws Exception {
        mockMvc.perform(get("/AgentAdmin/AgentConfigMethods/method/100000")
                .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetMethodsByAgentId200() throws Exception {
        mockMvc.perform(get("/AgentAdmin/AgentConfigMethods/agent/1")
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetMethodsByAgentId404() throws Exception {
        mockMvc.perform(get("/AgentAdmin/AgentConfigMethods/agent/100000")
                .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetMethodsByOperation() throws Exception {
        mockMvc.perform(get("/AgentAdmin/AgentConfigMethodsOperation/1?operation=ProjectList")
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.endpoint").value("http://InnoAgents:9098/api/keytoken"));
    }

    // @Test
    // public void testPostMethods201() throws Exception {
    // MethodConfigDTO methodConfigDTO = createMethodConfigDTO();
    // String requestJson = getJSON(methodConfigDTO);

    // mockMvc.perform(post("/AgentAdmin/AgentConfigMethods")
    // .contentType(APPLICATION_JSON)
    // .content(requestJson))
    // .andExpect(status().isCreated())
    // .andExpect(jsonPath("$.endpoint").value(methodConfigDTO.getEndpoint()))
    // .andDo(mvcResult -> {
    // String json = mvcResult.getResponse().getContentAsString();
    // methodConfigDTOResult = (MethodConfigDTO)
    // this.convertJSONStringToObject(json,
    // MethodConfigDTO.class);
    // });

    // Assertions.assertEquals(methodConfigDTO.getEndpoint(),
    // methodConfigDTOResult.getEndpoint());
    // }

    @Test
    public void testPostMethodsEmptyBody400() throws Exception {
        mockMvc.perform(post("/AgentAdmin/AgentConfigMethods")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPostMethodsWrongAgentId400() throws Exception {
        MethodConfigDTO methodConfigDTO = createMethodConfigDTO();
        methodConfigDTO.setAgentid(100000);
        String requestJson = getJSON(methodConfigDTO);

        mockMvc.perform(post("/AgentAdmin/AgentConfigMethods")
                .contentType(APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isBadRequest());
    }

    // @Test
    // public void testPutMethodsUpdate() throws Exception {
    // MethodConfigDTO methodConfigDTO = createMethodConfigDTO();
    // String requestJson = getJSON(methodConfigDTO);

    // mockMvc.perform(post("/AgentAdmin/AgentConfigMethods")
    // .contentType(APPLICATION_JSON)
    // .content(requestJson))
    // .andExpect(status().isCreated())
    // .andExpect(jsonPath("$.endpoint").value(methodConfigDTO.getEndpoint()))
    // .andDo(mvcResult -> {
    // String json = mvcResult.getResponse().getContentAsString();
    // methodConfigDTOResult = (MethodConfigDTO)
    // this.convertJSONStringToObject(json,
    // MethodConfigDTO.class);
    // });

    // Integer methodId = methodConfigDTOResult.getMethodid();

    // methodConfigDTO.setMethodid(methodId);
    // methodConfigDTO.setAgentid(34);
    // methodConfigDTO.setDescription("test2");
    // methodConfigDTO.setEndpoint("test2");
    // methodConfigDTO.setIsactive("Y");
    // methodConfigDTO.setOperation("test2");
    // methodConfigDTO.setRequesttype("POST");
    // requestJson = getJSON(methodConfigDTO);

    // mockMvc.perform(put("/AgentAdmin/AgentConfigMethods/" + methodId)
    // .contentType(APPLICATION_JSON)
    // .content(requestJson))
    // .andExpect(status().isOk())
    // .andDo(mvcResult -> {
    // String json = mvcResult.getResponse().getContentAsString();
    // methodConfigDTOResult = (MethodConfigDTO)
    // this.convertJSONStringToObject(json,
    // MethodConfigDTO.class);
    // });

    // Assertions.assertEquals(methodConfigDTO.getEndpoint(),
    // methodConfigDTOResult.getEndpoint());
    // Assertions.assertEquals(methodConfigDTO.getMethodid(),
    // methodConfigDTOResult.getMethodid());
    // Assertions.assertEquals(methodConfigDTO.getAgentid(),
    // methodConfigDTOResult.getAgentid());
    // }

    // @Test
    // public void testPutMethodsCreate() throws Exception {

    // MethodConfigDTO methodConfigDTO = createMethodConfigDTO();
    // methodConfigDTO.setMethodid(100000);
    // String requestJson = getJSON(methodConfigDTO);

    // mockMvc.perform(put("/AgentAdmin/AgentConfigMethods/100000")
    // .contentType(APPLICATION_JSON)
    // .content(requestJson))
    // .andExpect(status().isCreated())
    // .andDo(mvcResult -> {
    // String json = mvcResult.getResponse().getContentAsString();
    // methodConfigDTOResult = (MethodConfigDTO)
    // this.convertJSONStringToObject(json,
    // MethodConfigDTO.class);
    // });

    // Assertions.assertEquals(methodConfigDTO.getEndpoint(),
    // methodConfigDTOResult.getEndpoint());
    // Assertions.assertNotEquals(methodConfigDTO.getMethodid(),
    // methodConfigDTOResult.getMethodid());
    // }

    @Test
    public void testPutMethodsEmptyBody400() throws Exception {
        mockMvc.perform(put("/AgentAdmin/AgentConfigMethods/11")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPutMethodsWrongAgentId400() throws Exception {
        MethodConfigDTO methodConfigDTO = createMethodConfigDTO();
        methodConfigDTO.setAgentid(100000);
        String requestJson = getJSON(methodConfigDTO);

        mockMvc.perform(put("/AgentAdmin/AgentConfigMethods/11")
                .contentType(APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isBadRequest());
    }

    // @Test
    // public void testDeleteMethodsById200() throws Exception {

    // MethodConfigDTO methodConfigDTO = createMethodConfigDTO();
    // String requestJson = getJSON(methodConfigDTO);

    // mockMvc.perform(post("/AgentAdmin/AgentConfigMethods")
    // .contentType(APPLICATION_JSON)
    // .content(requestJson))
    // .andExpect(status().isCreated())
    // .andDo(mvcResult -> {
    // String json = mvcResult.getResponse().getContentAsString();
    // methodConfigDTOResult = (MethodConfigDTO)
    // this.convertJSONStringToObject(json,
    // MethodConfigDTO.class);
    // });

    // Integer methodId = methodConfigDTOResult.getMethodid();

    // mockMvc.perform(delete("/AgentAdmin/AgentConfigMethods/method/" + methodId)
    // .contentType(APPLICATION_JSON))
    // .andExpect(status().isOk())
    // .andExpect(jsonPath("$.methodid").value(methodId))
    // .andExpect(jsonPath("$.endpoint").value(methodConfigDTOResult.getEndpoint()));

    // mockMvc.perform(delete("/AgentAdmin/AgentConfigMethods/method/" + methodId)
    // .contentType(APPLICATION_JSON))
    // .andExpect(status().isNotFound());
    // }

    // @Test
    // public void testDeleteMethodsByAgentId200() throws Exception {

    // int agentId = 34;

    // String jsonString =
    // mockMvc.perform(get("/AgentAdmin/AgentConfigMethods/agent/" + agentId)
    // .accept(APPLICATION_JSON))
    // .andExpect(status().isOk())
    // .andReturn()
    // .getResponse()
    // .getContentAsString();

    // int initialSize = StringUtils.countMatches(jsonString, "methodid");

    // int count = 5;

    // MethodConfigDTO methodConfigDTO = createMethodConfigDTO();
    // String requestJson = getJSON(methodConfigDTO);
    // for (int i = 0; i < count; i++) {
    // mockMvc.perform(post("/AgentAdmin/AgentConfigMethods")
    // .contentType(APPLICATION_JSON)
    // .content(requestJson))
    // .andExpect(status().isCreated());
    // }

    // jsonString = mockMvc.perform(delete("/AgentAdmin/AgentConfigMethods/agent/" +
    // agentId)
    // .contentType(APPLICATION_JSON))
    // .andExpect(status().isOk())
    // .andReturn()
    // .getResponse()
    // .getContentAsString();

    // int finalSize = StringUtils.countMatches(jsonString, "methodid");

    // Assertions.assertEquals(count, finalSize - initialSize);

    // mockMvc.perform(delete("/AgentAdmin/AgentConfigMethods/agent/" + agentId)
    // .contentType(APPLICATION_JSON))
    // .andExpect(status().isNotFound());
    // }

    @Test
    public void testDeleteMethodsById404() throws Exception {
        mockMvc.perform(delete("/AgentAdmin/AgentConfigMethods/method/1000000")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteMethodsAgentById404() throws Exception {
        mockMvc.perform(delete("/AgentAdmin/AgentConfigMethods/agent/1000000")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    // @Test
    // public void testCRUDMethods() throws Exception {

    // MethodConfigDTO methodConfigDTO = createMethodConfigDTO();
    // String requestJson = getJSON(methodConfigDTO);

    // mockMvc.perform(post("/AgentAdmin/AgentConfigMethods")
    // .contentType(APPLICATION_JSON)
    // .content(requestJson))
    // .andExpect(status().isCreated())
    // .andExpect(jsonPath("$.endpoint").value(methodConfigDTO.getEndpoint()))
    // .andDo(mvcResult -> {
    // String json = mvcResult.getResponse().getContentAsString();
    // methodConfigDTOResult = (MethodConfigDTO)
    // this.convertJSONStringToObject(json, MethodConfigDTO.class);
    // });

    // Assertions.assertEquals(methodConfigDTO.getEndpoint(),
    // methodConfigDTOResult.getEndpoint());

    // Integer methodId = methodConfigDTOResult.getMethodid();

    // methodConfigDTO.setMethodid(methodId);
    // methodConfigDTO.setAgentid(34);
    // methodConfigDTO.setDescription("test2");
    // methodConfigDTO.setEndpoint("test2");
    // methodConfigDTO.setIsactive("Y");
    // methodConfigDTO.setOperation("test2");
    // methodConfigDTO.setRequesttype("POST");
    // requestJson = getJSON(methodConfigDTO);

    // mockMvc.perform(put("/AgentAdmin/AgentConfigMethods/" + methodId)
    // .contentType(APPLICATION_JSON)
    // .content(requestJson))
    // .andExpect(status().isOk())
    // .andDo(mvcResult -> {
    // String json = mvcResult.getResponse().getContentAsString();
    // methodConfigDTOResult = (MethodConfigDTO)
    // this.convertJSONStringToObject(json, MethodConfigDTO.class);
    // });

    // Assertions.assertEquals(methodConfigDTO.getEndpoint(),
    // methodConfigDTOResult.getEndpoint());
    // Assertions.assertEquals(methodConfigDTO.getMethodid(),
    // methodConfigDTOResult.getMethodid());
    // Assertions.assertEquals(methodConfigDTO.getAgentid(),
    // methodConfigDTOResult.getAgentid());

    // mockMvc.perform(delete("/AgentAdmin/AgentConfigMethods/method/" + methodId)
    // .contentType(APPLICATION_JSON))
    // .andExpect(status().isOk())
    // .andExpect(jsonPath("$.methodid").value(methodId))
    // .andExpect(jsonPath("$.endpoint").value(methodConfigDTOResult.getEndpoint()));

    // mockMvc.perform(delete("/AgentAdmin/AgentConfigMethods/method/" + methodId)
    // .contentType(APPLICATION_JSON))
    // .andExpect(status().isNotFound());
    // }

    // @Test
    // public void testGetCreateSeveralMethods() throws Exception {
    // MethodConfigDTO methodConfigDTO = createMethodConfigDTO();
    // String requestJson = getJSON(methodConfigDTO);

    // String jsonString = mockMvc.perform(get("/AgentAdmin/AgentConfigMethods")
    // .accept(APPLICATION_JSON))
    // .andExpect(status().isOk())
    // .andReturn()
    // .getResponse()
    // .getContentAsString();

    // int initialSize = StringUtils.countMatches(jsonString, "methodid");

    // int count = 5;
    // for (int i = 0; i < count; i++) {
    // mockMvc.perform(post("/AgentAdmin/AgentConfigMethods")
    // .contentType(APPLICATION_JSON)
    // .content(requestJson))
    // .andExpect(status().isCreated());
    // }

    // jsonString = mockMvc.perform(get("/AgentAdmin/AgentConfigMethods")
    // .accept(APPLICATION_JSON))
    // .andExpect(status().isOk())
    // .andReturn()
    // .getResponse()
    // .getContentAsString();

    // int finalSize = StringUtils.countMatches(jsonString, "methodid");

    // Assertions.assertEquals(count, finalSize - initialSize);
    // }

    /**
     * Table: agentconfigdetails
     */

    private DetailsConfigDTO detailsConfigDTOResult;

    private DetailsConfigDTO createDetailsConfigDTO() {
        DetailsConfigDTO detailsConfigDTO = new DetailsConfigDTO();
        detailsConfigDTO.setMethodid(151);
        detailsConfigDTO.setParamname("test");
        detailsConfigDTO.setParamtype("test");
        detailsConfigDTO.setRequestparam("test");
        detailsConfigDTO.setRequesttype("test");
        detailsConfigDTO.setIsactive("Y");
        return detailsConfigDTO;
    }

    @Test
    public void testGetDetailsList() throws Exception {
        mockMvc.perform(get("/AgentAdmin/AgentDetails")
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetDetailsById200() throws Exception {
        mockMvc.perform(get("/AgentAdmin/AgentDetails/details/1")
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.configDetId").value("1"))
                .andExpect(jsonPath("$.paramname").value("key"));
    }

    @Test
    public void testGetDetailsById404() throws Exception {
        mockMvc.perform(get("/AgentAdmin/AgentDetails/details/1000000")
                .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetDetailsByMethodId200() throws Exception {
        mockMvc.perform(get("/AgentAdmin/AgentDetails/method/1")
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetDetailsByMethodId404() throws Exception {
        mockMvc.perform(get("/AgentAdmin/AgentDetails/method/1000000")
                .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    // @Test
    // public void testPostDetails201() throws Exception {
    // DetailsConfigDTO detailsConfigDTO = createDetailsConfigDTO();
    // String requestJson = getJSON(detailsConfigDTO);

    // mockMvc.perform(post("/AgentAdmin/AgentDetails")
    // .contentType(APPLICATION_JSON)
    // .content(requestJson))
    // .andExpect(status().isCreated())
    // .andExpect(jsonPath("$.paramname").value(detailsConfigDTO.getParamname()))
    // .andDo(mvcResult -> {
    // String json = mvcResult.getResponse().getContentAsString();
    // detailsConfigDTOResult = (DetailsConfigDTO)
    // this.convertJSONStringToObject(json,
    // DetailsConfigDTO.class);
    // });

    // Assertions.assertEquals(detailsConfigDTO.getParamname(),
    // detailsConfigDTOResult.getParamname());
    // }

    @Test
    public void testPostDetailsEmptyBody400() throws Exception {
        mockMvc.perform(post("/AgentAdmin/AgentDetails")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPostDetailsWrongMethodId400() throws Exception {
        DetailsConfigDTO detailsConfigDTO = createDetailsConfigDTO();
        detailsConfigDTO.setMethodid(100000);
        String requestJson = getJSON(detailsConfigDTO);

        mockMvc.perform(post("/AgentAdmin/AgentDetails")
                .contentType(APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isBadRequest());
    }

    // @Test
    // public void testPutDetailsUpdate() throws Exception {
    // DetailsConfigDTO detailsConfigDTO = createDetailsConfigDTO();
    // String requestJson = getJSON(detailsConfigDTO);

    // mockMvc.perform(post("/AgentAdmin/AgentDetails")
    // .contentType(APPLICATION_JSON)
    // .content(requestJson))
    // .andExpect(status().isCreated())
    // .andExpect(jsonPath("$.paramname").value(detailsConfigDTO.getParamname()))
    // .andDo(mvcResult -> {
    // String json = mvcResult.getResponse().getContentAsString();
    // detailsConfigDTOResult = (DetailsConfigDTO)
    // this.convertJSONStringToObject(json,
    // DetailsConfigDTO.class);
    // });

    // Integer detailsId = detailsConfigDTOResult.getConfigDetId();

    // detailsConfigDTO.setConfigDetId(detailsId);
    // detailsConfigDTO.setMethodid(167);
    // detailsConfigDTO.setParamname("test2");
    // detailsConfigDTO.setParamtype("test2");
    // detailsConfigDTO.setRequestparam("test2");
    // detailsConfigDTO.setRequesttype("test2");
    // detailsConfigDTO.setIsactive("Y");
    // requestJson = getJSON(detailsConfigDTO);

    // mockMvc.perform(put("/AgentAdmin/AgentDetails/" + detailsId)
    // .contentType(APPLICATION_JSON)
    // .content(requestJson))
    // .andExpect(status().isOk())
    // .andDo(mvcResult -> {
    // String json = mvcResult.getResponse().getContentAsString();
    // detailsConfigDTOResult = (DetailsConfigDTO)
    // this.convertJSONStringToObject(json,
    // DetailsConfigDTO.class);
    // });

    // Assertions.assertEquals(detailsConfigDTO.getParamname(),
    // detailsConfigDTOResult.getParamname());
    // Assertions.assertEquals(detailsConfigDTO.getConfigDetId(),
    // detailsConfigDTOResult.getConfigDetId());
    // Assertions.assertEquals(detailsConfigDTO.getMethodid(),
    // detailsConfigDTOResult.getMethodid());
    // }

    // @Test
    // public void testPutDetailsCreate() throws Exception {

    // DetailsConfigDTO detailsConfigDTO = createDetailsConfigDTO();
    // detailsConfigDTO.setConfigDetId(100000);
    // String requestJson = getJSON(detailsConfigDTO);

    // mockMvc.perform(put("/AgentAdmin/AgentDetails/100000")
    // .contentType(APPLICATION_JSON)
    // .content(requestJson))
    // .andExpect(status().isCreated())
    // .andDo(mvcResult -> {
    // String json = mvcResult.getResponse().getContentAsString();
    // detailsConfigDTOResult = (DetailsConfigDTO)
    // this.convertJSONStringToObject(json,
    // DetailsConfigDTO.class);
    // });

    // Assertions.assertEquals(detailsConfigDTO.getParamname(),
    // detailsConfigDTOResult.getParamname());
    // Assertions.assertNotEquals(detailsConfigDTO.getConfigDetId(),
    // detailsConfigDTOResult.getConfigDetId());
    // }

    @Test
    public void testPutDetailsEmptyBody400() throws Exception {
        mockMvc.perform(put("/AgentAdmin/AgentDetails/26")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPutDetailsWrongMethodId400() throws Exception {
        DetailsConfigDTO detailsConfigDTO = createDetailsConfigDTO();
        detailsConfigDTO.setMethodid(100000);
        String requestJson = getJSON(detailsConfigDTO);

        mockMvc.perform(put("/AgentAdmin/AgentDetails/26")
                .contentType(APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isBadRequest());
    }

    // @Test
    // public void testDeleteDetails200() throws Exception {

    // DetailsConfigDTO detailsConfigDTO = createDetailsConfigDTO();
    // String requestJson = getJSON(detailsConfigDTO);

    // mockMvc.perform(post("/AgentAdmin/AgentDetails")
    // .contentType(APPLICATION_JSON)
    // .content(requestJson))
    // .andExpect(status().isCreated())
    // .andDo(mvcResult -> {
    // String json = mvcResult.getResponse().getContentAsString();
    // detailsConfigDTOResult = (DetailsConfigDTO)
    // this.convertJSONStringToObject(json,
    // DetailsConfigDTO.class);
    // });

    // Integer detailsId = detailsConfigDTOResult.getConfigDetId();

    // mockMvc.perform(delete("/AgentAdmin/AgentDetails/details/" + detailsId)
    // .contentType(APPLICATION_JSON))
    // .andExpect(status().isOk())
    // .andDo(mvcResult -> {
    // String json = mvcResult.getResponse().getContentAsString();
    // detailsConfigDTOResult = (DetailsConfigDTO)
    // this.convertJSONStringToObject(json,
    // DetailsConfigDTO.class);
    // });

    // Assertions.assertEquals(detailsConfigDTO.getParamname(),
    // detailsConfigDTOResult.getParamname());
    // Assertions.assertEquals(detailsId, detailsConfigDTOResult.getConfigDetId());

    // mockMvc.perform(delete("/AgentAdmin/AgentDetails/details/" + detailsId)
    // .contentType(APPLICATION_JSON))
    // .andExpect(status().isNotFound());
    // }

    @Test
    public void testDeleteDetails404() throws Exception {
        mockMvc.perform(delete("/AgentAdmin/AgentDetails/details/1000000")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    // @Test
    // public void testCRUDDetails() throws Exception {

    // DetailsConfigDTO detailsConfigDTO = createDetailsConfigDTO();
    // String requestJson = getJSON(detailsConfigDTO);

    // mockMvc.perform(post("/AgentAdmin/AgentDetails")
    // .contentType(APPLICATION_JSON)
    // .content(requestJson))
    // .andExpect(status().isCreated())
    // .andExpect(jsonPath("$.paramname").value(detailsConfigDTO.getParamname()))
    // .andDo(mvcResult -> {
    // String json = mvcResult.getResponse().getContentAsString();
    // detailsConfigDTOResult = (DetailsConfigDTO)
    // this.convertJSONStringToObject(json,
    // DetailsConfigDTO.class);
    // });

    // Integer detailsId = detailsConfigDTOResult.getConfigDetId();

    // detailsConfigDTO.setConfigDetId(detailsId);
    // detailsConfigDTO.setMethodid(167);
    // detailsConfigDTO.setParamname("test2");
    // detailsConfigDTO.setParamtype("test2");
    // detailsConfigDTO.setRequestparam("test2");
    // detailsConfigDTO.setRequesttype("test2");
    // detailsConfigDTO.setIsactive("Y");
    // requestJson = getJSON(detailsConfigDTO);

    // mockMvc.perform(put("/AgentAdmin/AgentDetails/" + detailsId)
    // .contentType(APPLICATION_JSON)
    // .content(requestJson))
    // .andExpect(status().isOk())
    // .andDo(mvcResult -> {
    // String json = mvcResult.getResponse().getContentAsString();
    // detailsConfigDTOResult = (DetailsConfigDTO)
    // this.convertJSONStringToObject(json,
    // DetailsConfigDTO.class);
    // });

    // Assertions.assertEquals(detailsConfigDTO.getParamname(),
    // detailsConfigDTOResult.getParamname());
    // Assertions.assertEquals(detailsConfigDTO.getConfigDetId(),
    // detailsConfigDTOResult.getConfigDetId());
    // Assertions.assertEquals(detailsConfigDTO.getMethodid(),
    // detailsConfigDTOResult.getMethodid());

    // mockMvc.perform(delete("/AgentAdmin/AgentDetails/details/" + detailsId)
    // .contentType(APPLICATION_JSON))
    // .andExpect(status().isOk())
    // .andDo(mvcResult -> {
    // String json = mvcResult.getResponse().getContentAsString();
    // detailsConfigDTOResult = (DetailsConfigDTO)
    // this.convertJSONStringToObject(json,
    // DetailsConfigDTO.class);
    // });

    // Assertions.assertEquals(detailsConfigDTO.getParamname(),
    // detailsConfigDTOResult.getParamname());
    // Assertions.assertEquals(detailsConfigDTO.getConfigDetId(),
    // detailsConfigDTOResult.getConfigDetId());

    // mockMvc.perform(delete("/AgentAdmin/AgentDetails/details/" + detailsId)
    // .contentType(APPLICATION_JSON))
    // .andExpect(status().isNotFound());
    // }

    // @Test
    // public void testGetCreateSeveralDetails() throws Exception {
    // DetailsConfigDTO detailsConfigDTO = createDetailsConfigDTO();
    // String requestJson = getJSON(detailsConfigDTO);

    // String jsonString = mockMvc.perform(get("/AgentAdmin/AgentDetails")
    // .accept(APPLICATION_JSON))
    // .andExpect(status().isOk())
    // .andReturn()
    // .getResponse()
    // .getContentAsString();

    // int initialSize = StringUtils.countMatches(jsonString, "configDetId");

    // int count = 5;
    // for (int i = 0; i < count; i++) {
    // mockMvc.perform(post("/AgentAdmin/AgentDetails")
    // .contentType(APPLICATION_JSON)
    // .content(requestJson))
    // .andExpect(status().isCreated());
    // }

    // jsonString = mockMvc.perform(get("/AgentAdmin/AgentDetails")
    // .accept(APPLICATION_JSON))
    // .andExpect(status().isOk())
    // .andReturn()
    // .getResponse()
    // .getContentAsString();

    // int finalSize = StringUtils.countMatches(jsonString, "configDetId");

    // Assertions.assertEquals(count, finalSize - initialSize);
    // }

    /**
     * Table: agentresponseconfig
     */

    private ResponseConfigDTO responseConfigDTOResult;

    private ResponseConfigDTO createResponseConfigDTO() {
        ResponseConfigDTO responseConfigDTO = new ResponseConfigDTO();
        responseConfigDTO.setMethodid(151);
        responseConfigDTO.setResponseparam("test");
        responseConfigDTO.setParamname("test");
        responseConfigDTO.setParamtype("test");
        responseConfigDTO.setIsactive("Y");
        return responseConfigDTO;
    }

    @Test
    public void testGetResponseList() throws Exception {
        mockMvc.perform(get("/AgentAdmin/AgentResponse")
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetResponseById200() throws Exception {
        mockMvc.perform(get("/AgentAdmin/AgentResponse/response/1")
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.configresponseid").value("1"))
                .andExpect(jsonPath("$.paramname").value("ProjectName"));
    }

    @Test
    public void testGetResponseById404() throws Exception {
        mockMvc.perform(get("/AgentAdmin/AgentResponse/response/1000000")
                .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetResponseByMethodId200() throws Exception {
        mockMvc.perform(get("/AgentAdmin/AgentResponse/method/1")
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetResponseByMethodId404() throws Exception {
        mockMvc.perform(get("/AgentAdmin/AgentResponse/method/1000000")
                .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    // @Test
    // public void testPostResponse201() throws Exception {
    // ResponseConfigDTO responseConfigDTO = createResponseConfigDTO();
    // String requestJson = getJSON(responseConfigDTO);

    // mockMvc.perform(post("/AgentAdmin/AgentResponse")
    // .contentType(APPLICATION_JSON)
    // .content(requestJson))
    // .andExpect(status().isCreated())
    // .andExpect(jsonPath("$.paramname").value(responseConfigDTO.getParamname()))
    // .andDo(mvcResult -> {
    // String json = mvcResult.getResponse().getContentAsString();
    // responseConfigDTOResult = (ResponseConfigDTO)
    // this.convertJSONStringToObject(json,
    // ResponseConfigDTO.class);
    // });

    // Assertions.assertEquals(responseConfigDTO.getParamname(),
    // responseConfigDTOResult.getParamname());
    // }

    @Test
    public void testPostResponseEmptyBody400() throws Exception {
        mockMvc.perform(post("/AgentAdmin/AgentResponse")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPostResponseWrongMethodId400() throws Exception {
        ResponseConfigDTO responseConfigDTO = createResponseConfigDTO();
        responseConfigDTO.setMethodid(100000);
        String requestJson = getJSON(responseConfigDTO);

        mockMvc.perform(post("/AgentAdmin/AgentResponse")
                .contentType(APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isBadRequest());
    }

    // @Test
    // public void testPutResponseUpdate() throws Exception {
    // ResponseConfigDTO responseConfigDTO = createResponseConfigDTO();
    // String requestJson = getJSON(responseConfigDTO);
    //
    // mockMvc.perform(post("/AgentAdmin/AgentResponse")
    // .contentType(APPLICATION_JSON)
    // .content(requestJson))
    // .andExpect(status().isCreated())
    // .andExpect(jsonPath("$.paramname").value(responseConfigDTO.getParamname()))
    // .andDo(mvcResult -> {
    // String json = mvcResult.getResponse().getContentAsString();
    // responseConfigDTOResult = (ResponseConfigDTO)
    // this.convertJSONStringToObject(json,
    // ResponseConfigDTO.class);
    // });
    //
    // Integer responseId = responseConfigDTOResult.getConfigresponseid();
    //
    // responseConfigDTO.setConfigresponseid(responseId);
    // responseConfigDTO.setMethodid(151);
    // responseConfigDTO.setResponseparam("test");
    // responseConfigDTO.setParamname("test");
    // responseConfigDTO.setParamtype("test");
    // responseConfigDTO.setIsactive("Y");
    // requestJson = getJSON(responseConfigDTO);
    //
    // mockMvc.perform(put("/AgentAdmin/AgentResponse/" + responseId)
    // .contentType(APPLICATION_JSON)
    // .content(requestJson))
    // .andExpect(status().isOk())
    // .andExpect(jsonPath("$.paramname").value(responseConfigDTO.getParamname()))
    // .andDo(mvcResult -> {
    // String json = mvcResult.getResponse().getContentAsString();
    // responseConfigDTOResult = (ResponseConfigDTO)
    // this.convertJSONStringToObject(json,
    // ResponseConfigDTO.class);
    // });
    //
    // Assertions.assertEquals(responseConfigDTO.getParamname(),
    // responseConfigDTOResult.getParamname());
    // Assertions.assertEquals(responseConfigDTO.getConfigresponseid(),
    // responseConfigDTOResult.getConfigresponseid());
    // Assertions.assertEquals(responseConfigDTO.getMethodid(),
    // responseConfigDTOResult.getMethodid());
    // }
    //
    // @Test
    // public void testPutResponseCreate() throws Exception {

    // ResponseConfigDTO responseConfigDTO = createResponseConfigDTO();
    // responseConfigDTO.setConfigresponseid(100000);
    // String requestJson = getJSON(responseConfigDTO);

    // mockMvc.perform(put("/AgentAdmin/AgentResponse/100000")
    // .contentType(APPLICATION_JSON)
    // .content(requestJson))
    // .andExpect(status().isCreated())
    // .andExpect(jsonPath("$.paramname").value(responseConfigDTO.getParamname()))
    // .andDo(mvcResult -> {
    // String json = mvcResult.getResponse().getContentAsString();
    // responseConfigDTOResult = (ResponseConfigDTO)
    // this.convertJSONStringToObject(json,
    // ResponseConfigDTO.class);
    // });

    // Assertions.assertEquals(responseConfigDTO.getParamname(),
    // responseConfigDTOResult.getParamname());
    // Assertions.assertNotEquals(responseConfigDTO.getConfigresponseid(),
    // responseConfigDTOResult.getConfigresponseid());
    // }

    @Test
    public void testPutResponseEmptyBody400() throws Exception {
        mockMvc.perform(put("/AgentAdmin/AgentResponse/26")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPutResponseWrongMethodId400() throws Exception {
        ResponseConfigDTO responseConfigDTO = createResponseConfigDTO();
        responseConfigDTO.setMethodid(100000);
        String requestJson = getJSON(responseConfigDTO);

        mockMvc.perform(put("/AgentAdmin/AgentResponse/26")
                .contentType(APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isBadRequest());
    }

    // @Test
    // public void testDeleteResponse200() throws Exception {

    // ResponseConfigDTO responseConfigDTO = createResponseConfigDTO();
    // String requestJson = getJSON(responseConfigDTO);

    // mockMvc.perform(post("/AgentAdmin/AgentResponse")
    // .contentType(APPLICATION_JSON)
    // .content(requestJson))
    // .andExpect(status().isCreated())
    // .andExpect(jsonPath("$.paramname").value(responseConfigDTO.getParamname()))
    // .andDo(mvcResult -> {
    // String json = mvcResult.getResponse().getContentAsString();
    // responseConfigDTOResult = (ResponseConfigDTO)
    // this.convertJSONStringToObject(json,
    // ResponseConfigDTO.class);
    // });

    // Integer responseId = responseConfigDTOResult.getConfigresponseid();

    // mockMvc.perform(delete("/AgentAdmin/AgentResponse/response/" + responseId)
    // .contentType(APPLICATION_JSON))
    // .andExpect(status().isOk())
    // .andDo(mvcResult -> {
    // String json = mvcResult.getResponse().getContentAsString();
    // responseConfigDTOResult = (ResponseConfigDTO)
    // this.convertJSONStringToObject(json,
    // ResponseConfigDTO.class);
    // });

    // Assertions.assertEquals(responseConfigDTO.getParamname(),
    // responseConfigDTOResult.getParamname());
    // Assertions.assertEquals(responseId,
    // responseConfigDTOResult.getConfigresponseid());

    // mockMvc.perform(delete("/AgentAdmin/AgentResponse/response/" + responseId)
    // .contentType(APPLICATION_JSON))
    // .andExpect(status().isNotFound());
    // }

    @Test
    public void testDeleteResponse404() throws Exception {
        mockMvc.perform(delete("/AgentAdmin/AgentResponse/response/1000000")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    // @Test
    // public void testCRUDResponse() throws Exception {

    // ResponseConfigDTO responseConfigDTO = createResponseConfigDTO();
    // String requestJson = getJSON(responseConfigDTO);

    // mockMvc.perform(post("/AgentAdmin/AgentResponse")
    // .contentType(APPLICATION_JSON)
    // .content(requestJson))
    // .andExpect(status().isCreated())
    // .andExpect(jsonPath("$.paramname").value(responseConfigDTO.getParamname()))
    // .andDo(mvcResult -> {
    // String json = mvcResult.getResponse().getContentAsString();
    // responseConfigDTOResult = (ResponseConfigDTO)
    // this.convertJSONStringToObject(json,
    // ResponseConfigDTO.class);
    // });

    // Integer responseId = responseConfigDTOResult.getConfigresponseid();

    // responseConfigDTO.setConfigresponseid(responseId);
    // responseConfigDTO.setMethodid(151);
    // responseConfigDTO.setResponseparam("test");
    // responseConfigDTO.setParamname("test");
    // responseConfigDTO.setParamtype("test");
    // responseConfigDTO.setIsactive("Y");
    // requestJson = getJSON(responseConfigDTO);

    // mockMvc.perform(put("/AgentAdmin/AgentResponse/" + responseId)
    // .contentType(APPLICATION_JSON)
    // .content(requestJson))
    // .andExpect(status().isOk())
    // .andExpect(jsonPath("$.paramname").value(responseConfigDTO.getParamname()))
    // .andDo(mvcResult -> {
    // String json = mvcResult.getResponse().getContentAsString();
    // responseConfigDTOResult = (ResponseConfigDTO)
    // this.convertJSONStringToObject(json,
    // ResponseConfigDTO.class);
    // });

    // Assertions.assertEquals(responseConfigDTO.getParamname(),
    // responseConfigDTOResult.getParamname());
    // Assertions.assertEquals(responseConfigDTO.getConfigresponseid(),
    // responseConfigDTOResult.getConfigresponseid());
    // Assertions.assertEquals(responseConfigDTO.getMethodid(),
    // responseConfigDTOResult.getMethodid());

    // mockMvc.perform(delete("/AgentAdmin/AgentResponse/response/" + responseId)
    // .contentType(APPLICATION_JSON))
    // .andExpect(status().isOk())
    // .andDo(mvcResult -> {
    // String json = mvcResult.getResponse().getContentAsString();
    // responseConfigDTOResult = (ResponseConfigDTO)
    // this.convertJSONStringToObject(json,
    // ResponseConfigDTO.class);
    // });

    // Assertions.assertEquals(responseConfigDTO.getParamname(),
    // responseConfigDTOResult.getParamname());
    // Assertions.assertEquals(responseId,
    // responseConfigDTOResult.getConfigresponseid());

    // mockMvc.perform(delete("/AgentAdmin/AgentResponse/response/" + responseId)
    // .contentType(APPLICATION_JSON))
    // .andExpect(status().isNotFound());
    // }

    // @Test
    // public void testGetCreateSeveralResponse() throws Exception {
    // ResponseConfigDTO responseConfigDTO = createResponseConfigDTO();
    // String requestJson = getJSON(responseConfigDTO);

    // String jsonString = mockMvc.perform(get("/AgentAdmin/AgentResponse")
    // .accept(APPLICATION_JSON))
    // .andExpect(status().isOk())
    // .andReturn()
    // .getResponse()
    // .getContentAsString();

    // int initialSize = StringUtils.countMatches(jsonString, "configresponseid");

    // int count = 5;
    // for (int i = 0; i < count; i++) {
    // mockMvc.perform(post("/AgentAdmin/AgentResponse")
    // .contentType(APPLICATION_JSON)
    // .content(requestJson))
    // .andExpect(status().isCreated());
    // }

    // jsonString = mockMvc.perform(get("/AgentAdmin/AgentResponse")
    // .accept(APPLICATION_JSON))
    // .andExpect(status().isOk())
    // .andReturn()
    // .getResponse()
    // .getContentAsString();

    // int finalSize = StringUtils.countMatches(jsonString, "configresponseid");

    // Assertions.assertEquals(count, finalSize - initialSize);
    // }
}
