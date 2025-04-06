export enum ErrorCode {
  INTERNAL_SERVER_ERROR = "E-0000",
  NO_ACCESS_TOKEN = "E-0001",
  INVALID_ACCESS_TOKEN = "E-0002",
  ACCESS_DENIED = "E-0003",

  USER_NOT_FOUND = "U-0001",
  USER_ALREADY_EXISTS = "U-0002",
  INVALID_CREDENTIALS = "U-0003",
  FRIEND_REQUEST_ALREADY_SENT = "U-0004",
  INVALID_FRIEND_REQUEST = "U-0005",

  JOURNAL_NOT_FOUND = "J-0001",
  SHELF_NOT_FOUND = "J-0002",
  CANNOT_CREATE_JOURNAL = "J-0003",

  SHOULD_NOT_HAPPEN = "!",
  INVALID_ERROR_CODE = "?",
}

export default class KiokeError {
  private code_: ErrorCode;
  private title_: string = "";
  private message_: string = "";
  private details_: string = "";

  private static reverseCodeMapping = new Map(
    Object.entries(ErrorCode).map(([key, value]) => [value, key]),
  );

  constructor(
    code: string | undefined,
    title?: string,
    message?: string,
    details?: string | undefined,
  ) {
    if (
      code === undefined ||
      KiokeError.reverseCodeMapping.get(code as ErrorCode) === undefined
    ) {
      this.code_ = ErrorCode.INVALID_ERROR_CODE;
    } else {
      this.code_ = code as ErrorCode;
    }

    if (title) {
      this.title_ = title;
    }

    if (message) {
      this.message_ = message;
    }

    if (details) {
      this.details_ = details;
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

  public title() {
    return this.title_;
  }

  public message() {
    return this.message_;
  }

  public details() {
    return this.details_;
  }
}
