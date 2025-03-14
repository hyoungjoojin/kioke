"use server";

import {
  CreatePageResponseBody,
  GetPageResponseBody,
} from "@/types/server/page";
import { protectedKioke } from "@/utils/server";

const createPage = async (journalId: string) => {
  await protectedKioke
    .post<CreatePageResponseBody>(`/journals/${journalId}/pages`, {
      json: {
        title: "",
      },
    })
    .json();
};

const getPage = async (journalId: string, pageId: string) => {
  const response = await protectedKioke
    .get<GetPageResponseBody>(`/journals/${journalId}/pages/${pageId}`)
    .json();

  return response;
};

export { createPage, getPage };
