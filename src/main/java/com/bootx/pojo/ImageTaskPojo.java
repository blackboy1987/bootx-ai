package com.bootx.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity-ImageTaskPojo
 *
 * @author 一枚猿：blackboyhjy1987
 */
public class ImageTaskPojo {

    @JsonProperty("request_id")
    private String requestId;
    private OutputDTO output = new OutputDTO();
    private UsageDTO usage = new UsageDTO();

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

    public UsageDTO getUsage() {
        return usage;
    }

    public void setUsage(UsageDTO usage) {
        this.usage = usage;
    }

    public static class OutputDTO {
        @JsonProperty("task_id")
        private String taskId;
        @JsonProperty("task_status")
        private String taskStatus;
        @JsonProperty("submit_time")
        private String submitTime;
        @JsonProperty("scheduled_time")
        private String scheduledTime;
        @JsonProperty("end_time")
        private String endTime;
        private List<ResultsDTO> results = new ArrayList<>();
        @JsonProperty("task_metrics")
        private TaskMetricsDTO taskMetrics = new TaskMetricsDTO();

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

        public List<ResultsDTO> getResults() {
            return results;
        }

        public void setResults(List<ResultsDTO> results) {
            this.results = results;
        }

        public TaskMetricsDTO getTaskMetrics() {
            return taskMetrics;
        }

        public void setTaskMetrics(TaskMetricsDTO taskMetrics) {
            this.taskMetrics = taskMetrics;
        }

        public static class TaskMetricsDTO {
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

        public static class ResultsDTO {
            @JsonProperty("url")
            private String url;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }

    public static class UsageDTO {
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
