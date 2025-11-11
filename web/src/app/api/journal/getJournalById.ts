import kioke from '@/app/api';
import { HttpMethod } from '@/constant/http';
import { MimeType } from '@/constant/mime';
import type { Journal } from '@/types/journal';
import type KiokeError from '@/util/error';
import type { Result } from 'neverthrow';

interface GetJournalByIdPathParams {
  journalId: string;
}

type GetJournalByIdResponse = Journal;

interface GetJournalByIdParams {
  path: GetJournalByIdPathParams;
}

async function getJournalById(
  params: GetJournalByIdParams,
): Promise<Result<Journal, KiokeError>> {
  return kioke<GetJournalByIdResponse>(`/journals/${params.path.journalId}`, {
    method: HttpMethod.GET,
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  });
}

export default getJournalById;
export type { GetJournalByIdParams };
