import kioke from '@/app/api';
import { MimeType } from '@/constant/mime';
import type KiokeError from '@/util/error';
import type { Result } from 'neverthrow';

interface GetImageQuery {
  pageId: string;
}

type GetImageResponse = string;

function url(imageId: string, pageId: string) {
  return `/images/${imageId}?pageId=${pageId}`;
}

export async function getImage(
  imageId: string,
  query: GetImageQuery,
): Promise<Result<GetImageResponse, KiokeError>> {
  return kioke<GetImageResponse>(url(imageId, query.pageId), {
    method: 'GET',
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  });
}
