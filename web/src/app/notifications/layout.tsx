import BaseHeader from '@/components/header/BaseHeader';
import BaseLayout from '@/components/layout/BaseLayout';
import { getQueryClient } from '@/lib/query';
import { notificationsQueryOptions } from '@/query/notification/getNotifications';
import { myProfileQueryOptions } from '@/query/profile';
import { handleError } from '@/util/error';
import { HydrationBoundary, dehydrate } from '@tanstack/react-query';
import type { Metadata } from 'next';

export const metadata: Metadata = {
  title: 'kioke - Notifications',
};

interface LayoutProps {
  children: React.ReactNode;
}

export default async function Layout({ children }: Readonly<LayoutProps>) {
  const queryClient = getQueryClient();
  await queryClient
    .fetchQuery(myProfileQueryOptions())
    .catch((error) => handleError(error));

  queryClient.prefetchQuery(notificationsQueryOptions());

  return (
    <HydrationBoundary state={dehydrate(queryClient)}>
      <BaseLayout header={<BaseHeader selectedTab='none' />} main={children} />
    </HydrationBoundary>
  );
}
