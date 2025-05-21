package com.taklip.yoda.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.taklip.yoda.dto.BrandDTO;
import com.taklip.yoda.model.Brand;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author askar
 */
public interface BrandService extends IService<Brand> {
    /**
     * Creates a new brand
     * 
     * @param brand the brand to create
     * @return true if successful
     */
    boolean create(Brand brand);

    /**
     * Deletes a brand by id
     * 
     * @param id the brand id
     */
    void deleteBrand(Long id);

    /**
     * Gets a brand by id
     * 
     * @param id the brand id
     * @return the brand
     */
    Brand getBrand(Long id);

    /**
     * Gets brand detail by id
     * 
     * @param id the brand id
     * @return the brand DTO
     */
    BrandDTO getBrandDetail(Long id);

    /**
     * Gets all brands
     * 
     * @return list of all brands
     */
    List<Brand> getBrands();

    /**
     * Gets paginated brands
     * 
     * @param offset the page offset
     * @param limit  the page limit
     * @return paginated brands
     */
    Page<Brand> getBrands(Integer offset, Integer limit);

    /**
     * Gets brands with highest hit counter
     * 
     * @param offset the page offset
     * @param limit  the page limit
     * @return paginated hot brands
     */
    Page<Brand> getHotBrands(Integer offset, Integer limit);

    /**
     * Gets top viewed brands
     * 
     * @param count the number of brands to return
     * @return list of top viewed brands
     */
    List<Brand> getBrandsTopViewed(int count);

    /**
     * Gets the hit counter for a brand
     * 
     * @param id the brand id
     * @return the hit counter
     */
    int getBrandHitCounter(Long id);

    /**
     * Increases the hit counter for a brand
     * 
     * @param id the brand id
     */
    void increaseBrandHitCounter(Long id);

    /**
     * Updates a brand
     * 
     * @param brand the brand to update
     * @return true if successful
     */
    boolean update(Brand brand);

    /**
     * Updates a brand's image
     * 
     * @param id   the brand id
     * @param file the image file
     * @return the updated brand
     */
    Brand updateImage(Long id, MultipartFile file);

    /**
     * Updates a brand's rating
     * 
     * @param id     the brand id
     * @param rating the new rating
     */
    void updateBrandRating(Long id, int rating);
}