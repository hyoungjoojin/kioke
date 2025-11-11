import kioke from '@/app/api';
import { HttpMethod } from '@/constant/http';
import { MimeType } from '@/constant/mime';

interface UpdateJournalRequestBody {
  title?: string;
  description?: string;
}

interface UpdateJournalPathParams {
  journalId: string;
}

interface UpdateJournalParams {
  path: UpdateJournalPathParams;
  body: UpdateJournalRequestBody;
}

async function updateJournal(params: UpdateJournalParams) {
  return kioke<void>(`/journals/${params.path.journalId}`, {
    method: HttpMethod.PATCH,
    body: JSON.stringify(params.body),
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  });
}

export default updateJournal;
export type { UpdateJournalParams };
