import { ErrorCode } from '@/constant/error';
import { Routes } from '@/constant/routes';
import logger from '@/lib/logger';
import type { ProblemDetail } from '@/types/server';
import { redirect } from 'next/navigation';

type KiokeErrorOptions = {
  code?: ErrorCode;
  detail?: ProblemDetail;
  error?: Error;
  status?: number;
};

export default class KiokeError extends Error {
  public readonly code: ErrorCode = ErrorCode.UNKNOWN_ERROR;
  public readonly error: Error | null = null;
  public readonly detail: ProblemDetail | null = null;

  constructor({ code, error, detail, status }: KiokeErrorOptions) {
    super();

    if (error) {
      this.error = error;
      super.stack = error.stack;
      super.cause = error.cause;
    }

    if (detail) {
      this.detail = detail;
      super.cause = this.detail;
      this.code =
        ErrorCode[detail.code as keyof typeof ErrorCode] ||
        ErrorCode.UNKNOWN_ERROR;
    }

    if (code) {
      this.code = code;
    } else if (status) {
      switch (status) {
        case 400:
          this.code = ErrorCode.BAD_REQUEST;
          break;

        case 401:
          this.code = ErrorCode.UNAUTHENTICATED;
          break;

        case 403:
          this.code = ErrorCode.ACCESS_DENIED;
          break;

        case 500:
          this.code = ErrorCode.INTERNAL_SERVER_ERROR;
          break;

        default:
          this.code = ErrorCode.UNKNOWN_ERROR;
          break;
      }
    }

    super.message = this.code;

    Error.captureStackTrace(this, KiokeError);
  }

  public toString() {
    return JSON.stringify({
      code: this.code,
      detail: this.detail || undefined,
      error: this.error || undefined,
    });
  }
}

const handlers: Partial<{
  [K in ErrorCode]: () => void;
}> = {
  [ErrorCode.ACCESS_DENIED]: () => {
    redirect(Routes.SIGN_IN);
  },
};

export function handleError(
  error: any,
  customHandlers?: Partial<{
    [K in ErrorCode]: () => void;
  }>,
) {
  if (error instanceof Error && error instanceof KiokeError) {
    logger.debug(error);

    const handler = {
      ...handlers,
      ...customHandlers,
    }[error.code];

    if (handler) {
      handler();
      return;
    }
  }

  throw error;
}
