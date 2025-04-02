"use server";

import { HttpResponseBody } from "@/types/server";
import {
  CreateJournalResponseBody,
  GetJournalResponseBody,
} from "@/types/server/journal";
import { processResponse, protectedKioke } from "@/utils/server";

export const createJournal = async (
  shelfId: string,
  title: string,
  description: string,
) => {
  const response = await protectedKioke
    .post<CreateJournalResponseBody>("journals", {
      json: {
        shelfId,
        title,
        description,
      },
    })
    .json();

  return response;
};

export const getJournal = async (jid: string) => {
  const response = protectedKioke.get<HttpResponseBody<GetJournalResponseBody>>(
    `journals/${jid}`,
  );

  return processResponse(response);
};

export const bookmarkJournal = async (journalId: string) => {
  await protectedKioke.post(`journals/${journalId}/bookmark`).json();
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

export const deleteBookmark = async (journalId: string) => {
  await protectedKioke.delete(`journals/${journalId}/bookmark`).json();
};
