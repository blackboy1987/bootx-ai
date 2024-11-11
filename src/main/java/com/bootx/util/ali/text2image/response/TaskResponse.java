package com.bootx.util.ali.text2image.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.collections.ArrayStack;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TaskResponse implements Serializable {

    @JsonProperty("request_id")
    private String requestId;

    private Output output;

    private Usage usage;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Output getOutput() {
        return output;
    }

    public void setOutput(Output output) {
        this.output = output;
    }

    public Usage getUsage() {
        return usage;
    }

    public void setUsage(Usage usage) {
        this.usage = usage;
    }

    public static class Output implements Serializable{

        @JsonProperty("task_status")
        private String taskStatus;

        @JsonProperty("task_id")
        private String taskId;

        @JsonProperty("submit_time")
        private String submitTime;

        @JsonProperty("scheduled_time")
        private String scheduledTime;

        @JsonProperty("end_time")
        private String endTime;

        private List<Result> results = new ArrayStack();

        @JsonProperty("task_metrics")
        private TaskMetrics task_metrics;


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

        public String getSubmitTime() {
            return submitTime;
        }

        public void setSubmitTime(String submitTime) {
            this.submitTime = submitTime;
        }

        public String getScheduledTime() {
            return scheduledTime;
        }

        public void setScheduledTime(String scheduledTime) {
            this.scheduledTime = scheduledTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public List<Result> getResults() {
            return results;
        }

        public void setResults(List<Result> results) {
            this.results = results;
        }

        public TaskMetrics getTask_metrics() {
            return task_metrics;
        }

        public void setTask_metrics(TaskMetrics task_metrics) {
            this.task_metrics = task_metrics;
        }

        public static class Result implements Serializable{
            private String url;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class TaskMetrics implements Serializable{

            @JsonProperty("TOTAL")
            private Integer total;

            @JsonProperty("SUCCEEDED")
            private Integer succeeded;

            @JsonProperty("FAILED")
            private Integer failed;

            public Integer getTotal() {
                return total;
            }

            public void setTotal(Integer total) {
                this.total = total;
            }

            public Integer getSucceeded() {
                return succeeded;
            }

            public void setSucceeded(Integer succeeded) {
                this.succeeded = succeeded;
            }

            public Integer getFailed() {
                return failed;
            }

            public void setFailed(Integer failed) {
                this.failed = failed;
            }
        }
    }

    public static class Usage implements Serializable{

        @JsonProperty("image_count")
        private Integer imageCount;

        public Integer getImageCount() {
            return imageCount;
        }

        public void setImageCount(Integer imageCount) {
            this.imageCount = imageCount;
        }
    }
}
