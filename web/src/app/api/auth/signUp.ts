import kioke from '@/app/api';
import { MimeType } from '@/constant/mime';

interface SignUpRequest {
  email: string;
  password: string;
}

function url() {
  return '/auth/signup';
}

export async function signUp(body: SignUpRequest) {
  return kioke<void>(url(), {
    method: 'POST',
    body: JSON.stringify(body),
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  });
}
