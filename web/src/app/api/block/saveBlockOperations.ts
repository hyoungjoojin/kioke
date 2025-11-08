import { MimeType } from '@/constant/mime';
import type { BlockOperation } from '@/types/page';
import env from '@/util/env';

const BACKEND_URL = env.NEXT_PUBLIC_BACKEND_URL;

function url() {
  return `/blocks`;
}

interface SaveBlockOperationsResponse {
  conversions: { before: string; after: string }[];
}

async function saveBlockOperations(
  ops: BlockOperation[],
): Promise<SaveBlockOperationsResponse> {
  return fetch(`${BACKEND_URL}/${url()}`, {
    method: 'PATCH',
    body: JSON.stringify(ops),
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  }).then(async (response) => response.json());
}

export { saveBlockOperations };
