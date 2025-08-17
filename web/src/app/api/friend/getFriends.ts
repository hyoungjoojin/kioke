import kioke from '@/app/api';
import { MimeType } from '@/constant/mime';

interface GetFriendsResponseBody {
  friends: {
    userId: string;
  }[];
}

function url() {
  return '/friends';
}

export async function getFriends() {
  return kioke<GetFriendsResponseBody>(url(), {
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  });
}
