import { CredentialsLoginResponseBody } from "@/types/server/auth";
import { kioke } from "./";

export const credentialsLogin = async (email: string, password: string) => {
  const response = kioke.post<CredentialsLoginResponseBody>("auth/login", {
    json: {
      email,
      password,
    },
  });

  return response;
};
