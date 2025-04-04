import { HttpResponseBody } from "@/types/server";
import {
  GetMyInformationResponseBody,
  SearchUserResponseBody,
} from "@/types/server/user";
import { kioke, processResponse, protectedKioke } from "@/utils/server";

export const getMyInformation = async (accessToken: string) => {
  const response = kioke
    .extend({
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    })
    .get<HttpResponseBody<GetMyInformationResponseBody>>("users");

  return processResponse(response);
};

export const searchUser = async (email: string) => {
  const response = protectedKioke.post<
    HttpResponseBody<SearchUserResponseBody>
  >("users/search", {
    json: {
      email,
    },
  });

  return processResponse(response);
};
