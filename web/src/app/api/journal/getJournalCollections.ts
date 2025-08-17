import kioke from '@/app/api';
import type { KiokeError } from '@/constant/error';
import { MimeType } from '@/constant/mime';
import type { JournalCollection } from '@/types/journal';
import type { Result } from 'neverthrow';

interface GetJournalCollectionsResponseBody {
  collections: {
    collectionId: string;
    name: string;
    journals: {
      journalId: string;
      title: string;
    }[];
    isDefault: boolean;
  }[];
}

function url() {
  return '/collections';
}

export async function getJournalCollections(): Promise<
  Result<JournalCollection[], KiokeError>
> {
  return kioke<GetJournalCollectionsResponseBody>(url(), {
    method: 'GET',
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  }).then((response) => response.map((data) => data.collections));
}
