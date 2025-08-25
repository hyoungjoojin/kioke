import BaseHeader from '@/components/header/BaseHeader';
import BaseLayout from '@/components/layout/BaseLayout';
import { getQueryClient } from '@/lib/query';
import { collectionsQueryOptions } from '@/query/collection';
import { handleError } from '@/util/error';
import { HydrationBoundary, dehydrate } from '@tanstack/react-query';

export default async function JournalsLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  const queryClient = getQueryClient();
  await queryClient
    .fetchQuery(collectionsQueryOptions())
    .catch((error) => handleError(error));

  return (
    <BaseLayout
      header={<BaseHeader selectedTab='journals' />}
      main={
        <HydrationBoundary state={dehydrate(queryClient)}>
          {children}
        </HydrationBoundary>
      }
    />
  );
}
