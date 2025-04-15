"use server";

import { HttpResponseBody } from "@/types/server";
import {
  CreatePageResponseBody,
  GetPageResponseBody,
} from "@/types/server/page";
import { processResponse, protectedKioke } from "@/utils/server";

const getPage = async (journalId: string, pageId: string) => {
  const response = protectedKioke
    .get<
      HttpResponseBody<GetPageResponseBody>
    >(`journals/${journalId}/pages/${pageId}`)
    .then((response) => processResponse(response));

  return response;
};

const createPage = async (journalId: string) => {
  protectedKioke
    .post<CreatePageResponseBody>(`journals/${journalId}/pages`, {
      json: {
        title: "",
      },
    })
    .then((response) => processResponse(response));
};

export { createPage, getPage };
