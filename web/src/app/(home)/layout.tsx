import BaseLayout from '@/components/layout/BaseLayout';
import { getQueryClient } from '@/lib/query';
import { myProfileQueryOptions } from '@/query/profile';
import { handleError } from '@/util/error';
import { HydrationBoundary, dehydrate } from '@tanstack/react-query';

import './styles/react-grid-layout.css';

export default async function HomeLayout({
  header,
  main,
}: {
  header: React.ReactNode;
  main: React.ReactNode;
}) {
  const queryClient = getQueryClient();
  await queryClient
    .fetchQuery(myProfileQueryOptions())
    .catch((error) => handleError(error));

  return (
    <HydrationBoundary state={dehydrate(queryClient)}>
      <BaseLayout header={header} main={main} />
    </HydrationBoundary>
  );
}
