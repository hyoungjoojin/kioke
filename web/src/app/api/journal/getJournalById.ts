import kioke from '@/app/api';
import { HttpMethod } from '@/constant/http';
import { MimeType } from '@/constant/mime';
import type { Role } from '@/constant/role';
import type { Journal } from '@/types/journal';
import type KiokeError from '@/util/error';
import type { Result } from 'neverthrow';

interface GetJournalByIdPathParams {
  journalId: string;
}

type GetJournalByIdResponse = {
  id: string;
  title: string;
  description: string;
  users: {
    userId: string;
    role: Role;
  }[];
  pages: {
    id: string;
    title: string;
    date: string;
  }[];
  isPublic: boolean;
};

interface GetJournalByIdParams {
  path: GetJournalByIdPathParams;
}

async function getJournalById(
  params: GetJournalByIdParams,
): Promise<Result<Journal, KiokeError>> {
  return await kioke<GetJournalByIdResponse>(
    `/journals/${params.path.journalId}`,
    {
      method: HttpMethod.GET,
      headers: {
        'Content-Type': MimeType.APPLICATION_JSON,
      },
    },
  ).then((data) =>
    data.map((data) => ({
      ...data,
      pages: data.pages.map((page) => ({ ...page, date: new Date(page.date) })),
    })),
  );
}

export default getJournalById;
export type { GetJournalByIdParams };
