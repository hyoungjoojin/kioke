import kioke from '@/app/api';

export interface UpdateMyProfileRequestBody {
  json: {
    name?: string;
    isOnboarded?: boolean;
  };
  profileImage?: File;
}

function url() {
  return '/users/me';
}

export async function updateMyProfile(requestBody: UpdateMyProfileRequestBody) {
  const formData = new FormData();
  formData.append(
    'a',
    new Blob([JSON.stringify(requestBody.json)], {
      type: 'application/json',
    }),
  );

  if (requestBody.profileImage) {
    formData.append('b', requestBody.profileImage);
  }

  return kioke<void>(url(), {
    method: 'PUT',
    body: formData,
  });
}
