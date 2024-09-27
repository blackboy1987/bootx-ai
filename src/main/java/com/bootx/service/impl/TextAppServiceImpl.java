package com.bootx.service.impl;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.dao.TextAppDao;
import com.bootx.entity.TextApp;
import com.bootx.entity.TextAppCategory;
import com.bootx.repository.TextAppRepository;
import com.bootx.service.TextAppService;
import jakarta.annotation.Resource;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * @author black
 */
@Service
public class TextAppServiceImpl extends BaseServiceImpl<TextApp,Long> implements TextAppService {

    @Resource
    private TextAppDao textAppDao;

    @Resource
    private TextAppRepository textAppRepository;

    @Override
    public TextApp findByName(String name) {
        return textAppDao.find("name",name);
    }

    @Override
    public Page<TextApp> findPage(Pageable pageable, String name, TextAppCategory textAppCategory) {
        org.springframework.data.domain.Page<TextApp> all = textAppRepository.findAll((Specification<TextApp>) (root, query, criteriaBuilder) -> {
            Predicate restrictions = criteriaBuilder.conjunction();
            if (StringUtils.isNotBlank(name)) {
                restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("name"), name));
            }
            if (textAppCategory!=null) {
                restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("textAppCategory"), textAppCategory));
            }
            return restrictions;
        }, org.springframework.data.domain.Pageable.ofSize(pageable.getPageSize()).withPage(pageable.getPageNumber() - 1));


        return new Page<>(all.getContent(),all.getTotalElements(),pageable);
    }

}
