import kioke from '@/app/api';
import type { KiokeError } from '@/constant/error';
import { MimeType } from '@/constant/mime';
import type { Journal } from '@/types/journal';
import type { Result } from 'neverthrow';

export interface CreateJournalRequestBody {
  collectionId: string;
  title: string;
}

interface CreateJournalResponseBody {
  journalId: string;
}

function url() {
  return '/journals';
}

export async function createJournal(
  requestBody: CreateJournalRequestBody,
): Promise<Result<Journal, KiokeError>> {
  return kioke<CreateJournalResponseBody>(url(), {
    method: 'POST',
    body: JSON.stringify(requestBody),
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  }).then((response) =>
    response.map((data) => ({
      journalId: data.journalId,
      title: requestBody.title,
      description: '',
      pages: [],
    })),
  );
}
