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

export const moveJournal = async (jid: string, shelfId: string) => {
  await protectedKioke
    .put(`journals/${jid}/shelf`, {
      json: {
        shelfId,
      },
    })
    .json();
};

export const deleteJournal = async (jid: string) => {
  await protectedKioke.delete(`journals/${jid}`).json();
};
