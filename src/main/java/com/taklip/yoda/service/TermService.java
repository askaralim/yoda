package com.taklip.yoda.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.taklip.yoda.dto.TermDTO;
import com.taklip.yoda.model.Term;

import java.util.List;

/**
 * @author askar
 */
public interface TermService extends IService<Term> {
    Term getTerm(Long id);

    TermDTO create(TermDTO term);

    TermDTO update(TermDTO term);

    void delete(Long id);

    List<Term> getTerms();

    TermDTO getTermDetail(Long id);

    Page<TermDTO> getByPage(Page<Term> page);
}
