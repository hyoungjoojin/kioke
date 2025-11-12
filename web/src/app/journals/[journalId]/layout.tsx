import BaseHeader from '@/components/header/BaseHeader';
import BaseLayout from '@/components/layout/BaseLayout';
import { ErrorCode } from '@/constant/error';
import { UseGetJournalByIdQueryDefaultOptions } from '@/hooks/query/useGetJournalByIdQuery';
import { getQueryClient } from '@/lib/query';
import { handleError } from '@/util/error';
import { HydrationBoundary, dehydrate } from '@tanstack/react-query';
import { notFound } from 'next/navigation';

export default async function JournalLayout({
  children,
  params,
}: Readonly<{
  children: React.ReactNode;
  params: Promise<{
    journalId: string;
  }>;
}>) {
  const { journalId } = await params;

  const queryClient = getQueryClient();

  await queryClient
    .fetchQuery(UseGetJournalByIdQueryDefaultOptions({ path: { journalId } }))
    .catch((error) =>
      handleError(error, {
        [ErrorCode.JOURNAL_NOT_FOUND]: () => {
          notFound();
        },
      }),
    );

  return (
    <BaseLayout
      header={<BaseHeader selectedTab='none' />}
      main={
        <HydrationBoundary state={dehydrate(queryClient)}>
          {children}
        </HydrationBoundary>
      }
    />
  );
}
