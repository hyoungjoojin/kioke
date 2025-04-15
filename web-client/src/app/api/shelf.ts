import { Shelf } from "@/types/primitives/shelf";
import { HttpResponseBody } from "@/types/server";
import { GetShelfResponseBody } from "@/types/server/shelf";
import { processResponse, protectedKioke } from "@/utils/server";

export const getShelves = async (): Promise<Shelf[]> => {
  const response = await protectedKioke
    .get<HttpResponseBody<GetShelfResponseBody[]>>("shelves")
    .then((response) => processResponse(response))
    .then((shelves) => {
      shelves.sort((s1, s2) => {
        if (s1.isArchive) {
          return 1;
        } else if (s2.isArchive) {
          return -1;
        } else {
          return 0;
        }
      });

      return shelves;
    });

  return response;
};

export const updateShelf = async (shelf: Shelf, name: string) => {
  await protectedKioke
    .patch(`shelves/${shelf.shelfId}`, {
      json: {
        name,
      },
    })
    .json();
};
