import { Card } from '@/components/ui/card';
import { Routes } from '@/constant/routes';
import { getQueryClient } from '@/lib/query';
import { cn } from '@/lib/utils';
import { myProfileQueryOptions } from '@/query/profile';
import { handleError } from '@/util/error';
import type { Metadata } from 'next';
import { redirect } from 'next/navigation';

export const metadata: Metadata = {
  applicationName: 'kioke',
  title: 'kioke - Onboarding',
};

export default async function OnboardingLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  const queryClient = getQueryClient();

  await queryClient
    .fetchQuery(myProfileQueryOptions())
    .then((profile) => {
      if (profile.onboarded) {
        redirect(Routes.HOME);
      }
    })
    .catch((error) => handleError(error));

  return (
    <main className={cn('flex flex-row h-dvh justify-center items-center')}>
      <Card className='w-[32rem]'>{children}</Card>
    </main>
  );
}
