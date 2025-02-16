import NextAuth from "next-auth";
import Credentials from "next-auth/providers/credentials";
import {
  AuthLoginInvalidCredentialsError,
  AuthServiceInternalServerError,
  AuthServiceNotAvailableError,
} from "./errors";
import { z } from "zod";
import { HTTPError } from "ky";
import { loginWithCredentials } from "@/app/api/auth";
import { getMyInformation } from "@/app/api/user";

export const LoginFormSchema = z.object({
  email: z.string().email({
    message: "login.email.invalid",
  }),
  password: z.string().nonempty({
    message: "login.password.empty",
  }),
});

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
        const fields = LoginFormSchema.safeParse(credentials);
        if (!fields.success) {
          return null;
        }

        const { email, password } = fields.data;

        const { uid, accessToken } = await loginWithCredentials(email, password)
          .then((response) => response.json())
          .catch((error) => {
            if (error instanceof HTTPError) {
              const status = error.response.status;
              if (status === 500) {
                throw new AuthServiceInternalServerError();
              } else if (status !== 201) {
                throw new AuthLoginInvalidCredentialsError();
              }
            }

            throw new AuthServiceNotAvailableError();
          });

        const { firstName, lastName } = await getMyInformation(accessToken)
          .then((response) => response.json())
          .catch((error) => {
            if (error instanceof HTTPError) {
              const status = error.response.status;
              if (status === 500) {
                throw new AuthServiceInternalServerError();
              }
            }

            throw new AuthServiceNotAvailableError();
          });

        return {
          uid,
          email,
          firstName,
          lastName,
          accessToken,
        };
      },
    }),
  ],
  callbacks: {
    jwt: async ({ token, user }) => {
      if (user) {
        token.user = user;
        token.accessToken = user.accessToken;
      }

      return token;
    },
    session: async ({ session, token }) => {
      session.user = token.user;
      session.accessToken = token.accessToken;

      return session;
    },
  },
  session: { strategy: "jwt" },
  pages: {
    signIn: "/auth/login",
  },
});
