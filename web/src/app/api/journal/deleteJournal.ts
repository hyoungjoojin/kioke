import kioke from '@/app/api';
import { HttpMethod } from '@/constant/http';
import { MimeType } from '@/constant/mime';
import type KiokeError from '@/util/error';
import type { Result } from 'neverthrow';

interface DeleteJournalPathParams {
  journalId: string;
}

interface DeleteJournalParams {
  path: DeleteJournalPathParams;
}

async function deleteJournal(
  params: DeleteJournalParams,
): Promise<Result<void, KiokeError>> {
  return kioke<void>(`/journals/${params.path.journalId}`, {
    method: HttpMethod.DELETE,
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  });
}

export default deleteJournal;
export type { DeleteJournalParams };
