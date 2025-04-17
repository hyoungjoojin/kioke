import type { Metadata } from "next";
import { NextIntlClientProvider } from "next-intl";
import { getLocale, getMessages } from "next-intl/server";
import { ThemeProvider } from "@/components/providers/ThemeProvider";
import QueryProvider from "@/components/providers/QueryProvider";
import { StoreProvider } from "@/components/providers/StoreProvider";

import "@/styles/globals.css";
import "@/styles/calendar.css";

export const metadata: Metadata = {
  title: "kioke",
};

export default async function RootLayout({
  children,
  modal,
}: Readonly<{
  children: React.ReactNode;
  modal: React.ReactNode;
}>) {
  const locale = await getLocale();
  const messages = await getMessages();

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
            <StoreProvider>
              <QueryProvider>
                {children}
                {modal}
              </QueryProvider>
            </StoreProvider>
          </ThemeProvider>
        </NextIntlClientProvider>
      </body>
    </html>
  );
}
