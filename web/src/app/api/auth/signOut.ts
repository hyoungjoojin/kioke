import kioke from '@/app/api';
import { MimeType } from '@/constant/mime';

function url() {
  return '/auth/signout';
}

export async function signOut() {
  return kioke<void>(url(), {
    method: 'POST',
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  });
}
