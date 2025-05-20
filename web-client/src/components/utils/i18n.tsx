'use client';

import {
  Select,
  SelectContent,
  SelectGroup,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from '@/components/ui/select';
import Icon, { IconName } from '@/components/utils/icon';
import { LOCALE_COOKIE_NAME } from '@/constants/cookie';
import { AVAILABLE_LOCALES } from '@/constants/locale';
import { setCookie } from 'cookies-next/client';
import { useRouter } from 'next/navigation';

export const SelectLanguage = () => {
  const router = useRouter();

  return (
    <div className='flex justify-center items-center'>
      <Icon name={IconName.LANGUAGE} className='mx-2' />

      <Select
        onValueChange={(locale) => {
          setCookie(LOCALE_COOKIE_NAME, locale);
          router.refresh();
        }}
      >
        <SelectTrigger className='h-[28px] w-[120px]'>
          <SelectValue placeholder='Language' />
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
