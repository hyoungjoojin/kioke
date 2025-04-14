import KiokeError, { ErrorCode } from "@/constants/errors";
import { auth } from "@/lib/auth";
import { HttpResponseBody } from "@/types/server";
import ky, { HTTPError, KyResponse } from "ky";
import { getSession } from "next-auth/react";

export const kioke = ky.create({
  prefixUrl: process.env.NEXT_PUBLIC_KIOKE_BACKEND_URL,
  headers: {
    "Content-Type": "application/json",
  },
});

export const protectedKioke = kioke.extend({
  hooks: {
    beforeRequest: [
      async (request) => {
        const session =
          typeof window === "undefined" ? await auth() : await getSession();

        if (session?.accessToken) {
          request.headers.set("Authorization", `Bearer ${session.accessToken}`);
        }
      },
    ],
  },
  credentials: "include",
});

export async function processResponse<T>(
  response: KyResponse<HttpResponseBody<T>>,
) {
  return response
    .json()
    .then((res) => {
      if (!res.data || !res.success) {
        throw new KiokeError(ErrorCode.SHOULD_NOT_HAPPEN);
      }

      return res.data;
    })
    .catch(async (error) => {
      if (error instanceof HTTPError) {
        const response: HttpResponseBody<null> = await error.response.json();
        const errorDetail = response.error;

        if (errorDetail) {
          throw new KiokeError(
            errorDetail.code,
            errorDetail.title,
            errorDetail.message,
            errorDetail.details,
          );
        } else {
          throw new KiokeError(ErrorCode.SHOULD_NOT_HAPPEN);
        }
      }

      throw error;
    });
}
