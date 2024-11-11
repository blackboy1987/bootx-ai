package com.bootx.util.ali.text2image.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.result.Output;

import java.io.Serializable;

/**
 * {"output":{"task_status":"PENDING","task_id":"dd2d527b-3009-4f93-80fd-31685247fdfa"},"request_id":"b1b0c6fa-3a85-9301-9175-500c4cfb5677"}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageSynthesisResponse implements Serializable {

    private Output output;

    @JsonProperty("request_id")
    private String requestId;

    public Output getOutput() {
        return output;
    }

    public void setOutput(Output output) {
        this.output = output;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Output implements Serializable{

        @JsonProperty("task_status")
        private String taskStatus;

        @JsonProperty("task_id")
        private String taskId;

        public String getTaskStatus() {
            return taskStatus;
        }

        public void setTaskStatus(String taskStatus) {
            this.taskStatus = taskStatus;
        }

        public String getTaskId() {
            return taskId;
        }

        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }
    }
}