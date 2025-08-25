import BaseHeader from '@/components/header/BaseHeader';
import BaseLayout from '@/components/layout/BaseLayout';
import { ErrorCode } from '@/constant/error';
import { getQueryClient } from '@/lib/query';
import { pageQueryOptions } from '@/query/page';
import { handleError } from '@/util/error';
import { HydrationBoundary, dehydrate } from '@tanstack/react-query';
import { notFound } from 'next/navigation';

export default async function Layout({
  main,
  params,
}: Readonly<{
  main: React.ReactNode;
  params: Promise<{ pageId: string }>;
}>) {
  const { pageId } = await params;

  const queryClient = getQueryClient();
  queryClient
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

  return (
    <HydrationBoundary state={dehydrate(queryClient)}>
      <BaseLayout header={<BaseHeader selectedTab='none' />} main={main} />;
    </HydrationBoundary>
  );
}
