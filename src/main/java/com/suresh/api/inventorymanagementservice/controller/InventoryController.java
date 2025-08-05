package com.suresh.api.inventorymanagementservice.controller;


import com.suresh.api.inventorymanagementservice.exception.FeatureNotSupported;
import com.suresh.api.inventorymanagementservice.model.Inventory;
import com.suresh.api.inventorymanagementservice.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class InventoryController {

    private static final Logger logger = LoggerFactory.getLogger(InventoryController.class);

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/hello")
    public String hello() {
        return "Hello Suresh";
    }

    @GetMapping("/hello/{name}")
    public String helloByName(@PathVariable String name) {
        return "Hello " + name;
    }

    @GetMapping("/product/{productId}")
    public Inventory getInventory(@PathVariable String productId) {
        logger.info("Request: {}", productId);
        Inventory inventory = new Inventory();
        inventory.setInventoryId("1000");
        inventory.setQuantity(10);
        inventory.setProductId(productId);
        inventory.setProductName("HDMI CABLE");
        return inventory;
    }

    @GetMapping("/all")
    public List<Inventory> getAllInventory() {
        throw new FeatureNotSupported("Feature not supported");
    }

}
