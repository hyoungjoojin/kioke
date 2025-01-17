import NextAuth from "next-auth";
import Credentials from "next-auth/providers/credentials";

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
        try {
          const response = await fetch(
            `${process.env.API_GATEWAY_URL}/auth/login`,
            {
              method: "POST",
              headers: {
                "Content-Type": "application/json",
              },
              body: JSON.stringify({
                email: credentials.email,
                password: credentials.password,
              }),
            },
          );

          if (response.status === 201) {
            const body = await response.json();
            return {
              id: body.uid,
              accessToken: body.accessToken,
            };
          }
        } catch (error) {
          console.log(error);
        }

        return null as any;
      },
    }),
  ],
  callbacks: {},
  session: { strategy: "jwt" },
  pages: {
    signIn: "/auth/login",
  },
});
