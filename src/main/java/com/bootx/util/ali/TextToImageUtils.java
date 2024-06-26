package com.bootx.util.ali;

import com.alibaba.dashscope.utils.Constants;
import com.bootx.util.JsonUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.HashMap;
import java.util.Map;

/**
 * @author black
 */
public class TextToImageUtils {

    public static String token = "sk-48fb566bdf2d47b7be330ed85acfb883";
    public static Output create(String prompt,String style,String size){
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
        Output output = JsonUtils.toObject(s, new TypeReference<Output>() {
        });
        System.out.println(s);
        return output;
    }

    public static class Output{

        @JsonProperty("request_id")
        private String requestId;
        private OutputDTO output;

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

        public static class OutputDTO {
            @JsonProperty("task_id")
            private String taskId;
            @JsonProperty("task_status")
            private String taskStatus;

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
        }
    }
}
