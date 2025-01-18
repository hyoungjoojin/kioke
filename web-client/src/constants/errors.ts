export const enum ErrorCode {
  AUTH_SERVICE_NOT_AVAILABLE = "A0",
  AUTH_SERVICE_INTERNAL_SERVER_ERROR = "A1",
  AUTH_LOGIN_INVALID_CREDENTIALS = "A2",

  UNKNOWN_ERROR = "Z1",
}

export function getErrorMessage(code: ErrorCode): string {
  switch (code) {
    case ErrorCode.AUTH_SERVICE_NOT_AVAILABLE:
      return "error.internal-server-error";

    case ErrorCode.AUTH_SERVICE_INTERNAL_SERVER_ERROR:
      return "error.internal-server-error";

    case ErrorCode.AUTH_LOGIN_INVALID_CREDENTIALS:
      return "error.login.invalid-credentials";

    case ErrorCode.UNKNOWN_ERROR:
    default:
      return "error.unknown-error";
  }
}
