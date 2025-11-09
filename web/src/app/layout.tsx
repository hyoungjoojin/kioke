import type { Metadata } from 'next';

import '@/styles/globals.css';
import '@/styles/animations.css';

import Modal from '@/components/modal/Modal';
import RootProvider from '@/components/provider/RootProvider';
import { Toaster } from '@/components/ui/sonner';
import { getLocale } from 'next-intl/server';
import { Domine } from 'next/font/google';

export const metadata: Metadata = {
  title: 'kioke',
  description: '',
};

const geist = Domine({
  subsets: ['latin'],
});

export default async function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  const locale = await getLocale();

  return (
    <html lang={locale} className={geist.className} suppressHydrationWarning>
      <head></head>
      <body className='h-dvh'>
        <RootProvider>
          {children}
          <Modal />
          <Toaster />
        </RootProvider>
      </body>
    </html>
  );
}
