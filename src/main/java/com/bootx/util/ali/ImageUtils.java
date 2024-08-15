package com.bootx.util.ali;

import com.bootx.util.JsonUtils;
import com.bootx.util.WebUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.collections.ArrayStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImageUtils {

    public static String token = "sk-48fb566bdf2d47b7be330ed85acfb883";

    public static void backgroundGeneration(Map<String,Object> parameters,String model){
        Map<String,String> headers = new HashMap<>();
        headers.put("X-DashScope-Async","enable");
        headers.put("Authorization","Bearer "+token);
        parameters.put("model",model);
        String url="https://dashscope.aliyuncs.com/api/v1/services/aigc/background-generation/generation/";
        String s = WebUtils.postBody(url, parameters, headers);
        System.out.println(s);
    }

    public static TaskResponse text2image(String prompt, String style, String size){
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("model","wanx-v1");

        Map<String, Object> input = new HashMap<>();
        input.put("prompt",prompt);
        //input.put("negative_prompt","");
        //input.put("ref_img","");
        parameters.put("input",input);
        /**
         * 输出图像的风格，目前支持以下风格取值：
         *
         * "<photography>" 摄影,
         * "<portrait>" 人像写真,
         * "<3d cartoon>" 3D卡通,
         * "<anime>" 动画,
         * "<oil painting>" 油画,
         * "<watercolor>"水彩,
         * "<sketch>" 素描,
         * "<chinese painting>" 中国画,
         * "<flat illustration>" 扁平插画,
         * "<auto>" 默认
         */
        parameters.put("style",style);
        parameters.put("size",size);
        parameters.put("n",1);
        /**
         * 图片生成时候的种子值，取值范围为(0, 4294967290) 。
         * 如果不提供，则算法自动用一个随机生成的数字作为种子，
         * 如果给定了，则根据 batch 数量分别生成 seed，seed+1，seed+2，seed+3为参数的图片。
         */
        parameters.put("seed",42);
        /**
         * 期望输出结果与垫图（参考图）的相似度，取值范围[0.0, 1.0]，数字越大，生成的结果与参考图越相似
         */
        parameters.put("ref_strength",0.5);
        /**
         * 垫图（参考图）生图使用的生成方式，
         * 可选值为'repaint' （默认） 和 'refonly';
         * 其中 repaint代表参考内容，refonly代表参考风格
         */
        parameters.put("ref_mode","repaint");
        String s= AliCommonUtils.create("https://dashscope.aliyuncs.com/api/v1/services/aigc/text2image/image-synthesis",parameters);
        TaskResponse output = JsonUtils.toObject(s, new TypeReference<TaskResponse>() {
        });
        System.out.println(s);
        return output;
    }

    /**
     * 人像风格重绘
     * @param imageUrl
     *      输入的图像 URL，
     *      分辨率：可支持输入分辨率范围：不小于256*256，不超过5760*3240, 长宽比不超过1.5:1，若超出1.5:1将自适应裁剪到1.5:1
     *      为确保生成质量，请上传脸部清晰照片，人脸比例不宜过小，并避免夸张姿势和表情
     *      类型：JPEG，PNG，JPG，BMP，WEBP
     *      大小：不超过10M
     * @param styleIndex
     *      想要生成的风格化类型索引：
     *      -1 参考上传图像风格
     *      0 复古漫画
     *      1 3D童话
     *      2 二次元
     *      3 小清新
     *      4 未来科技
     *      5 国画古风
     *      6 将军百战
     *      7 炫彩卡通
     *      8 清雅国风
     *      9 喜迎新年
     * @param styleRefUrl
     *      当styleIndex=-1时，必须传入； 其他风格，无需传入
     *      输入的图像 URL，
     *      分辨率：可支持输入分辨率范围：不小于256*256，不超过5760*3240, 长宽比不超过2:1
     *      类型：JPEG，PNG，JPG，BMP，WEBP
     *      大小：不超过10M
     * @return
     */
    public static TaskResponse repaint(String imageUrl, String styleIndex, String styleRefUrl){
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("model","wanx-style-repaint-v1");
        Map<String, Object> input = new HashMap<>();
        input.put("image_url",imageUrl);
        input.put("style_index",styleIndex);
        input.put("style_ref_url",styleRefUrl);
        parameters.put("input",input);
        String s= AliCommonUtils.create("https://dashscope.aliyuncs.com/api/v1/services/aigc/image-generation/generation",parameters);
        TaskResponse output = JsonUtils.toObject(s, new TypeReference<TaskResponse>() {
        });
        System.out.println(s);
        return output;
    }






    public static TaskResponse.OutputDTO getTask(String taskId){
        Map<String,String> headers = new HashMap<>();
        headers.put("X-DashScope-Async","enable");
        headers.put("Authorization","Bearer "+token);
        String s = WebUtils.get("https://dashscope.aliyuncs.com/api/v1/tasks/" + taskId, headers, null);
        TaskResponse task = JsonUtils.toObject(s, new TypeReference<TaskResponse>() {});
        return task.getOutput();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TaskResponse{

        @JsonProperty("request_id")
        private String requestId;
        private OutputDTO output = new OutputDTO();

        public String getRequestId() {
            return requestId;
        }

        public void setRequestId(String requestId) {
            this.requestId = requestId;
        }

        public OutputDTO getOutput() {
            return output;
        }

        public void setOutput(OutputDTO output) {
            this.output = output;
        }
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class OutputDTO {
            @JsonProperty("task_id")
            private String taskId;
            @JsonProperty("task_status")
            private String taskStatus;

            private List<Image> results = new ArrayStack();

            public String getTaskId() {
                return taskId;
            }

            public void setTaskId(String taskId) {
                this.taskId = taskId;
            }

            public String getTaskStatus() {
                return taskStatus;
            }

            public void setTaskStatus(String taskStatus) {
                this.taskStatus = taskStatus;
            }

            public List<Image> getResults() {
                return results;
            }

            public void setResults(List<Image> results) {
                this.results = results;
            }

            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Image {
                private String url;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }

        }
    }
}
