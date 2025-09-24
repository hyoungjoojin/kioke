import kioke from '@/app/api';
import { MimeType } from '@/constant/mime';

interface UpdateJournalPathParams {
  id: string;
}

export interface UpdateJournalRequest {
  title?: string;
  description?: string;
  coverImage?: string;
}

function url({ id }: UpdateJournalPathParams) {
  return `/journals/${id}`;
}

export async function updateJournal(
  pathParams: UpdateJournalPathParams,
  body: UpdateJournalRequest,
) {
  return kioke<void>(url(pathParams), {
    method: 'PATCH',
    body: JSON.stringify(body),
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  });
}
