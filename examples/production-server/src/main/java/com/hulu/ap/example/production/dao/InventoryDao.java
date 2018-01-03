package com.hulu.ap.example.production.dao;

import com.hulu.ap.example.production.vo.Inventory;

import java.util.List;
import java.util.Map;

/**
 * @author xu.zhang
 */
public interface InventoryDao {

    List<Inventory> getAll();

}
