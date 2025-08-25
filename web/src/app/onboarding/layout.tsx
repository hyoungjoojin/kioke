import { handleServerSideKiokeError } from '@/constant/error';
import { Routes } from '@/constant/routes';
import { getQueryClient } from '@/lib/query';
import { myProfileQueryOptions } from '@/query/profile';
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
  try {
    const profile = await queryClient.fetchQuery(myProfileQueryOptions());

    if (profile.onboarded) {
      redirect(Routes.HOME);
    }
  } catch (error) {
    handleServerSideKiokeError(error);
  }

  return <>{children}</>;
}
