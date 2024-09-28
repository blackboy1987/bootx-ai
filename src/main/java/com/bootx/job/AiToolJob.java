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
        //category1();
        category2();
        detail2();
    }

    private void detail() {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select id,typeId,type from aitool;");
        for (Map<String, Object> map : maps) {
            String s = map.get("id") + "";
            String typeId = (map.get("typeId") + "").trim();
            String type = map.get("type") + "";
            if (StringUtils.equals(type, "ai-bot")) {
                AiTool aiTool = AiBotUtils.get(typeId.replace(type + "_", ""));
                aiToolService.create(aiTool);
            } else if (StringUtils.equals(type, "ai-kit")) {
                AiTool aiTool = AiKitUtils.get(typeId.replace(type + "_", ""));
                aiToolService.create(aiTool);
            }
        }
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
        List<Map<String, Object>> maps1 = jdbcTemplate.queryForList("select id,type,typeId,otherUrl from aitoolcategory");
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
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select id,type,typeId,otherUrl from aitoolcategory");
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


    //@Scheduled(fixedRate = 1000 * 60 * 60 * 24)
    public void run() {
        int start1 = 1;
        String s1 = redisService.get("1_");
        try {
            start1 = Integer.parseInt(s1);
        } catch (Exception ignored) {
        }
        start(start1, 100000);


        int start2 = 1;
        String s2 = redisService.get("2_");
        try {
            start2 = Integer.parseInt(s2);
        } catch (Exception ignored) {
        }
        start(start1, 100000);
        start1(start2, 100000);
    }

    public void start(int start, int end) {
        long begin = System.currentTimeMillis();
        for (int i = start; i < end; i++) {
            AiTool aiTool = AiKitUtils.get(i + "");
            int finalI = i;
            new Thread(() -> {
                if (aiTool != null) {
                    AiToolCategory aiToolCategory = aiTool.getAiToolCategory();
                    aiToolCategory = aiToolCategoryService.create1(aiToolCategory);
                    aiTool.setAiToolCategory(aiToolCategory);
                    aiToolService.create(aiTool);
                    System.out.println(finalI + ":========================ok");
                } else {
                    System.out.println(finalI + ":no");
                }
            }).start();
            redisService.set("1_", finalI + "");
        }
        System.out.println(start + ":" + end + "==========结束:" + (System.currentTimeMillis() - begin));
    }

    public void start1(int start, int end) {
        long begin = System.currentTimeMillis();
        for (int i = start; i < end; i++) {
            AiTool aiTool = AiBotUtils.get(i + "");
            int finalI = i;
            new Thread(() -> {
                if (aiTool != null) {
                    AiToolCategory aiToolCategory = aiTool.getAiToolCategory();
                    aiToolCategory = aiToolCategoryService.create1(aiToolCategory);
                    aiTool.setAiToolCategory(aiToolCategory);
                    aiToolService.create(aiTool);
                    System.out.println(finalI + ":========================ok");
                } else {
                    System.out.println(finalI + ":no");
                }
            }).start();
            redisService.set("2_", finalI + "");
        }
        System.out.println(start + ":" + end + "==========结束:" + (System.currentTimeMillis() - begin));
    }

    private void detail2() {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select id,typeId,type from aitool where type=?","ai138");
        for (Map<String, Object> map : maps) {
            String s = map.get("id") + "";
            String typeId = (map.get("typeId") + "").trim();
            String type = map.get("type") + "";
            if (StringUtils.equals(type, "ai-bot")) {
                AiTool aiTool = AiBotUtils.get(typeId.replace(type + "_", ""));
                aiToolService.create(aiTool);
            } else if (StringUtils.equals(type, "ai-kit")) {
                AiTool aiTool = AiKitUtils.get(typeId.replace(type + "_", ""));
                aiToolService.create(aiTool);
            }
        }
    }
}
