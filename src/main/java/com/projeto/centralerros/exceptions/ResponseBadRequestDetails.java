package com.projeto.centralerros.exceptions;

public class ResponseBadRequestDetails extends ExceptionDetails{

    public static final class ResponseBadRequestDetailsBuilder {
        private String title;
        private int status;
        private String detail;
        private long timestamp;
        private String developerMessage;

        private ResponseBadRequestDetailsBuilder() {
        }

        public static ResponseBadRequestDetails.ResponseBadRequestDetailsBuilder newBuilder() {
            return new ResponseBadRequestDetails.ResponseBadRequestDetailsBuilder();
        }

        public ResponseBadRequestDetails.ResponseBadRequestDetailsBuilder title(String title) {
            this.title = title;
            return this;
        }

        public ResponseBadRequestDetails.ResponseBadRequestDetailsBuilder status(int status) {
            this.status = status;
            return this;
        }

        public ResponseBadRequestDetails.ResponseBadRequestDetailsBuilder detail(String detail) {
            this.detail = detail;
            return this;
        }

        public ResponseBadRequestDetails.ResponseBadRequestDetailsBuilder timestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public ResponseBadRequestDetails.ResponseBadRequestDetailsBuilder developerMessage(String developerMessage) {
            this.developerMessage = developerMessage;
            return this;
        }

        public ResponseBadRequestDetails build() {
            ResponseBadRequestDetails responseBadRequestDetails = new ResponseBadRequestDetails();
            responseBadRequestDetails.setTitle(title);
            responseBadRequestDetails.setStatus(status);
            responseBadRequestDetails.setDetail(detail);
            responseBadRequestDetails.setTimestamp(timestamp);
            responseBadRequestDetails.setDeveloperMessage(developerMessage);

            return responseBadRequestDetails;
        }
    }

}
