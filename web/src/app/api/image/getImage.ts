import kioke from '@/app/api';
import { MimeType } from '@/constant/mime';
import type KiokeError from '@/util/error';
import type { Result } from 'neverthrow';

type GetImageRequest = {
  context: 'PROFILE_IMAGE' | 'JOURNAL_COVER' | 'IMAGE_BLOCK';
  contextId: string;
  ids: string[];
};

type GetImageResponse = {
  imageId: string;
  url: string;
  width: number;
  height: number;
}[];

function url() {
  return '/images';
}

export async function getImage(
  request: GetImageRequest,
): Promise<Result<GetImageResponse, KiokeError>> {
  return kioke<GetImageResponse>(url(), {
    method: 'PUT',
    body: JSON.stringify(request),
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  });
}
