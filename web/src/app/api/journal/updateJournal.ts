import kioke from '@/app/api';
import { MimeType } from '@/constant/mime';

interface UpdateJournalPathParams {
  journalId: string;
}

export interface UpdateJournalRequestBody {
  title?: string;
  description?: string;
}

function url({ journalId }: UpdateJournalPathParams) {
  return `/journals/${journalId}`;
}

export async function updateJournal(
  pathParams: UpdateJournalPathParams,
  requestBody: UpdateJournalRequestBody,
) {
  return kioke<void>(url(pathParams), {
    method: 'PUT',
    body: JSON.stringify(requestBody),
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  });
}
