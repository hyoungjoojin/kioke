"use server";

import {
  CreatePageResponseBody,
  GetPageResponseBody,
} from "@/types/server/page";
import { protectedKioke } from "@/utils/server";

const createPage = async (journalId: string) => {
  await protectedKioke
    .post<CreatePageResponseBody>("pages", {
      json: {
        journalId,
      },
    })
    .json();
};

const getPage = async (journalId: string, pageId: string) => {
  const response = await protectedKioke
    .get<GetPageResponseBody>(`pages/${pageId}`, {
      searchParams: {
        journalId,
      },
    })
    .json();

  return response;
};

export { createPage, getPage };
