package com.taklip.yoda.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taklip.yoda.controller.PortalBaseController;
import com.taklip.yoda.dto.SolutionDTO;
import com.taklip.yoda.model.Solution;
import com.taklip.yoda.model.SolutionItem;
import com.taklip.yoda.service.SolutionService;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/api/v1/solution")
public class SolutionApiController extends PortalBaseController {
    @Autowired
    SolutionService solutionService;

    @GetMapping
    public ResponseEntity<Page<Solution>> list(@RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "3") Integer limit) {
        return ResponseEntity.ok(solutionService.getSolutions(offset, limit));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SolutionDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(solutionService.getSolutionDetail(id));
    }

    @PostMapping
    public ResponseEntity<Solution> create(@RequestBody Solution solution) {
        return ResponseEntity.status(HttpStatus.CREATED).body(solutionService.create(solution));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Solution> update(@PathVariable Long id, @RequestBody Solution solution) {
        return ResponseEntity.status(HttpStatus.OK).body(solutionService.update(solution));
    }

    @PostMapping("/{id}/uploadImage")
    public ResponseEntity<Void> uploadImage(@RequestParam MultipartFile file, @PathVariable Long id)
            throws Throwable {
        solutionService.updateSolutionImage(id, file);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{solutionId}/solutionItems")
    public ResponseEntity<List<SolutionItem>> getSolutionItems(@PathVariable Long solutionId) {
        List<SolutionItem> solutionItems = solutionService.getSolutionItemsBySolutionId(solutionId);
        return ResponseEntity.ok(solutionItems);
    }

    @PostMapping("/{solutionId}/solutionItem")
    public ResponseEntity<SolutionItem> createSolutionItem(@PathVariable Long solutionId,
            @RequestBody SolutionItem solutionItem) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(solutionService.createSolutionItem(solutionItem));
    }

    @PutMapping("/{solutionId}/solutionItem/{id}")
    public ResponseEntity<SolutionItem> updateSolutionItem(@PathVariable Long solutionId,
            @PathVariable Long id, @RequestBody SolutionItem solutionItem) {
        return ResponseEntity.ok(solutionService.updateSolutionItem(solutionItem));
    }

    @DeleteMapping("/{solutionId}/solutionItem/{id}")
    public ResponseEntity<Void> deleteSolutionItem(@PathVariable Long solutionId,
            @PathVariable Long id) {
        solutionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSolution(@PathVariable Long id) {
        solutionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
