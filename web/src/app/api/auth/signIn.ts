import kioke from '@/app/api';
import { MimeType } from '@/constant/mime';

interface SignInRequest {
  email: string;
  password: string;
}

function url() {
  return '/auth/signin';
}

export async function signIn(body: SignInRequest) {
  return kioke<void>(url(), {
    method: 'POST',
    body: JSON.stringify(body),
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  });
}
