import kioke from '@/app/api';
import type { JournalType } from '@/constant/journal';
import { MimeType } from '@/constant/mime';
import type { Role } from '@/constant/role';
import type { Journal } from '@/types/journal';
import type KiokeError from '@/util/error';
import type { Result } from 'neverthrow';

interface GetJournalPathParams {
  journalId: string;
}

export interface GetJournalResponse {
  id: string;
  coverUrl?: string;
  type: JournalType;
  title: string;
  description: string;
  users: {
    userId: string;
    role: Role;
  }[];
  pages: {
    id: string;
    title: string;
    date: Date;
  }[];
  isPublic: boolean;
}

function url({ journalId }: GetJournalPathParams) {
  return `/journals/${journalId}`;
}

export async function getJournal(
  pathParams: GetJournalPathParams,
): Promise<Result<Journal, KiokeError>> {
  return kioke<GetJournalResponse>(url(pathParams), {
    method: 'GET',
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  });
}
