package com.taklip.yoda.service.impl;

import com.taklip.jediorder.service.IdService;
import com.taklip.yoda.enums.ContentTypeEnum;
import com.taklip.yoda.mapper.ItemMapper;
import com.taklip.yoda.mapper.SolutionItemMapper;
import com.taklip.yoda.mapper.SolutionMapper;
import com.taklip.yoda.model.Item;
import com.taklip.yoda.model.Pagination;
import com.taklip.yoda.model.Solution;
import com.taklip.yoda.model.SolutionItem;
import com.taklip.yoda.service.FileService;
import com.taklip.yoda.service.SolutionService;
import com.taklip.yoda.tool.ImageUploader;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Transactional
@Service
public class SolutionServiceImpl implements SolutionService {
	private static Logger logger = LoggerFactory.getLogger(SolutionServiceImpl.class);

	@Autowired
	SolutionMapper solutionMapper;

	@Autowired
	SolutionItemMapper solutionItemMapper;

	@Autowired
	ItemMapper itemMapper;

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	@Autowired
	private IdService idService;

	@Autowired
	ImageUploader imageUpload;

	@Autowired
	FileService fileService;

	@Override
	public Solution getSolution(Long id) {
		Solution solution = solutionMapper.getById(id);

		solution.getSolutionItems().parallelStream().forEach(solutionItem -> {
			Item item =  itemMapper.getById(solutionItem.getItemId());
			solutionItem.setItem(item);
		});

		return solution;
	}

	@Override
	public Solution save(Solution solution) {
		if (null == solution.getId()) {
			return this.add(solution);
		}
		else {
			return this.update(solution);
		}
	}

	public Solution add(Solution solution) {
		solution.setId(idService.generateId());

		solution.preInsert();

		solutionMapper.insert(solution);

		return solution;
	}

	public Solution update(Solution solution) {
		Solution solutionDb = solutionMapper.getById(solution.getId());

		solutionDb.setTitle(solution.getTitle());
		solutionDb.setDescription(solution.getDescription());
		solutionDb.setCategoryId(solution.getCategoryId());
		solutionDb.setImagePath(solution.getImagePath());

		solutionDb.preUpdate();

		solutionMapper.update(solutionDb);

		return solutionDb;
	}

	public Solution updateSolutionImage(Long id, MultipartFile file) {
		Solution solution = solutionMapper.getById(id);

		imageUpload.deleteImage(solution.getImagePath());

		try {
			String imagePath = fileService.save(ContentTypeEnum.SOLUTION.getType(), id, file);

			solution.setImagePath(imagePath);
		}
		catch (IOException e) {
			logger.warn(e.getMessage());
		}

		solution.preUpdate();

		solutionMapper.update(solution);

		return solution;
	}

	@Override
	public void delete(Long id) {

	}

	@Override
	public List<Solution> getSolutions() {
		return solutionMapper.getSolutions();
	}

	@Override
	public Pagination<Solution> getSolutions(RowBounds rowBounds) {
		List<Solution> solutions = sqlSessionTemplate.selectList("com.taklip.yoda.mapper.SolutionMapper.getSolutions", null, rowBounds);

		List<Integer> count = sqlSessionTemplate.selectList("com.taklip.yoda.mapper.SolutionMapper.count");

		Pagination<Solution> page = new Pagination<>(rowBounds.getOffset(), count.get(0), rowBounds.getLimit(), solutions);

		return page;
	}

	@Override
	public SolutionItem getSolutionItemsById(Long id) {
		return solutionItemMapper.getById(id);
	}

	@Override
	public List<SolutionItem> getSolutionItems() {
		return solutionItemMapper.getSolutionItems();
	}

	@Override
	public List<SolutionItem> getSolutionItemsBySolutionId(Long solutionId) {
		return solutionItemMapper.getSolutionItemsBySolutionId(solutionId);
	}

	public void saveSolutionItem(SolutionItem solutionItem) {
		if (null == solutionItem.getId()) {
			this.addSolutionItem(solutionItem);
		}
		else {
			this.updateSolutionItem(solutionItem);
		}
	}

	public void addSolutionItem(SolutionItem solutionItem) {
		solutionItem.setId(idService.generateId());

		solutionItem.preInsert();

		solutionItemMapper.insert(solutionItem);
	}

	public SolutionItem updateSolutionItem(SolutionItem solutionItem) {
		solutionItem.preUpdate();

		solutionItemMapper.update(solutionItem);

		return solutionItem;
	}
}