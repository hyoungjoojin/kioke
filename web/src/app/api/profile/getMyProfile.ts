import kioke from '@/app/api';
import type { KiokeError } from '@/constant/error';
import { MimeType } from '@/constant/mime';
import type { Profile } from '@/types/profile';
import type { Result } from 'neverthrow';

interface GetMyProfileResponseBody {
  isOnboarded: boolean;
  email: string;
  name: string;
  profileImage: string;
}

function url() {
  return '/users/me';
}

export async function getMyProfile(): Promise<Result<Profile, KiokeError>> {
  return kioke<GetMyProfileResponseBody>(url(), {
    method: 'GET',
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  });
}
