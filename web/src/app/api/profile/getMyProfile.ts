import kioke from '@/app/api';
import { MimeType } from '@/constant/mime';
import type { MyProfile } from '@/types/profile';
import type KiokeError from '@/util/error';
import type { Result } from 'neverthrow';

interface GetMyProfileResponse {
  userId: string;
  email: string;
  name: string;
  onboarded: boolean;
  createdAt: Date;
}

function url() {
  return '/users/me';
}

export async function getMyProfile(): Promise<Result<MyProfile, KiokeError>> {
  return kioke<GetMyProfileResponse>(url(), {
    method: 'GET',
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  });
}
