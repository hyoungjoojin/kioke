import kioke from '@/app/api';
import type { KiokeError } from '@/constant/error';
import { MimeType } from '@/constant/mime';
import type { Collection } from '@/types/collection';
import type { Result } from 'neverthrow';

export interface CreateCollectionRequest {
  name: string;
}

interface CreateCollectionResponse {
  id: string;
}

function url() {
  return '/collections';
}

export async function createCollection(
  body: CreateCollectionRequest,
): Promise<Result<Collection, KiokeError>> {
  return kioke<CreateCollectionResponse>(url(), {
    method: 'POST',
    body: JSON.stringify(body),
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  }).then((response) =>
    response.map((data) => ({
      id: data.id,
      name: body.name,
      journals: [],
    })),
  );
}
