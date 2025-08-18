import kioke from '@/app/api';
import type { KiokeError } from '@/constant/error';
import { MimeType } from '@/constant/mime';
import type { MyProfile } from '@/types/profile';
import type { Result } from 'neverthrow';

interface GetMyProfileResponseBody {
  email: string;
  name: string;
  onboarded: boolean;
  createdAt: Date;
}

function url() {
  return '/users/me';
}

export async function getMyProfile(): Promise<Result<MyProfile, KiokeError>> {
  return kioke<GetMyProfileResponseBody>(url(), {
    method: 'GET',
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  });
}
