import { HomeTopLeft, HomeTopRight } from './page';
import { getMyProfileQueryOptions } from '@/app/api/profiles/query';
import BaseLayout from '@/components/layout/BaseLayout';
import { Routes } from '@/constant/routes';
import { getQueryClient } from '@/lib/query';
import { handleError } from '@/util/error';
import { HydrationBoundary, dehydrate } from '@tanstack/react-query';
import { redirect } from 'next/navigation';

interface HomeLayoutProps {
  children: React.ReactNode;
}

async function HomeLayout({ children }: HomeLayoutProps) {
  const queryClient = getQueryClient();
  await queryClient
    .fetchQuery(getMyProfileQueryOptions)
    .then((profile) => {
      if (!profile.onboarded) {
        redirect(Routes.ONBOARDING);
      }
    })
    .catch((error) => handleError(error));

  return (
    <HydrationBoundary state={dehydrate(queryClient)}>
      <BaseLayout topLeft={<HomeTopLeft />} topRight={<HomeTopRight />}>
        {children}
      </BaseLayout>
    </HydrationBoundary>
  );
}

export default HomeLayout;
