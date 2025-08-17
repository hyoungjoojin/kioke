import kioke from '@/app/api';
import { MimeType } from '@/constant/mime';

interface SignInWithCredentialsRequestBody {
  email: string;
  password: string;
}

function url() {
  return '/auth/signin';
}

export async function signInWithCredentials(
  requestBody: SignInWithCredentialsRequestBody,
) {
  return kioke<void>(url(), {
    method: 'POST',
    body: JSON.stringify(requestBody),
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  });
}
