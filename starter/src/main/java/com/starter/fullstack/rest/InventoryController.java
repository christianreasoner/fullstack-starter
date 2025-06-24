package com.starter.fullstack.rest;

import com.starter.fullstack.api.Inventory;
import com.starter.fullstack.dao.InventoryDAO;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
 * Inventory Controller.
 */
@RestController
@RequestMapping("/inventory")
public class InventoryController {
  private final InventoryDAO inventoryDAO;

  /**
   * Default Constructor.
   * @param inventoryDAO inventoryDAO.
   */
  public InventoryController(InventoryDAO inventoryDAO) {
    Assert.notNull(inventoryDAO, "Inventory DAO must not be null.");
    this.inventoryDAO = inventoryDAO;
  }

  /**
   * Find Inventory.
   * @return List of Inventory.
   */
  @GetMapping
  public List<Inventory> findInventories() {
    return this.inventoryDAO.findAll();
  }

  /**
   * Create Inventory.
   * @param inventory Inventory.
   * @return Inventory.
   */
  @PostMapping
  public Inventory createInventory(@Valid @RequestBody Inventory inventory) {
    return this.inventoryDAO.create(inventory);
  }

  /**
   * Delete Inventory.
   * @param ids List<String></String>.
   * @return Inventory.
   */
  @DeleteMapping
  public List<Optional<Inventory>> deleteInventory(@RequestBody List<String> ids) {
    return this.inventoryDAO.delete(ids);
  }

  /**
   * Update Inventory.
   * @param inventory Inventory.
   * @return Update.
   */
  @PutMapping
  public Inventory update(@Valid @RequestBody Inventory inventory) {
    this.inventoryDAO.update(inventory.getId(), inventory);
    return inventory;
  }
}