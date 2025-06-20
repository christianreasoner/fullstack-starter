package com.starter.fullstack.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.starter.fullstack.api.Inventory;
import com.starter.fullstack.dao.InventoryDAO;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class InventoryControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private MongoTemplate mongoTemplate;

  @Autowired
  private ObjectMapper objectMapper;

  private InventoryDAO inventoryDAO;

  @Before
  public void setup() throws Throwable {
    this.inventoryDAO = new InventoryDAO(this.mongoTemplate);
  }

  @After
  public void teardown() {
    this.mongoTemplate.dropCollection(Inventory.class);
  }

  /**
   * Test create endpoint.
   * @throws Throwable see MockMvc
   */
  @Test
  public void create() throws Throwable {
    Inventory inventory = new Inventory();
    inventory.setId("ID");
    inventory.setName("TEST");
    inventory.setProductType("hops");

    this.mockMvc.perform(post("/inventory")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(this.objectMapper.writeValueAsString(inventory)))
      .andExpect(status().isOk());

    Assert.assertEquals(1, this.mongoTemplate.findAll(Inventory.class).size());
  }

  /**
   * Test delete endpoint.
   * @throws Throwable see MockMvc
   */
  @Test
  public void deleteById() throws Throwable {
    Inventory inventory = new Inventory();
    inventory.setId("ID");
    inventory.setName("TEST");
    inventory.setProductType("hops");
    List<String> inventoryIds = new ArrayList<>();
    this.inventoryDAO.create(inventory);
    for (Inventory i : this.inventoryDAO.findAll()) {
      inventoryIds.add(i.getId());
    }

    this.mockMvc.perform(delete("/inventory")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(inventoryIds)))
      .andExpect(status().isOk());

    Assert.assertEquals(0, this.mongoTemplate.findAll(Inventory.class).size());
  }
}