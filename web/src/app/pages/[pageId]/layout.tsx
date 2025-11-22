import Editor from './components/Editor';
import { PageOptions } from './page';
import BaseLayout from '@/components/layout/BaseLayout';
import { PageBreadcrumbs } from '@/components/ui/breadcrumbs';
import { ErrorCode } from '@/constant/error';
import { UseGetJournalByIdQueryDefaultOptions } from '@/hooks/query/useGetJournalByIdQuery';
import { getQueryClient } from '@/lib/query';
import { pageQueryOptions } from '@/query/page';
import { handleError } from '@/util/error';
import { HydrationBoundary, dehydrate } from '@tanstack/react-query';
import { notFound } from 'next/navigation';

export default async function Layout({
  params,
}: Readonly<{
  params: Promise<{ pageId: string }>;
}>) {
  const { pageId } = await params;

  const queryClient = getQueryClient();
  const page = await queryClient
    .fetchQuery(
      pageQueryOptions({
        id: pageId,
      }),
    )
    .catch((error) =>
      handleError(error, {
        [ErrorCode.PAGE_NOT_FOUND]: () => {
          notFound();
        },
      }),
    );

  if (!page) {
    return null;
  }

  const journal = await queryClient
    .fetchQuery(
      UseGetJournalByIdQueryDefaultOptions({
        path: { journalId: page.journalId },
      }),
    )
    .catch((error) => handleError(error));

  if (!journal) {
    return null;
  }

  return (
    <HydrationBoundary state={dehydrate(queryClient)}>
      <BaseLayout
        topRight={<PageOptions />}
        topLeft={<PageBreadcrumbs journalId={page.journalId} pageId={pageId} />}
      >
        <Editor pageId={pageId} />
      </BaseLayout>
    </HydrationBoundary>
  );
}
