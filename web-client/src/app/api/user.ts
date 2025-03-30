import { HttpResponseBody } from "@/types/server";
import { GetMyInformationResponseBody } from "@/types/server/user";
import { kioke, processResponse } from "@/utils/server";

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
