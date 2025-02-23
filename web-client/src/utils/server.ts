import { auth } from "@/lib/auth";
import ky from "ky";
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
