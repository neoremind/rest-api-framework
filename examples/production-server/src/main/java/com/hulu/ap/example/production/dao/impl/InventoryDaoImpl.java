package com.hulu.ap.example.production.dao.impl;

import com.hulu.ap.example.production.dao.InventoryDao;
import com.hulu.ap.example.production.misc.ObjectCache;
import com.hulu.ap.example.production.vo.Inventory;

import java.util.List;

/**
 * @author xu.zhang
 */
public class InventoryDaoImpl implements InventoryDao {

    @Override
    public List<Inventory> getAll() {
        return ObjectCache.INVENTORY_LIST;
    }
}
