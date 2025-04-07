"use server";

import KiokeError, { ErrorCode } from "@/constants/errors";
import { KiokeAuthError, signIn } from "./";

export async function signInWithCredentials(
  email: string,
  password: string,
): Promise<{
  success: boolean;
  code: ErrorCode | null;
}> {
  try {
    await signIn("credentials", {
      email,
      password,
      redirect: false,
    });

    return {
      success: true,
      code: null,
    };
  } catch (error) {
    if (error instanceof KiokeAuthError && error.error instanceof KiokeError) {
      return {
        success: false,
        code: error.error.code(),
      };
    }

    return {
      success: false,
      code: null,
    };
  }
}
