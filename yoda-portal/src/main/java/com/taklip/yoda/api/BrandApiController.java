package com.taklip.yoda.api;

import com.github.pagehelper.PageInfo;
import com.taklip.yoda.model.Brand;
import com.taklip.yoda.model.Item;
import com.taklip.yoda.service.BrandService;
import com.taklip.yoda.service.ContentService;
import com.taklip.yoda.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author askar
 */
@RestController
@RequestMapping("/api/v1/brand")
public class BrandApiController {
    @Autowired
    BrandService brandService;

    @Autowired
    ItemService itemService;

    @Autowired
    ContentService contentService;

    @GetMapping
    public ResponseEntity<PageInfo<Brand>> list(@RequestParam(value = "offset", defaultValue = "0") Integer offset) {
        PageInfo<Brand> page = brandService.getBrands(offset, 20);

        return new ResponseEntity(page, HttpStatus.OK);
    }

    @GetMapping("/topBrands")
    public ResponseEntity<List<Item>> topBrands() {
        List<Brand> brands = brandService.getBrandsTopViewed(8);

        return new ResponseEntity(brands, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Brand> get(@PathVariable("id") Long id) {
        Brand brand = brandService.getBrand(id);

        List<Item> items = itemService.getItemsByBrandId(id);

        brand.setItems(items);

        return new ResponseEntity(brand, HttpStatus.OK);
    }
}