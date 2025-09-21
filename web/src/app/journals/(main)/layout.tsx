import BaseHeader from '@/components/header/BaseHeader';
import BaseLayout from '@/components/layout/BaseLayout';
import { getQueryClient } from '@/lib/query';
import { myProfileQueryOptions } from '@/query/profile';
import { handleError } from '@/util/error';
import { HydrationBoundary, dehydrate } from '@tanstack/react-query';
import type { Metadata } from 'next';

export const metadata: Metadata = {
  applicationName: 'kioke',
  title: 'kioke - Journals',
};

export default async function JournalsLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  const queryClient = getQueryClient();

  await queryClient
    .fetchQuery(myProfileQueryOptions())
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
