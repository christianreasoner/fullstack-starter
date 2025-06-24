package com.starter.fullstack.dao;

import com.starter.fullstack.api.Inventory;
import java.util.*;
import javax.annotation.Resource;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * Test Inventory DAO.
 */
@DataMongoTest
@RunWith(SpringRunner.class)
public class InventoryDAOTest {
  @ClassRule
  public static final MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));

  @Resource
  private MongoTemplate mongoTemplate;
  private InventoryDAO inventoryDAO;
  private static final String NAME = "Amber";
  private static final String PRODUCT_TYPE = "hops";

  @Before
  public void setup() {
    this.inventoryDAO = new InventoryDAO(this.mongoTemplate);
  }

  @After
  public void tearDown() {
    this.mongoTemplate.dropCollection(Inventory.class);
  }

  /**
   * Test Find All method.
   */
  @Test
  public void findAll() {
    Inventory inventory = new Inventory();
    inventory.setName(NAME);
    inventory.setProductType(PRODUCT_TYPE);
    this.mongoTemplate.save(inventory);
    List<Inventory> actualInventory = this.inventoryDAO.findAll();
    Assert.assertFalse(actualInventory.isEmpty());
  }

  /**
   * Test Create method.
   */
  @Test
  public void create() {
    Inventory inventory = new Inventory();
    inventory.setId("TEST_ID");
    inventory.setName(NAME);
    inventory.setProductType(PRODUCT_TYPE);
    inventoryDAO.create(inventory);

    Assert.assertEquals(1, mongoTemplate.count(new Query(), Inventory.class));
    Assert.assertEquals(mongoTemplate.findById(inventory.getId(), Inventory.class), inventory);

    Inventory inventory2 = new Inventory();
    inventory2.setId("TEST_ID");
    inventory2.setName(NAME);
    inventory2.setProductType(PRODUCT_TYPE);
    inventory2.setDescription("test description");
    inventoryDAO.create(inventory2);

    Assert.assertEquals(1, mongoTemplate.count(new Query(), Inventory.class));
    Assert.assertEquals(mongoTemplate.findById(inventory.getId(), Inventory.class), inventory2);
    Assert.assertEquals("test description", mongoTemplate.findById(inventory.getId(),
      Inventory.class).getDescription());
  }

  /**
   * Test Delete method.
   */
  @Test
  public void delete() {
    Inventory inventory = new Inventory();
    inventory.setName(NAME);
    inventory.setProductType(PRODUCT_TYPE);
    this.inventoryDAO.create(inventory);
    ArrayList<String> list = new ArrayList<>();
    list.add(inventory.getId());
    List<Optional<Inventory>> actualInventory = this.inventoryDAO.delete(list);

    Assert.assertTrue(actualInventory.get(0).isPresent());
    Assert.assertEquals(actualInventory.get(0).get(), inventory);
    Assert.assertEquals(0, this.mongoTemplate.count(new Query(), Inventory.class));
  }

  /**
   * Test Update method.
   */
  @Test
  public void update() {
    Inventory inventory = new Inventory();
    inventory.setId("TEST_ID");
    inventory.setName(NAME);
    inventory.setProductType(PRODUCT_TYPE);
    this.inventoryDAO.create(inventory);

    Inventory inventory2 = new Inventory();
    inventory2.setId("TEST_ID");
    inventory2.setName("NEW_NAME");
    inventory2.setProductType(PRODUCT_TYPE);
    this.inventoryDAO.update("TEST_ID", inventory2);
    Assert.assertEquals(1, this.mongoTemplate.count(new Query(), Inventory.class));
    Assert.assertEquals(mongoTemplate.findById("TEST_ID", Inventory.class), inventory2);
  }
}