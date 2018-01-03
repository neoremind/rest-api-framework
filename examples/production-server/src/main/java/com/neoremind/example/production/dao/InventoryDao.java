package com.neoremind.example.production.dao;

import com.neoremind.example.production.vo.Inventory;

import java.util.List;

/**
 * @author xu.zhang
 */
public interface InventoryDao {

    List<Inventory> getAll();

}
