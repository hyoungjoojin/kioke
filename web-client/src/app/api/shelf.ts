import { GetShelvesResponseBody } from "@/types/server/shelf";
import { protectedKioke } from "@/utils/server";

export const getShelves = async () => {
  const response = await protectedKioke
    .get<GetShelvesResponseBody>("shelves")
    .json();

  return response;
};
