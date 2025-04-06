"use server";

import { Role } from "@/constants/role";
import { HttpResponseBody } from "@/types/server";
import {
  CreateJournalResponseBody,
  GetJournalResponseBody,
} from "@/types/server/journal";
import { processResponse, protectedKioke } from "@/utils/server";
import { getUser } from "./user";

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

export const getJournal = async (journalId: string) => {
  const response = await processResponse(
    protectedKioke.get<HttpResponseBody<GetJournalResponseBody>>(
      `journals/${journalId}`,
    ),
  );

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
    return { ...page, date: new Date(page.date) };
  });

  return { ...response, users, pages };
};

export const shareJournal = async (
  userId: string,
  journalId: string,
  role: Role,
) => {
  const response = protectedKioke
    .post(`journals/${journalId}/share`, {
      json: {
        userId,
        role,
      },
    })
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
