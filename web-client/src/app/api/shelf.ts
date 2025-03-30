import { HttpResponseBody } from "@/types/server";
import { GetShelvesResponseBody } from "@/types/server/shelf";
import { processResponse, protectedKioke } from "@/utils/server";

export const getShelves = async () => {
  const response = await processResponse(
    protectedKioke.get<HttpResponseBody<GetShelvesResponseBody>>("shelves"),
  );

  response.shelves.sort((s1, s2) => {
    if (s1.isArchive) {
      return 1;
    } else if (s2.isArchive) {
      return -1;
    } else {
      return 0;
    }
  });

  return response.shelves;
};
