import type { Metadata } from "next";
import { NextIntlClientProvider } from "next-intl";
import { getLocale, getMessages } from "next-intl/server";
import { ThemeProvider } from "@/components/providers/ThemeProvider";
import QueryProvider from "@/components/providers/QueryProvider";
import { StoreProvider } from "@/components/providers/StoreProvider";
import { SidebarProvider } from "@/components/ui/sidebar";
import {
  dehydrate,
  HydrationBoundary,
  QueryClient,
} from "@tanstack/react-query";
import { SessionProvider } from "@/components/providers/SessionProvider";

import "@/styles/globals.css";
import { auth } from "@/lib/auth";

export const metadata: Metadata = {
  title: "kioke",
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
          <ThemeProvider
            attribute="class"
            defaultTheme="system"
            enableSystem
            disableTransitionOnChange
          >
            <SessionProvider session={session}>
              <StoreProvider>
                <QueryProvider>
                  <SidebarProvider defaultOpen={false}>
                    <HydrationBoundary state={dehydrate(queryClient)}>
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
