import kioke from '@/app/api';
import { MimeType } from '@/constant/mime';
import type { Theme } from '@/constant/theme';

export interface UpdatePreferencesRequest {
  theme: Theme;
}

function url() {
  return `/preferences`;
}

export async function updatePreferences(body: UpdatePreferencesRequest) {
  return kioke<void>(url(), {
    method: 'PATCH',
    body: JSON.stringify(body),
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  });
}
