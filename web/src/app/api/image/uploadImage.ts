import kioke from '@/app/api';
import { MimeType } from '@/constant/mime';
import type KiokeError from '@/util/error';
import type { Result } from 'neverthrow';

interface UploadImageRequest {
  file: File;
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

export async function uploadImage({
  file,
  width,
  height,
}: UploadImageRequest): Promise<Result<{ id: string }, KiokeError>> {
  return kioke<UploadImageResponse>(url(), {
    method: 'POST',
    body: JSON.stringify({
      name: file.name,
      contentType: file.type,
      contentLength: file.size,
      width,
      height,
    }),
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  }).then((response) =>
    response.asyncMap(async (data) => {
      await fetch(data.signedPostUrl, {
        method: 'PUT',
        body: file,
        headers: {
          'Content-Type': file.type,
        },
      });
      return { id: data.imageId };
    }),
  );
}
