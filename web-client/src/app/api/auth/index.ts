"use server";

import { CredentialsLoginResponseBody } from "@/types/server/auth";
import { kioke } from "@/utils/server";

export const loginWithCredentials = async (email: string, password: string) => {
  const response = kioke.post<CredentialsLoginResponseBody>("auth/login", {
    json: {
      email,
      password,
    },
  });

  return response;
};

export const registerWithCredentials = async (
  email: string,
  password: string,
  firstName: string,
  lastName: string,
): Promise<boolean> => {
  const success = await kioke
    .post("auth/register", {
      json: {
        email,
        password,
        firstName,
        lastName,
      },
    })
    .then((_) => {
      return true;
    })
    .catch((error) => {
      console.log(error);
      return false;
    });

  return success;
};
