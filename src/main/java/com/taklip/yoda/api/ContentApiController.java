package com.taklip.yoda.api;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
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
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taklip.yoda.dto.CommentDTO;
import com.taklip.yoda.dto.ContentDTO;
import com.taklip.yoda.model.Brand;
import com.taklip.yoda.model.ContentBrand;
import com.taklip.yoda.service.BrandService;
import com.taklip.yoda.service.ContentBrandService;
import com.taklip.yoda.service.ContentService;
import com.taklip.yoda.service.ContentUserRateService;
import com.taklip.yoda.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/content")
@RequiredArgsConstructor
@Slf4j
public class ContentApiController {

    private final ContentService contentService;

    private final ContentUserRateService contentUserRateService;

    private final ContentBrandService contentBrandService;

    private final BrandService brandService;

    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<Page<ContentDTO>> getAllContent(
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "10") Integer limit) {
        return ResponseEntity.ok(contentService.getContentsByPage(offset, limit));
    }

    @GetMapping("/featured")
    @Operation(summary = "Get featured contents", description = "Retrieve featured contents")
    public ResponseEntity<Page<ContentDTO>> getFeaturedContents(
            @Parameter(description = "Feature data") @RequestParam(
                    defaultValue = "true") Boolean featured,
            @Parameter(description = "Limit number of results") @RequestParam(
                    defaultValue = "10") Integer limit,
            @Parameter(description = "Offset") @RequestParam(defaultValue = "0") Integer offset) {
        return ResponseEntity
                .ok(contentService.getContentsByFeatureData(featured, offset, limit));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContentDTO> getContentById(@PathVariable Long id) {
        return ResponseEntity.ok(contentService.getContentById(id));
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<Page<CommentDTO>> getComments(@PathVariable Long id,
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "10") Integer limit) {
        return ResponseEntity.ok(commentService.getByContentId(id, offset, limit));
    }

    @GetMapping("/contentbrand/{id}")
    public ResponseEntity<ContentBrand> getContentBrand(@PathVariable Long id) {
        return ResponseEntity.ok(contentBrandService.getById(id));
    }

    @PostMapping
    public ResponseEntity<ContentDTO> createContent(@Valid @RequestBody ContentDTO content) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contentService.create(content));
    }

    @PostMapping("/contentbrand/save")
    public ResponseEntity<ContentBrand> createContentBrand(@RequestBody ContentBrand contentBrand) {

        String brandName = StringUtils.EMPTY;
        String brandLogo = StringUtils.EMPTY;
        Brand brand = null;

        if (null != contentBrand.getBrandId()) {
            brand = brandService.getBrand(contentBrand.getBrandId());
            brandName = brand.getName();
            brandLogo = brand.getImagePath();
        }

        contentBrand.setBrandName(brandName);
        contentBrand.setBrandLogo(brandLogo);

        contentBrandService.create(contentBrand);

        return ResponseEntity.ok(contentBrand);
    }

    @PostMapping(value = "/{id}/resetCounter")
    public ResponseEntity<Void> resetCounter(@PathVariable Long contentId) throws Throwable {
        contentService.resetHitCounter(contentId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/uploadImage")
    public ResponseEntity<Void> uploadImage(@RequestParam MultipartFile file,
            @PathVariable long id) {
        contentService.updateContentImage(id, file);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContentDTO> updateContent(@PathVariable Long id,
            @Valid @RequestBody ContentDTO content) {
        return ResponseEntity.ok(contentService.update(content));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContent(@PathVariable Long id) {
        contentService.deleteContent(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<ContentDTO>> searchContent(@RequestParam String q) {
        // List<ContentDTO> results = contentService.searchContents(q);
        // return ResponseEntity.ok(results);

        return ResponseEntity.ok(null);
    }

    @PostMapping("/{id}/rate")
    public JSONObject score(@PathVariable Long id, @RequestParam String thumb) {
        contentUserRateService.create(id, thumb);

        int score = contentUserRateService.getTotalRateByContentId(id);

        JSONObject jsonResult = new JSONObject();

        try {
            jsonResult.put("score", score);
        } catch (Exception e) {
            log.error("JSON: {}", jsonResult, e);
        }

        return jsonResult;
    }

    // public void saveContentContributors(HttpServletRequest request, ContentDTO content) {
    // Enumeration<String> names = request.getParameterNames();

    // while (names.hasMoreElements()) {
    // String name = (String) names.nextElement();

    // if (name.startsWith("contributorId")) {
    // String userId = request.getParameter(name);

    // if (StringUtils.isEmpty(userId)) {
    // continue;
    // }

    // User user = userService.getUser(Long.valueOf(userId));

    // if (null == user) {
    // continue;
    // }

    // ContentContributor contributor = new ContentContributor();

    // contributor.setApproved(true);
    // contributor.setContentId(content.getId());
    // contributor.setProfilePhotoSmall(user.getProfilePhotoSmall());
    // contributor.setUserId(user.getId());
    // contributor.setUsername(user.getUsername());
    // contributor.setVersion("1.0");

    // List<ContentContributor> results = contentContributorService
    // .getByContentIdAndUserId(content.getId(), user.getId());

    // if (results.isEmpty()) {
    // contentContributorService.create(contributor);
    // }
    // }
    // }
    // }
}
