import kioke from '@/app/api';
import { HttpMethod } from '@/constant/http';
import { MimeType } from '@/constant/mime';
import type { Journal } from '@/types/journal';
import type KiokeError from '@/util/error';
import type { Result } from 'neverthrow';

interface CreateJournalRequestBody {
  collectionId: string;
  title: string;
}

type CreateJournalResponse = Journal;

interface CreateJournalParams {
  body: CreateJournalRequestBody;
}

async function createJournal(
  params: CreateJournalParams,
): Promise<Result<Journal, KiokeError>> {
  return kioke<CreateJournalResponse>('/journals', {
    method: HttpMethod.POST,
    body: JSON.stringify(params.body),
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  });
}

export default createJournal;
export type { CreateJournalParams };
