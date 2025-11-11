import kioke from '@/app/api';
import { HttpMethod } from '@/constant/http';
import { MimeType } from '@/constant/mime';
import type { Collection } from '@/types/collection';
import type KiokeError from '@/util/error';
import type { Result } from 'neverthrow';

interface CreateCollectionRequestBody {
  name: string;
}

type CreateCollectionResponse = Collection;

interface CreateCollectionParams {
  body: CreateCollectionRequestBody;
}

async function createCollection(
  params: CreateCollectionParams,
): Promise<Result<Collection, KiokeError>> {
  return kioke<CreateCollectionResponse>('/collections', {
    method: HttpMethod.POST,
    body: JSON.stringify(params.body),
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  });
}

export default createCollection;
export type { CreateCollectionParams };
