package com.taklip.yoda.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taklip.yoda.dto.TermDTO;
import com.taklip.yoda.service.TermService;

/**
 * @author askar
 */
@RestController
@RequestMapping("/api/v1/term")
public class TermApiController {
    @Autowired
    private TermService termService;

    @GetMapping
    public ResponseEntity<Page<TermDTO>> page(@RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "5") Integer limit) {
        Page<TermDTO> page = termService.getByPage(new Page<>(offset, limit));

        // for (TermDTO term : page.getRecords()) {
        //     shortenTermDescription(term);
        // }

        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TermDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(termService.getTermDetail(id));
    }

    @PostMapping
    public ResponseEntity<TermDTO> create(@RequestBody TermDTO term) {
        return ResponseEntity.status(HttpStatus.CREATED).body(termService.create(term));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TermDTO> update(@PathVariable Long id, @RequestBody TermDTO term) {
        return ResponseEntity.status(HttpStatus.OK).body(termService.update(term));
    }

    // private TermDTO shortenTermDescription(TermDTO term) {
    //     String desc = term.getDescription();

    //     if (desc.length() > 200) {
    //         desc = desc.substring(0, 200);

    //         if (desc.indexOf("img") > 0) {
    //             desc = desc.substring(0, desc.indexOf("<img"));
    //         }

    //         desc = desc.trim().concat("...");

    //         term.setDescription(desc);
    //     }

    //     return term;
    // }
}
