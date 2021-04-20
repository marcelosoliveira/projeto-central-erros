package com.projeto.centralerros.exceptions;

public class ResponseNotFoundDetails extends ExceptionDetails {

    public static final class ResponseNotFoundDetailsBuilder {
        private String title;
        private int status;
        private String detail;
        private long timestamp;
        private String developerMessage;

        private ResponseNotFoundDetailsBuilder() {
        }

        public static ResponseNotFoundDetailsBuilder newBuilder() {
            return new ResponseNotFoundDetailsBuilder();
        }

        public ResponseNotFoundDetailsBuilder title(String title) {
            this.title = title;
            return this;
        }

        public ResponseNotFoundDetailsBuilder status(int status) {
            this.status = status;
            return this;
        }

        public ResponseNotFoundDetailsBuilder detail(String detail) {
            this.detail = detail;
            return this;
        }

        public ResponseNotFoundDetailsBuilder timestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public ResponseNotFoundDetailsBuilder developerMessage(String developerMessage) {
            this.developerMessage = developerMessage;
            return this;
        }

        public ResponseNotFoundDetails build() {
            ResponseNotFoundDetails responseNotFoundDetails = new ResponseNotFoundDetails();
            responseNotFoundDetails.setTitle(title);
            responseNotFoundDetails.setStatus(status);
            responseNotFoundDetails.setDetail(detail);
            responseNotFoundDetails.setTimestamp(timestamp);
            responseNotFoundDetails.setDeveloperMessage(developerMessage);

            return responseNotFoundDetails;
        }
    }
}
