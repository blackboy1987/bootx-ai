package com.bootx.util.caiji.aitool;

import com.bootx.entity.AiTool;
import com.bootx.entity.AiToolCategory;
import com.bootx.util.WebUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * https://ai-kit.cn/sites/1003.html
 */
public class Ai138Utils {
    private static final String TYPE = "ai138";

    private static final String baseUrl="https://www.ai138.com/";

    public static AiTool get(String id){
        AiTool aiTool = new AiTool();
        aiTool.setType(TYPE);
        String s = WebUtils.get(baseUrl+"link/"+id+".html", null);
        Document parse = Jsoup.parse(s);
        aiTool.setOtherUrl(baseUrl+"link/"+id+".html");

        // 网站图标
        Element content = parse.getElementById("content");
        Elements select = content.select("img.img-cover");
        String icon = select.attr("data-src");
        if(StringUtils.isNotBlank(icon)){
            aiTool.setCover(icon);
            aiTool.setTypeId(TYPE+"_"+id);
        }


        Elements elementsByClass = parse.getElementsByClass("site-content");
        if(!elementsByClass.isEmpty()){
            Element first1 = elementsByClass.first();
            // 分类。注意会有多级分类
            if(first1!=null){
                Elements elementsByClass1 = first1.getElementsByClass("btn-cat");
                List<String> categories = new ArrayList<>();
                for (Element element : elementsByClass1) {
                    categories.add(element.text());
                }
                aiTool.setCategoryNames(categories);
            }
            if(!elementsByClass.isEmpty()){
                Element first = elementsByClass.first();
                if (first!=null) {
                    // 名称
                    Elements elementsByClass2 = first.getElementsByClass("titlesite");
                    aiTool.setName(elementsByClass2.text());
                    // 简介
                    Elements elementsByClass3 = first.getElementsByClass("mb-2");
                    aiTool.setMemo(elementsByClass3.text());
                    // 标签
                    Elements elementsByClass4 = first.getElementsByClass("styled-link");
                    aiTool.setTag(elementsByClass4.text().replace(" 访问官网",""));
                    // 网站地址。有可能会没有
                    Elements elementsByClass5 = first.getElementsByClass("site-go-url");
                    if(!elementsByClass5.isEmpty()){
                        Elements a = Objects.requireNonNull(elementsByClass5.first()).getElementsByTag("a");
                        if(!a.isEmpty()){
                            aiTool.setUrl(a.attr("href"));
                        }
                    }
                }
            }
        }
        // 内容
        Elements elementsByClass1 = parse.getElementsByClass("content-wrap");
        if(!elementsByClass1.isEmpty()){
            Elements elementsByClass2 = elementsByClass1.first().getElementsByClass("panel-body");
            Element first = elementsByClass2.first();
            if(first!=null){
                for (Element a : first.getElementsByTag("a")) {
                    a.removeAttr("href");
                }
                aiTool.setDescription(elementsByClass2.html());
            }
        }
        if(StringUtils.isBlank(aiTool.getName())){
         return null;
        }
        return aiTool;
    }

    public static List<AiTool> getIcon(){
        List<AiTool> aiTools = new ArrayList<>();
        for (int i = 1; i < 100; i++) {
            String s = WebUtils.get(baseUrl+"wp-admin/admin-ajax.php?id=" + i + "&taxonomy=favorites&action=load_home_tab&post_id=0&sidebar=0", null);
            Document parse = Jsoup.parse(s);
            Elements elementsByClass = parse.getElementsByClass("url-card");
            for (Element byClass : elementsByClass) {
                AiTool aiTool = new AiTool();
                Element first = byClass.getElementsByTag("a").first();
                if(first!=null){
                    String href = first.attr("href");
                    String typeId = href.replaceAll(baseUrl+"link/", "").replaceAll(".html", "");
                    aiTool.setTypeId(TYPE+"_"+typeId);
                    String icon = first.getElementsByTag("img").attr("src");
                    aiTool.setIcon(icon);
                    Elements strong = first.getElementsByTag("strong");
                    aiTool.setName(strong.text());
                    Elements p = first.getElementsByTag("overflowClip_1");
                    aiTool.setMemo(p.text());
                    aiTools.add(aiTool);
                }
            }
            System.out.println(i);
        }
        System.out.println(aiTools.size());
        return aiTools;
    }

    public static List<AiToolCategory> category(){
        List<AiToolCategory> aiToolCategories = new ArrayList<>();
        String s = WebUtils.get(baseUrl, null);
        Document parse = Jsoup.parse(s);
        Element content = parse.getElementById("content");
        Elements h4List = content.getElementsByTag("h4");
        for (Element h4 : h4List) {
            if (StringUtils.equalsAnyIgnoreCase(h4.text(),"友情链接")){
                continue;
            }
            AiToolCategory parentCategory = new AiToolCategory();
            String id = h4.getElementsByTag("i").first().attr("id").replace("term-","");
            parentCategory.setType(TYPE);
            parentCategory.setTypeId(TYPE+"_"+id);
            parentCategory.setName(h4.text());
            aiToolCategories.add(parentCategory);
            // 他的二级分类
            Element element = h4.nextElementSibling();
            Elements lis = element.getElementsByTag("li");
            for (Element li : lis) {
                Elements a = li.getElementsByTag("a");
                String id1 = a.attr("id").replace("term-","").replace(id+"-","");
                String url = a.attr("data-link");
                AiToolCategory child = new AiToolCategory();
                child.setTypeId(TYPE+"_"+id1);
                child.setParent(parentCategory);
                child.setType(TYPE);
                child.setName(a.text());
                child.setOtherUrl(url);
                aiToolCategories.add(child);
            }
        }
        return aiToolCategories;
    }

    public static List<AiTool> getList(String categoryId){
        List<AiTool> aiTools = new ArrayList<>();
        String s = WebUtils.get(baseUrl+"wp-admin/admin-ajax.php?id=" + categoryId + "&taxonomy=favorites&action=load_home_tab&post_id=0&sidebar=0", null);
        Document parse = Jsoup.parse(s);
        Elements elementsByClass = parse.getElementsByClass("url-card");
        for (Element byClass : elementsByClass) {
            AiTool aiTool = new AiTool();
            aiTool.setType(TYPE);
            Element first = byClass.getElementsByTag("a").first();
            if(first!=null){
                String href = first.attr("href");
                aiTool.setOtherUrl(href);
                String typeId = href.replaceAll(baseUrl+"link/", "").replaceAll(".html", "");
                aiTool.setTypeId(TYPE+"_"+typeId);
                String icon = first.getElementsByTag("img").attr("src");
                aiTool.setIcon(icon);
                Elements strong = first.getElementsByTag("strong");
                aiTool.setName(strong.text());
                Elements p = first.getElementsByClass("overflowClip_1");
                aiTool.setMemo(p.text());
                aiTools.add(aiTool);
            }
        }
        return aiTools;
    }

    public static List<AiTool> getList(String url,Integer page){
        List<AiTool> aiTools = new ArrayList<>();
        String s = null;
        try {
            if(page==1){
                s = WebUtils.get(url, null);
            }else{
                s = WebUtils.get(url+"/page/"+page, null);
            }
        }catch (Exception e){
            return aiTools;
        }
        Document parse = Jsoup.parse(s);
        Elements elementsByClass = parse.getElementsByClass("url-card");
        for (Element byClass : elementsByClass) {
            AiTool aiTool = new AiTool();
            aiTool.setType(TYPE);
            Element first = byClass.getElementsByTag("a").first();
            if(first!=null){
                String href = first.attr("href");
                aiTool.setOtherUrl(href);
                String typeId = href.replaceAll(baseUrl+"link/", "").replaceAll(".html", "");
                aiTool.setTypeId(TYPE+"_"+typeId);
                String icon = first.getElementsByTag("img").attr("src");
                aiTool.setIcon(icon);
                Elements strong = first.getElementsByTag("strong");
                aiTool.setName(strong.text());
                Elements p = first.getElementsByClass("overflowClip_1");
                aiTool.setMemo(p.text());
                aiTools.add(aiTool);
            }
        }
        return aiTools;
    }

    public static void main(String[] args) {
        AiTool aiTool = get("421");

        System.out.println(aiTool);
    }
}
