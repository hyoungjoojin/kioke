import kioke from '@/app/api';
import type { KiokeError } from '@/constant/error';
import { MimeType } from '@/constant/mime';
import type { JournalCollection } from '@/types/journal';
import type { Result } from 'neverthrow';

interface GetJournalCollectionPathParams {
  collectionId: string;
}

interface GetJournalCollectionResponseBody {
  collectionId: string;
  name: string;
  isDefault: boolean;
  journals: {
    journalId: string;
    title: string;
  }[];
}

function url({ collectionId }: GetJournalCollectionPathParams) {
  return `/journals/collections/${collectionId}`;
}

export async function getJournalCollection(
  pathParams: GetJournalCollectionPathParams,
): Promise<Result<JournalCollection, KiokeError>> {
  return kioke<GetJournalCollectionResponseBody>(url(pathParams), {
    method: 'GET',
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  });
}
