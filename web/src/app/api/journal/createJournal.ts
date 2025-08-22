import kioke from '@/app/api';
import type { KiokeError } from '@/constant/error';
import { MimeType } from '@/constant/mime';
import type { Journal } from '@/types/journal';
import type { Result } from 'neverthrow';

export interface CreateJournalRequest {
  collectionId: string;
  title: string;
}

interface CreateJournalResponse {
  id: string;
}

function url() {
  return '/journals';
}

export async function createJournal(
  body: CreateJournalRequest,
): Promise<Result<Journal, KiokeError>> {
  return kioke<CreateJournalResponse>(url(), {
    method: 'POST',
    body: JSON.stringify(body),
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  }).then((response) =>
    response.map((data) => ({
      id: data.id,
      title: body.title,
      description: '',
      pages: [],
    })),
  );
}
