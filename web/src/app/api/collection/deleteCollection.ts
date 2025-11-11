import kioke from '@/app/api';
import { HttpMethod } from '@/constant/http';
import { MimeType } from '@/constant/mime';
import type KiokeError from '@/util/error';
import type { Result } from 'neverthrow';

interface DeleteCollectionPathParams {
  collectionId: string;
}

interface DeleteCollectionParams {
  path: DeleteCollectionPathParams;
}

async function deleteCollection(
  params: DeleteCollectionParams,
): Promise<Result<void, KiokeError>> {
  return kioke<void>(`/collections/${params.path.collectionId}`, {
    method: HttpMethod.DELETE,
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  });
}

export default deleteCollection;
export type { DeleteCollectionParams };
