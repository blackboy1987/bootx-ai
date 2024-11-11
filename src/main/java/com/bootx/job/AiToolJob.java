package com.bootx.job;

import com.bootx.entity.AiTool;
import com.bootx.entity.AiToolCategory;
import com.bootx.service.AiToolCategoryService;
import com.bootx.service.AiToolService;
import com.bootx.service.RedisService;
import com.bootx.util.caiji.aitool.Ai138Utils;
import com.bootx.util.caiji.aitool.AiBotUtils;
import com.bootx.util.caiji.aitool.AiKitUtils;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class AiToolJob {
    @Resource
    private AiToolCategoryService aiToolCategoryService;

    @Resource
    private AiToolService aiToolService;

    @Resource
    private RedisService redisService;
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Scheduled(fixedRate = 1000 * 60 * 60 * 24)
    public void category() {
        //category0();
        //detail0();
        //category1();
        //detail1();

        category2();
        detail2();
    }

    private void category1() {
        List<AiToolCategory> category1 = AiKitUtils.category();
        category1.forEach(item -> {
            try {
                aiToolCategoryService.create1(item);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println(item.getName());
            }
        });
        List<Map<String, Object>> maps1 = jdbcTemplate.queryForList("select id,type,typeId,otherUrl from aitoolcategory where type=?", "ai-kit");
        for (Map<String, Object> map : maps1) {
            String id = map.get("id") + "";
            AiToolCategory aiToolCategory = aiToolCategoryService.find(Long.valueOf(id));
            String type = map.get("type") + "";
            String typeId = map.get("typeId") + "";
            String otherUrl = map.get("otherUrl") + "";
            if (StringUtils.isEmpty(otherUrl) || StringUtils.equalsAnyIgnoreCase(otherUrl, "null")) {
                List<AiTool> list = AiKitUtils.getList(typeId.replace(type + "_", ""));
                for (AiTool aiTool : list) {
                    aiTool.setAiToolCategory(aiToolCategory);
                    aiToolService.create(aiTool);
                }
            } else {
                List<AiTool> list = AiKitUtils.getList(otherUrl, 1);
                list.addAll(AiKitUtils.getList(otherUrl, 2));
                list.addAll(AiKitUtils.getList(otherUrl, 3));
                list.addAll(AiKitUtils.getList(otherUrl, 4));
                list.addAll(AiKitUtils.getList(otherUrl, 5));
                for (AiTool aiTool : list) {
                    aiTool.setAiToolCategory(aiToolCategory);
                    aiToolService.create(aiTool);
                }
            }
        }
    }

    private void category0() {
        List<AiToolCategory> category = AiBotUtils.category();
        category.forEach(item -> {
            try {
                aiToolCategoryService.create1(item);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println(item.getName());
            }
        });
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select id,type,typeId,otherUrl from aitoolcategory where type=?", "ai-bot");
        for (Map<String, Object> map : maps) {
            String id = map.get("id") + "";
            AiToolCategory aiToolCategory = aiToolCategoryService.find(Long.valueOf(id));
            String type = map.get("type") + "";
            String typeId = map.get("typeId") + "";
            String otherUrl = map.get("otherUrl") + "";
            if (StringUtils.isEmpty(otherUrl) || StringUtils.equalsAnyIgnoreCase(otherUrl, "null")) {
                List<AiTool> list = AiBotUtils.getList(typeId.replace(type + "_", ""));
                for (AiTool aiTool : list) {
                    aiTool.setAiToolCategory(aiToolCategory);
                    aiToolService.create(aiTool);
                }
            } else {
                List<AiTool> list = AiBotUtils.getList(otherUrl, 1);
                list.addAll(AiBotUtils.getList(otherUrl, 2));
                list.addAll(AiBotUtils.getList(otherUrl, 3));
                list.addAll(AiBotUtils.getList(otherUrl, 4));
                list.addAll(AiBotUtils.getList(otherUrl, 5));
                for (AiTool aiTool : list) {
                    aiTool.setAiToolCategory(aiToolCategory);
                    aiToolService.create(aiTool);
                }
            }
        }
    }


    private void category2() {
        List<AiToolCategory> category = Ai138Utils.category();
        category.forEach(item -> {
            try {
                aiToolCategoryService.create1(item);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println(item.getName());
            }
        });
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select id,type,typeId,otherUrl from aitoolcategory");
        for (Map<String, Object> map : maps) {
            String id = map.get("id") + "";
            AiToolCategory aiToolCategory = aiToolCategoryService.find(Long.valueOf(id));
            String type = map.get("type") + "";
            String typeId = map.get("typeId") + "";
            String otherUrl = map.get("otherUrl") + "";
            if (StringUtils.isEmpty(otherUrl) || StringUtils.equalsAnyIgnoreCase(otherUrl, "null")) {
                List<AiTool> list = Ai138Utils.getList(typeId.replace(type + "_", ""));
                for (AiTool aiTool : list) {
                    aiTool.setAiToolCategory(aiToolCategory);
                    aiToolService.create(aiTool);
                }
            } else {
                List<AiTool> list = Ai138Utils.getList(otherUrl, 1);
                list.addAll(Ai138Utils.getList(otherUrl, 2));
                list.addAll(Ai138Utils.getList(otherUrl, 3));
                list.addAll(Ai138Utils.getList(otherUrl, 4));
                list.addAll(Ai138Utils.getList(otherUrl, 5));
                for (AiTool aiTool : list) {
                    aiTool.setAiToolCategory(aiToolCategory);
                    aiToolService.create(aiTool);
                }
            }
        }
    }

    private void detail0() {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select id,typeId,type,otherUrl from aitool where type=? order by id asc", "ai-bot");
        for (Map<String, Object> map : maps) {
            String otherUrl = (map.get("otherUrl") + "").trim();
            AiTool aiTool = AiBotUtils.detail(otherUrl);
            aiToolService.create(aiTool);
        }
    }

    private void detail1() {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select id,typeId,type,otherUrl from aitool where type=? order by id asc", "ai-kit");
        for (Map<String, Object> map : maps) {
            String otherUrl = (map.get("otherUrl") + "").trim();
            AiTool aiTool = AiKitUtils.detail(otherUrl);
            aiToolService.create(aiTool);
        }
    }

    private void detail2() {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select id,typeId,type,otherUrl from aitool where type=? order by id asc", "ai138");
        for (Map<String, Object> map : maps) {
            String otherUrl = (map.get("otherUrl") + "").trim();
            AiTool aiTool = Ai138Utils.detail(otherUrl);
            aiToolService.create(aiTool);
        }
    }
}
