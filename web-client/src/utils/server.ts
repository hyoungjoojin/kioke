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
  response: Promise<KyResponse<HttpResponseBody<T>>>,
) {
  return response
    .then((res) => res.json())
    .then((res) => {
      if (!res.data || !res.success) {
        throw new KiokeError(ErrorCode.SHOULD_NOT_HAPPEN);
      }

      return res.data;
    })
    .catch(async (error) => {
      if (error instanceof HTTPError) {
        const response: HttpResponseBody<null> = await error.response.json();
        throw new KiokeError(response.error?.code);
      }

      throw error;
    });
}
