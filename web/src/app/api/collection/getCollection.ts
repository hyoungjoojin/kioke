import kioke from '@/app/api';
import type { KiokeError } from '@/constant/error';
import { MimeType } from '@/constant/mime';
import type { Collection } from '@/types/collection';
import type { Result } from 'neverthrow';

interface GetCollectionPathParams {
  id: string;
}

export interface GetCollectionResponse {
  id: string;
  name: string;
  journals: {
    id: string;
    title: string;
  }[];
}

function url({ id }: GetCollectionPathParams) {
  return `/collections/${id}`;
}

export async function getCollection(
  pathParams: GetCollectionPathParams,
): Promise<Result<Collection, KiokeError>> {
  return kioke<GetCollectionResponse>(url(pathParams), {
    method: 'GET',
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  });
}
