"use server";

import { Role } from "@/constants/role";
import { HttpResponseBody } from "@/types/server";
import {
  CreateJournalResponseBody,
  GetJournalResponseBody,
  UpdateJournalRequestBody,
} from "@/types/server/journal";
import { processResponse, protectedKioke } from "@/utils/server";
import { getUser } from "./user";
import { Journal } from "@/types/primitives/journal";

export const getJournals = async (bookmarked: boolean = false) => {
  const response = protectedKioke
    .get<HttpResponseBody<GetJournalResponseBody[]>>("journals", {
      searchParams: {
        bookmarked,
      },
    })
    .then((response) => processResponse(response));

  return response;
};

export const getJournal = async (journalId: string): Promise<Journal> => {
  const response = await protectedKioke
    .get<HttpResponseBody<GetJournalResponseBody>>(`journals/${journalId}`)
    .then((response) => processResponse(response));

  const users = await Promise.all(
    response.users.map(async (user) => {
      const userInfo = await getUser(user.userId);

      return {
        role: Role[user.role as keyof typeof Role],
        userId: userInfo.userId,
        email: userInfo.email,
        firstName: userInfo.firstName,
        lastName: userInfo.lastName,
      };
    }),
  );

  const pages = response.pages.map((page) => {
    return { ...page, createdAt: new Date(page.createdAt) };
  });

  return { ...response, users, pages };
};

export const createJournal = async (
  shelfId: string,
  title: string,
  description: string,
) => {
  const response = protectedKioke
    .post<HttpResponseBody<CreateJournalResponseBody>>("journals", {
      json: {
        shelfId,
        title,
        description,
      },
    })
    .then((response) => processResponse(response));

  return response;
};

export const shareJournal = async (
  userId: string,
  journalId: string,
  role: Role,
) => {
  protectedKioke
    .post(`journals/${journalId}/share`, {
      json: {
        userId,
        role,
      },
    })
    .then((response) => processResponse(response));
};

export const updateJournal = async (
  journalId: string,
  data: UpdateJournalRequestBody,
) => {
  protectedKioke
    .patch(`journals/${journalId}`, {
      json: data,
    })
    .then((response) => processResponse(response));
};

export const bookmarkJournal = async (journalId: string) => {
  updateJournal(journalId, { bookmark: true });
};

export const moveJournal = async (journalId: string, shelfId: string) => {
  updateJournal(journalId, { shelfId });
};

export const deleteJournal = async (journalId: string) => {
  protectedKioke
    .delete(`journals/${journalId}`)
    .then((response) => processResponse(response));
};

export const deleteBookmark = async (journalId: string) => {
  updateJournal(journalId, { bookmark: false });
};
