export enum ErrorCode {
  USER_NOT_FOUND = "U-0001",
  USER_ALREADY_EXISTS = "U-0002",
  INVALID_CREDENTIALS = "U-0003",
  NO_ACCESS_TOKEN = "U-0004",
  INVALID_ACCESS_TOKEN = "U-0005",
  FRIEND_REQUEST_ALREADY_SENT = "U-0006",
  INVALID_FRIEND_REQUEST = "U-0007",

  JOURNAL_NOT_FOUND = "J-0001",
  SHELF_NOT_FOUND = "J-0002",
  CANNOT_CREATE_JOURNAL = "J-0003",
  NO_EDIT_PERMISSIONS = "J-0004",
  NO_DELETE_PERMISSIONS = "J-0005",

  INTERNAL_SERVER_ERROR = "E-0000",

  SHOULD_NOT_HAPPEN = "!",
  INVALID_ERROR_CODE = "?",
}

export default class KiokeError {
  private code_: ErrorCode;

  private static reverseCodeMapping = new Map(
    Object.entries(ErrorCode).map(([key, value]) => [value, key]),
  );

  constructor(code: string | undefined) {
    if (
      code === undefined ||
      KiokeError.reverseCodeMapping.get(code as ErrorCode) === undefined
    ) {
      this.code_ = ErrorCode.INVALID_ERROR_CODE;
    } else {
      this.code_ = code as ErrorCode;
    }
  }

  public static getErrorMessage = (code: ErrorCode) => {
    switch (code) {
      case ErrorCode.USER_NOT_FOUND: {
        return "error.user-not-found";
      }

      case ErrorCode.USER_ALREADY_EXISTS: {
        return "error.user-already-exists";
      }

      case ErrorCode.INVALID_CREDENTIALS: {
        return "error.invalid-credentials";
      }

      case ErrorCode.INTERNAL_SERVER_ERROR:
      default: {
        return "error.internal-server-error";
      }
    }
  };

  public code() {
    return this.code_;
  }
}
