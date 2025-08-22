import kioke from '@/app/api';
import type { KiokeError } from '@/constant/error';
import { MimeType } from '@/constant/mime';
import type { Page } from '@/types/page';
import type { Result } from 'neverthrow';

export interface CreatePageRequest {
  journalId: string;
  title: string;
  date: Date;
}

interface CreatePageResponse {
  pageId: string;
}

function url() {
  return '/pages';
}

export async function createPage(
  body: CreatePageRequest,
): Promise<Result<Page, KiokeError>> {
  return kioke<CreatePageResponse>(url(), {
    method: 'POST',
    body: JSON.stringify(body),
    headers: {
      'Content-Type': MimeType.APPLICATION_JSON,
    },
  }).then((response) =>
    response.map((data) => ({
      pageId: data.pageId,
      journalId: body.journalId,
      title: body.title,
      content: '',
      date: body.date,
    })),
  );
}
