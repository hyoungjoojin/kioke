import kioke from '@/app/api';
import type { KiokeError } from '@/constant/error';
import { MimeType } from '@/constant/mime';
import type { JournalCollection } from '@/types/journal';
import type { Result } from 'neverthrow';

export interface CreateJournalCollectionRequestBody {
  name: string;
}

interface CreateJournalCollectionResponseBody {
  collectionId: string;
  isDefault: boolean;
}

function url() {
  return '/collections';
}

export async function createJournalCollection(
  requestBody: CreateJournalCollectionRequestBody,
): Promise<Result<JournalCollection, KiokeError>> {
  return kioke<CreateJournalCollectionResponseBody>(url(), {
    method: 'POST',
    body: JSON.stringify(requestBody),
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  }).then((response) =>
    response.map((data) => ({
      collectionId: data.collectionId,
      name: requestBody.name,
      journals: [],
      isDefault: data.isDefault,
    })),
  );
}
