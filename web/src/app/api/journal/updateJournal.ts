import kioke from '@/app/api';
import type { JournalType } from '@/constant/journal';
import { MimeType } from '@/constant/mime';

interface UpdateJournalPathParams {
  id: string;
}

export interface UpdateJournalRequest {
  title?: string;
  type?: JournalType;
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
