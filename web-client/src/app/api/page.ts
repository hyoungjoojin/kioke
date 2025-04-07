"use server";

import { HttpResponseBody } from "@/types/server";
import {
  CreatePageResponseBody,
  GetPageResponseBody,
} from "@/types/server/page";
import { processResponse, protectedKioke } from "@/utils/server";

const createPage = async (journalId: string) => {
  await protectedKioke
    .post<CreatePageResponseBody>(`journals/${journalId}/pages`, {
      json: {
        title: "",
      },
    })
    .json();
};

const getPage = async (journalId: string, pageId: string) => {
  const response = protectedKioke.get<HttpResponseBody<GetPageResponseBody>>(
    `journals/${journalId}/pages/${pageId}`,
  );

  return processResponse(response);
};

export { createPage, getPage };
