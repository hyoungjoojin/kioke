import kioke from '@/app/api';
import { HttpMethod } from '@/constant/http';
import { MimeType } from '@/constant/mime';
import type { Collection } from '@/types/collection';
import type KiokeError from '@/util/error';
import type { Result } from 'neverthrow';

interface GetCollectionByIdPathParams {
  collectionId: string;
}

type GetCollectionByIdResponse = Collection;

interface GetCollectionByIdParams {
  path: GetCollectionByIdPathParams;
}

async function getCollectionById(
  params: GetCollectionByIdParams,
): Promise<Result<Collection, KiokeError>> {
  return kioke<GetCollectionByIdResponse>(
    `/collections/${params.path.collectionId}`,
    {
      method: HttpMethod.GET,
      headers: {
        'Content-Type': MimeType.APPLICATION_JSON,
      },
    },
  );
}

export default getCollectionById;
export type { GetCollectionByIdParams };
