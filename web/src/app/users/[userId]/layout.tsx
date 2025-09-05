import BaseHeader from '@/components/header/BaseHeader';
import BaseLayout from '@/components/layout/BaseLayout';
import { getQueryClient } from '@/lib/query';
import { dashboardQueryOptions } from '@/query/dashboard';
import { handleError } from '@/util/error';
import { HydrationBoundary, dehydrate } from '@tanstack/react-query';

interface UserLayoutProps {
  children: React.ReactNode;
  params: Promise<{
    userId: string;
  }>;
}

export default async function UserLayout({
  children,
  params,
}: UserLayoutProps) {
  const { userId } = await params;

  const queryClient = getQueryClient();
  await queryClient
    .fetchQuery(
      dashboardQueryOptions({
        userId,
      }),
    )
    .catch((error) => handleError(error));

  return (
    <HydrationBoundary state={dehydrate(queryClient)}>
      <BaseLayout header={<BaseHeader selectedTab='none' />} main={children} />
    </HydrationBoundary>
  );
}
