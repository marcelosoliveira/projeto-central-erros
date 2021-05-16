package br.com.centralerrors.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionDetails {

    private String title;
    private int status;
    private String detail;
    private long timestamp;
    private String developerMessage;


    public static final class ExceptionDetailsBuilder {
        private String title;
        private int status;
        private String detail;
        private long timestamp;
        private String developerMessage;

        private ExceptionDetailsBuilder() {
        }

        public static ExceptionDetailsBuilder newBuilder() {
            return new ExceptionDetailsBuilder();
        }

        public ExceptionDetailsBuilder title(String title) {
            this.title = title;
            return this;
        }

        public ExceptionDetailsBuilder status(int status) {
            this.status = status;
            return this;
        }

        public ExceptionDetailsBuilder detail(String detail) {
            this.detail = detail;
            return this;
        }

        public ExceptionDetailsBuilder timestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public ExceptionDetailsBuilder developerMessage(String developerMessage) {
            this.developerMessage = developerMessage;
            return this;
        }

        public ExceptionDetails build() {
            ExceptionDetails exceptionDetails = new ExceptionDetails();
            exceptionDetails.setTitle(title);
            exceptionDetails.setStatus(status);
            exceptionDetails.setDetail(detail);
            exceptionDetails.setTimestamp(timestamp);
            exceptionDetails.setDeveloperMessage(developerMessage);
            return exceptionDetails;
        }
    }
}
