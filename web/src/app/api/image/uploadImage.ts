import kioke from '@/app/api';
import { MimeType } from '@/constant/mime';
import type KiokeError from '@/util/error';
import type { Result } from 'neverthrow';

interface UploadImageRequest {
  name: string;
  contentLength: number;
  contentType: string;
}

type UploadImageResponse = {
  imageId: string;
  signedPostUrl: string;
};

function url() {
  return '/images';
}

export async function uploadImage(
  body: UploadImageRequest,
): Promise<Result<UploadImageResponse, KiokeError>> {
  return kioke<UploadImageResponse>(url(), {
    method: 'POST',
    body: JSON.stringify(body),
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  });
}
