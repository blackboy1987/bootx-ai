package com.bootx.service.impl;

import com.bootx.common.Filter;
import com.bootx.common.Order;
import com.bootx.dao.AiToolCategoryDao;
import com.bootx.entity.AiToolCategory;
import com.bootx.service.AiToolCategoryService;
import com.bootx.util.JsonUtils;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
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
public class AiToolCategoryServiceImpl extends BaseServiceImpl<AiToolCategory,Long> implements AiToolCategoryService {

    @Resource
    private AiToolCategoryDao aiToolCategoryDao;

    @Override
    public AiToolCategory create(String categoryName,String parentName) {
        AiToolCategory parent = null;
        if(StringUtils.isNotBlank(parentName)){
            parent = aiToolCategoryDao.find("name", parentName);
            if(parent==null){
                parent = new AiToolCategory();
                parent.setName(categoryName);
                parent = super.save(parent);
            }
        }
        AiToolCategory aiToolCategory = aiToolCategoryDao.find("name", categoryName);
        if(aiToolCategory!=null){
            return aiToolCategory;
        }
        aiToolCategory = new AiToolCategory();
        aiToolCategory.setName(categoryName);
        aiToolCategory.setParent(parent);
        aiToolCategory = super.save(aiToolCategory);
        return aiToolCategory;
    }


    @Override
    @Transactional(readOnly = true)
    public List<AiToolCategory> findList(Integer count, List<Filter> filters, List<Order> orders) {
        return aiToolCategoryDao.findList(count, filters, orders);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AiToolCategory> findRoots() {
        return aiToolCategoryDao.findRoots(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AiToolCategory> findRoots(Integer count) {
        return aiToolCategoryDao.findRoots(count);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AiToolCategory> findRoots(Integer count, boolean useCache) {
        return aiToolCategoryDao.findRoots(count);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AiToolCategory> findParents(AiToolCategory aiToolCategory, boolean recursive, Integer count) {
        return aiToolCategoryDao.findParents(aiToolCategory, recursive, count);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AiToolCategory> findParents(Long aiToolCategoryId, boolean recursive, Integer count, boolean useCache) {
        AiToolCategory aiToolCategory = aiToolCategoryDao.find(aiToolCategoryId);
        if (aiToolCategoryId != null && aiToolCategory == null) {
            return Collections.emptyList();
        }
        return aiToolCategoryDao.findParents(aiToolCategory, recursive, count);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AiToolCategory> findTree() {
        return aiToolCategoryDao.findChildren(null, true, null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AiToolCategory> findChildren(AiToolCategory aiToolCategory, boolean recursive, Integer count) {
        return aiToolCategoryDao.findChildren(aiToolCategory, recursive, count);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AiToolCategory> findChildren(Long aiToolCategoryId, boolean recursive, Integer count, boolean useCache) {
        AiToolCategory aiToolCategory = aiToolCategoryDao.find(aiToolCategoryId);
        if (aiToolCategoryId != null && aiToolCategory == null) {
            return Collections.emptyList();
        }
        return aiToolCategoryDao.findChildren(aiToolCategory, recursive, count);
    }

    @Override
    public AiToolCategory findByName(String name) {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select id from aiToolCategory where name=? limit 1", name);
        if(maps.isEmpty()){
            return null;
        }
        return super.find(Long.valueOf(maps.getFirst().get("id").toString()));
    }

    @Override
    public AiToolCategory create1(AiToolCategory item) {
        AiToolCategory parent = item.getParent();
        if(parent==null){
            AiToolCategory aiToolCategory = aiToolCategoryDao.find("typeId", item.getTypeId());
            if(aiToolCategory==null){
                item.setGrade(0);
                item.setTreePath(AiToolCategory.TREE_PATH_SEPARATOR);
                return super.save(item);
            }else{
                aiToolCategory.setOtherUrl(item.getOtherUrl());
                aiToolCategory.setTypeId(item.getTypeId());
                aiToolCategory.setType(item.getType());
                aiToolCategory.setName(item.getName());
                return super.update(aiToolCategory);
            }
        }else{
            AiToolCategory parent1 = aiToolCategoryDao.find("typeId", item.getParent().getTypeId());
            if(parent1==null){
                item.setGrade(0);
                item.setTreePath(AiToolCategory.TREE_PATH_SEPARATOR);
                parent1 = super.save(item);
            }else{
                parent1.setOtherUrl(item.getOtherUrl());
                parent1.setTypeId(item.getTypeId());
                parent1.setType(item.getType());
                parent1.setName(item.getName());
                parent1 = super.update(parent1);
            }
            String typeId = item.getTypeId();
            AiToolCategory aiToolCategory = aiToolCategoryDao.find("typeId", typeId);
            if(aiToolCategory==null){
                item.setParent(parent1);
                item.setGrade(parent1.getGrade()+1);
                item.setTreePath(parent1.getTreePath()+parent1.getId()+AiToolCategory.TREE_PATH_SEPARATOR);
                return super.save(item);
            }else{
                aiToolCategory.setParent(parent1);
                aiToolCategory.setGrade(parent1.getGrade()+1);
                aiToolCategory.setTreePath(parent1.getTreePath()+parent1.getId()+AiToolCategory.TREE_PATH_SEPARATOR);
                aiToolCategory.setOtherUrl(item.getOtherUrl());
                aiToolCategory.setTypeId(item.getTypeId());
                aiToolCategory.setType(item.getType());
                aiToolCategory.setName(item.getName());
                return super.update(aiToolCategory);
            }
        }

    }

    @Override
    @Transactional
    public AiToolCategory save(AiToolCategory aiToolCategory) {
        Assert.notNull(aiToolCategory, "[Assertion failed] - aiToolCategory is required; it must not be null");
        setValue(aiToolCategory);
        return super.save(aiToolCategory);
    }

    @Override
    @Transactional
    public AiToolCategory update(AiToolCategory aiToolCategory) {
        Assert.notNull(aiToolCategory, "[Assertion failed] - aiToolCategory is required; it must not be null");

        setValue(aiToolCategory);
        for (AiToolCategory children : aiToolCategoryDao.findChildren(aiToolCategory, true, null)) {
            setValue(children);
        }
        return super.update(aiToolCategory);
    }

    @Override
    @Transactional
    public AiToolCategory update(AiToolCategory aiToolCategory, String... ignoreProperties) {
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
    public void delete(AiToolCategory aiToolCategory) {
        super.delete(aiToolCategory);
    }

    /**
     * 设置值
     *
     * @param aiToolCategory
     *            商品分类
     */
    private void setValue(AiToolCategory aiToolCategory) {
        if (aiToolCategory == null) {
            return;
        }
        AiToolCategory parent = aiToolCategory.getParent();
        if (parent != null) {
            aiToolCategory.setTreePath(parent.getTreePath() + parent.getId() + AiToolCategory.TREE_PATH_SEPARATOR);
            aiToolCategory.setGrade(0);
        } else {
            aiToolCategory.setTreePath(AiToolCategory.TREE_PATH_SEPARATOR);
            aiToolCategory.setGrade(0);
        }
    }
}
