import kioke from '@/app/api';
import type KiokeError from '@/util/error';
import type { Result } from 'neverthrow';

type UploadImageResponse = string;

function url() {
  return '/image/upload';
}

export async function uploadImage(
  image: File,
): Promise<Result<UploadImageResponse, KiokeError>> {
  const form = new FormData();
  form.append('image', image);

  return kioke<UploadImageResponse>(url(), {
    method: 'PUT',
    body: form,
  });
}
