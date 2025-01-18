"use client";

import { CiGlobe } from "react-icons/ci";
import {
  Select,
  SelectContent,
  SelectGroup,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { AVAILABLE_LOCALES } from "@/constants/locale";
import { useRouter } from "next/navigation";
import { setCookie } from "cookies-next/client";
import { LOCALE_COOKIE_NAME } from "@/constants/cookie";

export const SelectLanguage = () => {
  const router = useRouter();

  return (
    <div className="flex justify-center items-center">
      <CiGlobe size={24} className="mx-2" />

      <Select
        onValueChange={(locale) => {
          setCookie(LOCALE_COOKIE_NAME, locale);
          router.refresh();
        }}
      >
        <SelectTrigger className="h-[28px] w-[120px]">
          <SelectValue placeholder="Language" />
        </SelectTrigger>
        <SelectContent>
          <SelectGroup>
            {Object.entries(AVAILABLE_LOCALES).map(([code, language]) => (
              <SelectItem key={code} value={code}>
                {language}
              </SelectItem>
            ))}
          </SelectGroup>
        </SelectContent>
      </Select>
    </div>
  );
};
