import { HttpResponseBody } from '@/types/server';
import {
  GetUserResponseBody,
  SearchUserResponseBody,
} from '@/types/server/user';
import { GetMyInformationResponseBodyDto } from '@/types/server/user/generated';
import { kioke, processResponse, protectedKioke } from '@/utils/server';

export const getMyInformation = async (accessToken: string) => {
  const response = kioke
    .extend({
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    })
    .get<HttpResponseBody<GetMyInformationResponseBodyDto>>('users/me')
    .then((response) => processResponse(response));

  return response;
};

export const getUser = async (userId: string) => {
  const response = protectedKioke
    .get<HttpResponseBody<GetUserResponseBody>>(`users/${userId}`)
    .then((response) => processResponse(response));

  return response;
};

export const searchUser = async (email: string) => {
  const response = protectedKioke
    .post<HttpResponseBody<SearchUserResponseBody>>('users/search', {
      json: {
        email,
      },
    })
    .then((response) => processResponse(response));

  return response;
};
