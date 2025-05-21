package com.taklip.yoda.convertor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.taklip.yoda.common.util.ExtraFieldUtil;
import com.taklip.yoda.common.util.PortalUtil;
import com.taklip.yoda.dto.BrandDTO;
import com.taklip.yoda.dto.CategoryDTO;
import com.taklip.yoda.dto.CommentDTO;
import com.taklip.yoda.dto.ContentBrandDTO;
import com.taklip.yoda.dto.ContentContributorDTO;
import com.taklip.yoda.dto.ContentDTO;
import com.taklip.yoda.dto.ItemDTO;
import com.taklip.yoda.dto.SolutionDTO;
import com.taklip.yoda.dto.SolutionItemDTO;
import com.taklip.yoda.dto.TermDTO;
import com.taklip.yoda.dto.UserDTO;
import com.taklip.yoda.model.Brand;
import com.taklip.yoda.model.Category;
import com.taklip.yoda.model.Comment;
import com.taklip.yoda.model.Content;
import com.taklip.yoda.model.ContentBrand;
import com.taklip.yoda.model.ContentContributor;
import com.taklip.yoda.model.Item;
import com.taklip.yoda.model.Solution;
import com.taklip.yoda.model.SolutionItem;
import com.taklip.yoda.model.Term;
import com.taklip.yoda.model.User;

@Component
public class ModelConvertor {
    @SuppressWarnings("unchecked")
    public <T> T convertTo(Object source, Class<T> clazz) {
        if (source instanceof Content && clazz.equals(ContentDTO.class)) {
            return (T) convertToContentDTO((Content) source);
        } else if (source instanceof Item && clazz.equals(ItemDTO.class)) {
            return (T) convertToItemDTO((Item) source);
        } else if (source instanceof Comment && clazz.equals(CommentDTO.class)) {
            return (T) convertToCommentDTO((Comment) source);
        } else if (source instanceof User && clazz.equals(UserDTO.class)) {
            return (T) convertToUserDTO((User) source);
        } else if (source instanceof Term && clazz.equals(TermDTO.class)) {
            return (T) convertToTermDTO((Term) source);
        } else if (source instanceof Solution && clazz.equals(SolutionDTO.class)) {
            return (T) convertToSolutionDTO((Solution) source);
        }

        return null;
    }

    public BrandDTO convertBrandToDTO(Brand brand) {
        if (brand == null)
            return null;

        return BrandDTO.builder()
                .id(brand.getId())
                .name(brand.getName())
                .description(brand.getDescription())
                .imagePath(brand.getImagePath())
                .foundDate(brand.getFoundDate())
                .hitCounter(brand.getHitCounter())
                .score(brand.getScore())
                .createTime(brand.getCreateTime())
                .updateTime(brand.getUpdateTime())
                .build();
    }

    public CategoryDTO convertToCategoryDTO(Category category) {
        if (category == null)
            return null;

        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .createTime(category.getCreateTime())
                .updateTime(category.getUpdateTime())
                .build();
    }

    public CommentDTO convertToCommentDTO(Comment comment) {
        if (comment == null)
            return null;

        return CommentDTO.builder()
                .id(comment.getId())
                .contentId(comment.getContentId())
                .rating(comment.getRating())
                .description(comment.getDescription())
                .siteId(comment.getSiteId())
                .userId(comment.getUserId())
                .createTime(comment.getCreateTime())
                .build();
    }

    public List<CommentDTO> convertToCommentDTOs(List<Comment> comments) {
        if (comments == null)
            return null;

        return comments.stream().map(this::convertToCommentDTO).collect(Collectors.toList());
    }

    public ContentBrandDTO convertToContentBrandDTO(ContentBrand contentBrand) {
        if (contentBrand == null)
            return null;

        return ContentBrandDTO.builder()
                .id(contentBrand.getId())
                .brandId(contentBrand.getBrandId())
                .contentId(contentBrand.getContentId())
                .brandName(contentBrand.getBrandName())
                .brandLogo(contentBrand.getBrandLogo())
                .description(contentBrand.getDescription().replace("<img src=\"/upload", "<img src=\"/yoda/upload"))
                .createTime(contentBrand.getCreateTime())
                .updateTime(contentBrand.getUpdateTime())
                .build();
    }

    public List<ContentBrandDTO> convertToContentBrandDTOs(List<ContentBrand> contentBrands) {
        if (contentBrands == null)
            return null;

        return contentBrands.stream().map(this::convertToContentBrandDTO).collect(Collectors.toList());
    }

    public ContentContributorDTO convertToContentContributorDTO(ContentContributor contentContributor) {
        if (contentContributor == null)
            return null;

        return ContentContributorDTO.builder()
                .id(contentContributor.getId())
                .contentId(contentContributor.getContentId())
                .userId(contentContributor.getUserId())
                .username(contentContributor.getUsername())
                .profilePhotoSmall(contentContributor.getProfilePhotoSmall())
                .version(contentContributor.getVersion())
                .approved(contentContributor.isApproved())
                .build();
    }

    public List<ContentContributorDTO> convertToContentContributorDTOs(List<ContentContributor> contentContributors) {
        if (contentContributors == null)
            return null;

        return contentContributors.stream().map(this::convertToContentContributorDTO).collect(Collectors.toList());
    }

    public ContentDTO convertToContentDTO(Content content) {
        if (content == null)
            return null;

        return ContentDTO.builder()
                .id(content.getId())
                .title(content.getTitle())
                .shortDescription(content.getShortDescription())
                .description(content.getDescription().replace("img src", "img data-src"))
                .featuredImage(content.getFeaturedImage())
                .pageTitle(content.getPageTitle())
                .naturalKey(content.getNaturalKey())
                .published(content.isPublished())
                .featureData(content.isFeatureData())
                .homePage(content.isHomePage())
                .createTime(content.getCreateTime())
                .updateTime(content.getUpdateTime())
                .publishDate(content.getPublishDate())
                .expireDate(content.getExpireDate())
                .hitCounter(content.getHitCounter())
                .score(content.getScore())
                .build();
    }

    public ItemDTO convertToItemDTO(Item item) {
        if (item == null)
            return null;

        return ItemDTO.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .description(item.getDescription())
                .shortDescription(PortalUtil.shortenDescription(item.getDescription()))
                .extraFieldList(ExtraFieldUtil.getExtraFields(item))
                .buyLinkList(ExtraFieldUtil.getBuyLinks(item))
                .contentId(item.getContentId())
                .siteId(item.getSiteId())
                .hitCounter(item.getHitCounter())
                .rating(item.getRating())
                .imagePath(item.getImagePath())
                .level(item.getLevel())
                .createTime(item.getCreateTime())
                .updateTime(item.getUpdateTime())
                .build();
    }

    public List<ItemDTO> convertToItemDTOs(List<Item> items) {
        if (items == null)
            return null;

        return items.stream().map(this::convertToItemDTO).collect(Collectors.toList());
    }

    public TermDTO convertToTermDTO(Term term) {
        if (term == null)
            return null;

        return TermDTO.builder()
                .id(term.getId())
                .title(term.getTitle())
                .description(PortalUtil.shortenDescription(term.getDescription()))
                .contentId(term.getContentId())
                .categoryId(term.getCategoryId())
                .hitCounter(term.getHitCounter())
                .createTime(term.getCreateTime())
                .updateTime(term.getUpdateTime())
                .build();
    }

    public SolutionDTO convertToSolutionDTO(Solution solution) {
        if (solution == null)
            return null;

        return SolutionDTO.builder()
                .id(solution.getId())
                .title(solution.getTitle())
                .description(solution.getDescription())
                .imagePath(solution.getImagePath())
                .categoryId(solution.getCategoryId())
                .createTime(solution.getCreateTime())
                .updateTime(solution.getUpdateTime())
                .build();
    }

    public SolutionItemDTO convertToSolutionItemDTO(SolutionItem solutionItem) {
        if (solutionItem == null)
            return null;

        return SolutionItemDTO.builder()
                .id(solutionItem.getId())
                .solutionId(solutionItem.getSolutionId())
                .itemId(solutionItem.getItemId())
                .description(solutionItem.getDescription())
                .build();
    }

    public UserDTO convertToUserDTO(User user) {
        if (user == null)
            return null;

        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .profilePhotoSmall(user.getProfilePhotoSmall())
                .createTime(user.getCreateTime())
                .updateTime(user.getUpdateTime())
                .build();
    }
}
