import type { GetJournalResponse } from './getJournal';
import kioke from '@/app/api';
import { MimeType } from '@/constant/mime';
import type { Journal } from '@/types/journal';
import type KiokeError from '@/util/error';
import type { Result } from 'neverthrow';

type GetJournalsResponse = GetJournalResponse[];

function url() {
  return `/journals`;
}

export async function getJournals(): Promise<Result<Journal[], KiokeError>> {
  return kioke<GetJournalsResponse>(url(), {
    method: 'GET',
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  });
}
