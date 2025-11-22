import kioke from '@/app/api';
import { HttpMethod } from '@/constant/http';
import { MimeType } from '@/constant/mime';
import type { Collection } from '@/types/collection';
import type KiokeError from '@/util/error';
import type { Result } from 'neverthrow';

type GetCollectionsResponse = Collection[];

async function getCollections(): Promise<Result<Collection[], KiokeError>> {
  return kioke<GetCollectionsResponse>('/collections?size=50', {
    method: HttpMethod.GET,
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  });
}

export default getCollections;
