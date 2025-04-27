'use server';

import { Role } from '@/constants/role';
import { HttpResponseBody } from '@/types/server';
import {
  CreateJournalResponseBodyDto,
  GetJournalResponseBodyDto,
  GetJournalsParams,
  GetJournalsResponseBodyDto,
  UpdateJournalRequestBodyDto,
} from '@/types/server/journal.generated';
import { processResponse, protectedKioke } from '@/utils/server';

export async function getJournals(
  params?: GetJournalsParams,
): Promise<GetJournalsResponseBodyDto> {
  const response = protectedKioke
    .get<HttpResponseBody<GetJournalsResponseBodyDto>>('journals', {
      searchParams: {
        ...params,
      },
    })
    .then((response) => processResponse(response));

  return response;
}

export const getJournal = async (
  journalId: string,
): Promise<GetJournalResponseBodyDto> => {
  const response = await protectedKioke
    .get<HttpResponseBody<GetJournalResponseBodyDto>>(`journals/${journalId}`)
    .then((response) => processResponse(response));

  return response;
};

export const createJournal = async (
  shelfId: string,
  title: string,
  description: string,
) => {
  const response = protectedKioke
    .post<HttpResponseBody<CreateJournalResponseBodyDto>>('journals', {
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
  data: UpdateJournalRequestBodyDto,
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
