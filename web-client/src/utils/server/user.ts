import { GetMyInformationResponseBody } from "@/types/server/user";
import { protectedKioke } from ".";

export const getMyInformation = async (accessToken: string) => {
  const response =
    protectedKioke(accessToken).get<GetMyInformationResponseBody>("users");

  return response;
};
