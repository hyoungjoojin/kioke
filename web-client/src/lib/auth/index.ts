import NextAuth from "next-auth";
import Credentials from "next-auth/providers/credentials";
import {
  AuthLoginInvalidCredentialsError,
  AuthServiceInternalServerError,
  AuthServiceNotAvailableError,
} from "./errors";

export const { handlers, signIn, signOut, auth } = NextAuth({
  providers: [
    Credentials({
      id: "credentials",
      name: "credentials",
      credentials: {
        email: {
          label: "Email",
          type: "text",
        },
        password: {
          label: "Password",
          type: "password",
        },
      },
      authorize: async (credentials) => {
        const { email, password } = credentials;

        let response: Response;
        try {
          response = await fetch(`${process.env.API_GATEWAY_URL}/auth/login`, {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify({ email, password }),
          });
        } catch (error) {
          throw new AuthServiceNotAvailableError();
        }

        if (response.status === 500) {
          throw new AuthServiceInternalServerError();
        }

        if (response.status !== 201) {
          throw new AuthLoginInvalidCredentialsError();
        }

        const body = await response.json();
        return {
          id: body.uid,
          accessToken: body.accessToken,
        };
      },
    }),
  ],
  callbacks: {},
  session: { strategy: "jwt" },
  pages: {
    signIn: "/auth/login",
  },
});
