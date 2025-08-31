import kioke from '@/app/api';
import type { JournalType } from '@/constant/journal';
import { MimeType } from '@/constant/mime';
import { Role } from '@/constant/role';
import type { Journal } from '@/types/journal';
import type KiokeError from '@/util/error';
import type { Result } from 'neverthrow';

export interface CreateJournalRequest {
  collectionId: string;
  type: JournalType;
  title: string;
}

interface CreateJournalResponse {
  id: string;
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
      id: data.id,
      type: body.type,
      title: body.title,
      description: '',
      pages: [],
      isPublic: false,
      role: Role.AUTHOR,
      collaborators: [],
    })),
  );
}
