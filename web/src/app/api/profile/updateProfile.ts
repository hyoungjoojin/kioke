import kioke from '@/app/api';
import { MimeType } from '@/constant/mime';

export interface UpdateProfileRequest {
  name?: string;
  onboarded?: boolean;
}

function url() {
  return '/users/me';
}

export async function updateProfile(body: UpdateProfileRequest) {
  return kioke<void>(url(), {
    method: 'PATCH',
    body: JSON.stringify(body),
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  });
}
