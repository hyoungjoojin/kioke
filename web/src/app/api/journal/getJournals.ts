import type { GetJournalResponse } from './getJournal';
import kioke from '@/app/api';
import { MimeType } from '@/constant/mime';
import type { Journal } from '@/types/journal';
import type { PagedModel } from '@/types/server';
import type KiokeError from '@/util/error';
import type { Result } from 'neverthrow';

type GetJournalsSearchParams = {
  page?: number;
  size?: number;
};

export type GetJournalsResponse = {
  content: GetJournalResponse[];
  page: PagedModel;
};

function url(params: GetJournalsSearchParams = {}) {
  const searchParams = new URLSearchParams();
  Object.entries(params).forEach(([key, value]) => {
    if (value !== undefined) {
      searchParams.set(key, value.toString());
    }
  });

  const search = searchParams.toString();
  return `/journals${search && '?' + search}`;
}

export async function getJournals(
  params: GetJournalsSearchParams,
): Promise<Result<{ content: Journal[]; page: PagedModel }, KiokeError>> {
  return kioke<GetJournalsResponse>(url(params), {
    method: 'GET',
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  });
}
