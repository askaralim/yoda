package com.taklip.yoda.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taklip.yoda.controller.PortalBaseController;
import com.taklip.yoda.dto.SolutionDTO;
import com.taklip.yoda.model.Solution;
import com.taklip.yoda.service.SolutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/v1/solution")
public class SolutionApiController extends PortalBaseController {
    @Autowired
    SolutionService solutionService;

    @GetMapping
    public ResponseEntity<Page<Solution>> list(@RequestParam(defaultValue = "0") Integer offset) {
        Page<Solution> page = solutionService.getSolutions(offset, 3);

        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SolutionDTO> get(@PathVariable Long id) {
        SolutionDTO solution = solutionService.getSolutionDetail(id);

        return new ResponseEntity<>(solution, HttpStatus.OK);
    }
}