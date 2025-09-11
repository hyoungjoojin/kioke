import kioke from '@/app/api';
import type KiokeError from '@/util/error';
import type { Result } from 'neverthrow';

interface UploadImageRequest {
  pageId: string;
}

type UploadImageResponse = string;

function url() {
  return '/images';
}

export async function uploadImage(
  image: File,
  json: UploadImageRequest,
): Promise<Result<UploadImageResponse, KiokeError>> {
  const form = new FormData();

  form.append('image', image);

  const jsonBlob = new Blob([JSON.stringify(json)], {
    type: 'application/json',
  });
  form.append('json', jsonBlob);

  return kioke<UploadImageResponse>(url(), {
    method: 'PUT',
    body: form,
  });
}
