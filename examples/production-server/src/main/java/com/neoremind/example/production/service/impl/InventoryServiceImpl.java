package com.neoremind.example.production.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.neoremind.example.production.dao.InventoryDao;
import com.neoremind.example.production.service.InventoryService;
import com.neoremind.example.production.vo.Inventory;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

/**
 * @author xu.zhang
 */
public class InventoryServiceImpl implements InventoryService {

    @Inject
    private InventoryDao inventoryDao;

    @Override
    public Map<Long, Integer> getAvailableProductNumbers(List<Long> productIdList) {
        Map<Long, Inventory> productId2InventoryMap = Maps.uniqueIndex(inventoryDao.getAll(), new Function<Inventory, Long>() {
            @Nullable
            @Override
            public Long apply(@Nullable Inventory input) {
                return input.getProductId();
            }
        });
        Map<Long, Integer> res = Maps.newHashMapWithExpectedSize(productIdList.size());
        for (Map.Entry<Long, Inventory> e : productId2InventoryMap.entrySet()) {
            if (productIdList.contains(e.getKey())) {
                res.put(e.getKey(), e.getValue().getAvailableNumber());
            }
        }
        return res;
    }

    @Override
    public void decreaseProductNumbers(List<Long> productIdList) {
        List<Inventory> inventoryList = inventoryDao.getAll();
        inventoryList.forEach(i -> i.setAvailableNumber(i.getAvailableNumber() - 1));
    }
}
