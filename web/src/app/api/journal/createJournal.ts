import kioke from '@/app/api';
import type { JournalType } from '@/constant/journal';
import { MimeType } from '@/constant/mime';
import type { Role } from '@/constant/role';
import type { Journal } from '@/types/journal';
import type KiokeError from '@/util/error';
import type { Result } from 'neverthrow';

export interface CreateJournalRequest {
  type: JournalType;
  title: string;
}

interface CreateJournalResponse {
  journalId: string;
  creator: {
    userId: string;
    role: Role;
  };
}

function url() {
  return '/journals';
}

export async function createJournal(
  body: CreateJournalRequest,
): Promise<Result<Journal, KiokeError>> {
  return kioke<CreateJournalResponse>(url(), {
    method: 'POST',
    body: JSON.stringify(body),
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  }).then((response) =>
    response.map((data) => ({
      id: data.journalId,
      type: body.type,
      title: body.title,
      description: '',
      users: [data.creator],
      pages: [],
      isPublic: false,
    })),
  );
}
