import BaseLayout from '@/components/layout/BaseLayout';
import { getQueryClient } from '@/lib/query';
import { myProfileQueryOptions } from '@/query/profile';
import { handleError } from '@/util/error';
import { HydrationBoundary, dehydrate } from '@tanstack/react-query';

import './styles/react-grid-layout.css';

import { Routes } from '@/constant/routes';
import { redirect } from 'next/navigation';

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
    .then((profile) => {
      if (!profile.onboarded) {
        redirect(Routes.ONBOARDING);
      }
    })
    .catch((error) => handleError(error));

  return (
    <HydrationBoundary state={dehydrate(queryClient)}>
      <BaseLayout header={header} main={main} />
    </HydrationBoundary>
  );
}
