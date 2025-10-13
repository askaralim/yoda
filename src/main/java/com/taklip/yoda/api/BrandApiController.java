package com.taklip.yoda.api;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taklip.yoda.dto.BrandDTO;
import com.taklip.yoda.dto.ItemDTO;
import com.taklip.yoda.model.Brand;
import com.taklip.yoda.service.BrandService;
import com.taklip.yoda.service.ContentService;
import com.taklip.yoda.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

/**
 * @author askar
 */
@RestController
@RequestMapping("/api/v1/brand")
@Slf4j
@Tag(name = "Brand Management", description = "Brand management API endpoints")
public class BrandApiController {
    @Autowired
    BrandService brandService;

    @Autowired
    ItemService itemService;

    @Autowired
    ContentService contentService;

    @GetMapping
    @Operation(summary = "Get brands", description = "Get brands by page")
    public ResponseEntity<Page<BrandDTO>> page(@RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "20") Integer limit) {
        return ResponseEntity.ok(brandService.getBrands(offset, limit));
    }

    @GetMapping("/all")
    @Operation(summary = "Get all brands", description = "Get all brands")
    public ResponseEntity<List<Brand>> allBrands() {
        return ResponseEntity.ok(brandService.getBrands());
    }

    @GetMapping("/topBrands")
    @Operation(summary = "Get top brands", description = "Get top brands by hit counter")
    public ResponseEntity<List<BrandDTO>> topBrands() {
        return ResponseEntity.ok(brandService.getBrandsTopViewed(8));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get brand", description = "Get brand by brand id")
    public ResponseEntity<BrandDTO> get(@PathVariable Long id) {
        // List<Item> items = itemService.getItemsByBrandId(id);
        // brand.setItems(items);
        return ResponseEntity.ok(brandService.getBrandDetail(id));
    }


    @GetMapping("/{id}/items")
    @Operation(summary = "Get items by brand id", description = "Get items by brand id")
    public ResponseEntity<Page<ItemDTO>> getItemsByBrandId(@PathVariable Long id,
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "10") Integer limit) {
        return ResponseEntity.ok(itemService.getItemsByBrandId(id, offset, limit));
    }

    @PostMapping
    @Operation(summary = "Create brand", description = "Create brand")
    public ResponseEntity<BrandDTO> create(@RequestBody Brand brand) {
        return ResponseEntity.status(HttpStatus.CREATED).body(brandService.create(brand));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update brand", description = "Update brand by brand id")
    public ResponseEntity<BrandDTO> update(@PathVariable Long id, @RequestBody Brand brand) {
        return ResponseEntity.status(HttpStatus.OK).body(brandService.update(brand));
    }

    @PostMapping("/{id}/uploadImage")
    @Operation(summary = "Upload brand image", description = "Upload brand image by brand id")
    public ResponseEntity<BrandDTO> uploadImage(@RequestParam MultipartFile file,
            @PathVariable Long id) {
        try {
            if (file.getBytes().length <= 0) {
                return ResponseEntity.badRequest().build();
            }

            if (StringUtils.isBlank(file.getName())) {
                return ResponseEntity.badRequest().build();
            }

            return ResponseEntity.ok(brandService.updateImage(id, file));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete brand", description = "Delete brand by brand id")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        brandService.deleteBrand(id);
        return ResponseEntity.noContent().build();
    }
}
