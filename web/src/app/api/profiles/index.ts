import kioke from '@/app/api';
import { HttpMethod } from '@/constant/http';
import { MimeType } from '@/constant/mime';
import type { Profile } from '@/types/profile';
import type KiokeError from '@/util/error';
import type { Result } from 'neverthrow';

async function getMyProfile(): Promise<Result<Profile, KiokeError>> {
  return kioke<{ name: string; email: string; onboarded: boolean }>(
    '/profiles/me',
    {
      method: HttpMethod.GET,
      headers: {
        'Content-Type': MimeType.APPLICATION_JSON,
      },
    },
  );
}

async function getProfile(options: {
  id: string;
}): Promise<Result<Profile, KiokeError>> {
  return kioke<{ name: string; email: string }>(`/profiles/${options.id}`, {
    method: HttpMethod.GET,
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  });
}

interface UpdateProfileParams {
  name?: string;
  onboarded?: boolean;
}

async function updateProfile(
  body: UpdateProfileParams,
): Promise<Result<void, KiokeError>> {
  return kioke<void>('/profiles/me', {
    method: HttpMethod.PATCH,
    body: JSON.stringify(body),
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  });
}

export { getMyProfile, getProfile, updateProfile };
export type { UpdateProfileParams };
