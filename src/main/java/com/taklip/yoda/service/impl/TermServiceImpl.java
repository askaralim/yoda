package com.taklip.yoda.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taklip.yoda.convertor.ModelConvertor;
import com.taklip.yoda.dto.TermDTO;
import com.taklip.yoda.mapper.TermMapper;
import com.taklip.yoda.model.Term;
import com.taklip.yoda.service.TermService;
import com.taklip.yoda.service.UserService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author askar
 */
@Transactional
@Service
@Slf4j
public class TermServiceImpl extends ServiceImpl<TermMapper, Term> implements TermService {
    @Autowired
    private ModelConvertor modelConvertor;

    @Autowired
    private UserService userService;

    @Override
    public TermDTO create(TermDTO termDto) {
        Term term = new Term();
        term.setTitle(termDto.getTitle());
        term.setDescription(termDto.getDescription());
        term.setContentId(termDto.getContentId());
        term.setCategoryId(termDto.getCategoryId());
        term.setHitCounter(termDto.getHitCounter());

        this.save(term);

        return modelConvertor.convertToTermDTO(term);
    }

    @Override
    public TermDTO update(TermDTO termDTO) {
        Term term = new Term();
        term.setId(termDTO.getId());
        term.setTitle(termDTO.getTitle());
        term.setDescription(termDTO.getDescription());
        term.setContentId(termDTO.getContentId());
        term.setCategoryId(termDTO.getCategoryId());
        term.setHitCounter(termDTO.getHitCounter());
        this.updateById(term);
        return modelConvertor.convertToTermDTO(term);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Term> getTerms() {
        return this.list();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TermDTO> getByPage(Page<Term> page) {
        Page<Term> termPage = this.page(page, new QueryWrapper<Term>().orderByDesc("id"));
        Page<TermDTO> termDTOPage = new Page<>();
        termDTOPage.setTotal(termPage.getTotal());
        termDTOPage.setCurrent(termPage.getCurrent());
        termDTOPage.setSize(termPage.getSize());

        List<TermDTO> records = new ArrayList<>();

        for (Term term : termPage.getRecords()) {
            TermDTO termDTO = modelConvertor.convertToTermDTO(term);
            termDTO.setCreateBy(
                    modelConvertor.convertToUserDTO(userService.getUser(term.getCreateBy())));
            termDTO.setUpdateBy(
                    modelConvertor.convertToUserDTO(userService.getUser(term.getUpdateBy())));
            records.add(termDTO);
        }

        termDTOPage.setRecords(records);

        return termDTOPage;
    }

    @Override
    @Transactional(readOnly = true)
    public TermDTO getTermDetail(Long id) {
        Term term = this.getById(id);
        TermDTO termDTO = modelConvertor.convertToTermDTO(term);
        termDTO.setCreateBy(
                modelConvertor.convertToUserDTO(userService.getUser(term.getCreateBy())));
        return termDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public Term getTerm(Long id) {
        return this.getById(id);
    }

    @Override
    public void delete(Long id) {
        this.removeById(id);
    }
}
