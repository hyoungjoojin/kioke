import BaseHeader from '@/components/header/BaseHeader';
import BaseLayout from '@/components/layout/BaseLayout';
import { ErrorCode } from '@/constant/error';
import { JournalType } from '@/constant/journal';
import { getQueryClient } from '@/lib/query';
import { journalQueryOptions } from '@/query/journal';
import { pageQueryOptions } from '@/query/page';
import { handleError } from '@/util/error';
import { HydrationBoundary, dehydrate } from '@tanstack/react-query';
import dynamic from 'next/dynamic';
import { notFound } from 'next/navigation';

function main(type: JournalType) {
  switch (type) {
    case JournalType.BASIC:
      return dynamic(() => import('./components/BasicJournalPageEditor'));

    case JournalType.SHORT:
      return dynamic(() => import('./components/ShortJournalPageEditor'));
  }
}

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
    .fetchQuery(journalQueryOptions({ journalId: page.journalId }))
    .catch((error) => handleError(error));

  if (!journal) {
    return null;
  }

  const Main = main(journal.type);
  return (
    <HydrationBoundary state={dehydrate(queryClient)}>
      <BaseLayout
        header={<BaseHeader selectedTab='none' />}
        main={<Main pageId={pageId} />}
      />
    </HydrationBoundary>
  );
}
