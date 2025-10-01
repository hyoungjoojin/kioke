import kioke from '@/app/api';
import { MimeType } from '@/constant/mime';
import type KiokeError from '@/util/error';
import type { Result } from 'neverthrow';

interface UploadImageRequest {
  name: string;
  contentType: string;
  contentLength: number;
  width: number;
  height: number;
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
