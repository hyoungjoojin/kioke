"use server";

import {
  CreateJournalResponseBody,
  GetJournalResponseBody,
} from "@/types/server/journal";
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

export const getJournal = async (jid: string) => {
  const response = await protectedKioke
    .get<GetJournalResponseBody>(`journals/${jid}`)
    .json();

  return response;
};
