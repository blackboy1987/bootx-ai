package com.bootx.service.impl;

import com.bootx.common.Filter;
import com.bootx.common.Order;
import com.bootx.dao.AiCategoryDao;
import com.bootx.dao.AiToolCategoryDao;
import com.bootx.entity.AiCategory;
import com.bootx.service.AiCategoryService;
import com.bootx.service.AiToolCategoryService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author black
 */
@Service
public class AiCategoryServiceImpl extends BaseServiceImpl<AiCategory,Long> implements AiCategoryService {

    @Resource
    private AiCategoryDao aiCategoryDao;

    @Override
    public AiCategory create(String categoryName,String parentName) {
        AiCategory parent = null;
        if(StringUtils.isNotBlank(parentName)){
            parent = aiCategoryDao.find("name", parentName);
            if(parent==null){
                parent = new AiCategory();
                parent.setName(categoryName);
                parent = super.save(parent);
            }
        }
        AiCategory aiToolCategory = aiCategoryDao.find("name", categoryName);
        if(aiToolCategory!=null){
            return aiToolCategory;
        }
        aiToolCategory = new AiCategory();
        aiToolCategory.setName(categoryName);
        aiToolCategory.setParent(parent);
        aiToolCategory = super.save(aiToolCategory);
        return aiToolCategory;
    }


    @Override
    @Transactional(readOnly = true)
    public List<AiCategory> findList(Integer count, List<Filter> filters, List<Order> orders) {
        return aiCategoryDao.findList(count, filters, orders);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AiCategory> findRoots() {
        return aiCategoryDao.findRoots(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AiCategory> findRoots(Integer count) {
        return aiCategoryDao.findRoots(count);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AiCategory> findRoots(Integer count, boolean useCache) {
        return aiCategoryDao.findRoots(count);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AiCategory> findParents(AiCategory aiToolCategory, boolean recursive, Integer count) {
        return aiCategoryDao.findParents(aiToolCategory, recursive, count);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AiCategory> findParents(Long aiToolCategoryId, boolean recursive, Integer count, boolean useCache) {
        AiCategory aiToolCategory = aiCategoryDao.find(aiToolCategoryId);
        if (aiToolCategoryId != null && aiToolCategory == null) {
            return Collections.emptyList();
        }
        return aiCategoryDao.findParents(aiToolCategory, recursive, count);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AiCategory> findTree() {
        return aiCategoryDao.findChildren(null, true, null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AiCategory> findChildren(AiCategory aiToolCategory, boolean recursive, Integer count) {
        return aiCategoryDao.findChildren(aiToolCategory, recursive, count);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AiCategory> findChildren(Long aiToolCategoryId, boolean recursive, Integer count, boolean useCache) {
        AiCategory aiToolCategory = aiCategoryDao.find(aiToolCategoryId);
        if (aiToolCategoryId != null && aiToolCategory == null) {
            return Collections.emptyList();
        }
        return aiCategoryDao.findChildren(aiToolCategory, recursive, count);
    }

    @Override
    public AiCategory findByName(String name) {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select id from aiToolCategory where name=? limit 1", name);
        if(maps.isEmpty()){
            return null;
        }
        return super.find(Long.valueOf(maps.getFirst().get("id").toString()));
    }


    @Override
    @Transactional
    public AiCategory save(AiCategory aiToolCategory) {
        Assert.notNull(aiToolCategory, "[Assertion failed] - aiToolCategory is required; it must not be null");
        setValue(aiToolCategory);
        return super.save(aiToolCategory);
    }

    @Override
    @Transactional
    public AiCategory update(AiCategory aiToolCategory) {
        Assert.notNull(aiToolCategory, "[Assertion failed] - aiToolCategory is required; it must not be null");

        setValue(aiToolCategory);
        for (AiCategory children : aiCategoryDao.findChildren(aiToolCategory, true, null)) {
            setValue(children);
        }
        return super.update(aiToolCategory);
    }

    @Override
    @Transactional
    public AiCategory update(AiCategory aiToolCategory, String... ignoreProperties) {
        return super.update(aiToolCategory, ignoreProperties);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        super.delete(id);
    }

    @Override
    @Transactional
    public void delete(Long... ids) {
        super.delete(ids);
    }

    @Override
    @Transactional
    public void delete(AiCategory aiToolCategory) {
        super.delete(aiToolCategory);
    }

    /**
     * 设置值
     *
     * @param aiToolCategory
     *            商品分类
     */
    private void setValue(AiCategory aiToolCategory) {
        if (aiToolCategory == null) {
            return;
        }
        AiCategory parent = aiToolCategory.getParent();
        if (parent != null) {
            aiToolCategory.setTreePath(parent.getTreePath() + parent.getId() + AiCategory.TREE_PATH_SEPARATOR);
            aiToolCategory.setGrade(0);
        } else {
            aiToolCategory.setTreePath(AiCategory.TREE_PATH_SEPARATOR);
            aiToolCategory.setGrade(0);
        }
    }
}
