import kioke from '@/app/api';
import { HttpMethod } from '@/constant/http';
import { MimeType } from '@/constant/mime';
import type KiokeError from '@/util/error';
import type { Result } from 'neverthrow';

interface DeleteCollectionPathParams {
  id: string;
}

function url({ id }: DeleteCollectionPathParams) {
  return `/collections/${id}`;
}

export async function deleteCollection(
  pathParams: DeleteCollectionPathParams,
): Promise<Result<void, KiokeError>> {
  return kioke<void>(url(pathParams), {
    method: HttpMethod.DELETE,
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  });
}
