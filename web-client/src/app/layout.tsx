import Modal from '@/components/modal';
import QueryProvider from '@/components/providers/QueryProvider';
import { SessionProvider } from '@/components/providers/SessionProvider';
import StoreProvider from '@/components/providers/StoreProvider';
import { ThemeProvider } from '@/components/providers/ThemeProvider';
import { SidebarProvider } from '@/components/ui/sidebar';
import { auth } from '@/lib/auth';
import '@/styles/globals.css';
import {
  HydrationBoundary,
  QueryClient,
  dehydrate,
} from '@tanstack/react-query';
import type { Metadata } from 'next';
import { NextIntlClientProvider } from 'next-intl';
import { getLocale, getMessages } from 'next-intl/server';

export const metadata: Metadata = {
  title: 'kioke',
};

export default async function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  const session = await auth();

  const locale = await getLocale();
  const messages = await getMessages();
  const queryClient = new QueryClient();

  return (
    <html lang={locale} suppressHydrationWarning>
      <body>
        <NextIntlClientProvider messages={messages}>
          <ThemeProvider>
            <SessionProvider session={session}>
              <StoreProvider>
                <QueryProvider>
                  <SidebarProvider defaultOpen={false}>
                    <HydrationBoundary state={dehydrate(queryClient)}>
                      <Modal />
                      {children}
                    </HydrationBoundary>
                  </SidebarProvider>
                </QueryProvider>
              </StoreProvider>
            </SessionProvider>
          </ThemeProvider>
        </NextIntlClientProvider>
      </body>
    </html>
  );
}
