"use server";

import { CreateJournalResponseBody } from "@/types/server/journal";
import { protectedKioke } from "@/utils/server";

export const createJournal = async (title: string, shelfId: string) => {
  const response = await protectedKioke
    .post<CreateJournalResponseBody>("journals", {
      json: {
        title,
        shelfId,
      },
    })
    .json();

  return response;
};
