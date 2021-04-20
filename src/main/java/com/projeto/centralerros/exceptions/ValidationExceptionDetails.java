package com.projeto.centralerros.exceptions;

import lombok.Getter;

@Getter
public class ValidationExceptionDetails extends ExceptionDetails {

    private String field;
    private String fieldMessage;

    public static final class ValidationExceptionDetailsBuilder {
        private String title;
        private int status;
        private String detail;
        private long timestamp;
        private String developerMessage;
        private String field;
        private String fieldMessage;

        private ValidationExceptionDetailsBuilder() {
        }

        public static ValidationExceptionDetails.ValidationExceptionDetailsBuilder newBuilder() {
            return new ValidationExceptionDetails.ValidationExceptionDetailsBuilder();
        }

        public ValidationExceptionDetails.ValidationExceptionDetailsBuilder title(String title) {
            this.title = title;
            return this;
        }

        public ValidationExceptionDetails.ValidationExceptionDetailsBuilder status(int status) {
            this.status = status;
            return this;
        }

        public ValidationExceptionDetails.ValidationExceptionDetailsBuilder detail(String detail) {
            this.detail = detail;
            return this;
        }

        public ValidationExceptionDetails.ValidationExceptionDetailsBuilder timestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public ValidationExceptionDetails.ValidationExceptionDetailsBuilder developerMessage(String developerMessage) {
            this.developerMessage = developerMessage;
            return this;
        }

        public ValidationExceptionDetails.ValidationExceptionDetailsBuilder field(String field) {
            this.field = field;
            return this;
        }

        public ValidationExceptionDetails.ValidationExceptionDetailsBuilder fieldMessage(String fieldMessage) {
            this.fieldMessage = fieldMessage;
            return this;
        }

        public ValidationExceptionDetails build() {
            ValidationExceptionDetails validationExceptionDetails = new ValidationExceptionDetails();
            validationExceptionDetails.setTitle(title);
            validationExceptionDetails.setStatus(status);
            validationExceptionDetails.setDetail(detail);
            validationExceptionDetails.setTimestamp(timestamp);
            validationExceptionDetails.setDeveloperMessage(developerMessage);
            validationExceptionDetails.field = field;
            validationExceptionDetails.fieldMessage = fieldMessage;

            return validationExceptionDetails;
        }
    }

}
