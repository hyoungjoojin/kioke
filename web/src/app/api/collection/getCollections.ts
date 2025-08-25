import type { GetCollectionResponse } from './getCollection';
import kioke from '@/app/api';
import { MimeType } from '@/constant/mime';
import type { Collection } from '@/types/collection';
import type KiokeError from '@/util/error';
import type { Result } from 'neverthrow';

interface GetCollectionsResponse {
  count: number;
  collections: GetCollectionResponse[];
}

function url() {
  return '/collections';
}

export async function getCollections(): Promise<
  Result<Collection[], KiokeError>
> {
  return kioke<GetCollectionsResponse>(url(), {
    method: 'GET',
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  }).then((response) => response.map((data) => data.collections));
}
