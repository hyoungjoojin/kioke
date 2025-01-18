"use server";

import { ErrorCode } from "@/constants/errors";
import { signIn } from "./";
import {
  AuthLoginInvalidCredentialsError,
  AuthServiceInternalServerError,
  AuthServiceNotAvailableError,
} from "./errors";

export async function signInWithCredentials(
  email: string,
  password: string,
): Promise<{
  success: boolean;
  code?: ErrorCode;
}> {
  try {
    await signIn("credentials", {
      email,
      password,
      redirect: false,
    });
  } catch (error) {
    console.log(error);
    let code: ErrorCode = ErrorCode.UNKNOWN_ERROR;

    switch (true) {
      case error instanceof AuthServiceNotAvailableError:
        code = ErrorCode.AUTH_SERVICE_NOT_AVAILABLE;
        break;

      case error instanceof AuthServiceInternalServerError:
        code = ErrorCode.AUTH_SERVICE_INTERNAL_SERVER_ERROR;
        break;

      case error instanceof AuthLoginInvalidCredentialsError:
        code = ErrorCode.AUTH_LOGIN_INVALID_CREDENTIALS;
        break;
    }

    return {
      success: false,
      code,
    };
  }

  return {
    success: true,
  };
}
