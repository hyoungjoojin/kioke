'use server';

import { HttpResponseBody } from '@/types/server';
import { CredentialsLoginResponseBody } from '@/types/server/auth';
import { kioke, processResponse } from '@/utils/server';

export const loginWithCredentials = async (email: string, password: string) => {
  const response = kioke
    .post<HttpResponseBody<CredentialsLoginResponseBody>>('auth/login', {
      json: {
        email,
        password,
      },
    })
    .then((response) => processResponse(response));

  return response;
};

export const registerWithCredentials = async (
  email: string,
  password: string,
  firstName: string,
  lastName: string,
): Promise<boolean> => {
  const success = await kioke
    .post('auth/register', {
      json: {
        email,
        password,
        firstName,
        lastName,
      },
    })
    .then(() => {
      return true;
    })
    .catch((_) => {
      return false;
    });

  return success;
};
