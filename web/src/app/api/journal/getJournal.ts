import kioke from '@/app/api';
import type { KiokeError } from '@/constant/error';
import { MimeType } from '@/constant/mime';
import type { Journal } from '@/types/journal';
import type { Result } from 'neverthrow';

interface GetJournalPathParams {
  journalId: string;
}

interface GetJournalResponseBody {
  journalId: string;
  title: string;
  description: string;
  pages: {
    pageId: string;
    title: string;
    date: Date;
  }[];
}

function url({ journalId }: GetJournalPathParams) {
  return `/journals/${journalId}`;
}

export async function getJournal(
  pathParams: GetJournalPathParams,
): Promise<Result<Journal, KiokeError>> {
  return kioke<GetJournalResponseBody>(url(pathParams), {
    method: 'GET',
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  });
}
