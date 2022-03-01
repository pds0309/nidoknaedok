package com.errors;

public enum ErrorCode {

    INVALID_INPUT(400, "E001", "요청 변수 확인 필요"),
    ACCESS_DENIED(403, "E003", "접근 제한됨"),
    NOT_FOUND(404, "E004", "요청 페이지 찾을 수 없음"),
    USER_REQUEST_NOT_FOUND(404, "E404", "요청 데이터를 찾을 수 없음"),
    METHOD_NOT_ALLOWED(405, "E005", "메소드 허용 안됨"),
    CONFLICT_INPUT(409, "E009", "요청 충돌 발생"),
    INTERNA_SERVER_ERROR(500, "E999", "내부 서버 오류[관리자에게 문의 바람]"),
    DATA_SERVICE_UNAVAILABLE(503, "E998", "요청 데이터를 처리할 수 없음");

    private final int status;
    private final String code;
    private final String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "{" +
                "status:" + status + ", code:'" + code + '\'' +
                ", message:'" + message + '\'' + '}';
    }
}
