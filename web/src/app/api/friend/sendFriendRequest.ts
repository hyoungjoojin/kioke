import kioke from '@/app/api';
import { MimeType } from '@/constant/mime';
import type KiokeError from '@/util/error';
import type { Result } from 'neverthrow';

export interface SendFriendRequest {
  userId: string;
}

function url() {
  return '/friends';
}

export async function sendFriendRequest(
  body: SendFriendRequest,
): Promise<Result<void, KiokeError>> {
  return kioke<void>(url(), {
    method: 'POST',
    body: JSON.stringify(body),
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  });
}
