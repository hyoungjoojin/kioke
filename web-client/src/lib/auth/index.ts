import { loginWithCredentials } from '@/app/api/auth';
import { getMyInformation } from '@/app/api/user';
import KiokeError from '@/constants/errors';
import NextAuth from 'next-auth';
import { CredentialsSignin } from 'next-auth';
import Credentials from 'next-auth/providers/credentials';
import { z } from 'zod';

export class KiokeAuthError extends CredentialsSignin {
  public error: KiokeError;

  constructor(error: KiokeError) {
    super();
    this.error = error;
  }
}

export const LoginFormSchema = z.object({
  email: z.string().email({
    message: 'login.email.invalid',
  }),
  password: z.string().nonempty({
    message: 'login.password.empty',
  }),
});

export const { handlers, signIn, signOut, auth } = NextAuth({
  debug: false,
  providers: [
    Credentials({
      id: 'credentials',
      name: 'credentials',
      credentials: {
        email: {
          label: 'Email',
          type: 'text',
        },
        password: {
          label: 'Password',
          type: 'password',
        },
      },
      authorize: async (credentials) => {
        const fields = LoginFormSchema.safeParse(credentials);
        if (!fields.success) {
          return null;
        }

        const { email, password } = fields.data;

        const { uid: userId, accessToken } = await loginWithCredentials(
          email,
          password,
        ).catch((error) => {
          throw new KiokeAuthError(error);
        });

        const { name } = await getMyInformation(accessToken);

        return {
          userId,
          email,
          name,
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
  session: { strategy: 'jwt' },
  pages: {
    signIn: '/auth/login',
  },
  logger: {
    error(_, ...__) {},
  },
});
