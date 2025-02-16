import { GetMyInformationResponseBody } from "@/types/server/user";
import { kioke } from "@/utils/server";

export const getMyInformation = async (accessToken: string) => {
  const response = kioke
    .extend({
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    })
    .get<GetMyInformationResponseBody>("users");

  return response;
};
