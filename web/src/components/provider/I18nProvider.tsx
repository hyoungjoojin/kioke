import { NextIntlClientProvider } from "next-intl";

export default function I18nProvider({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return <NextIntlClientProvider>{children}</NextIntlClientProvider>;
}
