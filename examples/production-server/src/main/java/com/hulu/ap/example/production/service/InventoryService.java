package com.hulu.ap.example.production.service;

import java.util.List;
import java.util.Map;

/**
 * Created by xu.zhang on 12/21/16.
 */
public interface InventoryService {

    Map<Long, Integer> getAvailableProductNumbers(List<Long> productIdList);

    void decreaseProductNumbers(List<Long> productIdList);

}
