package com.neoremind.example.production.dao.impl;

import com.neoremind.example.production.dao.InventoryDao;
import com.neoremind.example.production.misc.ObjectCache;
import com.neoremind.example.production.vo.Inventory;

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
