package com.taklip.yoda.api;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.taklip.yoda.dto.ContentDTO;
import com.taklip.yoda.model.Brand;
import com.taklip.yoda.model.ContentBrand;
import com.taklip.yoda.model.Item;
import com.taklip.yoda.service.BrandService;
import com.taklip.yoda.service.ContentBrandService;
import com.taklip.yoda.service.ContentService;
import com.taklip.yoda.service.ItemService;
import com.taklip.yoda.service.RedisService;
import com.taklip.yoda.common.contant.Constants;

/**
 * @author askar
 */
@RestController
@RequestMapping("/api/redis")
public class RedisController {
    @Autowired
    private RedisService redisService;

    @Autowired
    private ContentService contentService;

    @Autowired
    private ContentBrandService contentBrandService;
    @Autowired
    private BrandService brandService;

    @Autowired
    private ItemService itemService;

    @ResponseBody
    @RequestMapping("/delete/content")
    public ResponseEntity<String> deleteContents() {
        List<ContentDTO> contents = contentService.getContents();

        for (ContentDTO content : contents) {
            // contentService.deleteContentFromCache(content.getId());
            // redisService.delete(Constants.REDIS_CONTENT + ":" + content.getId());
            //
            // List<ContentContributor> cc = content.getContentContributors();
            //
            // for (ContentContributor c : cc) {
            // redisService.delete(Constants.REDIS_CONTENT_CONTRIBUROR_LIST + ":" +
            // content.getId());
            // }
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping("/delete/contentBrand")
    @ResponseBody
    public ResponseEntity<String> contentBrands() {
        List<ContentBrand> list = contentBrandService.getContentBrands();

        for (ContentBrand contentBrand : list) {
            if (StringUtils.isBlank(contentBrand.getBrandLogo())) {
                Brand brand = brandService.getBrand(contentBrand.getBrandId());
                contentBrand.setBrandLogo(brand.getImagePath());
            }
            String logo = contentBrand.getBrandLogo().replace("//", "/");
            contentBrand.setBrandLogo(logo);
            contentBrandService.create(contentBrand);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping("/delete/brand")
    @ResponseBody
    public ResponseEntity<String> deleteBrands() {
        List<Brand> list = brandService.getBrands();

        for (Brand brand : list) {
            redisService.delete(Constants.REDIS_BRAND + ":" + brand.getId());
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping("/delete/item")
    @ResponseBody
    public ResponseEntity<String> deleteItems() {
        List<Item> list = itemService.getItems();

        for (Item item : list) {
            redisService.delete(Constants.REDIS_ITEM + ":" + item.getId());
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
