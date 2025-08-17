import kioke from '@/app/api';
import { MimeType } from '@/constant/mime';

interface SignUpWithCredentialsRequestBody {
  email: string;
  password: string;
}

interface SignUpWithCredentialsResponseBody {
  userId: string;
}

function url() {
  return '/auth/signup';
}

export async function signUpWithCredentials(
  requestBody: SignUpWithCredentialsRequestBody,
) {
  return kioke<SignUpWithCredentialsResponseBody>(url(), {
    method: 'POST',
    body: JSON.stringify(requestBody),
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  });
}
