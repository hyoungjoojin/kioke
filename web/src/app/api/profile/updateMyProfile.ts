import kioke from '@/app/api';

export interface UpdateMyProfileRequestBody {
  name?: string;
  onboarded?: boolean;
}

function url() {
  return '/users/me';
}

export async function updateMyProfile(requestBody: UpdateMyProfileRequestBody) {
  return kioke<void>(url(), {
    method: 'PUT',
    body: JSON.stringify(requestBody),
  });
}
