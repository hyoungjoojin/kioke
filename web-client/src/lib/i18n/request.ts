import { LOCALE_COOKIE_NAME } from "@/constants/cookie";
import { AVAILABLE_LOCALES, DEFAULT_LOCALE } from "@/constants/locale";
import { getRequestConfig } from "next-intl/server";
import { cookies } from "next/headers";

export default getRequestConfig(async () => {
  const localeCookieValue =
    (await cookies()).get(LOCALE_COOKIE_NAME)?.value || DEFAULT_LOCALE;

  const locale = Object.keys(AVAILABLE_LOCALES).includes(localeCookieValue)
    ? localeCookieValue
    : DEFAULT_LOCALE;

  return {
    locale,
    messages: (await import(`../../../public/locales/${locale}.json`)).default,
  };
});
