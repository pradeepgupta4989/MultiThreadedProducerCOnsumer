package com.producerconsumer.counter;
import com.producerconsumer.counter.repository.ExecutionTimeStampRepository;
import com.producerconsumer.counter.repository.ProducerConsumerCountRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes= MultiThreadedProducerConsumerApplication.class)
public class MultithreadedProducerConsumerRouterTest {
    private MockMvc mvc;
    @Autowired
    WebApplicationContext webApplicationContext;
    @MockBean
    ProducerConsumerCountRepo producerConsumerCountRepo;
    @MockBean
    ExecutionTimeStampRepository executionTimeStampRepository;

    @Before
    public void setup(){
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void createProducerConsumerThreadPoolSuccess() throws Exception {
        String uri = "/producerConsumer/v1/createThreads/4/5";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
    }
    @Test
    public void createProducerConsumerThreadPoolBadRequest() throws Exception {
        String uri = "/producerConsumer/v1/createThreads/102/5";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    public void updateCounterSuccess() throws Exception {
        String uri = "/producerConsumer/v1/updateCounter/5";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    public void updateCounterBadRequest() throws Exception {
        String uri = "/producerConsumer/v1/updateCounter/102";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }



}
