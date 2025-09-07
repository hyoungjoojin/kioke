import kioke from '@/app/api';
import { MimeType } from '@/constant/mime';
import type KiokeError from '@/util/error';
import type { Result } from 'neverthrow';

type GetImageResponse = string;

function url(imageId: string) {
  return `/image/${imageId}`;
}

export async function getImage(
  imageId: string,
): Promise<Result<GetImageResponse, KiokeError>> {
  return kioke<GetImageResponse>(url(imageId), {
    method: 'GET',
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  });
}
