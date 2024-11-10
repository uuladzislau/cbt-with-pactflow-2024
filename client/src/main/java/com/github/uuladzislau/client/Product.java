package com.github.uuladzislau.client;

import java.util.List;

record Product(
        Long id,
        String name,
        Double price,
        ProductCategory category,
        List<String> stores
) {}
